package org.ups.m2dl.moneyetdystopieback.services.ItemServiceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.ItemRepository;
import org.ups.m2dl.moneyetdystopieback.services.ItemService;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private SellerService sellerService;

    private Item itemTest;

    private Seller sellerTest;

    @BeforeEach
    void setup() {
        itemService = new ItemService(itemRepository, sellerService);
    }

    @Test
    void whenUseSaveMethod_thenRepositoryItemInvoked() throws BusinessException {
        // GIVEN
        sellerTest =  new Seller("storeName51",null,null,null);
        sellerService.save(sellerTest);
        itemTest = new Item(null, "title51", "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png", "description51", 10, 5.f, null, sellerTest);
        when(itemService.getItemRepository().save(itemTest)).thenReturn(itemTest);
        // WHEN: save méthode est invoqué
        itemService.save(itemTest);
        // THEN: CustomerRepository est invoqué
        verify(itemService.getItemRepository()).save(itemTest);
    }

    @Test
    void whenUseFindByPseudoMethod_thenRepositoryItemInvoked() {
        // GIVEN
        when(itemService.getItemRepository().findAll()).thenReturn(null);
        // WHEN: save méthode est invoqué
        itemService.findAll();
        // THEN: CustomerRepository est invoqué
        verify(itemService.getItemRepository()).findAll();
    }

}