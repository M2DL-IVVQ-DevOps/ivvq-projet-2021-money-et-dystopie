package org.ups.m2dl.moneyetdystopieback.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ups.m2dl.moneyetdystopieback.domain.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<Item> findById(Long id);
}
