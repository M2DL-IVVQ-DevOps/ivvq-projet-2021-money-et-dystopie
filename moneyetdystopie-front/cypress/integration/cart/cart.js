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

Given(/^a cart with one item$/, function () {
    cy.get('.cardAction')
        .get('select')
        .first().select('1');
    cy.get('.md-button').first().click();
});

When(/^I add (\d+) items number (\d+) to the cart$/, function (quantity, number) {
    cy.get('.cardAction')
        .get('select')
        .eq(number).select(quantity.toString());
    cy.get('.md-button').eq(number).click();
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

Then(/^there are (\d+) items number (\d+) in the cart$/, function (quantity, number) {
    cy.get('.items')
        .get('.md-card.card')
        .get('.cardAction')
        .eq(number-1)
        .get('select')
        .find(':selected').contains(quantity.toString());
});

Then(/^there are (\d+) different items in the cart$/, function (value) {
    cy.get('.items').get('.md-card').should('have.length', value);
});