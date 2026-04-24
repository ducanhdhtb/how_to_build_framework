package base;

import com.microsoft.playwright.*;
import org.testng.annotations.*;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected String baseUrl; // Biến dùng để lưu URL môi trường

    // Hứng 2 giá trị từ thẻ <parameter> trong file XML
    @Parameters({"browser", "env"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browserName, @Optional("https://www.saucedemo.com/") String env) {
        playwright = Playwright.create();
        this.baseUrl = env; // Lưu URL để lát nữa test case lấy ra dùng

        // 1. Tạo Options và chỉ định dùng thẳng Chrome trên máy
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setChannel("chrome"); // <--- Thêm dòng này

        // 2. Chạy trình duyệt
        if (browserName.equalsIgnoreCase("firefox")) {
            // (Tạm thời chúng ta sẽ cấu hình Firefox sau)
            browser = playwright.firefox().launch(options);
        } else {
            browser = playwright.chromium().launch(options);
        }

        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    public void tearDown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();

    }
    public Page getPage() {
        return this.page;
    }
}