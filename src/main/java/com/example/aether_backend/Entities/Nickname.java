package com.example.aether_backend.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nickname {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // L'ID si autoincrementa (1, 2, 3...)
    private Long id;
    
    private String nome;

    private boolean usato; // Per tenere traccia se il nickname è già stato assegnato o no
}