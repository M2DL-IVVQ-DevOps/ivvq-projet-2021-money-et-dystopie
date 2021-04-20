package org.ups.m2dl.moneyetdystopieback.bean;


public class ItemBean {

    /**
     * Identifiant de l'annonce.
     */
    private Long id;

    /**
     * Titre de l'annonce, obligatoire.
     */
    private String title;

    /**
     * Description du produit optionnelle.
     */
    private String description;

    /**
     * Montant du produit en euros.
     */
    private float price;

    /**
     * Quantité disponible à la vente.
     */
    private int amount;

    /**
     * URL vers l'image à utiliser.
     */
    private String picture;

    /**
     * Version du produit.
     */
    private long version;

    private SellerBean sellerBean;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public String getPicture() {
        return picture;
    }

    public long getVersion() {
        return version;
    }

    public SellerBean getSellerBean() {
        return sellerBean;
    }
}
