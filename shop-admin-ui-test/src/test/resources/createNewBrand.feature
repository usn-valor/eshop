Feature: CreateNewBrand

  Scenario Outline: Brand Creation and Validation
    Given I open web browser
    When I navigate to login page
    And I provide username as "<username>" and password as "<password>"
    And I click on login button
    When Open Menu Brands
    And I click on create new button
    And I enter brand name as "<brandName>"
    And Submit new Brand

#    When Open dropdown menu
#    And click logout button
#    Then user logged out

    Examples:
      | username | password | brandName |
      | admin | admin | BMW |