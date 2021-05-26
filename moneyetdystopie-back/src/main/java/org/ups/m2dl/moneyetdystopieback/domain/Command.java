package org.ups.m2dl.moneyetdystopieback.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.ups.m2dl.moneyetdystopieback.enums.CommandState;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Command {

    /** Identifiant de la commande. */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** État de la commande. */
    @Getter
    @Setter
    @NotNull
    private CommandState state;

    /** Acheteur. */
    @Getter
    @Setter
    @NotNull
    @ManyToOne
    private Customer customer;

    /** Liste des articles de la commande. */
    @Getter
    @Setter
    @OneToMany
    @Size(min = 1)
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull(message = "La commande doit comporter au moins un article.")
    private List<ItemCommand> itemCommands;

    public void addItemsCommand(ItemCommand itemCommand) {
        if (this.itemCommands == null) {
            this.itemCommands = new ArrayList<>();
        }
        this.itemCommands.add(itemCommand);
    }
}
