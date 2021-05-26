package org.ups.m2dl.moneyetdystopieback.bean;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
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

    /** Acheteur. */
    @Getter
    @Setter
    @NotNull(message = "Le client doit être renseigné dans sa commande.")
    private CustomerBean customer;

    /** Liste des articles de la commande. */
    @Getter
    @Setter
    private List<ItemCommandBean> itemCommands;

    public void addItemsCommand(ItemCommandBean itemCommand) {
        if (this.itemCommands == null) {
            this.itemCommands = new ArrayList<>();
        }
        this.itemCommands.add(itemCommand);
    }
}
