package org.ups.m2dl.moneyetdystopieback;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ups.m2dl.moneyetdystopieback.domain.*;

import java.util.ArrayList;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

    private Acheteur acheteur;

    private Commande panier;

    private Commercant commercant;

    private Utilisateur utilisateur;

    private void initCommercant() {
        commercant = new Commercant("Boutique de FredoMgeon", new ArrayList<>(), new ArrayList<>());
    }

    private void initPanier() {
        panier = new Commande(EtatCommande.EN_COURS, new ArrayList<>());
    }

    private void initAcheteur() {
        acheteur = new Acheteur("FredoMigeon", "pas à Fougères, ça c'est JDG", utilisateur, panier, new ArrayList<>());
    }

    private void initUtilisateur() {
        utilisateur = new Utilisateur("Migeon", "Frédéric", "frederic.migeon@irit.fr", "aaaaaa", commercant, acheteur);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initCommercant();
        initPanier();
        initAcheteur();
        initUtilisateur();
    }
}
