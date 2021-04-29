package org.ups.m2dl.moneyetdystopieback.services;

import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.TokenRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

import java.nio.file.AccessDeniedException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final UserService userService;

    //TODO Remplacer cette valeur par une vraie valeur, actuellement c'est la valeur de test
    private final int TOKEN_DURABILITY_IN_HOURS = 1;


    public TokenService(TokenRepository tokenRepository, UserService userService){
        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }

    public Token saveToken(Token token) {
        try{
            return tokenRepository.save(token);
        }catch(Exception e){
            return null;
        }
    }

    public void removeToken(Token token){ tokenRepository.delete(token);}

    public Token getTokenByValue(String tokenValue){
        return tokenRepository.findTokenByValue(tokenValue).orElse(null);
    }

    public User getUserByTokenValue(String tokenValue) throws BusinessException{
        Token token = getTokenByValue(tokenValue);
        User user;
        if (isTokenValid(token)){
            user = token.getUtilisateur();
        }else{
            throw new BusinessException("");
        }
        return user;
    }

    public Token createNewTokenForUser(User user){
        Token newToken = new Token();
        newToken.setExpiration_date(generateTokenExpiryDate());
        newToken.setUtilisateur(user);
        newToken.setValue(generateToken());
        return newToken;
    }

    public Date generateTokenExpiryDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, TOKEN_DURABILITY_IN_HOURS);
        return calendar.getTime();
    }

    /**
     * Génère un token d'une taille spécifique aléatoirement
     */
    public String generateToken() throws IllegalStateException{
        String tokenValue;
        String carac_min = "abcdefghijklmnopqrstuvwxyz";
        String carac_maj = carac_min.toUpperCase();
        String chiffres = "0123456789";
        String symboles = "#{([|)]}$£?!€@.;,";
        String carac = carac_min + carac_maj + chiffres + symboles;
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(MoneyDystopieConstants.TOKEN_LENGTH);
        for (int i = 0; i < MoneyDystopieConstants.TOKEN_LENGTH; i++) {
            int rndCharAt = secureRandom.nextInt(carac.length());
            char rndChar = carac.charAt(rndCharAt);
            stringBuilder.append(rndChar);
        }
        tokenValue = stringBuilder.toString();
        if (getTokenByValue(tokenValue) != null){
            throw new IllegalStateException("");
        }
        return tokenValue;
    }

    public boolean isTokenValid(Token token){
        return (token == null || token.getExpiration_date().before(new Date()));
    }

    public boolean isTokenUserAssociationValid(Token token, User user){
        return (token.getUtilisateur().getEmail().equals(user.getEmail()));
    }

    public Token performNewTokenRequest(User user, String ancientTokenValue) throws AccessDeniedException {
        Token newToken;
        Token ancientToken = getTokenByValue(ancientTokenValue);
        if ((isTokenValid(ancientToken) && isTokenUserAssociationValid(ancientToken, user) || userService.checkUserPassword(user))){
            newToken = createNewTokenForUser(user);
            saveToken(newToken);
            removeToken(ancientToken);
        }else{
            removeToken(ancientToken);
            throw new AccessDeniedException("");
        }
        return newToken;
    }

    public Token removeTokenByValue(String tokenValue){
        Token token = getTokenByValue(tokenValue);
        removeToken(token);
        return token;
    }
}
