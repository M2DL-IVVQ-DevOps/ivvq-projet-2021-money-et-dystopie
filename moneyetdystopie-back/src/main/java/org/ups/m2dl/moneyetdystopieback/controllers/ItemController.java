package org.ups.m2dl.moneyetdystopieback.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.bean.ItemBean;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.ItemService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {

    @Getter
    @Setter
    private ItemService itemService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody ItemBean item) {
        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    ItemService.getBean(
                        itemService.create(ItemService.getDto(item))
                    )
                );
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(
                    new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT)
                );
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestBody ItemBean item) {
        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    ItemService.getBean(
                        itemService.update(ItemService.getDto(item))
                    )
                );
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(
                    new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT)
                );
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAll() {
        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(itemService.findAll());
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(
                    new Exception(MoneyDystopieConstants.CONTENUE_ERREUR_DEFAUT)
                );
        }
    }
}
