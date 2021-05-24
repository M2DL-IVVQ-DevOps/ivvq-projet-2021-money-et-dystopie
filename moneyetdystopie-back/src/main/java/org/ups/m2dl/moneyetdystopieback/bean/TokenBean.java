package org.ups.m2dl.moneyetdystopieback.bean;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ups.m2dl.moneyetdystopieback.domain.User;

@NoArgsConstructor
public class TokenBean {

    /** Id du token unique et autogénéré. */
    @Getter
    @Setter
    private Long id;

    /**
     * Valeur du token qui sera sauvegardée en cookie et récupérée pour être comparée et servir
     * d'identification.
     */
    @Getter
    @Setter
    private String value;

    /** Date après laquelle le token n'est plus valable. */
    private Date expirationDate;

    /** Utilisateur lié au token. */
    @Getter
    @Setter
    private User user;

    public TokenBean(Long id, String value, Date expirationDate, User user){
        this.id = id;
        this.value = value;
        this.expirationDate = (Date) expirationDate.clone();
        this.user = user;
    }

    public Date getExpirationDate() {
        return (Date) expirationDate.clone();
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = (Date) expirationDate.clone();
    }
}
