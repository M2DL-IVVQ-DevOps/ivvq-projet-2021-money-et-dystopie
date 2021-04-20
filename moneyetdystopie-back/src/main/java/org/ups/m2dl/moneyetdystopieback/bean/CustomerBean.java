package org.ups.m2dl.moneyetdystopieback.bean;

import java.util.List;

public class CustomerBean {

    /**
     * Identifiant de l'acheteur.
     * Pseudo de l'acheteur.
     */
    private String pseudo;

    /**
     * Adresse de l'acheteur.
     */
    private String address;

    /**
     * Compte utilisateur associé au commerçant.
     */
    private UserBean userAccount;

    /**
     * Panier de l'acheteur.
     */
    private CommandBean cart;

    /**
     * Liste des anciennes commandes de l'acheteur.
     */
    private List<CommandBean> pastCommands;


    public CustomerBean() {
    }

    public CustomerBean(String pseudo, String address) {
        this.setPseudo(pseudo);
        this.setAddress(address);
        this.setUserAccount(null);
        this.setCart(null);
        this.setPastCommands(null);
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getAddress() {
        return address;
    }

    public UserBean getUserAccount() {
        return userAccount;
    }

    public CommandBean getCart() {
        return cart;
    }

    public List<CommandBean> getPastCommands() {
        return pastCommands;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserAccount(UserBean userAccount) {
        this.userAccount = userAccount;
    }

    public void setCart(CommandBean cart) {
        this.cart = cart;
    }

    public void setPastCommands(List<CommandBean> pastCommands) {
        this.pastCommands = pastCommands;
    }
}
