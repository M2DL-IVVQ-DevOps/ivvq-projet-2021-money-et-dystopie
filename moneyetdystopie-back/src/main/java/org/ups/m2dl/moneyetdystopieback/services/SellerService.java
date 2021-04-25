package org.ups.m2dl.moneyetdystopieback.services;

import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.SellerRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class SellerService {

    private SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository){
        this.sellerRepository = sellerRepository;
    }

    public SellerRepository getSellerRepository() {
        return sellerRepository;
    }

    public Seller create(Seller seller) throws BusinessException{

        try {
            this.valid(seller);
        }catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
        seller.setCommands(null);
        seller.setItems(null);

        if(!this.findByStoreName(seller.getStoreName()).isEmpty()){
            throw new BusinessException("Une boutique '" + seller.getStoreName() + "' existe déjà.");
        }

        try {
            return this.save(seller);
        }catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public Seller save(Seller seller) throws BusinessException {

        if(seller == null){
            throw new BusinessException("Une boutique non défini ne peut être sauvegardé.");
        }
        try{
            return sellerRepository.save(seller);
        }catch (Exception e){
            throw new BusinessException("Une erreur est survenue lors de l'enregistrement de la boutique." + (e.getMessage() != null? e.getMessage():""));
        }

    }

    public List<Seller> findByStoreName(String storeName) {
        return sellerRepository.findByStoreName(storeName);
    }


    public void valid(Seller seller) throws BusinessException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Seller>> constraintViolations =
                validator.validate(seller);

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<Seller>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }
}
