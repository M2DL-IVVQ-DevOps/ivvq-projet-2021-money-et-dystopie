package org.ups.m2dl.moneyetdystopieback.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ups.m2dl.moneyetdystopieback.domain.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    public Optional<Token> findTokenByValue(String value);
}
