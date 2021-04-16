package org.ups.m2dl.moneyetdystopieback.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "app_user")
public class User {

    /**
     * Nom de famille de l'utilisateur.
     */
    private String lastName;

    /**
     * Prénom de l'utilisateur.
     */
    private String firstName;

    /**
     * Pseudo, unique, de l'utilisateur.
     */
    @Id
    @Email
    private String email;

    /**
     * Mot de passe, chiffré
     */
    private String password;

    /**
     * Compte commercant associé.
     */
    @OneToOne(mappedBy = "userAccount")
    private Seller sellerAccount;

    /**
     * Compte acheteur associé.
     */
    @OneToOne(mappedBy = "userAccount")
    private Customer customerAccount;

    public User() {
    }

    public User(String lastName, String firstName, @Email String email, String password, Seller sellerAccount, Customer customerAccount) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.sellerAccount = sellerAccount;
        this.customerAccount = customerAccount;
    }
}
