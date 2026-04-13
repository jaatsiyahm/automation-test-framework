package web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement zipCodeField;

    @FindBy(css = "[data-test='continue']")
    private WebElement continueButton;

    @FindBy(css = "[data-test='finish']")
    private WebElement finishButton;

    @FindBy(css = ".complete-header")
    private WebElement successMessage;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void fillCheckoutInfo(String firstName, String lastName, String zipCode) {
        typeText(firstNameField, firstName);
        typeText(lastNameField, lastName);
        typeText(zipCodeField, zipCode);
    }

    public void clickContinue() {
        clickElement(continueButton);
    }

    public void clickFinish() {
        clickElement(finishButton);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }
}