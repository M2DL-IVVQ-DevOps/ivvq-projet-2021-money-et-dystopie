package org.ups.m2dl.moneyetdystopieback.controllers;

import java.nio.file.AccessDeniedException;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
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

    @Getter
    private final UserService userService;

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
            User user = userService.getDto(userBean);
            Token token = tokenService.performNewTokenRequest(user, tokenValue);
            response.addCookie(tokenService.createTokenCookie(token));
            return ResponseEntity.status(HttpStatus.OK).body(userService.getBean(token.getUser()));
        }catch (BusinessException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(
                    MoneyDystopieConstants.DEFAULT_ERROR_CONTENT
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
                    userService.getBean(
                        tokenService.getUserByTokenValue(tokenValue)
                    )
                );
        } catch (BusinessException e) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    MoneyDystopieConstants.DEFAULT_ERROR_CONTENT
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
                    MoneyDystopieConstants.DEFAULT_ERROR_CONTENT
                );
        }
    }
}
