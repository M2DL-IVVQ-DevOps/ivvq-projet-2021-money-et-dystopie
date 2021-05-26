package org.ups.m2dl.moneyetdystopieback.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.CommandService;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    @Getter
    private final UserService userService;
    @Getter
    private final SellerService sellerService;
    @Getter
    private final CommandService commandService;
    @Getter
    private final TokenService tokenService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody UserBean user) {
        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    UserService.getBean(
                        userService.create(UserService.getDto(user))
                    )
                );
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(MoneyDystopieConstants.DEFAULT_ERROR_CONTENT);
        }
    }

    @GetMapping(value = "/sellerCommands", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllCommandBySeller(@CookieValue(value = "token", defaultValue = "") String tokenValue) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(CommandService.getBean(sellerService.getAllCommands(tokenService.getUserByTokenValue(tokenValue))));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(MoneyDystopieConstants.DEFAULT_ERROR_CONTENT);
        }
    }
}
