package org.ups.m2dl.moneyetdystopieback.services;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Command;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.SellerRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

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
                MoneyDystopieConstants.REGISTER_UNDEFINED_SHOP_ERROR
            );
        }
        try {
            return sellerRepository.save(seller);
        } catch (Exception e) {
            throw new BusinessException(
                MoneyDystopieConstants.REGISTER_SHOP_ERROR
            );
        }
    }

    public Seller findByStoreName(String storeName) {
        if (storeName == null || storeName.isBlank()) {
            return null;
        }
        return sellerRepository.findByStoreName(storeName).orElse(null);
    }

    public List<Command> getAllCommands(User user) throws BusinessException {
        if (user == null) {
            throw new BusinessException(
                    MoneyDystopieConstants.UNDEFINED_USER_ERROR
            );
        }
        if(user.getSellerAccount() == null) {
            throw new BusinessException(
                    MoneyDystopieConstants.UNFOUND_REFERENCED_SHOP_ERROR
            );
        }
        return user.getSellerAccount().getCommands();
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
