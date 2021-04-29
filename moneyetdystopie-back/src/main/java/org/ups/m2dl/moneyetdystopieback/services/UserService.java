package org.ups.m2dl.moneyetdystopieback.services;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final SellerService sellerService;

    private final CustomerService customerService;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, SellerService sellerService, CustomerService customerService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.sellerService = sellerService;
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Transactional
    public User create(User user) throws BusinessException {

        if(user.getCustomerAccount() == null && user.getSellerAccount() == null){
            throw new BusinessException("Le compte doit être acheteur ou commerçant.");
        }

        if(!this.findByEmail(user.getEmail()).isEmpty()){
            throw new BusinessException("Un utilisateur '" + user.getEmail() + "' existe déjà.");
        }

        try {
            if(user.getSellerAccount() != null){
                sellerService.valid(user.getSellerAccount());
            }
            if(user.getCustomerAccount() != null){
                customerService.valid(user.getCustomerAccount());
            }
            this.valid(user);
        }catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }

        try {
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
        }catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        }
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

    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
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

    public boolean checkUserPassword(User user){
        User dbUser = findByEmail(user.getEmail()).get(0);
        return (dbUser != null && user != null && passwordEncoder.matches(user.getPassword(),dbUser.getPassword()));
    }
}
