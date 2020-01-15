package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import runner.TestRunner;

import java.util.List;

public class DeleteCustomerPage {
    @FindBy(xpath = "//div[1]/div[2]/button")
    WebElement bankManagerLoginButton;

    @FindBy(xpath = "//div[2]/div/div[1]/button[1]")
    WebElement addCustomerButton;

    @FindBy(xpath = "//div[1]/input")
    WebElement firstName;

    @FindBy(xpath = "//div[2]/input")
    WebElement lastName;

    @FindBy(xpath = "//div[3]/input")
    WebElement postCode;

    @FindBy(xpath = "//form/button")
    WebElement addCustomerBtn;

    @FindBy(xpath = "//div[1]/button[3]")
    WebElement customerTabButton;

    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div/div/table/tbody")
    WebElement customerTable;

    int beforeTableLength, afterTableLength;

    public void redirectToAddCustomerPageDelete() throws InterruptedException {
        Thread.sleep(2000);
        bankManagerLoginButton.click();
        Thread.sleep(3000);
        addCustomerButton.click();
    }

    public void setUserInformationDelete(String firstNameInput, String lastNameInput, String postCodeInput) throws InterruptedException {
        this.firstName.clear();
        if (!firstNameInput.equals("null")) {
            Thread.sleep(1000);
            this.firstName.sendKeys(firstNameInput);
        }
        this.lastName.clear();
        if (!lastNameInput.equals("null")) {
            Thread.sleep(1000);
            this.lastName.sendKeys(lastNameInput);
        }
        this.postCode.clear();
        if (!postCodeInput.equals("null")) {
            Thread.sleep(1000);
            this.postCode.sendKeys(postCodeInput);
        }
    }

    public void clickSubmitDelete() {
        addCustomerBtn.click();
    }

    public void verifyAddCustomerSuccessfullyDelete() throws InterruptedException {
        String successText = "";
        Alert alert = TestRunner.driver.switchTo().alert();
        String alertMessage = alert.getText();
        Thread.sleep(2000);
        alert.accept();
        for (int i = 0; i < alertMessage.length() - 6; i++) {
            successText += alertMessage.charAt(i);
        }
        Assert.assertEquals(successText, "Customer added successfully with customer");
    }

    public void clickCustomersTabButton() {
        customerTabButton.click();
    }

    public void deleteCustomer(String firstName, String lastName, String postCode) {
        List<WebElement> rows = customerTable.findElements(By.tagName("tr"));
        beforeTableLength = rows.size();
        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
            if (cols.get(0).getText().equals(firstName) && cols.get(1).getText().equals(lastName)
                    && cols.get(2).getText().equals(postCode)) {
                TestRunner.driver.findElement(By.xpath("//tr[" + (i + 1) + "]//button")).click();
            }
        }
    }

    public void verifyDeleteCustomerSuccessfully(String firstName, String lastName, String postCode, Boolean success) {
        List<WebElement> rows = customerTable.findElements(By.tagName("tr"));
        Boolean check = false;

        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
            if (!cols.get(0).getText().equals(firstName) && !cols.get(1).getText().equals(lastName)
                    && !cols.get(2).getText().equals(postCode)) {
                System.out.println("Phát hiện ra bọn đó không bằng nhau rồi nhé. Xóa đúng đứa rồi đó. Yên tâm");
                check = true;
                break;
            }
        }
        if (success) {
            if (!check) Assert.assertEquals(1, 0);
        } else {
            if (check) Assert.assertEquals(1, 0);
        }
    }
}