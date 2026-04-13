package web.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import web.pages.*;

import java.util.List;
import java.util.Map;

public class SauceDemoSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    // ====== HOOKS ======

    @Before("@web")
    public void setUp() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        System.out.println("=== Web Test Started ===");
    }

    @After("@web")
    public void tearDown() {
        DriverManager.quitDriver();
        System.out.println("=== Web Test Finished ===");
    }

    // ====== GIVEN STEPS ======

    @Given("I open web {string}")
    public void openPage(String url) {
        driver.get(url);
        System.out.println("Opened: " + url);
    }

    @Given("Logged in as {string} with password {string}")
    public void loginAs(String username, String password) {
        driver.get("https://www.saucedemo.com");
        loginPage.login(username, password);
        System.out.println("Logged in as: " + username);
    }

    @Given("Added product to cart")
    public void addProductToCart() {
        productsPage.addFirstItemToCart();
    }

    // ====== WHEN STEPS ======

    @When("I type on username {string}")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("I type on password {string}")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click Login button")
    public void clickLogin() {
        loginPage.clickLoginButton();
    }

    @When("Click product Sauce Labs Backpack")
    public void clickFirstProduct() {
        productsPage.clickFirstProduct();
    }

    @When("Click {string} button")
    public void clickButton(String buttonText) {
        switch (buttonText) {
            case "Add to cart":
                productsPage.addFirstItemToCart();
                break;
            case "Checkout":
                cartPage.clickCheckout();
                break;
            case "Continue":
                checkoutPage.clickContinue();
                break;
            case "Finish":
                checkoutPage.clickFinish();
                break;
            case "logout":
                productsPage.clickLogout();
                break;
            default:
                throw new RuntimeException("Button tidak dikenal: " + buttonText);
        }
    }

    @When("Sort option with {string}")
    public void selectSortOption(String option) {
        productsPage.selectSortOption(option);
    }

    @When("Click cart icon")
    public void clickCartIcon() {
        productsPage.clickCartIcon();
    }

    @When("inputting shipment information:")
    public void fillShippingInfo(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> info = rows.get(0);
        checkoutPage.fillCheckoutInfo(
                info.get("firstName"),
                info.get("lastName"),
                info.get("zipCode")
        );
    }

    @When("Click hamburger menu")
    public void openHamburgerMenu() {
        productsPage.openHamburgerMenu();
    }

    // ====== THEN STEPS ======

    @Then("directed to products page")
    public void verifyOnProductPage() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                "Should be directed to inventory page, but it shows : " + currentUrl,
                currentUrl.contains("inventory")
        );
    }

    @Then("Page should contain text {string}")
    public void verifyPageContainsText(String expectedText) {
        String pageSource = driver.getPageSource();
        Assert.assertTrue(
                "Text '" + expectedText + "' Text Not Found!",
                pageSource.contains(expectedText)
        );
    }

    @Then("Page should shows error message")
    public void verifyErrorDisplayed() {
        Assert.assertTrue(
                "Error message not shown!",
                loginPage.isErrorMessageDisplayed()
        );
    }

    @Then("Error message should contain {string}")
    public void verifyErrorMessage(String expectedMessage) {
        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(
                "Error message not contain '" + expectedMessage + "'. Actual: " + actualError,
                actualError.contains(expectedMessage)
        );
    }

    @Then("Cart quantity should be {int}")
    public void verifyCartCount(int expectedCount) {
        int actualCount = productsPage.getCartItemCount();
        Assert.assertEquals(
                "Items in cart mismatched!",
                expectedCount,
                actualCount
        );
    }

    @Then("Product should be sorted in low to high price")
    public void verifyProductsSortedByPrice() {
        List<Double> prices = productsPage.getAllProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(
                    "Price not sorted! " + prices.get(i) + " > " + prices.get(i + 1),
                    prices.get(i) <= prices.get(i + 1)
            );
        }
    }

    @Then("Directed to login page")
    public void verifyOnLoginPage() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                "Should be directed to login page!",
                currentUrl.equals("https://www.saucedemo.com/") ||
                        currentUrl.equals("https://www.saucedemo.com")
        );
    }
}