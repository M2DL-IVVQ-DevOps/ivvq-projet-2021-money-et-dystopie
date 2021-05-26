const {
    Given,
    When,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

beforeEach(() => {
    mockConnexion();
    cy.visit('http://localhost:8080/index.html');
    cy.get('[id=connection-button]').click();
});

function goToShop() {
    cy.get('.scene_nav')
        .get('.scene_nav_list')
        .get('.scene_nav_item')
        .get('.scene_nav_button').eq(2).click();
}

Given(/^the 'add item' form$/, function () {
    goToShop();
    cy.get('.div-button-action-new-item')
        .get('button').first()
        .click();
});


When(/^I watch the 'add item' form$/, function () {
    cy.get('.form');
});

When(/^I submit form$/, function () {
    cy.get('.form').get('.button-add-item').click();
});

When(/^I submit valid form$/, function () {
    mockItem();
    mockItemAll();
    cy.get('.form').get('.button-add-item').click();
    cy.wait('@mockItem');
    cy.wait('@mockItemAll');
});

When(/^I fill the title with "([^"]*)" in item form$/, function (title) {
    cy.get('.form').get('.title').type(title);
});

When(/^I fill the description with "([^"]*)" in item form$/, function (description) {
    cy.get('.form').get('.description').type(description);
});

When(/^I fill the picture with "([^"]*)" in item form$/, function (url) {
    cy.get('.form').get('.picture').type(url);
});

When(/^I fill the amount with (\d+) in item form$/, function (amount) {
    cy.get('.form').get('.amount').type(amount);
});

When(/^I fill the price with "([^"]*)" in item form$/, function (price) {
    cy.get('.form').get('.price').type(price);
});


Then(/^an error should be displayed$/, function () {
    cy.get('.form').get('.errors').should('be.visible');
});

Then(/^no error should be displayed$/, function () {
    cy.get('.form').get('.errors').should('not.exist');
});

Then(/^no see 'add item' form$/, function () {
    cy.get('.form').should('not.exist');
});

function mockItem() {
    cy.intercept('POST','/item/create', {
        "id": 4,
        "title": "Bitcoin",
        "picture": "https://cdn.dribbble.com/users/791530/screenshots/15336558/media/02b09bcee2c72083607b40f5e9beadc7.png?compress=1&resize=1000x750",
        "description": "...",
        "amount": 12,
        "price": 1004.4,
        "sellerAccount": {
            "storeName": "Lecrochet1"
        }
    }).as('mockItem');
}

function mockItemAll() {
    cy.intercept('GET','/item/all', {
        "statusCode": 200,
        "id": 4,
        "title": "Bitcoin",
        "picture": "https://cdn.dribbble.com/users/791530/screenshots/15336558/media/02b09bcee2c72083607b40f5e9beadc7.png?compress=1&resize=1000x750",
        "description": "...",
        "amount": 12,
        "price": 1004.4,
        "sellerAccount": {
            "storeName": "Lecrochet1"
        }
    }).as('mockItemAll');
}

function mockConnexion() {
    cy.intercept('POST','/token/create/',
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
