package org.ups.m2dl.moneyetdystopieback.domain;

import org.ups.m2dl.moneyetdystopieback.enums.CommandState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Command")
public class Command {

    /**
     * Identifiant de la commande.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Etat de la commande.
     */
    @NotNull
    private CommandState state;

    /**
     * Liste des articles de la commande.
     */
    @OneToMany
    private List<Item> items;

    public Command() {
    }

    public Command(CommandState state, List<Item> items) {
        this.setState(state);
        this.setItems(items);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommandState getState() {
        return state;
    }

    public void setState(CommandState state) {
        this.state = state;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
