package org.ups.m2dl.moneyetdystopieback.bean;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ItemCommandBean {

    /** Identifiant de l'article commandé. */
    @Getter
    @Setter
    private Long id;

    /** Quantité commandé. */
    @Getter
    @Setter
    private Integer amount;

    /** Item commandé. */
    @Getter
    @Setter
    @Size(min = 1)
    private ItemBean item;
}
