package org.ups.m2dl.moneyetdystopieback.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Item {

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
    private String title;

    /**
     * Description du produit optionnelle.
     */
    private String description;

    /**
     * Montant du produit en euros.
     */
    private float price;

    /**
     * Quantité disponible à la vente.
     */
    private int amount;

    /**
     * URL vers l'image à utiliser.
     */
    private String picture;

    /**
     * Version du produit.
     */
    private int version;

    @NotNull
    @ManyToOne
    private Seller seller;
}
