package org.ups.m2dl.moneyetdystopieback.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ups.m2dl.moneyetdystopieback.bean.CommandBean;
import org.ups.m2dl.moneyetdystopieback.bean.CustomerBean;
import org.ups.m2dl.moneyetdystopieback.bean.ItemBean;
import org.ups.m2dl.moneyetdystopieback.domain.Command;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.OrderRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {

    @Getter
    private final OrderRepository orderRepository;

    @Getter
    private final ItemService itemService;
    @Getter
    private final CustomerService customerService;

    @Transactional
    public Command create(Command order) throws BusinessException {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new BusinessException(
                    "La commande doit comporter au moins un article."
            );
        }

        this.valid(order);

        for(Item item : order.getItems()) {
            itemService.create(item);
        }

        Command savedOrder = save(order);

        savedOrder.getCustomer().addPastCommand(savedOrder);

        return savedOrder;
    }

    public Command save(Command order) throws BusinessException {
        if (order == null) {
            throw new BusinessException(
                    "Une commande non définie ne peut être sauvegardé."
            );
        }
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new BusinessException(
                    "Une erreur est survenue lors de l'enregistrement de la commande." +
                            (e.getMessage() == null ? e.getMessage() : "")
            );
        }
    }

    public void valid(Command order) throws BusinessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Command>> constraintViolations = validator.validate(
                order
        );

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<Command>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }

    public static CommandBean getBean(Command order) {
        CommandBean orderBean = new CommandBean();
        BeanUtils.copyProperties(order, orderBean);
        orderBean.setCustomer(CustomerService.getBean(order.getCustomer()));
        orderBean.setItems(order.getItems().stream().map(ItemService::getBean).collect(Collectors.toList()));
        return orderBean;
    }

    public static Command getDto(CommandBean orderBean) {
        Command order = new Command();
        BeanUtils.copyProperties(orderBean, order);
        order.setCustomer(CustomerService.getDto(orderBean.getCustomer()));
        order.setItems(orderBean.getItems().stream().map(ItemService::getDto).collect(Collectors.toList()));
        return order;
    }

}
