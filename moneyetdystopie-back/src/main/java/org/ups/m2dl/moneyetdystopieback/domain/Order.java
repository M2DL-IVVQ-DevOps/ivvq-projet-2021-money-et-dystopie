package org.ups.m2dl.moneyetdystopieback.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Order {

    /**
     * Identifiant de la commande.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Etat de la commande.
     */
    private OrderState state;

    /**
     * Liste des articles de la commande.
     */
    @OneToMany
    private List<Item> items;

    public Order() {
    }

    public Order(OrderState state, List<Item> items) {
        this.state = state;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }
}
