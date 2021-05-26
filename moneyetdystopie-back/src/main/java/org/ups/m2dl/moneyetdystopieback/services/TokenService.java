package org.ups.m2dl.moneyetdystopieback.services;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.TokenRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@Service
@AllArgsConstructor
public class TokenService {

    @Getter
    private final TokenRepository tokenRepository;

    @Getter
    private final UserService userService;

    public Token saveToken(Token token) {
        try {
            return tokenRepository.save(token);
        } catch (Exception e) {
            return null;
        }
    }

    public void removeToken(Token token) {
        tokenRepository.delete(token);
    }

    public Token getTokenByValue(String tokenValue) {
        return tokenRepository.findTokenByValue(tokenValue).orElse(null);
    }

    public User getUserByTokenValue(String tokenValue)
        throws BusinessException {
        Token token = getTokenByValue(tokenValue);
        User user;
        if (isTokenValid(token)) {
            user = token.getUser();
        } else {
            throw new BusinessException(
                MoneyDystopieConstants.EXPIRED_CONNEXION_ERROR
            );
        }
        return user;
    }

    public Token createNewTokenForUser(User user) throws BusinessException {
        Token newToken = new Token();
        newToken.setExpirationDate(generateTokenExpiryDate());
        newToken.setUser(user);
        newToken.setValue(generateToken());
        userService.addTokenToUser(user, newToken);
        return newToken;
    }

    public Date generateTokenExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(
            Calendar.MINUTE,
            MoneyDystopieConstants.TOKEN_DURABILITY_IN_MINUTES
        );
        return calendar.getTime();
    }

    /** Génère un token d'une taille spécifique aléatoirement */
    public String generateToken() throws BusinessException {
        String tokenValue;
        String caracMin = "abcdefghijklmnopqrstuvwxyz";
        String caracMaj = caracMin.toUpperCase();
        String chiffres = "0123456789";
        String carac = caracMin + caracMaj + chiffres;
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(
            MoneyDystopieConstants.TOKEN_LENGTH
        );
        for (int i = 0; i < MoneyDystopieConstants.TOKEN_LENGTH; i++) {
            int rndCharAt = secureRandom.nextInt(carac.length());
            char rndChar = carac.charAt(rndCharAt);
            stringBuilder.append(rndChar);
        }
        tokenValue = stringBuilder.toString();
        if (getTokenByValue(tokenValue) != null) {
            throw new BusinessException("");
        }
        return tokenValue;
    }

    public boolean isTokenValid(Token token) {
        return (token != null && token.getExpirationDate().after(new Date()));
    }

    public boolean isTokenUserAssociationValid(Token token, User user) {
        return (
            token != null &&
            user != null &&
            token.getUser().getEmail().equals(user.getEmail())
        );
    }

    public Token performNewTokenRequest(User user, String ancientTokenValue)
        throws BusinessException {
        Token returnedToken;
        Token ancientToken = getTokenByValue(ancientTokenValue);
        if (userService.checkUserPassword(user)) {
            user = userService.findByEmail(user.getEmail());
            returnedToken = createNewTokenForUser(user);
            saveToken(returnedToken);
            if (ancientToken != null) {
                removeToken(ancientToken);
            }
        } else if (
            (user.getEmail() == null || user.getPassword() == null) &&
            isTokenValid(ancientToken)
        ) {
            returnedToken = ancientToken;
        } else {
            if (ancientToken != null) {
                removeToken(ancientToken);
            }
            throw new BusinessException(
                MoneyDystopieConstants.INVALID_CONNEXION_ERROR
            );
        }
        return returnedToken;
    }

    public boolean removeTokenByValue(String tokenValue)
        throws BusinessException {
        Token token = getTokenByValue(tokenValue);
        removeToken(token);
        userService.removeTokenToUser(token.getUser(), token);
        return true;
    }

    public Cookie createTokenCookie(Token token) {
        Cookie cookie = new Cookie(
            MoneyDystopieConstants.COOKIE_NAME,
            token.getValue()
        );
        cookie.setMaxAge(
            MoneyDystopieConstants.TOKEN_DURABILITY_IN_MINUTES * 60
        );
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }
}
