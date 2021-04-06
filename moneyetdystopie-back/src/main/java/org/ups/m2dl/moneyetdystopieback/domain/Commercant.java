package org.ups.m2dl.moneyetdystopieback.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Commercant {

    /**
     * Nom du commerçant.
     */
    @Id
    private String nomBoutique;

    /**
     * Compte utilisateur associé au commerçant.
     */
    @OneToOne
    private Utilisateur compteUtilisateur;

    /**
     * Liste des produits en vente.
     */
    @OneToMany(mappedBy = "commercant")
    private List<Article> articles;

    /**
     * Liste des produits en vente.
     */
    @OneToMany
    private List<Commande> commandes;

    public Commercant() {

    }

    public Commercant(String nomBoutique, List<Article> articles,
                      List<Commande> commandes) {
        this.nomBoutique = nomBoutique;
        this.articles = articles;
        this.commandes = commandes;
    }

    public String getNomBoutique() {
        return nomBoutique;
    }

    public void setNomBoutique(String nomPublic) {
        this.nomBoutique = nomPublic;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
