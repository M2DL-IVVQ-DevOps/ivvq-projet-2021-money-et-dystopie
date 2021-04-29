package org.ups.m2dl.moneyetdystopieback.services.tokenservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.TokenRepository;
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class TokenServiceTest {
    private TokenService tokenService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private Token token;

    @MockBean
    private User user;

    @BeforeEach
    public void setup(){
        tokenService = new TokenService(tokenRepository, userService);
    }

    @Test
    void whenUseSaveMethod_thenRepositoryTokenInvoked() {
        // when : un saveToken() est appelé sur un tokenService
        tokenService.saveToken(token);
        // then : saveToken() du dépôt associé au service est invoqué
        verify(tokenRepository).save(token);
    }

    @Test
    public void whenUseRemoveMethod_thenRepositoryTokenInvoked() {
        // when : un removeToken() est appelé sur un tokenService
        tokenService.removeToken(token);
        // then : removeToken() du dépôt associé au service est invoqué
        verify(tokenRepository).delete(token);
    }

    @Test
    public void whenGetTokenByValueMethod_thenRepositoryTokenInvoked(){
        // when : un getTokenByValue() est appelé sur un tokenService
        tokenService.getTokenByValue("email@adresse.truc");
        // then : findTokenByValue() du dépôt associé au service est invoqué
        verify(tokenRepository).findTokenByValue("email@adresse.truc");
    }










    @Test
    public void testGeneratedTokenHasCorrectLength(){
        // when : on génère un token
        String tokenValue = tokenService.generateToken();
        // then : la valeur du token est précisément celle attendue
        assertEquals(tokenValue.length(), MoneyDystopieConstants.TOKEN_LENGTH);
    }

    @Test
    void testTwoDistinctsGeneratedTokensAreNotEquals(){
        // when : deux tokens sont générés
        String tokenValue1 = tokenService.generateToken();
        String tokenValue2 = tokenService.generateToken();
        // then : les deux tokens ne sont pas identiques
        assertNotEquals(tokenValue1, tokenValue2);
    }


    @Test
    void testCreateNewTokenForUserGivesCorrectUser(){
        // given : un utilisateur
        User userTest = new User();
        userTest.setFirstName("Patrick");
        userTest.setLastName("Bob");
        userTest.setEmail("bob.patrick@rien.com");
        // when : un nouveau token est créé pour cet utilisateur
        Token newToken = tokenService.createNewTokenForUser(userTest);
        // then : le token de cet utilisateur n'est pas null
        assertEquals(userTest.getLastName(), newToken.getUtilisateur().getLastName());
        assertEquals(userTest.getEmail(), newToken.getUtilisateur().getEmail());
        assertEquals(userTest.getFirstName(), newToken.getUtilisateur().getFirstName());
    }

    @Test
    void testIsTokenValidWithNotValidToken(){
        // given : un token invalide avec une date dépassée
        Token tokenTest = new Token();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 1);
        tokenTest.setExpiration_date(calendar.getTime());
        // when : on vérifie la validité du token
        boolean result = tokenService.isTokenValid(tokenTest);
        // then : le token est invalide
        assertFalse(result);
    }

    @Test
    void testIsTokenValidWithValidToken(){
        // given : un token valide avec une date non dépassée
        Token tokenTest = new Token();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, -1);
        tokenTest.setExpiration_date(calendar.getTime());
        // when : on vérifie la validité du token
        boolean result = tokenService.isTokenValid(tokenTest);
        // then : le token est valide
        assertTrue(result);
    }

    @Test
    void testIsTokenValidWithNull(){
        // given : un token null
        Token tokenTest = null;
        // when : on vérifie la validité du token
        boolean result = tokenService.isTokenValid(tokenTest);
        // then : le token est valide
        assertTrue(result);
    }
}