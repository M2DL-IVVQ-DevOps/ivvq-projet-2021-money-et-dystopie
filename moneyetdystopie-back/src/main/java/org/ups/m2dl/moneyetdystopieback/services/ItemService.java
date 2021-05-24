package org.ups.m2dl.moneyetdystopieback.services;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ups.m2dl.moneyetdystopieback.bean.ItemBean;
import org.ups.m2dl.moneyetdystopieback.bean.SellerBean;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.ItemRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@Service
public class ItemService {

    @Getter
    private final ItemRepository itemRepository;

    @Getter
    private final SellerService sellerService;

    @Getter
    private final TokenService tokenService;

    @Transactional
    public Item create(Item item, User dbUser) throws BusinessException {
        if (
            dbUser == null ||
            dbUser.getSellerAccount() == null ||
            dbUser.getSellerAccount().getStoreName().isBlank()
        ) {
            throw new BusinessException(
                MoneyDystopieConstants.UNREFERENCED_SHOP_ERROR
            );
        }
        Seller seller = sellerService.findByStoreName(
            dbUser.getSellerAccount().getStoreName()
        );
        if (seller == null) {
            throw new BusinessException(
                MoneyDystopieConstants.UNFOUND_REFERENCED_SHOP_ERROR
            );
        }
        item.setSellerAccount(seller);
        this.valid(item);

        item = this.save(item);

        try {
            item.getSellerAccount().addItem(item);

            sellerService.save(item.getSellerAccount());

            return item;
        } catch (BusinessException businessException) {
            item.getSellerAccount().removeItem(item);
            throw businessException;
        }
    }

    @Transactional
    public Item save(Item item) throws BusinessException {
        if (item == null) {
            throw new BusinessException(
                MoneyDystopieConstants.UNDEFINED_ITEM_ERROR
            );
        }
        try {
            return itemRepository.save(item);
        } catch (Exception e) {
            throw new BusinessException(
                MoneyDystopieConstants.REGISTER_ITEM_ERROR
            );
        }
    }

    public Item findById(Long id) {
        if (id == null) {
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

        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(
            item
        );

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<Item>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }

    public static ItemBean getBean(Item item) {
        ItemBean itemBean = new ItemBean();
        BeanUtils.copyProperties(item, itemBean);

        if (item.getSellerAccount() != null) {
            itemBean.setSellerAccount(new SellerBean());
            BeanUtils.copyProperties(
                item.getSellerAccount(),
                itemBean.getSellerAccount()
            );
        }

        return itemBean;
    }

    public static Item getDto(ItemBean itemBean) {
        Item item = new Item();
        BeanUtils.copyProperties(itemBean, item);

        if (itemBean.getSellerAccount() != null) {
            item.setSellerAccount(new Seller());
            BeanUtils.copyProperties(
                itemBean.getSellerAccount(),
                item.getSellerAccount()
            );
        }

        return item;
    }
}
