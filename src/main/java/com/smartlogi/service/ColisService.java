package com.smartlogi.service;

import com.smartlogi.entity.Colis;
import com.smartlogi.enums.StatutColis;
import java.util.List;

public interface ColisService {
    // US: "Enregistrer un colis et l’assigner à un livreur"
    Colis saveColisAndAssign(Colis colis, Long livreurId);

    // US: "Mettre à jour le statut d’un colis"
    Colis updateColisStatus(Long colisId, StatutColis statut);

    // US: "Lister tous les colis assignés à un livreur"
    List<Colis> findColisByLivreurId(Long livreurId);

    // US: "Supprimer ou corriger une information erronée"
    void deleteColis(Long colisId);
    Colis updateColis(Colis colis);
}