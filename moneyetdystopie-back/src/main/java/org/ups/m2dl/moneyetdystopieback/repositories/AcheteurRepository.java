package org.ups.m2dl.moneyetdystopieback.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ups.m2dl.moneyetdystopieback.domain.Acheteur;

@Repository
public interface AcheteurRepository extends CrudRepository<Acheteur, Long> {
}
