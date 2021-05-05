package org.ups.m2dl.moneyetdystopieback.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ups.m2dl.moneyetdystopieback.domain.Token;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    public Optional<Token> findTokenByValue(String value);
}
