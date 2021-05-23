const {
    Given,
    When,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

beforeEach(() => {
    cy.visit('http://localhost:8080/index.html');
});

When(/^I fill the last name with "([^"]*)" in account creation form$/, function (lastName) {
    cy.get('.form').get('.lastName').type(lastName);
});

When(/^I fill the first name with "([^"]*)" in account creation form$/, function (firstName) {
    cy.get('.form').get('.lastName').type(firstName);
});

When(/^I fill the email with "([^"]*)" in account creation form$/, function (email) {
    cy.get('.form').get('.lastName').type(email);
});

When(/^I fill the password with "([^"]*)" in account creation form$/, function (password) {
    cy.get('.form').get('.lastName').type(password);
});
When(/^I select customerAccount$/, function () {
    mockItem();
    cy.get('.form').get('.md-checkbox-7h4kwgasx').click();
    cy.wait('@mockItem');
});
When(/^I select sellerAccount$/, function () {
    mockItem();
    cy.get('.form').get('.md-checkbox-kmbeyxvj').click();
    cy.wait('@mockItem');
});