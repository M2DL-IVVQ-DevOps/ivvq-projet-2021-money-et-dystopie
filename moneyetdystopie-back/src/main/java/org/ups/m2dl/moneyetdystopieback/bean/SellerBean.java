package org.ups.m2dl.moneyetdystopieback.bean;

import java.util.List;

public class SellerBean {

    /**
     * Nom du commerçant.
     */
    private String storeName;

    /**
     * Compte utilisateur associé au commerçant.
     */
    private UserBean userAccount;

    /**
     * Liste des produits en vente.
     */
    private List<ItemBean> items;

    /**
     * Liste des produits en vente.
     */
    private List<CommandBean> commands;


    public SellerBean() {

    }

    public SellerBean(String storeName, List<ItemBean> items, List<CommandBean> commands) {
        this.setStoreName(storeName);
        this.setItems(items);
        this.setCommands(commands);
    }

    public SellerBean(String storeName) {
        this.setStoreName(storeName);
        this.setItems(null);
        this.setCommands(null);
        this.userAccount = null;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String nomPublic) {
        this.storeName = nomPublic;
    }

    public List<ItemBean> getItems() {
        return items;
    }


    public UserBean getUserAccount() {
        return userAccount;
    }

    public List<CommandBean> getCommands() {
        return commands;
    }

    public void setUserAccount(UserBean userAccount) {
        this.userAccount = userAccount;
    }

    public void setItems(List<ItemBean> items) {
        this.items = items;
    }

    public void setCommands(List<CommandBean> commands) {
        this.commands = commands;
    }


}
