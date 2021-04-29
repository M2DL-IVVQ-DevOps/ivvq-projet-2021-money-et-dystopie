package org.ups.m2dl.moneyetdystopieback.bean;

public class UserBean {

    /**
     * Nom de famille de l'utilisateur.
     */
    private String lastName;

    /**
     * Prénom de l'utilisateur.
     */
    private String firstName;

    /**
     * Pseudo, unique, de l'utilisateur.
     */
    private String email;

    /**
     * Mot de passe, chiffré
     */
    private String password;

    /**
     * Compte commercant associé.
     */
    private SellerBean sellerAccount;

    /**
     * Compte acheteur associé.
     */
    private CustomerBean customerAccount;

    public UserBean() {
    }

    public UserBean(String lastName, String firstName, String email, String password) {
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setEmail(email);
        this.setPassword(password);
        this.setSellerAccount(null);
        this.setCustomerAccount(null);
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public SellerBean getSellerAccount() {
        return sellerAccount;
    }

    public CustomerBean getCustomerAccount() {
        return customerAccount;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSellerAccount(SellerBean sellerAccount) {
        this.sellerAccount = sellerAccount;
    }

    public void setCustomerAccount(CustomerBean customerAccount) {
        this.customerAccount = customerAccount;
    }

}
