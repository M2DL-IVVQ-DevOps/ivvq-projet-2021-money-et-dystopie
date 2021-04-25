package org.ups.m2dl.moneyetdystopieback.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("token")
public class TokenController {
    private final TokenService tokenService;

    public TokenController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @RequestMapping(
            value="/create",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody User user,
                                         @CookieValue(value="token", defaultValue = "none") String token) {
        try{
            return ResponseEntity.status(200).body(tokenService.performNewTokenRequest(user, token));
        }catch (AccessDeniedException e){
            return ResponseEntity.badRequest().body(new AccessDeniedException(e.getMessage()));
        }catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(new AccessDeniedException(e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT));
        }
    }

    @RequestMapping(
            value="/check",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST)
    public ResponseEntity<Object> check(@CookieValue(value="token", defaultValue = "none") String token) {
        try{
            return ResponseEntity.status(200).body(tokenService.getUserByTokenValue(token));
        }catch (AccessDeniedException e){
            return ResponseEntity.badRequest().body(new AccessDeniedException(e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT));
        }
    }

    @RequestMapping(
            value="/remove",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST)
    public ResponseEntity<Object> remove(@CookieValue(value="token", defaultValue = "none") String token) {
        try{
            return ResponseEntity.status(200).body(tokenService.removeTokenByValue(token));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT));
        }
    }
}
