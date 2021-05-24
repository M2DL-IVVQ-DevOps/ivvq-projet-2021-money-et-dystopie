package org.ups.m2dl.moneyetdystopieback.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ups.m2dl.moneyetdystopieback.bean.CommandBean;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.CommandService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@RestController
@RequestMapping("/command")
public class CommandController {

    @Getter
    @Setter
    private CommandService commandService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@Param("cardNumber") String cardNumber, @RequestBody CommandBean command) {
        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    CommandService.getBean(
                        commandService.create(CommandService.getDto(command), cardNumber)
                    )
                );
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .badRequest()
                .body(MoneyDystopieConstants.DEFAULT_ERROR_CONTENT);
        }
    }
}
