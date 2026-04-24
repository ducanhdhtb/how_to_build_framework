package tests;

import base.BaseTest;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.regex.Pattern;
import utils.ExcelUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {

    @DataProvider(name = "excelData")
    public Object[][] getExcelData() {
        String path = "src/test/resources/data/LoginData.xlsx";
        // Chúng ta gọi hàm từ ExcelUtils, truyền vào đường dẫn và tên Sheet
        return ExcelUtils.getExcelData(path, "Sheet1");
    }

    @Test(dataProvider = "excelData")
    public void testLoginFlow(String username, String password, String expectedResult) {
        // 1. Mở trang web bằng biến baseUrl đã hứng từ testng.xml trong BaseTest
        page.navigate(baseUrl);

        // 2. Khởi tạo trang Login và thực hiện đăng nhập
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(username, password);

        // 3. Kiểm chứng kết quả (Assertion)
        if (expectedResult.equals("success")) {
            // Thay thế cho lệnh Assert.assertTrue(...) cũ
            assertThat(page).hasURL(Pattern.compile(".*inventory.html.*"));
        } else {
            // Nếu là test case thất bại, kiểm tra thông báo lỗi
            String actualError = loginPage.getErrorMessage();
            Assert.assertEquals(actualError, expectedResult, "Lỗi: Thông báo sai!");
        }
    }
}