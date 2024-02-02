package com.diazcomfort.backend.DTOs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.diazcomfort.backend.entity.User;


public class UsuarioDetallesDTO implements UserDetails{

    private String nombreUsuario;
    private String contraseña;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioDetallesDTO(String userName, String password,
    Collection<? extends GrantedAuthority> authorities) {
        this.nombreUsuario = userName;
        this.contraseña = password;
        this.authorities = authorities;
        }

    public static UsuarioDetallesDTO build(User usuario) {
        List<GrantedAuthority> authorities= new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol().getName()));
        return new UsuarioDetallesDTO(usuario.getEmail(), usuario.getPassword(), authorities);
      }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return contraseña;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
