package org.ups.m2dl.moneyetdystopieback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    /** Identifiant de l'acheteur. Pseudo de l'acheteur. */
    @Getter
    @Setter
    @Id
    @NotNull(message = "Le pseudo doit être renseigné.")
    @Size(
        min = 5,
        max = 50,
        message = "Le pseudo doit faire entre 5 et 50 caractère."
    )
    private String pseudo;

    /** Adresse de l'acheteur. */
    @Getter
    @Setter
    @Size(
        min = 10,
        max = 150,
        message = "L'adresse doit faire entre 10 et 150 caractère."
    )
    @NotNull(message = "L'adresse doit être renseigné.")
    private String address;

    /** Compte utilisateur associé au commerçant. */
    @Getter
    @Setter
    @JsonIgnore
    @OneToOne
    private User userAccount;

    /** Panier de l'acheteur. */
    @Getter
    @Setter
    @OneToOne
    private Command cart;

    /** Liste des anciennes commandes de l'acheteur. */
    @Getter
    @Setter
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<Command> pastCommands;
}
