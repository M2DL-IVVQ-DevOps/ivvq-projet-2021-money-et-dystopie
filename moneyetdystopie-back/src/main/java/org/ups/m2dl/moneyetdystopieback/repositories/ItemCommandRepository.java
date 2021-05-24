package org.ups.m2dl.moneyetdystopieback.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ups.m2dl.moneyetdystopieback.domain.ItemCommand;

@Repository
public interface ItemCommandRepository
    extends CrudRepository<ItemCommand, Long> {
    Optional<ItemCommand> findById(Long id);
}
