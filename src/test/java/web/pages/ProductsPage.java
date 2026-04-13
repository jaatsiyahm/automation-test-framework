package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductsPage extends BasePage {

    // ====== LOCATORS ======

    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".product_sort_container")
    private WebElement sortDropdown;

    @FindBy(css = ".inventory_item")
    private List<WebElement> productItems;

    @FindBy(css = "[data-test^='add-to-cart']")
    private List<WebElement> addToCartButtons;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement hamburgerMenu;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    // ====== CONSTRUCTOR ======

    public ProductsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ====== ACTIONS ======

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public void clickFirstProduct() {
        if (!productItems.isEmpty()) {
            WebElement firstProduct = productItems.get(0);
            WebElement productName = firstProduct.findElement(By.cssSelector(".inventory_item_name"));
            clickElement(productName);
        }
    }

    public void addFirstItemToCart() {
        if (!addToCartButtons.isEmpty()) {
            clickElement(addToCartButtons.get(0));
        }
    }

    public int getCartItemCount() {
        try {
            String badgeText = getText(cartBadge);
            return Integer.parseInt(badgeText);
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickCartIcon() {
        clickElement(cartIcon);
    }

    public void selectSortOption(String optionText) {
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(optionText);
    }

    public List<Double> getAllProductPrices() {
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".inventory_item_price"));
        return priceElements.stream()
                .map(el -> Double.parseDouble(el.getText().replace("$", "")))
                .collect(java.util.stream.Collectors.toList());
    }

    public void openHamburgerMenu() {
        clickElement(hamburgerMenu);
    }

    public void clickLogout() {
        clickElement(logoutLink);
    }
}