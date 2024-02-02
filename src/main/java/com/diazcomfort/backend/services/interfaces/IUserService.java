package com.diazcomfort.backend.services.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.diazcomfort.backend.DTOs.RegistroUsuarioDTO;
import com.diazcomfort.backend.entity.User;

public interface IUserService {
    public Optional<User> obtenerUsuarioPorCorreo(String correo);

    public List<User> obtenerUsuarios();

    public List<User> obtenerUsuarioPorTermino(String termino);

    public User editarUsuario(RegistroUsuarioDTO usuario, UUID id);

    public void eliminarUsuario(UUID id);

}
