package pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private Page page;

    // 1. Lưu trữ các Locator
    private String usernameInput = "#user-name";
    private String passwordInput = "#password";
    private String loginButton = "#login-button";
    private String errorMessage = "h3[data-test='error']";

    // 2. Constructor nhận 'page' từ BaseTest
    public LoginPage(Page page) {
        this.page = page;
    }

    // 3. Các hành động (Actions) trên trang
    public void login(String username, String password) {
        page.fill(usernameInput, username);
        page.fill(passwordInput, password);
        page.click(loginButton);
    }

    public String getErrorMessage() {
        return page.locator(errorMessage).textContent();
    }
}