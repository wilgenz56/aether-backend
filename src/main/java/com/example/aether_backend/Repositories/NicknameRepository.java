package com.example.aether_backend.Repositories; // Usa il tuo package

import com.example.aether_backend.Entities.Nickname;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NicknameRepository extends JpaRepository<Nickname, Long> {
    
    // Peschiamo l'intera riga del database dove in_uso è falso
    @Query(value = "SELECT * FROM nickname WHERE usato = 0 ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Nickname trovaNomeLiberoCasuale();

    // Ci serve per ritrovare l'oggetto conoscendo solo il testo del nome
    Optional<Nickname> findByNome(String nome);

}