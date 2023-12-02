package sgu.ltudm.BaiTapTuan3;//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import org.json.JSONObject;
//
//public class WikipediaApi {
//
//    public static void main(String[] args) {
//        String searchTerm = "Tên ca sĩ"; // Thay thế bằng tên ca sĩ mà người dùng nhập
//
//        try {
//            // Tạo URL cho API của Wikipedia
//            String apiUrl = "https://vi.wikipedia.org/w/api.php?action=query&format=json&prop=extracts|pageimages&exintro&explaintext&titles=" + searchTerm;
//            URL url = new URL(apiUrl);
//
//            // Mở kết nối HTTP
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//
//            // Lấy dữ liệu JSON từ phản hồi
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//            reader.close();
//
//            // Chuyển dữ liệu JSON thành đối tượng JSON
//            JSONObject jsonResponse = new JSONObject(response.toString());
//
//            // Trích xuất thông tin từ đối tượng JSON
//            JSONObject pages = jsonResponse.getJSONObject("query").getJSONObject("pages");
//            String pageId = pages.keys().next(); // Lấy ID của trang Wikipedia
//            JSONObject page = pages.getJSONObject(pageId);
//            String title = page.getString("title"); // Họ tên ca sĩ
//            String extract = page.getString("extract"); // Thông tin mô tả
//
//            // In ra thông tin tìm được
//            System.out.println("Họ tên: " + title);
//            System.out.println("Thông tin: " + extract);
//
//            // Đóng kết nối
//            connection.disconnect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
