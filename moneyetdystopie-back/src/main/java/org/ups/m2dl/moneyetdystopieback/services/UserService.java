package org.ups.m2dl.moneyetdystopieback.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ups.m2dl.moneyetdystopieback.bean.CustomerBean;
import org.ups.m2dl.moneyetdystopieback.bean.SellerBean;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.UserRepository;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@AllArgsConstructor
@Service
public class UserService {

    @Getter
    private final UserRepository userRepository;

    @Getter
    private final SellerService sellerService;

    @Getter
    private final CustomerService customerService;

    @Getter
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(User user) throws BusinessException {
        if (
            user.getCustomerAccount() == null && user.getSellerAccount() == null
        ) {
            throw new BusinessException(
                MoneyDystopieConstants.USER_NOT_CUSTOMER_OR_SELLER_ERROR
            );
        }

        if (this.findByEmail(user.getEmail()) != null) {
            throw new BusinessException(
                "Un utilisateur '" + user.getEmail() + "' existe déjà."
            );
        }

        String passwordSyntaxValidation = checkPasswordSyntax(
            user.getPassword()
        );
        if (!passwordSyntaxValidation.equals("")) {
            throw new BusinessException(passwordSyntaxValidation);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getSellerAccount() != null) {
            sellerService.valid(user.getSellerAccount());
        }
        if (user.getCustomerAccount() != null) {
            customerService.valid(user.getCustomerAccount());
        }
        this.valid(user);

        if (user.getSellerAccount() != null) {
            user.setSellerAccount(
                sellerService.create(user.getSellerAccount())
            );
        }

        if (user.getCustomerAccount() != null) {
            user.setCustomerAccount(
                customerService.create(user.getCustomerAccount())
            );
        }

        User u = this.save(user);

        if (user.getSellerAccount() != null) {
            user.getSellerAccount().setUserAccount(u);
        }

        if (user.getCustomerAccount() != null) {
            user.getCustomerAccount().setUserAccount(u);
        }

        return u;
    }

    public String checkPasswordSyntax(String password) {
        String retour = "";
        if (password.length() < 8) {
            retour = MoneyDystopieConstants.PASSWORD_SIZE_ERROR;
        } else if (!password.matches("^.*[A-Z].*$")) {
            retour = MoneyDystopieConstants.PASSWORD_UPPERCASE_ERROR;
        } else if (!password.matches("^.*[a-z].*$")) {
            retour = MoneyDystopieConstants.PASSWORD_LOWERCASE_ERROR;
        } else if (!password.matches("^.*[0-9].*$")) {
            retour = MoneyDystopieConstants.PASSWORD_DIGIT_ERROR;
        }
        return retour;
    }

    public User save(User user) throws BusinessException {
        if (user == null) {
            throw new BusinessException(
                MoneyDystopieConstants.REGISTER_USER_ERROR
            );
        }
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new BusinessException(
                MoneyDystopieConstants.REGISTER_USER_ERROR
            );
        }
    }

    public User findByEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        return userRepository.findByEmail(email).orElse(null);
    }

    public void valid(User user) throws BusinessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(
            user
        );

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
            throw new BusinessException(iterator.next().getMessage());
        }
    }

    public static UserBean getBean(User user) {
        UserBean userBean = new UserBean();
        BeanUtils.copyProperties(user, userBean);
        userBean.setPassword("");

        if (user.getCustomerAccount() != null) {
            userBean.setCustomerAccount(new CustomerBean());
            BeanUtils.copyProperties(
                user.getCustomerAccount(),
                userBean.getCustomerAccount()
            );
        }

        if (user.getSellerAccount() != null) {
            userBean.setSellerAccount(new SellerBean());
            BeanUtils.copyProperties(
                user.getSellerAccount(),
                userBean.getSellerAccount()
            );
        }

        return userBean;
    }

    public static User getDto(UserBean userBean) {
        User user = new User();
        BeanUtils.copyProperties(userBean, user);

        if (userBean.getCustomerAccount() != null) {
            user.setCustomerAccount(new Customer());
            BeanUtils.copyProperties(
                userBean.getCustomerAccount(),
                user.getCustomerAccount()
            );
        }

        if (userBean.getSellerAccount() != null) {
            user.setSellerAccount(new Seller());
            BeanUtils.copyProperties(
                userBean.getSellerAccount(),
                user.getSellerAccount()
            );
        }

        return user;
    }

    public boolean checkUserPassword(User user) {
        if (user != null) {
            User dbUser = findByEmail(user.getEmail());
            return (
                dbUser != null &&
                passwordEncoder.matches(
                    user.getPassword(),
                    dbUser.getPassword()
                )
            );
        }
        return false;
    }

    public void addTokenToUser(User user, Token token)
        throws BusinessException {
        token.setUser(user);
        if (user.getTokenList() == null) {
            user.setTokenList(new ArrayList<>());
        }
        user.getTokenList().add(token);
        save(user);
    }

    public void removeTokenToUser(User user, Token token)
        throws BusinessException {
        user.getTokenList().remove(token);
        save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
