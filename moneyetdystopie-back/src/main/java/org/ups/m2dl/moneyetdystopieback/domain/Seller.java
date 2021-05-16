package org.ups.m2dl.moneyetdystopieback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seller {

    /** Nom du commerçant. */
    @Getter
    @Setter
    @Id
    @NotNull(message = "Le nom de boutique doit être renseigné.")
    @Size(
        min = 1,
        max = 30,
        message = "Le nom de la boutique doit faire entre 1 et 30 caractère."
    )
    private String storeName;

    /** Compte utilisateur associé au commerçant. */
    @Getter
    @Setter
    @JsonIgnore
    @OneToOne
    private User userAccount;

    /** Liste des produits en vente. */
    @Getter
    @Setter
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<Item> items;

    /** Liste des produits en vente. */
    @Getter
    @Setter
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<Command> commands;

    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}
