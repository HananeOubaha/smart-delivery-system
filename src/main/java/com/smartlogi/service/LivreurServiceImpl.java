package com.smartlogi.service;

import com.smartlogi.entity.Livreur;
import com.smartlogi.repository.LivreurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Très important!

import java.util.List;
import java.util.Optional;

@Service // Dit à Spring : "Ceci est un bean de service"
@Transactional // Dit à Spring : "Gère les transactions pour toutes les méthodes"
public class LivreurServiceImpl implements LivreurService {

    // --- INJECTION PAR CONSTRUCTEUR (Type 1) ---
    private final LivreurRepository livreurRepository;

    // @Autowired est optionnel car il n'y a qu'un seul constructeur
    public LivreurServiceImpl(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }
    // -------------------------------------------

    @Override
    public Livreur saveLivreur(Livreur livreur) {
        // Logique de vérification (ex: doublon de téléphone) peut être ajoutée ici
        return livreurRepository.save(livreur);
    }

    @Override
    public Optional<Livreur> findLivreurById(Long id) {
        return livreurRepository.findById(id);
    }

    @Override
    public List<Livreur> findAllLivreurs() {
        return livreurRepository.findAll();
    }

    @Override
    public void deleteLivreur(Long id) {
        livreurRepository.deleteById(id);
    }
}