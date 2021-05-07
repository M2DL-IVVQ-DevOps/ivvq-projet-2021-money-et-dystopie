package org.ups.m2dl.moneyetdystopieback.bean;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class SellerBean {

    /** Nom du commerçant. */
    @Getter
    @Setter
    private String storeName;

    /** Compte utilisateur associé au commerçant. */
    @Getter
    @Setter
    private UserBean userAccount;

    /** Liste des produits en vente. */
    @Getter
    @Setter
    private List<ItemBean> items;

    /** Liste des produits en vente. */
    @Getter
    @Setter
    private List<CommandBean> commands;
}
