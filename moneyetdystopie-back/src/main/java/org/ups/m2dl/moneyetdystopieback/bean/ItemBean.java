package org.ups.m2dl.moneyetdystopieback.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ItemBean {

    /** Identifiant de l'annonce. */
    @Getter
    @Setter
    private Long id;

    /** Titre de l'annonce, obligatoire. */
    @Getter
    @Setter
    private String title;

    /** URL vers l'image à utiliser. */
    @Getter
    @Setter
    private String picture;

    /** Description du produit optionnelle. */
    @Getter
    @Setter
    private String description;

    /** Quantité disponible à la vente. */
    @Getter
    @Setter
    private Integer amount;

    /** Montant du produit en euros. */
    @Getter
    @Setter
    private Float price;

    /** Version du produit. */
    @Getter
    @Setter
    private Long version;

    /** Vendeur du produit. */
    @Getter
    @Setter
    private SellerBean sellerAccount;
}
