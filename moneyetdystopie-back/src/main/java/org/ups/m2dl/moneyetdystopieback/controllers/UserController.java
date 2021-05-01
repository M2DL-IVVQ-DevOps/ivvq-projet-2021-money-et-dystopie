package org.ups.m2dl.moneyetdystopieback.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

@AllArgsConstructor
@RestController
public class UserController {

    @Getter
    @Setter
    private UserService userService;

    @PostMapping(value="/user/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody UserBean user) {
        try{
            return ResponseEntity.status(200).body(userService.getBean(userService.create(userService.getDto(user))));
        }catch (BusinessException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
