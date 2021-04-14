package org.ups.m2dl.moneyetdystopieback.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Seller {

    /**
     * Nom du commerçant.
     */
    @Id
    private String storeName;

    /**
     * Compte utilisateur associé au commerçant.
     */
    @OneToOne
    private User userAccount;

    /**
     * Liste des produits en vente.
     */
    @OneToMany(mappedBy = "seller")
    private List<Item> items;

    /**
     * Liste des produits en vente.
     */
    @OneToMany
    private List<Order> orders;

    public Seller() {

    }

    public Seller(String storeName, List<Item> items,
                  List<Order> orders) {
        this.storeName = storeName;
        this.items = items;
        this.orders = orders;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String nomPublic) {
        this.storeName = nomPublic;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setOrders(List<Item> items) {
        this.items = items;
    }
}
