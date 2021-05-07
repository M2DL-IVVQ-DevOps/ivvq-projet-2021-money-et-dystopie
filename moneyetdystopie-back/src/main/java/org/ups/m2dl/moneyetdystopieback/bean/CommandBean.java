package org.ups.m2dl.moneyetdystopieback.bean;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ups.m2dl.moneyetdystopieback.enums.CommandState;

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

    /** Liste des articles de la commande. */
    @Getter
    @Setter
    private List<ItemBean> items;
}
