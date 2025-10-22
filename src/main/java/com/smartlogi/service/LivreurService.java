package com.smartlogi.service;

import com.smartlogi.entity.Livreur;
import java.util.List;
import java.util.Optional;

public interface LivreurService {
    Livreur saveLivreur(Livreur livreur);
    Optional<Livreur> findLivreurById(Long id);
    List<Livreur> findAllLivreurs();
    void deleteLivreur(Long id);

}