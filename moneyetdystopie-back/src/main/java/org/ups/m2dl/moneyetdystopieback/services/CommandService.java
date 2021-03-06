package org.ups.m2dl.moneyetdystopieback.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import org.ups.m2dl.moneyetdystopieback.bean.*;
import org.ups.m2dl.moneyetdystopieback.domain.*;
import org.ups.m2dl.moneyetdystopieback.enums.CommandState;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.CommandRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@Service
public class CommandService {

    @Getter
    private final CommandRepository commandRepository;

    @Getter
    private final ItemService itemService;

    @Getter
    private final ItemCommandService itemCommandService;

    @Getter
    private final CustomerService customerService;

    @Getter
    private final SellerService sellerService;

    @Transactional
    public Command create(Command command, String cardNumber, User user)
        throws BusinessException {
        final CardService cardService = new CardService(cardNumber);
        if (!cardService.isCardNumberValid()) {
            throw new BusinessException(
                MoneyDystopieConstants.WRONG_CARD_NUMBER_ERROR
            );
        }

        if (command.getCustomer() == null) {
            throw new BusinessException(
                MoneyDystopieConstants.UNDEFINED_CUSTOMER_ERROR
            );
        }
        Customer customer = user.getCustomerAccount();

        if (customer == null) {
            throw new BusinessException(
                MoneyDystopieConstants.UNDEFINED_CUSTOMER_ERROR
            );
        }
        command.setCustomer(customer);

        command.setState(CommandState.WAITING_FOR_SHIPMENT);

        this.valid(command);

        List<ItemCommand> itemCommandsSaved = new ArrayList<>();
        for (int i = 0; i < command.getItemCommands().size(); i++) {
            itemCommandsSaved.add(
                itemCommandService.create(command.getItemCommands().get(i))
            );
        }
        command.setItemCommands(itemCommandsSaved);

        command = this.save(command);

        command.getCustomer().addPastCommand(command);
        command.setCustomer(customerService.save(command.getCustomer()));

        for (ItemCommand itemCommand : command.getItemCommands()) {
            Seller seller = sellerService.findByStoreName(
                itemCommand.getItem().getSellerAccount().getStoreName()
            );
            seller.addCommand(command);
            sellerService.save(seller);
        }

        return command;
    }

    public Command save(Command command) throws BusinessException {
        if (command == null) {
            throw new BusinessException(
                MoneyDystopieConstants.REGISTER_COMMAND_ERROR
            );
        }
        try {
            return commandRepository.save(command);
        } catch (Exception e) {
            throw new BusinessException(
                MoneyDystopieConstants.REGISTER_COMMAND_ERROR
            );
        }
    }

    public void valid(Command command) throws BusinessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Command>> constraintViolations = validator.validate(
            command
        );

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<Command>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }

    public static CommandBean getBean(Command command) {
        CommandBean commandBean = new CommandBean();
        BeanUtils.copyProperties(command, commandBean);

        if (command.getItemCommands() != null) {
            for (ItemCommand itemCommand : command.getItemCommands()) {
                commandBean.addItemsCommand(
                    ItemCommandService.getBean(itemCommand)
                );
            }
        }

        if (command.getCustomer() != null) {
            commandBean.setCustomer(new CustomerBean());
            BeanUtils.copyProperties(
                command.getCustomer(),
                commandBean.getCustomer()
            );
        }

        return commandBean;
    }

    public static List<CommandBean> getBean(List<Command> commands) {
        return commands
            .stream()
            .map(CommandService::getBean)
            .collect(Collectors.toList());
    }

    public static Command getDto(CommandBean commandBean) {
        Command command = new Command();
        BeanUtils.copyProperties(commandBean, command);

        if (commandBean.getItemCommands() != null) {
            for (ItemCommandBean itemCommandBean : commandBean.getItemCommands()) {
                command.addItemsCommand(
                    ItemCommandService.getDto(itemCommandBean)
                );
            }
        }

        if (commandBean.getCustomer() != null) {
            command.setCustomer(new Customer());
            BeanUtils.copyProperties(
                commandBean.getCustomer(),
                command.getCustomer()
            );
        }

        return command;
    }
}
