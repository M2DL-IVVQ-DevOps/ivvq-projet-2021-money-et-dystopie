package org.ups.m2dl.moneyetdystopieback.domain;

import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Token {
    /**
     * Id du token unique et autogénéré.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    /**
     * Valeur du token qui sera sauvegardée en cookie et récupérée pour être comparée et servir d'identification.
     */
    @Column(name="value")
    @NotNull
    @Size(min = MoneyDystopieConstants.TOKEN_LENGTH, max = MoneyDystopieConstants.TOKEN_LENGTH)
    private String value;

    /**
     * Date après laquelle le token n'est plus valable.
     */
    @Column(name="expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration_date;


    /**
     * Utilisateur lié au token.
     */
    @ManyToOne
    @NotNull
    private Utilisateur utilisateur;

    public Token(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }


}
