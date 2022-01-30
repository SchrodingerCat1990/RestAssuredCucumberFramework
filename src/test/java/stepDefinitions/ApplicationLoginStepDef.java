package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class ApplicationLoginStepDef {

        @Given("^User is on landing page$")
        public void user_is_on_landing_page() throws Throwable {
                System.out.println("This is the landing page");
        }

        @When("User logs in to the site with username {string} and password {string}")
        public void user_logs_in_to_the_site_with_username_and_password(String string, String string2) {
                // Write code here that turns the phrase above into concrete actions
                System.out.println("Username is: "+ string + " Password is: "+ string2);
        }

        @Then("^Home page is displayed$")
        public void home_page_is_displayed() throws Throwable {
                System.out.println("This is the home page");
        }

        @And("Home page title is displayed {string}")
        public void home_page_title_is_displayed(String string) {
                // Write code here that turns the phrase above into concrete actions
                System.out.println("Home page is title is displayed: "+ string);
        }

}