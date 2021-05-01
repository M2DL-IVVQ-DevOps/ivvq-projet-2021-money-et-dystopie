package org.ups.m2dl.moneyetdystopieback.repositories;

import org.springframework.data.repository.CrudRepository;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;

import java.util.Optional;

public interface SellerRepository extends CrudRepository<Seller, Long> {
    Optional<Seller> findByStoreName(String storeName);
}
