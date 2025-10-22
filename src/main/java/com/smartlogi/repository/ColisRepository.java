package com.smartlogi.repository;

import com.smartlogi.entity.Colis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColisRepository extends JpaRepository<Colis, Long> {

    // Ceci est la seule méthode personnalisée dont nous avons besoin
    // pour la User Story "Lister tous les colis assignés à un livreur".
    // Spring va la comprendre et générer le SQL pour nous.
    List<Colis> findByLivreurId(Long livreurId);
}