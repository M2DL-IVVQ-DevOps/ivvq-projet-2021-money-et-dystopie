package org.ups.m2dl.moneyetdystopieback.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Commande {

    /**
     * Identifiant de la commande.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Etat de la commande.
     */
    private EtatCommande etat;

    /**
     * Liste des articles de la commande.
     */
    @OneToMany
    private List<Article> articles;

    public Commande() {
    }

    public Commande(EtatCommande etat, List<Article> articles) {
        this.etat = etat;
        this.articles = articles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public void setEtat(EtatCommande etat) {
        this.etat = etat;
    }
}
