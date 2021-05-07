package org.ups.m2dl.moneyetdystopieback.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ups.m2dl.moneyetdystopieback.enums.CommandState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Command")
public class Command {

    /**
     * Identifiant de la commande.
     */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Etat de la commande.
     */
    @Getter
    @Setter
    @NotNull
    private CommandState state;

    /**
     * Liste des articles de la commande.
     */
    @Getter
    @Setter
    @OneToMany
    private List<Item> items;
}
