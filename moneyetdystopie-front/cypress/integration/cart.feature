Feature: Cart tests

  I want to check cart behavior

  Scenario: Cart is empty when initialized
    Given an empty cart
    When I watch my cart
    Then "Aucun article" should be displayed

  Scenario: Adding a product to the cart
    Given an empty cart
    When I add an item to the cart
    Then the cart size is 1

  Scenario: Deleting a product from the cart
    Given a cart with one item
    When I delete the item from the cart
    Then "Aucun article" should be displayed

