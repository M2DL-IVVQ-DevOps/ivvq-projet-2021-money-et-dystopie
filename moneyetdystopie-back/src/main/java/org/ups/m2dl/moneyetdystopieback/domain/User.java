package org.ups.m2dl.moneyetdystopieback.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    /**
     * Nom de famille de l'utilisateur.
     */
    @Getter
    @Setter
    @NotNull(message = "Le nom doit être renseigné.")
    @Size( min = 1, max = 30, message = "Le nom doit faire entre 1 et 50 caractère.")
    private String lastName;

    /**
     * Prénom de l'utilisateur.
     */
    @Getter
    @Setter
    @NotNull(message = "Le prénom doit être renseigné.")
    @Size( min = 1, max = 30, message = "Le prénom doit faire entre 1 et 50 caractère.")
    private String firstName;

    /**
     * Pseudo, unique, de l'utilisateur.
     */
    @Getter
    @Setter
    @Id
    @Email
    @NotNull(message = "Le mail doit être renseigné.")
    @Size( min = 1, max = 100, message = "Le mail doit faire entre 1 et 100 caractère.")
    private String email;

    /**
     * Mot de passe, chiffré
     */
    @Getter
    @Setter
    @NotNull(message = "Le mot de passe doit être renseigné.")
    @Size( min = 10, max = 100, message = "Le mot de passe doit faire entre 10 et 100 caractère.")
    private String password;

    /**
     * Compte commercant associé.
     */
    @Getter
    @Setter
    @OneToOne
    private Seller sellerAccount;

    /**
     * Compte acheteur associé.
     */
    @Getter
    @Setter
    @OneToOne
    private Customer customerAccount;
}
