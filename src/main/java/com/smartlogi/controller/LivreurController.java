package com.smartlogi.controller;

import com.smartlogi.service.LivreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.smartlogi.entity.Livreur;
import com.smartlogi.entity.Colis;
import com.smartlogi.service.ColisService;
import java.util.List;

@RestController // 1. Dit à Spring que c'est un contrôleur d'API REST
@RequestMapping("/api/livreurs") // 2. Dit que toutes les méthodes ici commenceront par /api/livreurs
public class LivreurController {

    // 3. On injecte notre couche métier (que le applicationContext.xml a déjà créée)
    private final LivreurService livreurService;
    private final ColisService colisService;

    @Autowired
    public LivreurController(LivreurService livreurService, ColisService colisService) {
        this.livreurService = livreurService;
        this.colisService = colisService;
    }
    // --- CREATE (Créer un nouveau livreur) ---
    @PostMapping
    public Livreur createLivreur(@RequestBody Livreur livreur) {
        // 1. @RequestBody convertit le JSON entrant en objet Livreur
        // 2. On appelle notre service, qui fait le vrai travail
        return livreurService.saveLivreur(livreur);
    }
    // --- READ (Lire tous les livreurs) ---
    @GetMapping
    public List<Livreur> getAllLivreurs() {
        // Appelle le service pour trouver tous les livreurs
        return livreurService.findAllLivreurs();
    }

    // --- READ (Lire un seul livreur par son ID) ---
    @GetMapping("/{id}")
    public Livreur getLivreurById(@PathVariable Long id) {
        // 1. @PathVariable prend l'ID depuis l'URL (ex: /api/livreurs/5)
        // 2. On appelle le service pour trouver ce livreur
        // .orElse(null) renvoie le livreur s'il est trouvé, ou 'null' sinon
        return livreurService.findLivreurById(id).orElse(null);
    }
    // --- UPDATE (Mettre à jour un livreur existant) ---
    @PutMapping("/{id}")
    public Livreur updateLivreur(@PathVariable Long id, @RequestBody Livreur livreurDetails) {
        // 1. On cherche le livreur existant
        Livreur livreurExistant = livreurService.findLivreurById(id)
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé !"));

        // 2. On met à jour ses informations
        livreurExistant.setNom(livreurDetails.getNom());
        livreurExistant.setPrenom(livreurDetails.getPrenom());
        livreurExistant.setVehicule(livreurDetails.getVehicule());
        livreurExistant.setTelephone(livreurDetails.getTelephone());

        // 3. On sauvegarde les modifications (save() fait aussi la mise à jour)
        return livreurService.saveLivreur(livreurExistant);
    }

    // --- DELETE (Supprimer un livreur) ---
    @DeleteMapping("/{id}")
    public String deleteLivreur(@PathVariable Long id) {
        // 1. On vérifie s'il existe avant de le supprimer
        livreurService.findLivreurById(id)
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé !"));

        // 2. On appelle le service pour le supprimer
        livreurService.deleteLivreur(id);

        // 3. On renvoie un message de confirmation
        return "Livreur supprimé avec succès (ID: " + id + ")";
    }
    // --- READ (Lire tous les colis d'un livreur spécifique) ---
    // C'est la User Story !
    // Ex: GET /api/livreurs/1/colis
    @GetMapping("/{id}/colis")
    public List<Colis> getColisForLivreur(@PathVariable Long id) {
        // 1. @PathVariable prend l'ID du livreur depuis l'URL
        // 2. On appelle le ColisService (pas le LivreurService !)
        return colisService.findColisByLivreurId(id);
    }
}