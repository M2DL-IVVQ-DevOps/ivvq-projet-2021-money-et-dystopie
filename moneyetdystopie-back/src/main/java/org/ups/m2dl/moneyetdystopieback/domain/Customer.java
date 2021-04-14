package org.ups.m2dl.moneyetdystopieback.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {

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
    private String address;

    /**
     * Compte utilisateur associé au commerçant.
     */
    @OneToOne
    private User userAccount;

    /**
     * Panier de l'acheteur.
     */
    @OneToOne
    private Order cart;

    /**
     * Liste des anciennes commandes de l'acheteur.
     */
    @OneToMany
    private List<Order> pastOrders;

    public Customer() {
    }

    public Customer(String pseudo, String address, User userAccount, Order cart, List<Order> pastOrders) {
        this.pseudo = pseudo;
        this.address = address;
        this.userAccount = userAccount;
        this.cart = cart;
        this.pastOrders = pastOrders;
    }
}
