package org.ups.m2dl.moneyetdystopieback.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.CommandService;
import org.ups.m2dl.moneyetdystopieback.services.CustomerService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Getter
    @Setter
    private CustomerService customerService;

    @Getter
    @Setter
    private CommandService commandService;

    @GetMapping(value = "/getPastCommands", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPastCommands(@RequestParam String pseudo) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            commandService.getBean(customerService.getPastCommands(pseudo))
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
