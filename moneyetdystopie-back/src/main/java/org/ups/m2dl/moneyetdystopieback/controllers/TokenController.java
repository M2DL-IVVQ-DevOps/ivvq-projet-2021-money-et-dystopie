package org.ups.m2dl.moneyetdystopieback.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    public TokenController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @PostMapping(
            value="/create",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> create(@RequestBody UserBean userBean,
                                         HttpServletResponse response,
                                         @CookieValue(value="token", defaultValue = "none") String tokenValue) {
        try{
            User user = new User();
            BeanUtils.copyProperties(userBean,user);
            response.addCookie(tokenService.createTokenCookie(user, tokenValue));
            return ResponseEntity.status(200).body(true);
        }catch (BusinessException e){
            return ResponseEntity.badRequest().body(e);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT));
        }
    }

    @PostMapping(
            value="/check",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getUser(@CookieValue(value="token", defaultValue = "") String tokenValue) {
        try{
            User user = tokenService.getUserByTokenValue(tokenValue);
            UserBean userBean = new UserBean();
            BeanUtils.copyProperties(user,userBean);
            return ResponseEntity.status(200).body(userBean);
        }catch (BusinessException e){
            return ResponseEntity.badRequest().body(new AccessDeniedException(e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT));
        }
    }

    @PostMapping(
            value="/remove",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> remove(@CookieValue(value="token", defaultValue = "none") String tokenValue) {
        try{
            return ResponseEntity.status(200).body(tokenService.removeTokenByValue(tokenValue));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT));
        }
    }
}
