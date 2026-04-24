package tests;

import base.BaseTest;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    // Nguồn dữ liệu test (Test Data)
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
                {"standard_user", "secret_sauce", "success"}, // Đăng nhập đúng
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
                {"standard_user", "sai_pass", "Epic sadface: Username and password do not match any user in this service"}
        };
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
            // Nếu là test case thành công, kiểm tra URL xem đã chuyển trang chưa
            Assert.assertTrue(page.url().contains("inventory.html"), "Lỗi: Không thể chuyển sang trang sản phẩm!");
        } else {
            // Nếu là test case thất bại, kiểm tra thông báo lỗi
            String actualError = loginPage.getErrorMessage();
            Assert.assertEquals(actualError, expectedResult, "Lỗi: Thông báo sai!");
        }
    }
}