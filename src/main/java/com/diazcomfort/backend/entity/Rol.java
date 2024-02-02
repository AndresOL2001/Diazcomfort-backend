package com.diazcomfort.backend.entity;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "diazcomfort")
public class Rol {
    public Rol(UUID rol) {
        this.IdRol = rol;
    }

    @Id
    private UUID IdRol;
    
    private String name;

    @OneToMany(mappedBy = "rol",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> usuarios = new HashSet<>();
}
