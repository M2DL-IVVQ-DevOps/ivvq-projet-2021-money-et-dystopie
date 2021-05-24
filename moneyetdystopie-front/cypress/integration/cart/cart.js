const {
    Given,
    When,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

const CARD_ACTIONS = ".cardAction";

beforeEach(() => {
    mockCatalogue();
    mockConnexion();
    cy.visit('http://localhost:8080/index.html');
    cy.get('[id=connection-button]').click();
    cy.wait('@mockCatalogue');
});

function goToCart() {
    cy.get('.scene_nav')
        .get('.scene_nav_list')
        .get('.scene_nav_item')
        .get('.scene_nav_button').eq(1).click();
}

function mockCatalogue() {
    cy.intercept('GET','/item/all', [
        {
            "id": 1,
            "title": "Titre1",
            "picture": "https://cdn.radiofrance.fr/s3/cruiser-production/2021/03/c4d31527-b59d-438f-905c-bdbc64ec4b3e/801x410_bob_leponge_patrick.jpg",
            "description": "Ma description",
            "amount": 10,
            "price": 5.0,
            "sellerAccount": {
                "storeName": "Master DL seller"
            }
        },{
            "id": 2,
            "title": "Titre2",
            "picture": "https://img.over-blog-kiwi.com/0/92/63/65/20150215/ob_6820b6_le-chateau-ambulant-02.jpg",
            "description": "Une jolie description",
            "amount": 8,
            "price": 1.52,
            "sellerAccount": {
                "storeName": "master DSL"
            }
        },{
            "id": 3,
            "title": "Bitcoin",
            "picture": "https://cdn.dribbble.com/users/791530/screenshots/15336558/media/02b09bcee2c72083607b40f5e9beadc7.png?compress=1&resize=1000x750",
            "description": "...",
            "amount": 12,
            "price": 1004.4,
            "sellerAccount": {
                "storeName": "WHo?"
            }
        }
    ]).as('mockCatalogue');
}


function mockConnexion() {
    cy.intercept('POST','/user/create/',
            {
                "lastName": "FRANZESE",
                "firstName": "Alessandra",
                "email": "2monMail2@efef.gr",
                "password": "",
                "sellerAccount": {
                    "storeName": "Lecrochet1",
                    "items": null,
                    "commands": null
                },
                "customerAccount": {
                    "pseudo": "dsfdsfsd2",
                    "address": "54ruejenesaisou",
                    "cart": null,
                    "pastCommands": null
                }
            }
        ).as('mockConnexion');
}

Given(/^an empty cart$/, function () {
    // This is intentional
});

Given(/^a cart with one item$/, function () {
    cy.get(CARD_ACTIONS)
        .get('select')
        .first().select('1');
    cy.get('.md-button').first().click();
});

When(/^I add (\d+) items number (\d+) to the cart$/, function (quantity, number) {
    cy.get(CARD_ACTIONS)
        .get('select')
        .eq(number).select(quantity.toString());
    cy.get('.md-button').eq(number).click();
});

When(/^I delete the item from the cart$/, function () {
    goToCart();
    cy.get('.items')
        .get('.md-card.card')
        .get(CARD_ACTIONS)
        .get('select')
        .select('0');
    cy.get('.items')
        .get('.md-button-content')
        .click();
});

When(/^I watch my cart$/, function () {
    goToCart();
});

Then(/^"([^"]*)" should be displayed$/, function (text) {
    cy.get('.items').get('.no').contains(text);
});

Then(/^there are (\d+) items number (\d+) in the cart$/, function (quantity, number) {
    cy.get('.items')
        .get('.md-card.card')
        .get(CARD_ACTIONS)
        .eq(number-1)
        .get('select')
        .find(':selected').contains(quantity.toString());
});

Then(/^there are (\d+) different items in the cart$/, function (value) {
    cy.get('.items').get('.md-card').should('have.length', value);
});
