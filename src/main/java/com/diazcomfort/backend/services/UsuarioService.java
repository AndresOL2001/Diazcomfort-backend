package com.diazcomfort.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.diazcomfort.backend.DTOs.RegistroUsuarioDTO;
import com.diazcomfort.backend.entity.User;
import com.diazcomfort.backend.helpers.Mapper;
import com.diazcomfort.backend.repository.UserRepository;
import com.diazcomfort.backend.services.interfaces.IUserService;

@Service
public class UsuarioService implements IUserService{

    private final UserRepository usuarioRepository;
    private final Mapper mapper;
    public UsuarioService(UserRepository usuarioRepository,Mapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    public Optional<User> obtenerUsuarioPorCorreo(String correo) {
        Optional<User> usuario = usuarioRepository.findByEmailIgnoreCase(correo);
        if(usuario.isPresent()){
            return Optional.of(usuario.get());
        }else{
            return Optional.empty();
        }
    }

    public User guardarUsuario(RegistroUsuarioDTO usuario){
        return usuarioRepository.save(mapper.loginUserDTOtoUser(usuario));
    }

    @Override
    public List<User> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<User> obtenerUsuarioPorTermino(String termino) {
       return usuarioRepository.findByNameContainingIgnoreCase(termino);
    }

    @Override
    public User editarUsuario(RegistroUsuarioDTO usuario, UUID id) {
       Optional<User> usuarioBD = usuarioRepository.findById(id);
        if(usuarioBD.isEmpty()){
            throw new RuntimeException("Error: No existe ningún usuario asociado a ese id");
        }else{
            usuarioBD.get().setEmail(usuario.getEmail());
            usuarioBD.get().setName(usuario.getName());
            usuarioBD.get().setPassword(usuario.getPassword());
           return usuarioRepository.save(usuarioBD.get());
        }
    }

    @Override
    public void eliminarUsuario(UUID id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar usuario: "+e.getMessage());
            // TODO: handle exception
        }
    }
}
