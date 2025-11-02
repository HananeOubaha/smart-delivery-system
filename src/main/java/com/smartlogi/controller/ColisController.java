package com.smartlogi.controller;

import com.smartlogi.entity.Colis;
import com.smartlogi.enums.StatutColis;
import com.smartlogi.service.ColisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/colis") // Adresse de base pour les colis
public class ColisController {

    private final ColisService colisService;

    @Autowired
    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    // --- CREATE (Enregistrer et Assigner un colis) ---
    // On passe le 'livreurId' en tant que paramètre dans l'URL
    // Ex: POST /api/colis?livreurId=1
    @PostMapping
    public Colis createAndAssignColis(@RequestBody Colis colis,
                                      @RequestParam Long livreurId) {
        // Le @RequestBody prend le JSON du colis
        // Le @RequestParam prend le 'livreurId' de l'URL
        return colisService.saveColisAndAssign(colis, livreurId);
    }

    // --- UPDATE (Mettre à jour le STATUT d'un colis) ---
    // C'est une User Story spécifique !
    // Ex: PUT /api/colis/4/statut
    @PutMapping("/{id}/statut")
    public Colis updateStatut(@PathVariable Long id,
                              @RequestBody StatutColis statut) {
        // Le @RequestBody prend juste le nouveau statut, ex: "EN_TRANSIT"
        return colisService.updateColisStatus(id, statut);
    }

    // --- DELETE (Supprimer un colis) ---
    @DeleteMapping("/{id}")
    public String deleteColis(@PathVariable Long id) {
        colisService.deleteColis(id);
        return "Colis supprimé avec succès (ID: " + id + ")";
    }
}