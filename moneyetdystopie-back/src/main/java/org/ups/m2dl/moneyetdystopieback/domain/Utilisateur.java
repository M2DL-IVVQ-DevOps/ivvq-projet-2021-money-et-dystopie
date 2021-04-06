package org.ups.m2dl.moneyetdystopieback.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

@Entity
public class Utilisateur {

    /**
     * Nom de famille de l'utilisateur.
     */
    private String nom;

    /**
     * Prénom de l'utilisateur.
     */
    private String prenom;

    /**
     * Pseudo, unique, de l'utilisateur.
     */
    @Id
    @Email
    private String adresseMail;

    /**
     * Mot de passe, chiffré
     */
    private String motDePasse;

    /**
     * Compte commercant associé.
     */
    @OneToOne(mappedBy = "compteUtilisateur")
    private Commercant compteCommercant;

    /**
     * Compte acheteur associé.
     */
    @OneToOne(mappedBy = "compteUtilisateur")
    private Acheteur compteAcheteur;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, @Email String adresseMail, String motDePasse, Commercant compteCommercant, Acheteur compteAcheteur) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.compteCommercant = compteCommercant;
        this.compteAcheteur = compteAcheteur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String pseudo) {
        this.adresseMail = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
