package com.diazcomfort.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "diazcomfort",name = "users")
public class User {
    @Id
    private UUID idUser;
    private String email;
    @JsonIgnore
    private String password;
    @ManyToOne(fetch = FetchType.EAGER) // Si eliminamos el siniestro todas sus documentos igual se eliminan
    @JoinColumn(name = "id_rol")
    @JsonIgnore
    private Rol rol;
    private String name;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PMCheck> checks = new HashSet<>();

    public User(UUID idUser) {
        this.idUser = idUser;
    }
}
