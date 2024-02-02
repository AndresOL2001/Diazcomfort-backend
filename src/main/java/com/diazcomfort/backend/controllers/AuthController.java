package com.diazcomfort.backend.controllers;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diazcomfort.backend.DTOs.MessageDTO;
import com.diazcomfort.backend.DTOs.JwtDTO;
import com.diazcomfort.backend.DTOs.LoginUserDTO;
import com.diazcomfort.backend.DTOs.RegistroUsuarioDTO;
import com.diazcomfort.backend.security.JWT.*;
import com.diazcomfort.backend.entity.User;
import com.diazcomfort.backend.services.UsuarioService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, JwtProvider jwtProvider,
            UsuarioService usuarioService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtProvider = jwtProvider;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginUserDTO loginUser, BindingResult bidBindingResult) {
        // Comprobar Cuenta Activada
        JwtDTO jwtDto;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginUser.getEmail(), loginUser.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            jwtDto = new JwtDTO(jwt);
        } catch (Exception e) {
            MessageDTO eMessage = new MessageDTO();
            eMessage.setMessage(e.getMessage());
            eMessage.setStatus("BAD_REQUEST");
            return new ResponseEntity<>(eMessage, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }

    @PostMapping("/registro")
    public ResponseEntity<Object> registro(@Valid @RequestBody RegistroUsuarioDTO registroUsuarioDTO, BindingResult bidBindingResult) {

        try {
            User usuarioResponse = usuarioService.guardarUsuario(registroUsuarioDTO);
            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            MessageDTO errorMessage = new MessageDTO(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

     @GetMapping("/{correo}")
    public ResponseEntity<Object> getUserById(@PathVariable String correo) {

        try {
            Optional<User> usuarioResponse = usuarioService.obtenerUsuarioPorCorreo(correo);
            return ResponseEntity.ok(usuarioResponse.get());
        } catch (Exception e) {
            MessageDTO errorMessage = new MessageDTO(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

  
}
