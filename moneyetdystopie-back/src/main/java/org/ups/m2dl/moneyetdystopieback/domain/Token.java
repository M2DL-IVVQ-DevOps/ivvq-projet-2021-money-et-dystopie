package org.ups.m2dl.moneyetdystopieback.domain;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    /** Id du token unique et autogénéré. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Getter
    @Setter
    private Long id;

    /**
     * Valeur du token qui sera sauvegardée en cookie et récupérée pour être comparée et servir
     * d'identification.
     */
    @NotNull
    @Size(
        min = MoneyDystopieConstants.TOKEN_LENGTH,
        max = MoneyDystopieConstants.TOKEN_LENGTH
    )
    @Getter
    @Setter
    private String value;

    /** Date après laquelle le token n'est plus valable. */
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Getter
    @Setter
    private Date expirationDate;

    /** Utilisateur lié au token. */
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @Getter
    @Setter
    private User user;

    @Override
    public boolean equals(Object o) {
        if (o instanceof Token) {
            Token token = (Token) o;
            return (
                getId().equals(token.getId()) &&
                getValue().equals(token.getValue())
            );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId().hashCode() + getValue().hashCode();
    }
}
