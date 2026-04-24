
package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    // ... nội dung hàm getJsonData ở đây ...
    public static Object[][] getJsonData(String filePath) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(filePath));

            // Đọc file thành danh sách các Map
            List<Map<String, String>> dataList = gson.fromJson(reader, new TypeToken<List<Map<String, String>>>() {}.getType());

            // Khởi tạo mảng 2 chiều: Hàng = số lượng item, Cột = 3 (user, pass, result)
            Object[][] data = new Object[dataList.size()][3];

            for (int i = 0; i < dataList.size(); i++) {
                data[i][0] = dataList.get(i).get("username");
                data[i][1] = dataList.get(i).get("password");
                data[i][2] = dataList.get(i).get("expectedResult");
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


