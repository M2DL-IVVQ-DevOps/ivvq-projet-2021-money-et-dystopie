package org.ups.m2dl.moneyetdystopieback.bean;

import org.ups.m2dl.moneyetdystopieback.enums.CommandState;

import java.util.List;

public class CommandBean {

    /**
     * Identifiant de la commande.
     */
    private Long id;

    /**
     * Etat de la commande.
     */
    private CommandState state;

    /**
     * Liste des articles de la commande.
     */
    private List<ItemBean> items;

    public CommandBean() {
    }

    public CommandBean(CommandState state, List<ItemBean> items) {
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

    public void setItems(List<ItemBean> items) {
        this.items = items;
    }

    public List<ItemBean> getItems() {
        return items;
    }
}
