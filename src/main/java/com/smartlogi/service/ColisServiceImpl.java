package com.smartlogi.service;

import com.smartlogi.entity.Colis;
import com.smartlogi.entity.Livreur;
import com.smartlogi.enums.StatutColis;
import com.smartlogi.repository.ColisRepository;
import com.smartlogi.repository.LivreurRepository; // On a besoin des deux
import org.springframework.beans.factory.annotation.Autowired; // Importe l'annotation
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ColisServiceImpl implements ColisService {

    // --- INJECTION PAR CHAMP (Field Injection - Type 2) ---
    @Autowired
    private LivreurRepository livreurRepository;
    // ----------------------------------------------------

    private ColisRepository colisRepository;

    // --- INJECTION PAR SETTER (Type 3) ---
    @Autowired
    public void setColisRepository(ColisRepository colisRepository) {
        this.colisRepository = colisRepository;
    }
    // ---------------------------------------

    @Override
    public Colis saveColisAndAssign(Colis colis, Long livreurId) {
        // 1. Trouver le livreur
        Optional<Livreur> livreurOpt = livreurRepository.findById(livreurId);

        if (livreurOpt.isPresent()) {
            // 2. Assigner le livreur au colis
            colis.setLivreur(livreurOpt.get());
            // 3. Sauvegarder le colis
            return colisRepository.save(colis);
        } else {
            // Gérer l'erreur si le livreur n'existe pas
            throw new RuntimeException("Livreur non trouvé avec l'ID: " + livreurId);
        }
    }

    @Override
    public Colis updateColisStatus(Long colisId, StatutColis statut) {
        Optional<Colis> colisOpt = colisRepository.findById(colisId);

        if (colisOpt.isPresent()) {
            Colis colis = colisOpt.get();
            colis.setStatut(statut);
            return colisRepository.save(colis); // save() fait aussi la mise à jour
        } else {
            throw new RuntimeException("Colis non trouvé avec l'ID: " + colisId);
        }
    }

    @Override
    public List<Colis> findColisByLivreurId(Long livreurId) {
        // On utilise la méthode magique créée à l'étape 3
        return colisRepository.findByLivreurId(livreurId);
    }

    @Override
    public void deleteColis(Long colisId) {
        colisRepository.deleteById(colisId);
    }

    @Override
    public Colis updateColis(Colis colis) {
        // S'assure que l'ID existe avant de mettre à jour
        if(colis.getId() == null || !colisRepository.existsById(colis.getId())) {
            throw new RuntimeException("Impossible de mettre à jour : Colis non trouvé.");
        }
        // Il faut aussi gérer le livreur...
        // Pour une simple mise à jour, on peut supposer que le livreur est déjà attaché.
        return colisRepository.save(colis);
    }
}