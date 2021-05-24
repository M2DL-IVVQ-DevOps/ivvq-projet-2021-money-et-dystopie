package org.ups.m2dl.moneyetdystopieback.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class CustomerBean {

    /** Identifiant de l'acheteur. Pseudo de l'acheteur. */
    @Getter
    @Setter
    private String pseudo;

    /** Adresse de l'acheteur. */
    @Getter
    @Setter
    private String address;

    /** Compte utilisateur associé au commerçant. */
    @Getter
    @Setter
    @JsonIgnore
    private UserBean userAccount;

    /** Panier de l'acheteur. */
    @Getter
    @Setter
    private CommandBean cart;

    /** Liste des anciennes commandes de l'acheteur. */
    @Getter
    @Setter
    private List<CommandBean> pastCommands;
}
