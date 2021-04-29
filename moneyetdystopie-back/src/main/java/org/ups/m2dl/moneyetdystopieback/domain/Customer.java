package org.ups.m2dl.moneyetdystopieback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Customer {

    /**
     * Identifiant de l'acheteur.
     * Pseudo de l'acheteur.
     */
    @Id
    @NotNull(message = "Le pseudo doit être renseigné.")
    @Size( min = 5, max = 50, message = "Le pseudo doit faire entre 5 et 50 caractère.")
    private String pseudo;

    /**
     * Adresse de l'acheteur.
     */

    @Size( min = 10, max = 150, message = "L'adresse doit faire entre 10 et 150 caractère.")
    @NotNull(message = "L'adresse doit être renseigné.")
    private String address;

    /**
     * Compte utilisateur associé au commerçant.
     */
    @JsonIgnore
    @OneToOne
    private User userAccount;

    /**
     * Panier de l'acheteur.
     */
    @OneToOne
    private Command cart;

    /**
     * Liste des anciennes commandes de l'acheteur.
     */
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<Command> pastCommands;


    public Customer() {
    }

    public Customer(String pseudo, String address) {
        this.setPseudo(pseudo);
        this.setAddress(address);
        this.setUserAccount(null);
        this.setCart(null);
        this.setPastCommands(null);
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getAddress() {
        return address;
    }

    public User getUserAccount() {
        return userAccount;
    }

    public Command getCart() {
        return cart;
    }

    public List<Command> getPastCommands() {
        return pastCommands;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserAccount(User userAccount) {
        this.userAccount = userAccount;
    }

    public void setCart(Command cart) {
        this.cart = cart;
    }

    public void setPastCommands(List<Command> pastCommands) {
        this.pastCommands = pastCommands;
    }
}