package org.ups.m2dl.moneyetdystopieback.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessMessage;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value="/user/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody UserBean user) {
        try{
            return ResponseEntity.status(200).body(userService.getBean(userService.create(userService.getDto(user))));
        }catch (BusinessException e){
            return ResponseEntity.badRequest().body(new BusinessMessage(e));
        }
    }

}
