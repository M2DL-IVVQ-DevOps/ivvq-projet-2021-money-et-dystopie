package org.ups.m2dl.moneyetdystopieback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Seller {

    /**
     * Nom du commerçant.
     */
    @Id
    @NotNull(message = "Le nom de boutique doit être renseigné.")
    @Size( min = 1, max = 30, message = "Le nom de la boutique doit faire entre 1 et 30 caractère.")
    private String storeName;

    /**
     * Compte utilisateur associé au commerçant.
     */
    @JsonIgnore
    @OneToOne
    private User userAccount;

    /**
     * Liste des produits en vente.
     */
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<Item> items;

    /**
     * Liste des produits en vente.
     */
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<Command> commands;


    public Seller() {

    }

    public Seller(String storeName, List<Item> items, List<Command> commands) {
        this.setStoreName(storeName);
        this.setItems(items);
        this.setCommands(commands);
        this.setUserAccount(null);
    }

    public Seller(String storeName) {
        this.setStoreName(storeName);
        this.setItems(null);
        this.setCommands(null);
        this.setUserAccount(null);
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


    public User getUserAccount() {
        return userAccount;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setUserAccount(User userAccount) {
        this.userAccount = userAccount;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }


}
