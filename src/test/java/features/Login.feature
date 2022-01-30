Feature: Application Login

  Scenario: Home page default login
    Given User is on landing page
    When User logs in to the site with username "panther" and password "2345"
    Then Home page is displayed
    And Home page title is displayed "true"

  Scenario: Home page default login failed
    Given User is on landing page
    When User logs in to the site with username "tiger" and password "1234"
    Then Home page is displayed
    And Home page title is displayed "false"