package org.ups.m2dl.moneyetdystopieback.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.ups.m2dl.moneyetdystopieback.bean.CustomerBean;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.CustomerRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

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
                "Un acheteur non défini ne peut être sauvegardé."
            );
        }
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new BusinessException(
                "Une erreur est survenue lors de l'enregistrement de l'acheteur." +
                (e.getMessage() != null ? e.getMessage() : "")
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

    public static CustomerBean getBean(Customer customer) {
        CustomerBean customerBean = new CustomerBean();
        BeanUtils.copyProperties(customer, customerBean);
        customerBean.setUserAccount(UserService.getBean(customer.getUserAccount()));
        // FIXME La conversion Bean <-> DTO induit une boucle infinie.
        //  Le code ci dessous est commenté pour éviter un StackOverFlow.
        //  La conversion est incomplète du coup.
        //if(customer.getPastCommands() != null) {
        //    customerBean.setPastCommands(customer.getPastCommands().stream().map(OrderService::getBean).collect(Collectors.toList()));
        //}
        return customerBean;
    }

    public static Customer getDto(CustomerBean customerBean) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerBean, customer);
        customer.setUserAccount(UserService.getDto(customerBean.getUserAccount()));
        // FIXME La conversion Bean <-> DTO induit une boucle infinie.
        //  Le code ci dessous est commenté pour éviter un StackOverFlow.
        //  La conversion est incomplète du coup.
        //if(customerBean.getPastCommands() != null) {
        //    customer.setPastCommands(customerBean.getPastCommands().stream().map(OrderService::getDto).collect(Collectors.toList()));
        //}
        return customer;
    }
}
