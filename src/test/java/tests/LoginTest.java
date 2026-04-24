package tests;

import base.BaseTest;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.regex.Pattern;
import utils.ExcelUtils;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {

    @DataProvider(name = "excelData", parallel = true) // Cho phép DataProvider chạy song song
    public Object[][] getExcelData() {
        return ExcelUtils.getExcelData("src/test/resources/data/LoginData.xlsx", "Sheet1");
    }

    @Test(dataProvider = "excelData")
    public void testLoginFlow(String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage();
        loginPage.login(username, password);

        if (expectedResult.equals("success")) {
            assertThat(getPage()).hasURL(Pattern.compile(".*inventory.html.*"));
        } else {
            Assert.assertEquals(loginPage.getErrorMessage(), expectedResult);
        }
    }
}