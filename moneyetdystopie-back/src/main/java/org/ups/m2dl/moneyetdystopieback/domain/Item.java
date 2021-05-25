package org.ups.m2dl.moneyetdystopieback.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {

    /** Identifiant de l'annonce. */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** Titre de l'annonce, obligatoire. */
    @Getter
    @Setter
    @NotNull(message = "Le titre doit être renseigné.")
    @Size(
        min = 2,
        max = 100,
        message = "Le titre doit faire entre 2 et 100 caractère."
    )
    private String title;

    /** URL vers l'image à utiliser. */
    @Getter
    @Setter
    @URL(message = "L'url est mal formé.")
    private String picture;

    /** Description du produit optionnelle. */
    @Getter
    @Setter
    @NotNull(message = "La description doit être renseignée.")
    @Size(
        min = 10,
        max = 200,
        message = "La description doit faire entre 10 et 200 caractère."
    )
    private String description;

    /** Quantité disponible à la vente. */
    @Getter
    @Setter
    @PositiveOrZero
    @NotNull(message = "La quantité doit être renseignée.")
    private Integer amount;

    /** Montant du produit en euros. */
    @Getter
    @Setter
    @Positive
    @NotNull(message = "Le prix doit être renseignée.")
    private Float price;

    /** Version du produit. */
    @Getter
    @Setter
    @Version
    private Long version;

    /** Vendeur du produit. */
    @Getter
    @Setter
    @NotNull(message = "La vendeur n'est pas renseigné.")
    @ManyToOne
    private Seller sellerAccount;
}
