package org.ups.m2dl.moneyetdystopieback.repositories;

import org.springframework.data.repository.CrudRepository;
import org.ups.m2dl.moneyetdystopieback.domain.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByEmail(String email);
}
