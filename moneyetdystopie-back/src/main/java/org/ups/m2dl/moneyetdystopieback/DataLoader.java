package org.ups.m2dl.moneyetdystopieback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ups.m2dl.moneyetdystopieback.domain.*;
import org.ups.m2dl.moneyetdystopieback.repositories.CustomerRepository;
import org.ups.m2dl.moneyetdystopieback.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Customer buyer;

    private Order cart;

    private Order order1;
    private Order order2;
    private Order order3;

    private Seller seller;

    private User user;

    private void initSeller() {
        seller = new Seller("Boutique de FredoMgeon", new ArrayList<>(), new ArrayList<>());
    }

    private void initCart() {
        cart = new Order(OrderState.IN_PROGRESS, new ArrayList<>());
    }

    private void initOrders() {
        order1 = new Order(OrderState.WAITING_FOR_SHIPMENT, new ArrayList<>());
        order2 = new Order(OrderState.WAITING_FOR_DELIVERY, new ArrayList<>());
        order3 = new Order(OrderState.WAITING_FOR_PAYMENT, new ArrayList<>());
    }

    private void initBuyer() {
        buyer = new Customer("FredoMigeon", "pas à Fougères, ça c'est JDG", user, cart, List.of(order1, order2, order3));
    }

    private void initUser() {
        user = new User("Migeon", "Frédéric", "frederic.migeon@irit.fr", "aaaaaa", seller, buyer);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initSeller();
        initOrders();
        initCart();
        initBuyer();
        initUser();

        orderRepository.save(cart);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        customerRepository.save(buyer);
    }
}
