const {
    Given,
    When,
    Then,
} = require("cypress-cucumber-preprocessor/steps");

Given (/^the account creation form$/, function () {
    cy.visit('http://localhost:8080/index.html');
});

When(/^i fill the lastName with "([^"]*)" in account creation form$/, function (lastName) {
    cy.get('#accountCreationForm').get('#lastNameCreation').type(lastName);
});

When(/^i fill the firstName with "([^"]*)" in account creation form$/, function (firstName) {
    cy.get('#accountCreationForm').get('#firstNameCreation').type(firstName);
});

When(/^i fill the email with "([^"]*)" in account creation form$/, function (email) {
    cy.get('#accountCreationForm').get('#emailCreation').type(email);
});

When(/^i fill the password with "([^"]*)" in account creation form$/, function (password) {
    cy.get('#accountCreationForm').get('#passwordCreation').type(password);
});

When(/^i select seller account$/, function () {
    cy.get('#accountCreationForm').get('.sellerCheckBox').click();
});

When(/^i fill the shop with "([^"]*)" in account creation form$/, function (shop) {
    cy.get('#accountCreationForm').get('#shopCreation').type(shop);
});

When(/^i select customer account$/, function () {
    cy.get('#accountCreationForm').get('.customerCheckBox').click();
});

When(/^i fill the nickName with "([^"]*)" in account creation form$/, function (nickName) {
    cy.get('#accountCreationForm').get('#nickNameCreation').type(nickName);
});

When(/^i fill the address with "([^"]*)" in account creation form$/, function (address) {
    cy.get('#accountCreationForm').get('#addressCreation').type(address);
});

When(/^i submit account creation form$/, function () {
    cy.get('#accountCreationForm').get('#creationAccountSubmit').click();
});

When(/^i submit valid account creation form$/, function () {
    mockAccountCreation();
    cy.get('#accountCreationForm').get('#creationAccountSubmit').click();
    cy.wait('@mockAccountCreation');
});

Then(/^an error should be displayed on the account creation form$/, function () {
    cy.get('#accountCreationForm').get('.error').should('be.visible');
});

Then(/^no error should be displayed on the account creation form$/, function () {
    cy.get('#accountCreationForm').get('.error').should('not.exist');
});

Then(/^success popup visible$/, function () {
    cy.get('#serverSuccessMessage').should('be.visible');
});

Then(/^success popup not visible$/, function () {
    cy.get('#serverSuccessMessage').should('not.exist');
});

function mockAccountCreation() {
    cy.intercept('POST','/user/create', {
        "lastName": "4",
        "firstName": "Bitcoin",
        "password": "Password1",
        "email": "email@mail.com",
        "sellerAccount": {
            "storeName": "WHo?"
        }
    }).as('mockAccountCreation');
}
