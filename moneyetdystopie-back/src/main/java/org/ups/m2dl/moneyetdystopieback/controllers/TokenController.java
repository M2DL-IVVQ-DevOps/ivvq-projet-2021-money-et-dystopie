package org.ups.m2dl.moneyetdystopieback.controllers;

import java.nio.file.AccessDeniedException;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {

    @Getter
    private final TokenService tokenService;

    @PostMapping(
        value = "/create",
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Object> create(
        @RequestBody UserBean userBean,
        HttpServletResponse response,
        @CookieValue(value = "token", defaultValue = "none") String tokenValue
    ) {
        try {
            User user = UserService.getDto(userBean);
            response.addCookie(
                tokenService.createTokenCookie(user, tokenValue)
            );
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(
                    new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT)
                );
        }
    }

    @PostMapping(
        value = "/check",
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Object> getUserMatch(
        @CookieValue(value = "token", defaultValue = "") String tokenValue
    ) {
        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    UserService.getBean(
                        tokenService.getUserByTokenValue(tokenValue)
                    )
                );
        } catch (BusinessException e) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new AccessDeniedException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT)
                );
        }
    }

    @PostMapping(
        value = "/remove",
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Object> remove(
        @CookieValue(value = "token", defaultValue = "none") String tokenValue
    ) {
        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokenService.removeTokenByValue(tokenValue));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT)
                );
        }
    }
}
