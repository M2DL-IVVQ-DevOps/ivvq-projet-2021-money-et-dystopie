package org.ups.m2dl.moneyetdystopieback.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private String description;

    /**
     * Montant du produit en euros.
     */
    @NotNull
    private float price;

    /**
     * Quantité disponible à la vente.
     */
    @NotNull
    private int amount;

    /**
     * URL vers l'image à utiliser.
     */
    @NotNull
    private String picture;

    /**
     * Version du produit.
     */
    @NotNull
    @Version
    private long version;

    @NotNull
    @ManyToOne
    private Seller seller;
}
