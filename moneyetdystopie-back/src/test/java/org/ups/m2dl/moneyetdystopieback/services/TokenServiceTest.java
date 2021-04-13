package org.ups.m2dl.moneyetdystopieback.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.Utilisateur;
import org.ups.m2dl.moneyetdystopieback.repositories.TokenRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class TokenServiceTest {
    private TokenService tokenService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UtilisateurService utilisateurService;

    @MockBean
    private Token token;

    @MockBean
    private Utilisateur utilisateur;

    @Before
    public void setup(){
        tokenService = new TokenService(tokenRepository, utilisateurService);
    }

    @Test
    public void testGeneratedTokenHasCorrectLength(){
        // when : on génère un token
        String tokenValue = tokenService.generateToken();
        // then : la valeur du token est précisément celle attendue
        assertEquals(tokenValue.length(), MoneyDystopieConstants.TOKEN_LENGTH);
    }

    @Test
    public void testTwoDistinctsGeneratedTokensAreNotEquals(){
        // when : deux tokens sont générés
        String tokenValue1 = tokenService.generateToken();
        String tokenValue2 = tokenService.generateToken();
        // then : les deux tokens ne sont pas identiques
        assertNotEquals(tokenValue1, tokenValue2);
    }

    @Test
    public void testSaveTokenIsDelegatedRepository(){
        // when : un saveToken() est appelé sur un tokenService
        tokenService.saveToken(token);
        // then : saveToken() du dépôt associé au service est invoqué
        verify(tokenRepository).save(token);
    }

    @Test
    public void testCreateNewTokenForUserGivesCorrectUser(){
        // given : un utilisateur
        Utilisateur utilisateurTest = new Utilisateur();
        utilisateurTest.setPrenom("Patrick");
        utilisateurTest.setNom("Bob");
        utilisateurTest.setAdresseMail("bob.patrick@rien.com");
        // when : un nouveau token est créé pour cet utilisateur
        Token newToken = tokenService.createNewTokenForUser(utilisateurTest);
        // then : le token de cet utilisateur n'est pas null
        assertEquals(utilisateurTest.getNom(), newToken.getUtilisateur().getNom());
        assertEquals(utilisateurTest.getAdresseMail(), newToken.getUtilisateur().getAdresseMail());
        assertEquals(utilisateurTest.getPrenom(), newToken.getUtilisateur().getPrenom());
    }

    @Test
    public void testIsTokenValidWithNotValidToken(){
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
    public void testIsTokenValidWithValidToken(){
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
    public void testIsTokenValidWithNull(){
        // given : un token null
        Token tokenTest = null;
        // when : on vérifie la validité du token
        boolean result = tokenService.isTokenValid(tokenTest);
        // then : le token est valide
        assertTrue(result);
    }


}