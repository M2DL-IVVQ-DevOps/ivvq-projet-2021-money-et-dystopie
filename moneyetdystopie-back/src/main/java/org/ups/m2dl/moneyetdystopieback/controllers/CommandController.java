package org.ups.m2dl.moneyetdystopieback.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.bean.CommandBean;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.CommandService;
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@RestController
@RequestMapping("/command")
public class CommandController {

    @Getter
    private final CommandService commandService;

    @Getter
    private final TokenService tokenService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@Param("cardNumber") String cardNumber, @RequestBody CommandBean command, @CookieValue(value = "token", defaultValue = "") String tokenValue) {
        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    CommandService.getBean(
                        commandService.create(CommandService.getDto(command), cardNumber, tokenService.getUserByTokenValue(tokenValue))
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
}
