package org.ups.m2dl.moneyetdystopieback.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ups.m2dl.moneyetdystopieback.domain.ItemCommand;

import java.util.Optional;

@Repository
public interface ItemCommandRepository extends CrudRepository<ItemCommand, Long> {
    Optional<ItemCommand> findById(Long id);
}
