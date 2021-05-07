package org.ups.m2dl.moneyetdystopieback.controllers.controllers_integration_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.ups.m2dl.moneyetdystopieback.bean.ItemBean;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.services.ItemService;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerCreateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SellerService sellerService;

    private String jsonResult;
    private Item itemTest;
    private Seller sellerTest;
    private ObjectMapper mapper;
    private String jsonUserTest;
    private int itemsNumber;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    void whenSaveItem_thenItemReturn() throws Exception {

        // GIVEN
        sellerTest =  new Seller("storeName39",null,null,null);
        itemTest = new Item(null, "title39", "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png", "description39", 10, 5.f, null, sellerTest);
        sellerService.save(sellerTest);

        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc.perform(post("/item/create").contentType(MediaType.APPLICATION_JSON).content(jsonUserTest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                });

        ItemBean resultFromJson = mapper.readValue(jsonResult, ItemBean.class);

        // THEN
        Assertions.assertEquals( itemTest.getTitle(), resultFromJson.getTitle(), "The returned item does not comply.");
        Assertions.assertEquals( itemTest.getPicture(), resultFromJson.getPicture(), "The returned item does not comply.");
        Assertions.assertEquals( itemTest.getAmount(), resultFromJson.getAmount(), "The returned item does not comply.");
        Assertions.assertEquals( itemTest.getPrice(), resultFromJson.getPrice(), "The returned item does not comply.");

        Assertions.assertEquals( itemTest.getSellerAccount().getStoreName(), resultFromJson.getSellerAccount().getStoreName(), "The returned item does not comply.");
    }

    @Test
    void whenSaveUser_thenItemIsSavedSellerModified() throws Exception {

        // GIVEN
        sellerTest =  new Seller("storeName40",null,null,null);
        itemTest = new Item(null, "title40", "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png", "description40", 10, 5.f, null, sellerTest);
        sellerService.save(sellerTest);
        itemsNumber = itemService.findAll().size();

        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc.perform(post("/item/create").contentType(MediaType.APPLICATION_JSON).content(jsonUserTest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                });

        ItemBean result = mapper.readValue(jsonResult, ItemBean.class);

        // THEN
        Item resultItem = itemService.findById(result.getId());

        Assertions.assertNotNull( resultItem, "The saved item was not found.");

        Assertions.assertEquals( itemsNumber + 1, itemService.findAll().size(), "The saved item exist.");

        Assertions.assertEquals( itemTest.getTitle(), resultItem.getTitle(), "The returned item does not comply.");
        Assertions.assertEquals( itemTest.getPicture(), resultItem.getPicture(), "The returned item does not comply.");
        Assertions.assertEquals( itemTest.getAmount(), resultItem.getAmount(), "The returned item does not comply.");
        Assertions.assertEquals( itemTest.getPrice(), resultItem.getPrice(), "The returned item does not comply.");

        // THEN
        Seller resultSeller = sellerService.findByStoreName(resultItem.getSellerAccount().getStoreName());

        Assertions.assertNotNull( resultSeller, "The saved seller was not found.");

        Assertions.assertNotNull( resultSeller.getItems(), "The seller does not comply.");

        Assertions.assertEquals( sellerTest.getStoreName(), resultSeller.getStoreName(), "The returned user does not comply.");
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "title41,description41,1,2.f,false,",
                    ",description42,1,2.f,true,storeName42",
                    "title44,,1,2.f,true,storeName44",
                    "title45,description45,,2.f,true,storeName45",
                    "title46,description46,1,,true,storeName46"
            }
    )
    void whenSaveItemWithoutElement_thenThrowBusinessException(String title, String description, Integer amount, Float price, Boolean areSeller, String storeName) throws Exception {

        // GIVEN
        if(areSeller) {
            sellerTest = new Seller(storeName,null,null,null);
            sellerService.save(sellerTest);
        }
        itemTest = new Item(null, title, null, description, amount, price, null, areSeller? sellerTest : null);

        itemsNumber = itemService.findAll().size();

        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc.perform(post("/item/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonUserTest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andDo(mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                });

        // THEN
        Assertions.assertFalse(jsonResult.isBlank());
        Assertions.assertEquals( itemsNumber, itemService.findAll().size(), "The saved item exist.");
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "title47,description47,1,2.f,false,",
                    ",description48,1,2.f,true,storeName48",
                    "title50,,1,2.f,true,storeName50",
                    "title51,description51,,2.f,true,storeName51",
                    "title52,description52,1,,true,storeName52"
            }
    )
    void whenSaveUserWithoutId_thenNoSave(String title, String description, Integer amount, Float price, Boolean areSeller, String storeName) throws Exception {

        // GIVEN
        if(areSeller) {
            sellerTest = new Seller(storeName,null,null,null);
            sellerService.save(sellerTest);
        }
        itemTest = new Item(null, title, "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png", description, amount, price, null, areSeller? sellerTest : null);
        itemsNumber = itemService.findAll().size();

        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc.perform(post("/item/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonUserTest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType));

        // THEN
        Assertions.assertEquals( itemsNumber, itemService.findAll().size(), "The saved item exist.");

        // THEN
        Seller resultSeller = sellerService.findByStoreName(itemTest.getSellerAccount() != null ? itemTest.getSellerAccount().getStoreName() : null);

        if(areSeller){
            Assertions.assertNotNull( resultSeller, "The saved seller was found.");
        } else {
            Assertions.assertNull(resultSeller, "The saved seller was exist.");
        }

        if(areSeller){
            Assertions.assertTrue( resultSeller.getItems().isEmpty(), "The item is add in seller.");
        }
    }
}