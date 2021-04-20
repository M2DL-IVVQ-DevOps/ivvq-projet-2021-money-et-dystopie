package org.ups.m2dl.moneyetdystopieback.repositories;

import org.springframework.data.repository.CrudRepository;
import org.ups.m2dl.moneyetdystopieback.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
