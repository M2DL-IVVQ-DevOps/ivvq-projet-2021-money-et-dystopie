package org.ups.m2dl.moneyetdystopieback.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.bean.CommandBean;
import org.ups.m2dl.moneyetdystopieback.domain.Command;
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
    public ResponseEntity<Object> create(@RequestBody CommandBean command) {
        try {
            Command commandDto = CommandService.getDto(command);
            Command resultDto = commandService.create(commandDto);
            CommandBean result = CommandService.getBean(resultDto);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT);
        }
    }
}
