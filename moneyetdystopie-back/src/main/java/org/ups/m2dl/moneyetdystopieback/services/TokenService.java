package org.ups.m2dl.moneyetdystopieback.services;

import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.TokenRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final UserService userService;

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
            user = token.getUser();
        }else{
            throw new BusinessException("Votre session a expiré, veuillez vous reconnecter.");
        }
        return user;
    }

    public Token createNewTokenForUser(User user) throws BusinessException {
        Token newToken = new Token();
        newToken.setExpirationDate(generateTokenExpiryDate());
        newToken.setUser(user);
        newToken.setValue(generateToken());
        userService.addTokenToUser(user,newToken);
        return newToken;
    }

    public Date generateTokenExpiryDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, MoneyDystopieConstants.TOKEN_DURABILITY_IN_MINUTES);
        return calendar.getTime();
    }

    /**
     * Génère un token d'une taille spécifique aléatoirement
     */
    public String generateToken() throws BusinessException{
        String tokenValue;
        String caracMin = "abcdefghijklmnopqrstuvwxyz";
        String caracMaj = caracMin.toUpperCase();
        String chiffres = "0123456789";
        String carac = caracMin + caracMaj + chiffres;
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(MoneyDystopieConstants.TOKEN_LENGTH);
        for (int i = 0; i < MoneyDystopieConstants.TOKEN_LENGTH; i++) {
            int rndCharAt = secureRandom.nextInt(carac.length());
            char rndChar = carac.charAt(rndCharAt);
            stringBuilder.append(rndChar);
        }
        tokenValue = stringBuilder.toString();
        if (getTokenByValue(tokenValue) != null){
            throw new BusinessException("");
        }
        return tokenValue;
    }

    public boolean isTokenValid(Token token){
        return (token != null && token.getExpirationDate().before(new Date()));
    }

    public boolean isTokenUserAssociationValid(Token token, User user){
        return (token != null && user != null && token.getUser().getEmail().equals(user.getEmail()));
    }

    public Token performNewTokenRequest(User user, String ancientTokenValue) throws BusinessException {
        Token newToken;
        Token ancientToken = getTokenByValue(ancientTokenValue);
        if ((isTokenValid(ancientToken) && isTokenUserAssociationValid(ancientToken, user)) || userService.checkUserPassword(user)){
            newToken = createNewTokenForUser(user);
            saveToken(newToken);
            if (ancientToken != null){
                removeToken(ancientToken);
            }
        }else{
            if (ancientToken != null){
                removeToken(ancientToken);
            }
            throw new BusinessException("Impossible de vous connecter avec les identifiants renseignés.");
        }
        return newToken;
    }

    public boolean removeTokenByValue(String tokenValue) throws BusinessException {
        Token token = getTokenByValue(tokenValue);
        removeToken(token);
        userService.removeTokenToUser(token.getUser(),token);
        return true;
    }
}