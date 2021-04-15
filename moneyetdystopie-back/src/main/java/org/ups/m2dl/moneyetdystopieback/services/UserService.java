package org.ups.m2dl.moneyetdystopieback.services;

import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.repositories.UtilisateurRepository;

@Service
public class UserService {
    private UtilisateurRepository utilisateurRepository;

    public UserService(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;
    }
}
