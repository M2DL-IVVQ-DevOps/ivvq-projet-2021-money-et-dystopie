package org.ups.m2dl.moneyetdystopieback.repositories;

import org.springframework.data.repository.CrudRepository;
import org.ups.m2dl.moneyetdystopieback.domain.Command;

public interface OrderRepository extends CrudRepository<Command, Long> {
}
