package org.ups.m2dl.moneyetdystopieback.bean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import java.util.Date;

public class TokenBean {
    /**
     * Id du token unique et autogénéré.
     */
    private Long id;

    /**
     * Valeur du token qui sera sauvegardée en cookie et récupérée pour être comparée et servir d'identification.
     */
    private String value;

    /**
     * Date après laquelle le token n'est plus valable.
     */
    private Date expiration_date;


    /**
     * Utilisateur lié au token.
     */
    private User user;

    public TokenBean(){

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

    public User getUtilisateur() {
        return user;
    }

    public void setUtilisateur(User user) {
        this.user = user;
    }


}
