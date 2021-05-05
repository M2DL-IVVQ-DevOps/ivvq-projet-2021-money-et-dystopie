package org.ups.m2dl.moneyetdystopieback.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.CustomerRepository;

import javax.validation.*;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class CustomerService {

    @Getter
    @Setter
    private final CustomerRepository customerRepository;

    public Customer create(Customer customer) throws BusinessException {

        this.valid(customer);

        customer.setCart(null);
        customer.setPastCommands(null);

        if(this.findByPseudo(customer.getPseudo()) != null){
            throw new BusinessException("Un acheteur '" + customer.getPseudo() + "' existe déjà.");
        }

        return this.save(customer);
    }


    public Customer save(Customer customer) throws BusinessException {

        if(customer == null){
            throw new BusinessException("Un acheteur non défini ne peut être sauvegardé.");
        }
        try{
            return customerRepository.save(customer);
        } catch (Exception e){
            throw new BusinessException("Une erreur est survenue lors de l'enregistrement de l'acheteur." + (e.getMessage() != null? e.getMessage() : ""));
        }
    }

    public Customer findByPseudo (String pseudo){
        if(pseudo==null || pseudo.isBlank()){
            return null;
        }
        Optional<Customer> customer = customerRepository.findByPseudo(pseudo);
        return customer.isPresent() ? customer.get() : null;
    }

    public void valid(Customer customer) throws BusinessException{

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<Customer>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }
}
