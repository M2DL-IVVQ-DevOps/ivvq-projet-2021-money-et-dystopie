package org.ups.m2dl.moneyetdystopieback.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Command;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.CustomerRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    public List<Command> getPastCommands(String customerPseudo) throws BusinessException {
        Customer customer = findByPseudo(customerPseudo);
        if(customer == null){
            throw new BusinessException(
                    "L'acheuteur '" + customerPseudo + "' n'a pu être trouvé."
            );
        }
        return customer.getPastCommands();
    }
}
