package com.smartlogi.repository;

import com.smartlogi.entity.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indique à Spring que c'est un bean de la couche "données"
public interface LivreurRepository extends JpaRepository<Livreur, Long> {
    // C'est tout !
    // Spring Data JPA va implémenter automatiquement :
    // save(), findById(), findAll(), deleteById(), etc.
}