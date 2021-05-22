package org.ups.m2dl.moneyetdystopieback.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ups.m2dl.moneyetdystopieback.bean.ItemBean;
import org.ups.m2dl.moneyetdystopieback.bean.ItemCommandBean;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.domain.ItemCommand;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.ItemCommandRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

@AllArgsConstructor
@Service
public class ItemCommandService {

    @Getter
    private final ItemCommandRepository itemCommandRepository;

    @Getter
    private final ItemService itemService;

    @Transactional
    public ItemCommand create(ItemCommand itemCommand) throws BusinessException {

        if(itemCommand.getItem() == null) {
            throw new BusinessException(
                    "L'item référencé n'a pu être trouvé."
            );
        }
        itemCommand.setItem(itemService.findById(itemCommand.getItem().getId()));

        this.valid(itemCommand);

        // Décrémente la quantité d'item de la boutique en question
        if(itemCommand.getItem().getAmount() < itemCommand.getAmount()){
            throw new BusinessException(
                    "La quantité d'item demandé pour la commande est supérieure à celle-ci pouvant être fournie."
            );
        }

        itemCommand.getItem().setAmount(itemCommand.getItem().getAmount() - itemCommand.getAmount());


        itemService.save(itemCommand.getItem());
        return save(itemCommand);
    }

    @Transactional
    public ItemCommand save(ItemCommand itemCommand) throws BusinessException {
        if (itemCommand == null) {
            throw new BusinessException(
                "Un ItemCommand non défini ne peut être sauvegardé."
            );
        }
        try {
            return itemCommandRepository.save(itemCommand);
        } catch (Exception e) {
            throw new BusinessException(
                "Une erreur est survenue lors de l'enregistrement de l'article." +
                (e.getMessage() == null ? e.getMessage() : "")
            );
        }
    }

    public void valid(ItemCommand itemCommand) throws BusinessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ItemCommand>> constraintViolations = validator.validate(
                itemCommand
        );

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<ItemCommand>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }

    public static ItemCommandBean getBean(ItemCommand itemCommand) {
        ItemCommandBean itemCommandBean = new ItemCommandBean();
        BeanUtils.copyProperties(itemCommand, itemCommandBean);

        if (itemCommand.getItem() != null) {
            itemCommandBean.setItem(new ItemBean());
            BeanUtils.copyProperties(
                    itemCommand.getItem(),
                    itemCommandBean.getItem()
            );
        }

        return itemCommandBean;
    }

    public static ItemCommand getDto(ItemCommandBean itemCommandBean) {
        ItemCommand itemCommand = new ItemCommand();
        BeanUtils.copyProperties(itemCommandBean, itemCommand);

        if (itemCommandBean.getItem() != null) {
            itemCommand.setItem(new Item());
            BeanUtils.copyProperties(
                    itemCommandBean.getItem(),
                    itemCommand.getItem()
            );
        }

        return itemCommand;
    }
}
