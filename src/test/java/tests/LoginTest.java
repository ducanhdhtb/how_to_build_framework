package tests;

import base.BaseTest;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.regex.Pattern;
import utils.JsonUtils;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        // Gọi hàm tiện ích và truyền đường dẫn tương đối vào đây
        String path = "src/test/resources/data/loginData.json";
        return JsonUtils.getJsonData(path);
    }

    @Test(dataProvider = "loginData")
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