package org.ups.m2dl.moneyetdystopieback.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ups.m2dl.moneyetdystopieback.enums.CommandState;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class CommandBean {

    /** Identifiant de la commande. */
    @Getter
    @Setter
    private Long id;

    /** Etat de la commande. */
    @Getter
    @Setter
    private CommandState state;

    /** Acheteur. */
    @Getter
    @Setter
    private CustomerBean customer;

    /** Liste des articles de la commande. */
    @Getter
    @Setter
    private List<ItemBean> items;
}
