package com.diazcomfort.backend.entity;

import java.time.LocalDateTime;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class PMCheck {

    @Id
    public UUID PMCheckId;

    public String customer;

    @ManyToOne(fetch=FetchType.EAGER)//Si eliminamos el siniestro todas sus documentos igual se eliminan
    @JoinColumn(name = "id_usuario")
    public User usuario;

    @CreationTimestamp
    public LocalDateTime createdAt;

    @UpdateTimestamp
    public LocalDateTime  updatedAt;

    private String inspectionFee;

    private String recomendationTotalPrice;

    private String address;

    @OneToMany(mappedBy = "pmcheck", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PMForm> pmforms = new HashSet<>();

    public PMCheck(UUID id) {
        this.PMCheckId = id;
    }
}
