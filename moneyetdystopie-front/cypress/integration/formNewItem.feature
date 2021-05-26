Feature: Form New Item tests

  I want to check form new item behavior

  Scenario: Open the 'add item' form
    Given the 'add item' form
    When I watch the 'add item' form
    Then no error should be displayed

  Scenario: Submit a item empty
    Given the 'add item' form
    When I submit form
    When I watch the 'add item' form
    Then an error should be displayed

  Scenario: Submit a full and correct item

    Given the 'add item' form
    When I fill the title with "Mon joli titre" in item form
    When I fill the description with "Une description avec assez de caractère." in item form
    When I fill the picture with "https://focus.levif.be/medias/3696/1892773.jpg" in item form
    When I fill the amount with 120 in item form
    When I fill the price with "1,02" in item form
    When I submit valid form
    Then no see 'add item' form

  Scenario: Submit a incomplete (title) item

    Given the 'add item' form
    When I fill the description with "Une description avec assez de caractère." in item form
    When I fill the picture with "https://focus.levif.be/medias/3696/1892773.jpg" in item form
    When I fill the amount with 120 in item form
    When I fill the price with "1,02" in item form
    When I submit form
    Then an error should be displayed

  Scenario: Submit a incomplete (description) item

    Given the 'add item' form
    When I fill the title with "Mon joli titre" in item form
    When I fill the picture with "https://focus.levif.be/medias/3696/1892773.jpg" in item form
    When I fill the amount with 120 in item form
    When I fill the price with "1,02" in item form
    When I submit form
    Then an error should be displayed

  Scenario: Submit a incomplete (picture) item

    Given the 'add item' form
    When I fill the title with "Mon joli titre" in item form
    When I fill the description with "Une description avec assez de caractère." in item form
    When I fill the amount with 120 in item form
    When I fill the price with "1,02" in item form
    When I submit form
    Then an error should be displayed

  Scenario: Submit a incomplete (amount) item

    Given the 'add item' form
    When I fill the title with "Mon joli titre" in item form
    When I fill the description with "Une description avec assez de caractère." in item form
    When I fill the picture with "https://focus.levif.be/medias/3696/1892773.jpg" in item form
    When I fill the price with "1,02" in item form
    When I submit form
    Then an error should be displayed

  Scenario: Submit a incomplete (price) item

    Given the 'add item' form
    When I fill the title with "Mon joli titre" in item form
    When I fill the description with "Une description avec assez de caractère." in item form
    When I fill the picture with "https://focus.levif.be/medias/3696/1892773.jpg" in item form
    When I fill the amount with 120 in item form
    When I submit form
    Then an error should be displayed
