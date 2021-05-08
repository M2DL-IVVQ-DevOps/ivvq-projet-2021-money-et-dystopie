package org.ups.m2dl.moneyetdystopieback.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.ups.m2dl.moneyetdystopieback.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
