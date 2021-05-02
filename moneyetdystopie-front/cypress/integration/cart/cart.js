const {
    Given,
    When,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

beforeEach(() => {
    cy.visit('http://localhost:8080/');
    cy.get('[id=connection-button]').click();
});

function goToCart() {
    cy.get('.scene_nav')
        .get('.scene_nav_list')
        .get('.scene_nav_item')
        .get('img').eq(2).click();
}

Given(/^an empty cart$/, function () {
});

When(/^I add an item to the cart$/, function () {
    cy.get('.cardAction')
        .get('select')
        .first().select('1');
    cy.get('.md-button').first().click();
});

Then(/^the cart size is (\d+)$/, function (value) {
    goToCart();
    cy.get('.items').should('have.length', value);
    cy.get('.items')
        .get('.md-card.card')
        .get('.cardAction')
        .get('select')
        .find(':selected').contains('1');
});

Given(/^a cart with one item$/, function () {
    cy.get('.cardAction')
        .get('select')
        .first().select('1');
    cy.get('.md-button').first().click();
});

When(/^I delete the item from the cart$/, function () {
    goToCart();
    cy.get('.items')
        .get('.md-card.card')
        .get('.cardAction')
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