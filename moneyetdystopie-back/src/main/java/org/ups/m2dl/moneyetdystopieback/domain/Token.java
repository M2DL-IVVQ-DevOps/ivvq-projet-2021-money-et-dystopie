package org.ups.m2dl.moneyetdystopieback.domain;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@Entity
public class Token {

    /** Id du token unique et autogénéré. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
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
    private String value;

    /** Date après laquelle le token n'est plus valable. */
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date expirationDate;

    /** Utilisateur lié au token. */
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private User user;

    public Token() {}

    public Token(String value, Date expirationDate) {
        this.value = value;
        this.expirationDate = (Date) expirationDate.clone();
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

    public Date getExpirationDate() {
        return (Date) expirationDate.clone();
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = (Date) expirationDate.clone();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
