package base;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import utils.ConfigReader;

public class BaseTest {
    private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> tlContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> tlPage = new ThreadLocal<>();

    public static Page getPage() {
        return tlPage.get();
    }

    @Parameters({"browser", "env"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browserName, @Optional("https://www.saucedemo.com/") String env) {
        Playwright playwright = Playwright.create();
        tlPlaywright.set(playwright);

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false);
        Browser browser = browserName.equalsIgnoreCase("firefox") ?
                playwright.firefox().launch(options) :
                playwright.chromium().launch(options);
        tlBrowser.set(browser);

        BrowserContext context = browser.newContext();
        tlContext.set(context);

        Page page = context.newPage();
        tlPage.set(page);

        page.navigate(env);
    }

    @AfterMethod
    public void tearDown() {
        if (tlPage.get() != null) tlPage.get().close();
        if (tlContext.get() != null) tlContext.get().close();
        if (tlBrowser.get() != null) tlBrowser.get().close();
        if (tlPlaywright.get() != null) tlPlaywright.get().close();

        tlPage.remove();
        tlContext.remove();
        tlBrowser.remove();
        tlPlaywright.remove();
    }
}