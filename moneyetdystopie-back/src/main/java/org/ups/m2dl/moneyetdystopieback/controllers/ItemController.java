package org.ups.m2dl.moneyetdystopieback.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ups.m2dl.moneyetdystopieback.bean.ItemBean;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.ItemService;

@AllArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {

    @Getter
    @Setter
    private ItemService itemService;

    @PostMapping(value="/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody ItemBean item) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(itemService.getBean(itemService.create(itemService.getDto(item))));
        }catch (BusinessException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
