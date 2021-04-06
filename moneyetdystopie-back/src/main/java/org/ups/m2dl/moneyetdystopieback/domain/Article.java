package org.ups.m2dl.moneyetdystopieback.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Article {

    /**
     * Identifiant de l'annonce.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Titre de l'annonce, obligatoire.
     */
    @NotNull
    private String titre;

    /**
     * Description du produit optionnelle.
     */
    private String description;

    /**
     * Montant du produit en euros.
     */
    private float prix;

    /**
     * Quantité disponible à la vente.
     */
    private int quantite;

    /**
     * URL vers l'image à utiliser.
     */
    private String image;

    /**
     * Version du produit.
     */
    private int version;

    @NotNull
    @ManyToOne
    private Commercant commercant;
}
