package org.ups.m2dl.moneyetdystopieback.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ItemCommand {

    /** Identifiant de l'article commandé. */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** Quantité commandée. */
    @Getter
    @Setter
    @Positive
    @NotNull(message = "La quantité doit être renseignée.")
    private Integer amount;

    /** Item commandé. */
    @Getter
    @Setter
    @NotNull(message = "L'item n'est pas renseigné.")
    @ManyToOne
    private Item item;
}
