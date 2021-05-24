package org.ups.m2dl.moneyetdystopieback.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.CommandService;
import org.ups.m2dl.moneyetdystopieback.services.CustomerService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Getter
    private final CustomerService customerService;

    @GetMapping(value = "/getPastCommands", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPastCommands(@RequestParam String pseudo) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            CommandService.getBean(customerService.getPastCommands(pseudo))
                    );
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new Exception(MoneyDystopieConstants.DEFAULT_ERROR_CONTENT)
                    );
        }
    }
}
