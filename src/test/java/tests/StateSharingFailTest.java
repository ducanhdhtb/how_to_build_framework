package tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.*;

public class StateSharingFailTest {
    Playwright playwright;
    Browser browser;
    Page page; // Tab này sẽ bị dùng chung cho mọi test case!

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));


        // ⚠️ SAI LẦM NẰM Ở ĐÂY: Khởi tạo Page 1 lần duy nhất trước khi chạy class
        page = browser.newPage();
    }

    @DataProvider(name = "users")
    public Object[][] getData() {
        return new Object[][] {
                {"standard_user", "secret_sauce"}, // Lần 1
                {"problem_user", "secret_sauce"}   // Lần 2
        };
    }

    @Test(dataProvider = "users")
    public void testLogin(String user, String pass) {
        // 1. Vào trang chủ
        page.navigate("https://www.saucedemo.com/");

        // 2. Điền thông tin và đăng nhập
        page.fill("#user-name", user);
        page.fill("#password", pass);
        page.click("#login-button");

        // 3. Kiểm tra xem đã vào được trang sản phẩm chưa
        Assert.assertTrue(page.url().contains("inventory.html"));
    }

    @AfterClass
    public void tearDown() {
        browser.close();
        playwright.close();
    }
}

//Chuyện gì sẽ xảy ra khi bạn bấm Run? 💥
//Vòng lặp 1 (Với standard_user): ✅ Test PASS
//
//Web mở ra, điền user/pass thành công.
//
//Web chuyển sang trang inventory.html.
//
//Trình duyệt lưu lại Cookie đăng nhập của standard_user.
//
//Vòng lặp 2 (Với problem_user): ❌ Test FAIL SẬP MẶT
//
//TestNG bắt đầu vòng 2, gọi lệnh page.navigate("https://www.saucedemo.com/").
//
//Do vẫn dùng chung cái page cũ từ vòng 1, Cookie đăng nhập vẫn còn y nguyên.
//
//Trang web SauceDemo nhận diện: "À, người này đã đăng nhập rồi, cho vào thẳng bên trong luôn, không cần hiện màn hình Login nữa!". Nó tự động nhảy thẳng vào trang inventory.html.
//
//Playwright cố gắng tìm ô #user-name để điền chữ problem_user, nhưng tìm hoài không thấy (vì làm gì có ô đăng nhập nào ở trang sản phẩm).
//
//Sau 30 giây chờ đợi, Playwright ném ra lỗi TimeoutError và sập test.
//
//Đây chính là hậu quả của việc Chia sẻ trạng thái (State Sharing). Dữ liệu của test trước làm ô nhiễm môi trường của test sau.
//
//Để giải quyết triệt để, Playwright cung cấp BrowserContext - một đối tượng đóng vai trò như một "Cửa sổ ẩn danh" mới tinh, không chứa bất kỳ Cookie nào cũ, khởi tạo chỉ mất vài mili-giây.
//
//Dựa vào những gì chúng ta đã phân tích về tốc độ chậm/nhanh của các thành phần, bạn sẽ cấu trúc lại class BaseTest của chúng ta như thế nào? Thành phần nào (Playwright, Browser, BrowserContext, Page) sẽ được đặt trong @BeforeClass, và thành phần nào sẽ được đặt trong @BeforeMethod?