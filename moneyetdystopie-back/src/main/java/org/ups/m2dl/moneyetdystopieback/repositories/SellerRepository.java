package org.ups.m2dl.moneyetdystopieback.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;

public interface SellerRepository extends CrudRepository<Seller, Long> {
    Optional<Seller> findByStoreName(String storeName);
}
