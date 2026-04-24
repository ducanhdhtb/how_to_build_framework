package utils;

import io.qameta.allure.Attachment;
import org.testng.ITestListener;
import org.testng.ITestResult;
import base.BaseTest;

public class TestListener implements ITestListener {

    // Hàm này sẽ tự động chạy ngay khi một test case bị Fail
    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        // Lấy đối tượng page từ BaseTest để chụp ảnh
        com.microsoft.playwright.Page page = ((BaseTest) testClass).getPage();

        if (page != null) {
            saveScreenshot(page);
        }
    }

    // Annotation này báo cho Allure biết đây là tệp đính kèm
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(com.microsoft.playwright.Page page) {
        return page.screenshot();
    }
}