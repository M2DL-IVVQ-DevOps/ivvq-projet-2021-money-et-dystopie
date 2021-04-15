package org.ups.m2dl.moneyetdystopieback.services;

import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.Utilisateur;
import org.ups.m2dl.moneyetdystopieback.repositories.TokenRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {
    private TokenRepository tokenRepository;
    private UtilisateurService utilisateurService;

    //TODO Remplacer cette valeur par une vraie valeur, actuellement c'est la valeur de test
    private final int TOKEN_DURABILITY_IN_HOURS = 1;


    public TokenService(TokenRepository tokenRepository, UtilisateurService utilisateurService){
        this.tokenRepository = tokenRepository;
        this.utilisateurService = utilisateurService;
    }

    public Token saveToken(Token token){
        return tokenRepository.save(token);
    }

    public Token getTokenByValue(String tokenValue){
        return tokenRepository.findTokenByValue(tokenValue).orElse(null);
    }

    public Token createNewTokenForUser(Utilisateur utilisateur){
        Token newToken = new Token();
        newToken.setExpiration_date(generateTokenExpiryDate());
        newToken.setUtilisateur(utilisateur);
        newToken.setValue(generateToken());
        return newToken;
    }

    public Date generateTokenExpiryDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, TOKEN_DURABILITY_IN_HOURS);
        return calendar.getTime();
    }

    public String generateToken(){
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
        return stringBuilder.toString();
    }

    public boolean isTokenValid(Token token){
        return (token == null || token.getExpiration_date().before(new Date()));
    }
}
