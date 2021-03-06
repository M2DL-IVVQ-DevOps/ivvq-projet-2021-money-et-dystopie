package org.ups.m2dl.moneyetdystopieback.services.token_service_test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.TokenRepository;
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TokenServiceTest {

    private TokenService tokenService;

    private UserService userServiceMock;

    private TokenRepository tokenRepositoryMock;

    @MockBean
    private Token tokenMock;

    private Token testToken;

    private User testUser;

    private User testUserConnexion;

    @BeforeEach
    public void setupBeforeEach() {
        Seller sellerTest = new Seller("Boutika", null, null, null);
        Customer customerTest = new Customer(
            "pseudo",
            "adresse",
            null,
            null,
            null
        );
        testUser =
            new User(
                "lastName",
                "firstName",
                "email",
                "password",
                sellerTest,
                customerTest,
                null
            );
        testUserConnexion =
            new User(null, null, "email", "password", null, null, null);

        Calendar calendarTest = Calendar.getInstance();
        calendarTest.setTime(new Date());
        calendarTest.add(
            Calendar.MINUTE,
            MoneyDystopieConstants.TOKEN_DURABILITY_IN_MINUTES + 1
        );
        testToken = new Token(1L, "valeur", calendarTest.getTime(), new User());
        tokenRepositoryMock = Mockito.mock(TokenRepository.class);
        userServiceMock = Mockito.mock(UserService.class);
        tokenService = new TokenService(tokenRepositoryMock, userServiceMock);
    }

    @Test
    void givenValidToken_whenUseSaveTokenMethod_thenRepositoryTokenInvoked() {
        // when : un saveToken() est appel?? sur un tokenService
        tokenService.saveToken(tokenMock);
        // then : saveToken() du d??p??t associ?? au service est invoqu??
        verify(tokenRepositoryMock).save(tokenMock);
    }

    @Test
    void givenValidToken_whenUseSaveTokenMethod_thenTokenReturned() {
        // given : un token ?? sauvegarder
        Mockito
            .when(tokenRepositoryMock.save(Mockito.any()))
            .thenReturn(testToken);
        // when : un saveToken() est appel?? sur un tokenService
        Token resultToken = tokenService.saveToken(testToken);
        // then : le token sauvegard?? par le repo est retourn??
        assertEquals(
            testToken.getId(),
            resultToken.getId(),
            "Les identifiants des tokens doivent correspondre."
        );
        assertEquals(
            testToken.getUser(),
            resultToken.getUser(),
            "Les User des tokens doivent correspondre."
        );
        assertEquals(
            testToken.getValue(),
            resultToken.getValue(),
            "Les valeurs des tokens doivent correspondre."
        );
        assertEquals(
            testToken.getExpirationDate(),
            resultToken.getExpirationDate(),
            "Les dates d'expiration des tokens doivent correspondre."
        );
    }

    @Test
    void givenNotValidToken_whenUseSaveTokenMethod_thenExceptionThrown() {
        // given : un token ?? sauvegarder
        Mockito
            .when(tokenRepositoryMock.save(Mockito.any()))
            .thenThrow(new EmptyResultDataAccessException(0));
        // when : un saveToken() est appel?? sur un tokenService
        Token resulToken = tokenService.saveToken(testToken);
        // then : le token sauvegard?? par le repo est retourn??
        assertNull(resulToken, "Le token doit ??tre null.");
    }

    @Test
    void whenUseRemoveTokenMethod_thenRepositoryTokenInvoked() {
        // when : un removeToken() est appel?? sur un tokenService
        tokenService.removeToken(tokenMock);
        // then : removeToken() du d??p??t associ?? au service est invoqu??
        verify(tokenRepositoryMock).delete(tokenMock);
    }

    @Test
    void whenGetTokenByValueMethod_thenRepositoryTokenInvoked() {
        // when : un getTokenByValue() est appel?? sur un tokenService
        tokenService.getTokenByValue("email@adresse.truc");
        // then : findTokenByValue() du d??p??t associ?? au service est invoqu??
        verify(tokenRepositoryMock).findTokenByValue("email@adresse.truc");
    }

    @Test
    void givenExistingToken_whenGetTokenByValueMethod_thenTokenReturned() {
        // given : un token existant ?? rechercher
        Mockito
            .when(tokenRepositoryMock.findTokenByValue(Mockito.any()))
            .thenReturn(Optional.of(testToken));
        // when : un getTokenByValue() est appel?? sur un tokenService
        Token resultToken = tokenService.getTokenByValue("email@adresse.truc");
        // then : le r??sultat correspond ?? l'attendu
        assertEquals(
            testToken.getId(),
            resultToken.getId(),
            "Les identifiants des tokens doivent correspondre."
        );
        assertEquals(
            testToken.getUser(),
            resultToken.getUser(),
            "Les User des tokens doivent correspondre."
        );
        assertEquals(
            testToken.getValue(),
            resultToken.getValue(),
            "Les valeurs des tokens doivent correspondre."
        );
        assertEquals(
            testToken.getExpirationDate(),
            resultToken.getExpirationDate(),
            "Les dates d'expiration des tokens doivent correspondre."
        );
    }

    @Test
    void givenNotExistingToken_whenGetTokenByValueMethod_thenNullReturned() {
        // given : un token inexistant ?? rechercher
        Mockito
            .when(tokenRepositoryMock.findTokenByValue(Mockito.any()))
            .thenReturn(Optional.empty());
        // when : un getTokenByValue() est appel?? sur un tokenService
        Token resultToken = tokenService.getTokenByValue("email@adresse.truc");
        // then : le r??sultat est null
        assertNull(resultToken, "Le token doit ??tre null.");
    }

    @Test
    void whenCreateNewTokenForUser_thenUserIsAffected()
        throws BusinessException {
        // when : un token est cr???? pour un utilisateur
        Token resultToken = tokenService.createNewTokenForUser(testUser);

        // then : le token retourn?? contient bien l'utilisateur
        assertEquals(
            testUser.getLastName(),
            resultToken.getUser().getLastName(),
            "Les noms doivent correspondre."
        );
        assertEquals(
            testUser.getEmail(),
            resultToken.getUser().getEmail(),
            "Les emails doivent correspondre."
        );
        assertEquals(
            testUser.getFirstName(),
            resultToken.getUser().getFirstName(),
            "Les pr??noms doivent correspondre."
        );
        assertEquals(
            testUser.getPassword(),
            resultToken.getUser().getPassword(),
            "Les mots de passe doivent correspondre."
        );
        assertEquals(
            testUser.getCustomerAccount().getAddress(),
            resultToken.getUser().getCustomerAccount().getAddress(),
            "Les adresses doivent correspondre."
        );
        assertEquals(
            testUser.getCustomerAccount().getPseudo(),
            resultToken.getUser().getCustomerAccount().getPseudo(),
            "Les pseudos doivent correspondre."
        );
        assertEquals(
            testUser.getSellerAccount().getStoreName(),
            resultToken.getUser().getSellerAccount().getStoreName(),
            "Les noms de magasin doivent correspondre."
        );
    }

    @Test
    void whenGenerateExpiryDate_thenGenerationIsCorrect() {
        // given : une date ult??rieure ?? la date d'expiration
        Calendar calendarTest = Calendar.getInstance();
        calendarTest.setTime(new Date());
        calendarTest.add(
            Calendar.MINUTE,
            MoneyDystopieConstants.TOKEN_DURABILITY_IN_MINUTES + 1
        );
        // when : on g??n??re une date
        Date returnedDate = tokenService.generateTokenExpiryDate();
        // then : la date g??n??r??e est dans l'intervalle
        assertTrue(
            calendarTest.getTime().after(returnedDate),
            "A la date attendue, le token devrait ??tre expir??."
        );
        assertTrue(
            new Date().before(returnedDate),
            "A la date d'aujourd'hui, le token devrait ??tre valide."
        );
    }

    @Test
    void whenGenerateTokenTwice_thenBothTokenAreDistinct()
        throws BusinessException {
        // when : deux tokens sont g??n??r??s
        String tokenValue1 = tokenService.generateToken();
        String tokenValue2 = tokenService.generateToken();
        // then : les deux tokens ne sont pas identiques
        assertNotEquals(
            tokenValue1,
            tokenValue2,
            "Les deux valeurs du token devraient ??tre diff??rentes."
        );
    }

    @Test
    void whenGenerateToken_thenTokenHasCorrectSize() throws BusinessException {
        // when : on g??n??re un token
        String tokenValue = tokenService.generateToken();
        // then : la valeur du token est pr??cis??ment celle attendue
        assertEquals(
            MoneyDystopieConstants.TOKEN_LENGTH,
            tokenValue.length(),
            "La taille du token devrait ??tre d??finie ?? " +
            MoneyDystopieConstants.TOKEN_LENGTH
        );
    }

    @Test
    void whenGenerateAlreadyExistingTokenValue_thenExceptionThrown() {
        Mockito
            .when(tokenRepositoryMock.findTokenByValue(Mockito.any()))
            .thenReturn(Optional.of(testToken));
        // when : on g??n??re un token dont la valeur existe d??j??
        // then : une exception est lev??e
        assertThrows(
            BusinessException.class,
            () -> tokenService.generateToken()
        );
    }

    @Test
    void givenValidToken_whenIsValidToken_thenTrueReturned() {
        // given : un token invalide avec une date d??pass??e
        Token tokenTest = new Token();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 10000);
        tokenTest.setExpirationDate(calendar.getTime());
        // when : on v??rifie la validit?? du token
        boolean result = tokenService.isTokenValid(tokenTest);
        // then : le token est invalide
        assertTrue(result, "Le token devrait ??tre valide.");
    }

    @Test
    void givenNotValidToken_whenIsValidToken_thenFalseReturned() {
        // given : un token valide avec une date non d??pass??e
        Token tokenTest = new Token();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, -1);
        tokenTest.setExpirationDate(calendar.getTime());
        // when : on v??rifie la validit?? du token
        boolean result = tokenService.isTokenValid(tokenTest);
        // then : le token est valide
        assertFalse(result, "Le token devrait ??tre invalide.");
    }

    @Test
    void givenNulToken_whenIsValidToken_thenFalseReturned() {
        // when : on v??rifie la validit?? du token null
        boolean result = tokenService.isTokenValid(null);
        // then : le token est valide
        assertFalse(result, "Le token devrait ??tre invalide.");
    }

    @Test
    void givenNullTokenAndCorrectUser_whenIsTokenUserAssociationValid_thenFalseReturned() {
        // when : on v??rifie l'association token / utilisateur
        boolean response = tokenService.isTokenUserAssociationValid(
            null,
            testUser
        );
        // then : l'association est incorrecte
        assertFalse(
            response,
            "L'association user - token devrait ??tre invalide."
        );
    }

    @Test
    void givenCorrectTokenAndNullUser_whenIsTokenUserAssociationValid_thenFalseReturned() {
        // given : un token correct et un utilisateur null
        Token token = new Token(null, "token", new Date(), null);
        // when : on v??rifie l'association token / utilisateur
        boolean response = tokenService.isTokenUserAssociationValid(
            token,
            null
        );
        // then : l'association est incorrecte
        assertFalse(
            response,
            "L'association user - token devrait ??tre invalide."
        );
    }

    @Test
    void givenNullTokenAndNullUser_whenIsTokenUserAssociationValid_thenFalseReturned() {
        // given : un token null et un utilisateur null
        // when : on v??rifie l'association token / utilisateur
        boolean response = tokenService.isTokenUserAssociationValid(null, null);
        // then : l'association est incorrecte
        assertFalse(
            response,
            "L'association user - token devrait ??tre invalide."
        );
    }

    @Test
    void givenCorrectTokenAndCorrectUserWithRightAssociation_whenIsTokenUserAssociationValid_thenTrueReturned()
        throws BusinessException {
        // given : un token correct et un utilisateur correct avec une association valide
        Token testToken = tokenService.createNewTokenForUser(testUser);
        // when : on v??rifie l'association token / utilisateur
        boolean response = tokenService.isTokenUserAssociationValid(
            testToken,
            testUser
        );
        // then : l'association est incorrecte
        assertTrue(response, "L'association user - token devrait ??tre valide.");
    }

    @Test
    void givenValidUserAndValidToken_whenPerformNewTokenRequest_thenTokenReturned()
        throws BusinessException {
        Mockito
            .when(tokenRepositoryMock.findTokenByValue(Mockito.any()))
            .thenReturn(Optional.of(testToken))
            .thenReturn(Optional.empty());
        Mockito
            .when(userServiceMock.checkUserPassword(Mockito.any()))
            .thenReturn(true);
        Mockito
            .when(userServiceMock.findByEmail(Mockito.any()))
            .thenReturn(testUser);
        Mockito
            .when(tokenRepositoryMock.save(Mockito.any()))
            .thenReturn(testToken);
        // when : On demande une cr??ation de token pour un utilisateur valide et un token valide
        Token returnedToken = tokenService.performNewTokenRequest(
            testUserConnexion,
            testToken.getValue()
        );
        // then : le save du nouveau token et le delete de l'ancien token sont appel??s et le token
        // retourn?? n'est pas null
        verify(tokenRepositoryMock).save(returnedToken);
        verify(tokenRepositoryMock).delete(testToken);
        assertNotNull(
            returnedToken,
            "Le token retourn?? ne doit pas ??tre null."
        );
    }

    @Test
    void givenNotValidUserAndNotValidToken_whenPerformNewTokenRequest_thenExceptionThrown()
        throws BusinessException {
        Mockito
            .when(tokenRepositoryMock.findTokenByValue(Mockito.any()))
            .thenReturn(Optional.of(testToken));
        Mockito
            .when(userServiceMock.checkUserPassword(Mockito.any()))
            .thenReturn(false);
        // when : On demande une cr??ation de token pour un utilisateur invalide et un token invalide
        assertThrows(
            BusinessException.class,
            () ->
                tokenService.performNewTokenRequest(
                    testUserConnexion,
                    testToken.getValue()
                ),
            "Une business exception doit ??tre lev??e."
        );
        // then : le token invalide est bien supprim??
        verify(tokenRepositoryMock).delete(testToken);
    }

    @Test
    void whenRemoveTokenByValueMethod_thenRepositoryTokenInvoked() {
        // when : un getTokenByValue() est appel?? sur un tokenService
        tokenService.getTokenByValue("email@adresse.truc");
        Mockito
            .when(tokenRepositoryMock.findTokenByValue(Mockito.any()))
            .thenReturn(Optional.of(testToken));
        // then : findTokenByValue() du d??p??t associ?? au service est invoqu??
        verify(tokenRepositoryMock).findTokenByValue("email@adresse.truc");
    }

    @Test
    void whenCreateCookie_thenCookieIsValid() {
        // given : un token
        Token token = new Token();
        token.setValue("5");

        // when : on cr???? le cookie du token
        Cookie cookie = tokenService.createTokenCookie(token);

        // then le cookie est valide
        assertTrue(
            cookie.isHttpOnly(),
            "Le cookie doit ??tre param??tr?? en HTTP only."
        );
        assertTrue(
            cookie.getSecure(),
            "Le cookie doit ??tre param??tr?? en secure mode."
        );
        assertEquals(
            "/",
            cookie.getPath(),
            "Le path du cookie doit ??tre root."
        );
        assertEquals(
            token.getValue(),
            cookie.getValue(),
            "La valeur du cookie doit ??tre la valeur du token."
        );
        assertEquals(
            MoneyDystopieConstants.COOKIE_NAME,
            cookie.getName(),
            "La cl?? du cookie doit ??tre celle d??finie dans les constantes."
        );
    }
}
