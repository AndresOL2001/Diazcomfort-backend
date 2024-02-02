package com.diazcomfort.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diazcomfort.backend.DTOs.UsuarioDetallesDTO;
import com.diazcomfort.backend.entity.User;


@Service
public class UsuarioDetalleService implements UserDetailsService{

    private final UsuarioService userService;
    
    @Autowired
    public UsuarioDetalleService(UsuarioService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        Optional<User> user = userService.obtenerUsuarioPorCorreo(userName);
        if(user.isEmpty()){
            throw new RuntimeException("Error no existe ningún usuario con el correo: "+userName);
        }
        return UsuarioDetallesDTO.build(user.get());
    }
}