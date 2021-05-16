package org.ups.m2dl.moneyetdystopieback.controllers.controllers_integration_test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerUpdateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SellerService sellerService;

    private Seller sellerTest;
    private String jsonResult;
    private ObjectMapper mapper;
    private String jsonUserTest;
    private int itemsNumber;

    private final MediaType contentType = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype()
    );

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    void whenUpdateItem_thenItemReturn() throws Exception {
        // GIVEN
        sellerTest = new Seller("storeName39", null, null, null);
        Item expected = new Item(
            null,
            "title39",
            "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
            "description39",
            10,
            5.f,
            null,
            sellerTest
        );
        sellerService.save(sellerTest);
        itemService.save(expected);
        expected.setAmount(expected.getAmount() - 1);
        jsonUserTest = new Gson().toJson(expected);

        // WHEN
        mockMvc
            .perform(
                post("/item/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserTest)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        ItemBean actual = mapper.readValue(jsonResult, ItemBean.class);

        // THEN
        assertAll(
            "The returned item does not comply.",
            () -> assertEquals(expected.getTitle(), actual.getTitle()),
            () -> assertEquals(expected.getPicture(), actual.getPicture()),
            () -> assertEquals(expected.getAmount(), actual.getAmount()),
            () -> assertEquals(expected.getPrice(), actual.getPrice()),
            () ->
                assertEquals(
                    expected.getSellerAccount().getStoreName(),
                    actual.getSellerAccount().getStoreName()
                )
        );
    }

    @Test
    void whenUpdateIncorrectItem_thenHttpErrorIsThrownAndItemNotUpdated()
        throws Exception {
        // GIVEN
        sellerTest = new Seller("storeName40", null, null, null);
        Item expected = new Item(
            null,
            "title40",
            "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
            "description40",
            10,
            5.f,
            null,
            sellerTest
        );
        sellerService.save(sellerTest);
        itemService.save(expected);
        itemsNumber = itemService.findAll().size();

        expected.setAmount(-1);
        jsonUserTest = new Gson().toJson(expected);

        // WHEN
        mockMvc
            .perform(
                post("/item/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserTest)
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        // THEN
        Assertions.assertFalse(jsonResult.isBlank());
    }
}
