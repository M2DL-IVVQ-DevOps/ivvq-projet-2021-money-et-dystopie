package org.ups.m2dl.moneyetdystopieback.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Acheteur {

    /**
     * Identifiant de l'acheteur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Pseudo de l'acheteur.
     */
    private String pseudo;

    /**
     * Adresse de l'acheteur.
     */
    private String adresse;

    /**
     * Compte utilisateur associé au commerçant.
     */
    @OneToOne
    private Utilisateur compteUtilisateur;

    /**
     * Panier de l'acheteur.
     */
    @OneToOne
    private Commande panier;

    /**
     * Liste des anciennes commandes de l'acheteur.
     */
    @OneToMany
    private List<Commande> anciennesCommandes;

    public Acheteur() {
    }

    public Acheteur(String pseudo, String adresse, Utilisateur compteUtilisateur, Commande panier, List<Commande> anciennesCommandes) {
        this.pseudo = pseudo;
        this.adresse = adresse;
        this.compteUtilisateur = compteUtilisateur;
        this.panier = panier;
        this.anciennesCommandes = anciennesCommandes;
    }
}
