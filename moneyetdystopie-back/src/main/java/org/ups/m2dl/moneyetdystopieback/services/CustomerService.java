package org.ups.m2dl.moneyetdystopieback.services;

import java.util.Iterator;
import java.util.Set;
import javax.validation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.CustomerRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@Service
public class CustomerService {

    @Getter
    private final CustomerRepository customerRepository;

    public Customer create(Customer customer) throws BusinessException {
        this.valid(customer);

        customer.setCart(null);
        customer.setPastCommands(null);

        if (this.findByPseudo(customer.getPseudo()) != null) {
            throw new BusinessException(
                "Un acheteur '" + customer.getPseudo() + "' existe déjà."
            );
        }

        return this.save(customer);
    }

    public Customer save(Customer customer) throws BusinessException {
        if (customer == null) {
            throw new BusinessException(
                MoneyDystopieConstants.UNDEFINED_CUSTOMER_ERROR
            );
        }
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new BusinessException(
                MoneyDystopieConstants.REGISTER_CUSTOMER_ERROR
            );
        }
    }

    public Customer findByPseudo(String pseudo) {
        if (pseudo == null || pseudo.isBlank()) {
            return null;
        }
        return customerRepository.findByPseudo(pseudo).orElse(null);
    }

    public void valid(Customer customer) throws BusinessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(
            customer
        );

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<Customer>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }
}
