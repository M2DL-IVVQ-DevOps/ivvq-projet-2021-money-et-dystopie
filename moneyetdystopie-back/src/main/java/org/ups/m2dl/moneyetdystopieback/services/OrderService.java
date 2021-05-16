package org.ups.m2dl.moneyetdystopieback.services;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ups.m2dl.moneyetdystopieback.bean.CommandBean;
import org.ups.m2dl.moneyetdystopieback.bean.CustomerBean;
import org.ups.m2dl.moneyetdystopieback.domain.Command;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.enums.CommandState;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.OrderRepository;

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

        if(order.getState() != CommandState.WAITING_FOR_PAYMENT) {
            throw new BusinessException(
                    "La commande doit être en attente de paiement pour être créée."
            );
        }

        this.valid(order);

        for (Item item : order.getItems()) {
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
        CustomerBean c = new CustomerBean();
        BeanUtils.copyProperties(order.getCustomer(), c);
        orderBean.setCustomer(c);
        orderBean.setItems(
            order
                .getItems()
                .stream()
                .map(ItemService::getBean)
                .collect(Collectors.toList())
        );
        return orderBean;
    }

    public static Command getDto(CommandBean orderBean) {
        Command order = new Command();
        BeanUtils.copyProperties(orderBean, order);
        Customer c = new Customer();
        BeanUtils.copyProperties(orderBean.getCustomer(), c);
        order.setCustomer(c);
        order.setItems(
            orderBean
                .getItems()
                .stream()
                .map(ItemService::getDto)
                .collect(Collectors.toList())
        );
        return order;
    }
}
