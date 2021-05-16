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
    @Setter
    private Date expirationDate;

    /** Utilisateur lié au token. */
    @Getter
    @Setter
    private User user;
}
