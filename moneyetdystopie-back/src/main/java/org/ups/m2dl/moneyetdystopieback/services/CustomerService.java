package org.ups.m2dl.moneyetdystopieback.services;

import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.CustomerRepository;

import javax.validation.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public Customer create(Customer customer) throws BusinessException {

        try {
            this.valid(customer);
        }catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }

        customer.setCart(null);
        customer.setPastCommands(null);

        if(!this.findByPseudo(customer.getPseudo()).isEmpty()){
            throw new BusinessException("Un acheteur '" + customer.getPseudo() + "' existe déjà.");
        }

        try {
            return this.save(customer);
        }catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
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

    public List<Customer> findByPseudo (String pseudo){
        return customerRepository.findByPseudo(pseudo);
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
