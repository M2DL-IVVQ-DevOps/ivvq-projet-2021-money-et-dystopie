package org.ups.m2dl.moneyetdystopieback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ups.m2dl.moneyetdystopieback.domain.*;
import org.ups.m2dl.moneyetdystopieback.repositories.AcheteurRepository;
import org.ups.m2dl.moneyetdystopieback.repositories.CommandeRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

    @Autowired
    private AcheteurRepository acheteurRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    private Acheteur acheteur;

    private Commande panier;

    private Commande commande1;
    private Commande commande2;
    private Commande commande3;

    private Commercant commercant;

    private User user;

    private void initCommercant() {
        commercant = new Commercant("Boutique de FredoMgeon", new ArrayList<>(), new ArrayList<>());
    }

    private void initPanier() {
        panier = new Commande(EtatCommande.EN_COURS, new ArrayList<>());
    }

    private void initCommandes() {
        commande1 = new Commande(EtatCommande.EN_ATTENTE_D_EXPEDITION, new ArrayList<>());
        commande2 = new Commande(EtatCommande.EN_ATTENTE_DE_LIVRAISON, new ArrayList<>());
        commande3 = new Commande(EtatCommande.EN_ATTENTE_DE_PAIEMENT, new ArrayList<>());
    }

    private void initAcheteur() {
        acheteur = new Acheteur("FredoMigeon", "pas à Fougères, ça c'est JDG", user, panier, List.of(commande1, commande2, commande3));
    }

    private void initUtilisateur() {
        user = new User("Migeon", "Frédéric", "frederic.migeon@irit.fr", "aaaaaa", commercant, acheteur);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initCommercant();
        initCommandes();
        initPanier();
        initAcheteur();
        initUtilisateur();

        commandeRepository.save(panier);
        commandeRepository.save(commande1);
        commandeRepository.save(commande2);
        commandeRepository.save(commande3);
        acheteurRepository.save(acheteur);
    }
}
