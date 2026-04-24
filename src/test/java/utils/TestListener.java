package utils;

import io.qameta.allure.Attachment;
import org.testng.ITestListener;
import org.testng.ITestResult;
import base.BaseTest;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("⚠️ Đang xử lý lỗi cho test: " + result.getName());
        try {
            Object testClass = result.getInstance();
            // Chắc chắn rằng testClass không null trước khi lấy page
            com.microsoft.playwright.Page page = ((base.BaseTest) testClass).getPage();

            if (page != null) {
                byte[] screenshot = page.screenshot();
                saveScreenshot(screenshot); // Gọi hàm đính kèm với mảng byte
                System.out.println("📸 Đã đính kèm ảnh vào Allure thành công!");
            }
        } catch (Exception e) {
            System.out.println("❌ Không thể chụp ảnh: " + e.getMessage());
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }
}