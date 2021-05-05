package org.ups.m2dl.moneyetdystopieback.domain;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {

    /**
     * Nom de famille de l'utilisateur.
     */
    @NotNull
    @Size( min = 1, max = 30, message = "Le nom doit faire entre 1 et 50 caractère.")
    private String lastName;

    /**
     * Prénom de l'utilisateur.
     */
    @NotNull
    @Size( min = 1, max = 30, message = "Le prénom doit faire entre 1 et 50 caractère.")
    private String firstName;

    /**
     * Pseudo, unique, de l'utilisateur.
     */
    @Id
    @Email
    @NotNull(message = "Le mail doit être renseigné.")
    @Size( min = 1, max = 100, message = "Le mail doit faire entre 1 et 100 caractère.")
    private String email;

    /**
     * Mot de passe, chiffré
     */
    @NotNull
    @Size( min = 60, max = 60, message = "Le hash mot de passe doit faire exactement 60 caractères.")
    private String password;

    /**
     * Compte commercant associé.
     */
    @OneToOne
    private Seller sellerAccount;

    /**
     * Compte acheteur associé.
     */
    @OneToOne
    private Customer customerAccount;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokenList;

    public User() {
    }

    public User(String lastName, String firstName, String email, String password) {
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setEmail(email);
        this.setPassword(password);
        this.setSellerAccount(null);
        this.setCustomerAccount(null);
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Seller getSellerAccount() {
        return sellerAccount;
    }

    public Customer getCustomerAccount() {
        return customerAccount;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSellerAccount(Seller sellerAccount) {
        this.sellerAccount = sellerAccount;
    }

    public void setCustomerAccount(Customer customerAccount) {
        this.customerAccount = customerAccount;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
}