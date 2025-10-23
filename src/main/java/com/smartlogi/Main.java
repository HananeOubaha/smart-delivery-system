package com.smartlogi;

import com.smartlogi.entity.Colis;
import com.smartlogi.entity.Livreur;
import com.smartlogi.enums.StatutColis;
import com.smartlogi.service.ColisService;
import com.smartlogi.service.LivreurService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Démarrage du contexte Spring...");

        // 1. Charger le fichier de configuration XML
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("Contexte chargé avec succès !");

        // 2. Récupérer les beans
        LivreurService livreurService = context.getBean(LivreurService.class);
        ColisService colisService = context.getBean(ColisService.class);

        System.out.println("--- Début des Scénarios de Test ---");

        try {
            // --- SCÉNARIO 1 : Gérer les livreurs (CRUD) ---
            System.out.println("\n[SCÉNARIO 1] Création de 2 livreurs...");
            Livreur livreurA = new Livreur("hanane", "oubaha", "Camionnette", "0611111111");
            Livreur livreurB = new Livreur("hajar", "walfi", "Scooter", "0622222222");

            livreurA = livreurService.saveLivreur(livreurA);
            livreurB = livreurService.saveLivreur(livreurB);

            System.out.println("Livreur A créé avec ID: " + livreurA.getId());
            System.out.println("Livreur B créé avec ID: " + livreurB.getId());

            // --- SCÉNARIO 2 : Enregistrer des colis et les assigner ---
            System.out.println("\n[SCÉNARIO 2] Création de 3 colis...");
            Colis colis1 = new Colis("Client X", "Adresse X", 5.0, StatutColis.PREPARATION, null);
            Colis colis2 = new Colis("Client Y", "Adresse Y", 2.5, StatutColis.PREPARATION, null);
            Colis colis3 = new Colis("Client Z", "Adresse Z", 10.0, StatutColis.PREPARATION, null);

            System.out.println("Assignation Colis 1 et 2 au Livreur A...");
            colis1 = colisService.saveColisAndAssign(colis1, livreurA.getId());
            colis2 = colisService.saveColisAndAssign(colis2, livreurA.getId());

            System.out.println("Assignation Colis 3 au Livreur B...");
            colis3 = colisService.saveColisAndAssign(colis3, livreurB.getId());

            System.out.println("Colis 1 (ID " + colis1.getId() + ") assigné à Livreur ID " + colis1.getLivreur().getId());
            System.out.println("Colis 3 (ID " + colis3.getId() + ") assigné à Livreur ID " + colis3.getLivreur().getId());


            // --- SCÉNARIO 3 : Mettre à jour le statut d'un colis ---
            System.out.println("\n[SCÉNARIO 3] Mise à jour du statut du Colis 1...");
            Colis colis1MisAJour = colisService.updateColisStatus(colis1.getId(), StatutColis.EN_TRANSIT);
            System.out.println("Nouveau statut du Colis 1: " + colis1MisAJour.getStatut());


            // --- SCÉNARIO 4 : Lister tous les colis assignés à un livreur ---
            System.out.println("\n[SCÉNARIO 4] Liste des colis pour le Livreur A (ID " + livreurA.getId() + ")...");
            List<Colis> colisLivreurA = colisService.findColisByLivreurId(livreurA.getId());

            for (Colis colis : colisLivreurA) {
                System.out.println("  -> Colis ID: " + colis.getId() + " | Statut: " + colis.getStatut() + " | Destinataire: " + colis.getDestinataire());
            }
            System.out.println(colisLivreurA.size() + " colis trouvés pour le Livreur A.");


            // --- SCÉNARIO 5 : Supprimer une information ---
            System.out.println("\n[SCÉNARIO 5] Suppression du Colis 2 (ID " + colis2.getId() + ")...");
            colisService.deleteColis(colis2.getId());
            System.out.println("Colis 2 supprimé.");

            // Vérification de la suppression
            List<Colis> colisLivreurAVerif = colisService.findColisByLivreurId(livreurA.getId());
            System.out.println(colisLivreurAVerif.size() + " colis restants pour le Livreur A.");


        } catch (Exception e) {
            System.err.println("ERREUR LORS DES TESTS: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n--- Fin des Scénarios de Test ---");

        // 3. Fermer le contexte
        context.close();
        System.out.println("Contexte fermé. Fin de l'application.");
    }
}