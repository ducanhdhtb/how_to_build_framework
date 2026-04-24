package pages;

import com.microsoft.playwright.Page;
import base.BaseTest;

public class LoginPage {
    private Page page;

    public LoginPage() {
        // Lấy đúng page của luồng hiện tại
        this.page = BaseTest.getPage();
    }

    public void login(String user, String pass) {
        page.fill("#user-name", user);
        page.fill("#password", pass);
        page.click("#login-button");
    }

    public String getErrorMessage() {
        return page.locator("h3[data-test='error']").textContent();
    }
}