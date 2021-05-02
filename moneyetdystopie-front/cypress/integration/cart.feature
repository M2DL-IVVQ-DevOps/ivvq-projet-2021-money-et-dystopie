Feature: Cart tests

  I want to check cart behavior

  Scenario: Cart is empty when initialized
    Given an empty cart
    When I watch my cart
    Then "Aucun article" should be displayed

  Scenario: Adding a product to the cart
    Given an empty cart
    When I add 1 items number 1 to the cart
    When I watch my cart
    Then there are 1 items number 1 in the cart

  Scenario: Deleting a product from the cart
    Given a cart with one item
    When I delete the item from the cart
    Then "Aucun article" should be displayed

  Scenario: Adding 0 product to the cart
    Given an empty cart
    When I add 0 items number 1 to the cart
    When I watch my cart
    Then "Aucun article" should be displayed

  Scenario: Adding multiple products to the cart
    Given an empty cart
    When I add 3 items number 1 to the cart
    When I watch my cart
    Then there are 3 items number 1 in the cart

  Scenario: Adding multiple products and multiple items to the cart
    Given an empty cart
    When I add 4 items number 1 to the cart
    When I add 2 items number 2 to the cart
    When I watch my cart
    Then there are 2 different items in the cart
    Then there are 4 items number 1 in the cart
    Then there are 2 items number 2 in the cart