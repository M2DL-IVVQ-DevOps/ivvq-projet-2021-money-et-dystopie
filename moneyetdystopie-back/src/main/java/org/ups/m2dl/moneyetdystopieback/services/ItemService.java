package org.ups.m2dl.moneyetdystopieback.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ups.m2dl.moneyetdystopieback.bean.ItemBean;
import org.ups.m2dl.moneyetdystopieback.bean.SellerBean;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.ItemRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ItemService {

    @Getter
    @Setter
    private final ItemRepository itemRepository;

    @Getter
    @Setter
    private final SellerService sellerService;

    @Transactional
    public Item create(Item item) throws BusinessException {

            if (item.getSellerAccount() == null || item.getSellerAccount().getStoreName().isBlank()) {
                throw new BusinessException("La boutique n'est pas référencée.");
            }
            Seller seller = sellerService.findByStoreName(item.getSellerAccount().getStoreName());
            if (seller == null) {
                throw new BusinessException("La boutique référencée n'a pu être trouvée.");
            }
            item.setSellerAccount(seller);
            this.valid(item);

            item = this.save(item);

        try {
            item.getSellerAccount().addItem(item);

            sellerService.save(item.getSellerAccount());

            return item;
        } catch (BusinessException businessException){
            item.getSellerAccount().removeItem(item);
            throw businessException;
        }
    }

    public Item save(Item item) throws BusinessException {
        if(item == null){
            throw new BusinessException("Un article non défini ne peut être sauvegardé.");
        }
        try {
            return itemRepository.save(item);
        }catch (Exception e){
            throw new BusinessException("Une erreur est survenue lors de l'enregistrement de l'article." + (e.getMessage() == null ? e.getMessage() : ""));
        }
    }

    public Item findById(Long id) {
        if(id == null){
            return null;
        }
        return itemRepository.findById(id).orElse(null);
    }

    public List<Item> findAll() {
        return (List<Item>) itemRepository.findAll();
    }

    public void valid(Item item) throws BusinessException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Item>> constraintViolations =
                validator.validate(item);

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<Item>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }

    public ItemBean getBean(Item item) {
        ItemBean itemBean = new ItemBean();
        BeanUtils.copyProperties(item, itemBean);

        if(item.getSellerAccount() != null){
            itemBean.setSellerAccount(new SellerBean());
            BeanUtils.copyProperties(item.getSellerAccount(), itemBean.getSellerAccount());
        }

        return itemBean;
    }

    public Item getDto(ItemBean itemBean) {
        Item item = new Item();
        BeanUtils.copyProperties(itemBean, item);

        if(itemBean.getSellerAccount() != null){
            item.setSellerAccount(new Seller());
            BeanUtils.copyProperties(itemBean.getSellerAccount(), item.getSellerAccount());
        }

        return item;
    }
}