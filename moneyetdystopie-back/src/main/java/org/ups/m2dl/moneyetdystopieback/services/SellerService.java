package org.ups.m2dl.moneyetdystopieback.services;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.SellerRepository;

@AllArgsConstructor
@Service
public class SellerService {

    @Getter
    private final SellerRepository sellerRepository;

    public Seller create(Seller seller) throws BusinessException {
        this.valid(seller);
        seller.setCommands(null);
        seller.setItems(null);

        if (this.findByStoreName(seller.getStoreName()) != null) {
            throw new BusinessException(
                "Une boutique '" + seller.getStoreName() + "' existe déjà."
            );
        }

        return this.save(seller);
    }

    public Seller save(Seller seller) throws BusinessException {
        if (seller == null) {
            throw new BusinessException(
                "Une boutique non défini ne peut être sauvegardé."
            );
        }
        try {
            return sellerRepository.save(seller);
        } catch (Exception e) {
            throw new BusinessException(
                "Une erreur est survenue lors de l'enregistrement de la boutique." +
                (e.getMessage() != null ? e.getMessage() : "")
            );
        }
    }

    public Seller findByStoreName(String storeName) {
        if (storeName == null || storeName.isBlank()) {
            return null;
        }
        return sellerRepository.findByStoreName(storeName).orElse(null);
    }

    public void valid(Seller seller) throws BusinessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Seller>> constraintViolations = validator.validate(
            seller
        );

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<Seller>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }
}
