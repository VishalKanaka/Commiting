package utilities;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import StepDefinitions.SetupClass;
import io.cucumber.java.Scenario;

import org.openqa.selenium.interactions.Actions;

/**
 * BasePage class provides common functionalities and operations for interacting with web elements.
 */
public class BasePage {
    protected WebDriver driver;
    private Scenario scenario;

    /**
     * Constructor to initialize BasePage with WebDriver and Scenario objects.
     * @param driver The WebDriver object.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.scenario = SetupClass.getScenario();
    }

    /**
     * Method to type text into an input field.
     * @param locator The locator of the input field.
     * @param text The text to be typed.
     */
    public void sendKeys(String locator, String text) {
        WebElement element = driver.findElement(getBy(locator));
        element.sendKeys(text);
    }

    /**
     * Method to select an option from a dropdown by its visible text.
     * @param locator The locator of the dropdown.
     * @param visibleText The visible text of the option to be selected.
     */
    public void selectDropdownByVisibleText(String locator, String visibleText) {
        WebElement dropdownElement = driver.findElement(getBy(locator));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(visibleText);
    }

    /**
     * Method to click on an element.
     * @param locator The locator of the element to be clicked.
     */
    public void click(String locator) {
        WebElement element = driver.findElement(getBy(locator));
        System.out.println("Before clicking" + element.toString());
        element.click();
    }

    /**
     * Method to verify the presence of an element.
     * @param locator The locator of the element to be verified.
     * @return True if element is present, otherwise False.
     */
    public boolean verifyElementPresent(String locator) {
        List<WebElement> element = driver.findElements(getBy(locator));
        return !element.isEmpty();
    }

    /**
     * Helper method to convert locator string to By object.
     * @param locator The locator string.
     * @return The corresponding By object.
     */
    private By getBy(String locator) {
        if (locator.startsWith("//")) {
            return By.xpath(locator);
        } else if (locator.startsWith("id=")) {
            String[] split = locator.split("=");
            return By.id(split[1]);
        } else if (locator.startsWith("name=")) {
            String[] split = locator.split("=");
            return By.name(split[1]);
        } else if (locator.startsWith("class=")) {
            String[] split = locator.split("=");
            return By.className(split[1]);
        } else if (locator.startsWith("css=")) {
            String[] split = locator.split("=");
            return By.cssSelector(split[1]);
        } else if (locator.startsWith("link=")) {
            String[] split = locator.split("=");
            return By.linkText(split[1]);
        } else if (locator.startsWith("partialLink=")) {
            String[] split = locator.split("=");
            return By.partialLinkText(split[1]);
        } else {
            // Assuming default is "xpath"
            return By.xpath(locator);
        }
    }

    /**
     * Method to compare text of an element with expected text.
     * @param locator The locator of the element.
     * @param textToCompare The expected text.
     * @return True if texts match, otherwise False.
     */
    public boolean compareText(String locator, String textToCompare) {
        String actualText = driver.findElement(getBy(locator)).getAttribute("textContent");
        Assert.assertEquals("Comparing Text", textToCompare, actualText);
        return actualText.equals(textToCompare);
    }

    /**
     * Method to find a web element using locator.
     * @param locator The locator of the element.
     * @return The WebElement.
     */
    public WebElement findElement(String locator) {
        return driver.findElement(getBy(locator));
    }

    /**
     * Method to scroll to a web element.
     * @param locator The locator of the element.
     * @throws InterruptedException If interrupted while waiting.
     */
    public void scrollToElement(String locator) throws InterruptedException {
        WebElement element = driver.findElement(getBy(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(2000);
    }

    /**
     * Method to assert the presence of an element.
     * @param locator The locator of the element to be asserted.
     * @throws InterruptedException If interrupted while waiting.
     */
    public void validateElementPresentAssertion(String locator) throws InterruptedException {
        Assert.assertTrue(verifyElementPresent(locator));
    }

    /**
     * Method to switch to an iframe.
     */
    public void switchToIframe() {
        driver.switchTo().frame(0); // Assuming the iframe is the first one on the page
    }

    /**
     * Method to switch back to default content.
     */
    public void backToNormal() {
        driver.switchTo().defaultContent();
    }

    /**
     * Method to hover over an element.
     * @param driver The WebDriver object.
     * @param elementXPath The XPath of the element to hover over.
     */
    public static void hoverOverElement(WebDriver driver, String elementXPath) {
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(elementXPath));
        actions.moveToElement(element).perform();
    }

    /**
     * Method to select an element from a list based on visible text.
     * @param visibleText The visible text of the element to be selected.
     */
    public void selectlistElement(String visibleText) {
        String optionXPath = String.format("//ul[@class='sub-menu']//a[text()='%s']", visibleText);
        driver.findElement(By.xpath(optionXPath)).click();
    }

    /**
     * Method to highlight an element on the page.
     * @param locator The locator of the element to be highlighted.
     */
    public void elementHighlighter(String locator) {
        try {
            WebElement element = driver.findElement(getBy(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
        } catch (Exception e) {
            System.err.println("Error highlighting element: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
