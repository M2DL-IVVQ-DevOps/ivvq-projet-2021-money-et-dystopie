package org.ups.m2dl.moneyetdystopieback.services.token_service_test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.Cookie;
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
    public void setup() {
        tokenService = new TokenService(tokenRepository, userService);
    }

    @Test
    void whenUseSaveTokenMethod_thenRepositoryTokenInvoked() {
        // when : un saveToken() est appelé sur un tokenService
        tokenService.saveToken(token);
        // then : saveToken() du dépôt associé au service est invoqué
        verify(tokenRepository).save(token);
    }

    @Test
    void whenUseRemoveTokenMethod_thenRepositoryTokenInvoked() {
        // when : un removeToken() est appelé sur un tokenService
        tokenService.removeToken(token);
        // then : removeToken() du dépôt associé au service est invoqué
        verify(tokenRepository).delete(token);
    }

    @Test
    void whenGetTokenByValueMethod_thenRepositoryTokenInvoked() {
        // when : un getTokenByValue() est appelé sur un tokenService
        tokenService.getTokenByValue("email@adresse.truc");
        // then : findTokenByValue() du dépôt associé au service est invoqué
        verify(tokenRepository).findTokenByValue("email@adresse.truc");
    }

    @Test
    void whenCreateNewTokenForUser_thenUserIsAffected()
        throws BusinessException {
        User user = new User(
            "G",
            "Romain",
            "truc@truc.fr",
            "MotdepasseA1",
            null,
            null,
            null
        );
        Token token = tokenService.createNewTokenForUser(user);
        assertEquals(user.getLastName(), token.getUser().getLastName());
        assertEquals(user.getEmail(), token.getUser().getEmail());
        assertEquals(user.getFirstName(), token.getUser().getFirstName());
        assertEquals(user.getPassword(), token.getUser().getPassword());
    }

    @Test
    void whenCreateNewTokenForUser_thenItsValueIsCorrect()
        throws BusinessException {
        User user = new User(
            "G",
            "Romain",
            "truc@truc.fr",
            "MotdepasseA1",
            null,
            null,
            null
        );
        Token token = tokenService.createNewTokenForUser(user);
        assertEquals(
            MoneyDystopieConstants.TOKEN_LENGTH,
            token.getValue().length()
        );
    }

    @Test
    void whenGenerateTokenTwice_thenBothTokenAreDistinct()
        throws BusinessException {
        // when : deux tokens sont générés
        String tokenValue1 = tokenService.generateToken();
        String tokenValue2 = tokenService.generateToken();
        // then : les deux tokens ne sont pas identiques
        assertNotEquals(tokenValue1, tokenValue2);
    }

    @Test
    void whenGenerateToken_thenTokenHasCorrectSize() throws BusinessException {
        // when : on génère un token
        String tokenValue = tokenService.generateToken();
        // then : la valeur du token est précisément celle attendue
        assertEquals(MoneyDystopieConstants.TOKEN_LENGTH, tokenValue.length());
    }

    @Test
    void givenValidToken_whenIsValidToken_thenTrueReturned() {
        // given : un token invalide avec une date dépassée
        Token tokenTest = new Token();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 10000);
        tokenTest.setExpirationDate(calendar.getTime());
        // when : on vérifie la validité du token
        boolean result = tokenService.isTokenValid(tokenTest);
        // then : le token est invalide
        assertTrue(result);
    }

    @Test
    void givenNotValidToken_whenIsValidToken_thenFalseReturned() {
        // given : un token valide avec une date non dépassée
        Token tokenTest = new Token();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, -1);
        tokenTest.setExpirationDate(calendar.getTime());
        // when : on vérifie la validité du token
        boolean result = tokenService.isTokenValid(tokenTest);
        // then : le token est valide
        assertFalse(result);
    }

    @Test
    void givenNulToken_whenIsValidToken_thenFalseReturned() {
        // given : un token null
        Token tokenTest = null;
        // when : on vérifie la validité du token
        boolean result = tokenService.isTokenValid(tokenTest);
        // then : le token est valide
        assertFalse(result);
    }

    @Test
    void givenNullTokenAndCorrectUser_whenIsTokenUserAssociationValid_thenFalseReturned() {
        // given : un token null et un utilisateur correct
        User user = new User(
            "G",
            "Romain",
            "truc@truc.fr",
            "MotdepasseA1",
            null,
            null,
            null
        );
        // when : on vérifie l'association token / utilisateur
        boolean response = tokenService.isTokenUserAssociationValid(null, user);
        // then : l'association est incorrecte
        assertFalse(response);
    }

    @Test
    void givenCorrectTokenAndNullUser_whenIsTokenUserAssociationValid_thenFalseReturned() {
        // given : un token correct et un utilisateur null
        Token token = new Token(null,"token", new Date(),null);
        // when : on vérifie l'association token / utilisateur
        boolean response = tokenService.isTokenUserAssociationValid(
            token,
            null
        );
        // then : l'association est incorrecte
        assertFalse(response);
    }

    @Test
    void givenNullTokenAndNullUser_whenIsTokenUserAssociationValid_thenFalseReturned() {
        // given : un token null et un utilisateur null
        // when : on vérifie l'association token / utilisateur
        boolean response = tokenService.isTokenUserAssociationValid(null, null);
        // then : l'association est incorrecte
        assertFalse(response);
    }

    @Test
    void givenCorrectTokenAndCorrectUserWithRightAssociation_whenIsTokenUserAssociationValid_thenTrueReturned()
        throws BusinessException {
        // given : un token correct et un utilisateur correct avec une association valide
        User user = new User(
            "G",
            "Romain",
            "truc@truc.fr",
            "MotdepasseA1",
            null,
            null,
            null
        );
        Token token = tokenService.createNewTokenForUser(user);
        // when : on vérifie l'association token / utilisateur
        boolean response = tokenService.isTokenUserAssociationValid(
            token,
            user
        );
        // then : l'association est incorrecte
        assertTrue(response);
    }

    @Test
    void whenCreateCookie_thenCookieIsValid() {
        // given : un token
        Token token = new Token();
        token.setValue("5");

        // when : on créé le cookie du token
        Cookie cookie = tokenService.createTokenCookie(token);

        // then le cookie est valide
        assertTrue(cookie.isHttpOnly());
        assertTrue(cookie.getSecure());
        assertEquals("/", cookie.getPath());
        assertEquals(token.getValue(), cookie.getValue());
        assertEquals("token", cookie.getName());
    }
}
