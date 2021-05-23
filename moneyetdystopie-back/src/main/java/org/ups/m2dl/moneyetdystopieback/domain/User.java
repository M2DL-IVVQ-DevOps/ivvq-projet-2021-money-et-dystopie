package org.ups.m2dl.moneyetdystopieback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    /** Nom de famille de l'utilisateur. */
    @Getter
    @Setter
    @NotNull(message = "Le nom doit être renseigné.")
    @Size(
        min = 1,
        max = 30,
        message = "Le nom doit faire entre 1 et 50 caractère."
    )
    private String lastName;

    /** Prénom de l'utilisateur. */
    @Getter
    @Setter
    @NotNull(message = "Le prénom doit être renseigné.")
    @Size(
        min = 1,
        max = 30,
        message = "Le prénom doit faire entre 1 et 50 caractère."
    )
    private String firstName;

    /** Pseudo, unique, de l'utilisateur. */
    @Getter
    @Setter
    @Id
    @Email
    @NotNull(message = "Le mail doit être renseigné.")
    @Size(
        min = 1,
        max = 100,
        message = "Le mail doit faire entre 1 et 100 caractère."
    )
    private String email;

    /** Mot de passe, chiffré */
    @Getter
    @Setter
    @NotNull(message = "Le mot de passe doit être renseigné.")
    @Size(
        min = 60,
        max = 60,
        message = "Le hash mot de passe doit faire exactement 60 caractères."
    )
    private String password;

    /** Compte commercant associé. */
    @Getter
    @Setter
    @OneToOne
    private Seller sellerAccount;

    /** Compte acheteur associé. */
    @Getter
    @Setter
    @OneToOne
    private Customer customerAccount;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Token> tokenList;
}
