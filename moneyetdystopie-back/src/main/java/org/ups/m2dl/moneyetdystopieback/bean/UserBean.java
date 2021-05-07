package org.ups.m2dl.moneyetdystopieback.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class UserBean {

    /**
     * Nom de famille de l'utilisateur.
     */
    @Getter
    @Setter
    private String lastName;

    /**
     * Prénom de l'utilisateur.
     */
    @Getter
    @Setter
    private String firstName;

    /**
     * Pseudo, unique, de l'utilisateur.
     */
    @Getter
    @Setter
    private String email;

    /**
     * Mot de passe, chiffré
     */
    @Getter
    @Setter
    private String password;

    /**
     * Compte commercant associé.
     */
    @Getter
    @Setter
    private SellerBean sellerAccount;

    /**
     * Compte acheteur associé.
     */
    @Getter
    @Setter
    private CustomerBean customerAccount;

}
