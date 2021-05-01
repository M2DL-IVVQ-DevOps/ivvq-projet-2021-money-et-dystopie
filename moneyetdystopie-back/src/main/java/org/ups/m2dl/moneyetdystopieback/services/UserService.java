package org.ups.m2dl.moneyetdystopieback.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ups.m2dl.moneyetdystopieback.bean.CustomerBean;
import org.ups.m2dl.moneyetdystopieback.bean.SellerBean;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {

    @Getter
    @Setter
    private UserRepository userRepository;

    @Getter
    @Setter
    private SellerService sellerService;

    @Getter
    @Setter
    private CustomerService customerService;

    @Transactional
    public User create(User user) throws BusinessException {

        if(user.getCustomerAccount() == null && user.getSellerAccount() == null){
            throw new BusinessException("Le compte doit être acheteur ou commerçant.");
        }

        if(this.findByEmail(user.getEmail()) != null){
            throw new BusinessException("Un utilisateur '" + user.getEmail() + "' existe déjà.");
        }

        if(user.getSellerAccount() != null){
            sellerService.valid(user.getSellerAccount());
        }
        if(user.getCustomerAccount() != null){
            customerService.valid(user.getCustomerAccount());
        }
        this.valid(user);

        if(user.getSellerAccount() != null){
            user.setSellerAccount(sellerService.create(user.getSellerAccount()));
        }

        if(user.getCustomerAccount() != null){
           user.setCustomerAccount(customerService.create(user.getCustomerAccount()));
        }

        User u = this.save(user);

        if(user.getSellerAccount() != null){
           user.getSellerAccount().setUserAccount(u);
        }

        if(user.getCustomerAccount() != null){
           user.getCustomerAccount().setUserAccount(u);
        }

        return u;
    }

    public User save(User user) throws BusinessException {
        if(user == null){
            throw new BusinessException("Un utilisateur non défini ne peut être sauvegardé.");
        }
        try {
            return userRepository.save(user);
        }catch (Exception e){
            throw new BusinessException("Une erreur est survenue lors de l'enregistrement de l'utilisateur." + (e.getMessage() == null ? e.getMessage() : ""));
        }
    }

    public User findByEmail(String email) {
        if(email==null || email.isBlank()){
            return null;
        }
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() ? user.get() : null;
    }

    public void valid(User user) throws BusinessException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }

    public UserBean getBean(User user) {
        UserBean userBean = new UserBean();
        BeanUtils.copyProperties(user, userBean);

        if(user.getCustomerAccount() != null){
            userBean.setCustomerAccount(new CustomerBean());
            BeanUtils.copyProperties(user.getCustomerAccount(), userBean.getCustomerAccount());
        }

        if(user.getSellerAccount() != null){
            userBean.setSellerAccount(new SellerBean());
            BeanUtils.copyProperties(user.getSellerAccount(), userBean.getSellerAccount());
        }

        return userBean;
    }

    public User getDto(UserBean userBean) {
        User user = new User();
        BeanUtils.copyProperties(userBean, user);

        if(userBean.getCustomerAccount() != null){
            user.setCustomerAccount(new Customer());
            BeanUtils.copyProperties(userBean.getCustomerAccount(), user.getCustomerAccount());
        }

        if(userBean.getSellerAccount() != null){
            user.setSellerAccount(new Seller());
            BeanUtils.copyProperties(userBean.getSellerAccount(), user.getSellerAccount());
        }

        return user;
    }
}
