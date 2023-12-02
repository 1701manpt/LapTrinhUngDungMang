package sgu.ltudm.BaiTapNop1;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class bai1_3119410261_ThaiPhuongNam_server {
    private ServerSocket serverSocket;
    private ExecutorService executor;

    public bai1_3119410261_ThaiPhuongNam_server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("bai2_3119410261_ThaiPhuongNam_server listening on port " + port);
//            executor = Executors.newFixedThreadPool(10); // Create a thread pool for handling multiple connections.
            executor = Executors.newCachedThreadPool();
        } catch (IOException e) {
            System.err.println("Error: Could not create server socket on port " + port);
        }
    }

    public static void main(String[] args) {
        try {
            int port = 1071;
            bai1_3119410261_ThaiPhuongNam_server server = new bai1_3119410261_ThaiPhuongNam_server(port);
            server.startListening();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void startListening() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                Runnable clientHandler = () -> handleClient(clientSocket);

                executor.execute(clientHandler);
            } catch (IOException e) {
                System.err.println("Error: Client connection failed.");
            }
        }
    }

    public void handleClient(Socket clientSocket) {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            // 3. Xử lý dữ liệu từ client
            switch (in.readLine()) {
                case "requestTiki": {
                    System.out.println("Request ID: requestTiki");
                    switch (in.readLine()) {
                        case "requestProductByLink":
                            System.out.println("Request ID: requestProductByLink");
                            requestProductByLink(in.readLine(), out);
                            break;
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    // sẽ tách ra ngoài class bai2_3119410261_ThaiPhuongNam_server
    private String getIdFromLink(String link) {
        // https://tiki.vn/macbook-air-m2-2022-p189371737.html?&itm_medium=CPC&itm_source=tiki-recospid=189371739
        Pattern pattern = Pattern.compile("-p(.*?)\\.html");
        Matcher matcher = pattern.matcher(link);
        String idLink = null;

        while (matcher.find()) {
            idLink = matcher.group(1);
        }

        return idLink;
    }

    // sẽ tách ra ngoài class bai2_3119410261_ThaiPhuongNam_server
    private String getDataById(String idLink) throws Exception {
        int reviewLength = 10;

        // Địa chỉ URL của API
        String productUrl = "https://tiki.vn/api/v2/products/" + idLink;
        String reviewsUrl = "https://tiki.vn/api/v2/reviews?limit=" + reviewLength + "&include=comments,contribute_info,attribute_vote_summary&sort=score%7Cdesc,id%7Cdesc,stars%7Call&page=1&spid=" + idLink + "&product_id=" + idLink;

        // Gửi yêu cầu HTTP và lấy dữ liệu JSON
        Document productDocument = Jsoup.connect(productUrl).ignoreContentType(true).get();
        Document reviewsDocument = Jsoup.connect(reviewsUrl).ignoreContentType(true).get();

        // Lấy dữ liệu JSON từ phần body
        String productJson = productDocument.body().text();
        String reviewsJson = reviewsDocument.body().text();

        JSONObject productJsonObject = new JSONObject(productJson);
        JSONObject reviewsJsonObject = new JSONObject(reviewsJson);

        int id = productJsonObject.getInt("id");
        String name = productJsonObject.getString("name");
        double price = productJsonObject.getDouble("price");

        float ratingAverage = reviewsJsonObject.getFloat("rating_average");
        int reviewsCount = reviewsJsonObject.getInt("reviews_count");
        JSONArray reviewsJsonArray = reviewsJsonObject.getJSONArray("data");

        // Chuyển JSONArray thành mảng
        int length = reviewLength;
        if (reviewsJsonArray.length() < 10) {
            length = reviewsJsonArray.length();
        }

        LinkedList reviewList = new LinkedList();

        for (int i = 0; i < length; i++) {
            JSONObject reviewJsonObject = reviewsJsonArray.getJSONObject(i);
            reviewList.add(reviewJsonObject.getString("title") + ": " + reviewJsonObject.getString("content"));
        }

        String reviews = "- " + String.join("\n- ", reviewList);

        LinkedList product = new LinkedList();
        product.add("Mã sản phẩm: " + id);
        product.add("Tên sản phẩm: " + name);
        product.add("Giá: " + String.format("%.0f", price));
        product.add("Xếp hạng trung bình: " + ratingAverage);
        product.add("Đây là " + length + " bài đánh giá đầu tiên ở trên trang web Tiki sẽ hiện ra cho bạn đọc trước: ");
        product.add(reviews);

        String data = String.join("\n", product);

        return data;
    }

    public void requestProductByLink(String link, BufferedWriter out) throws Exception {
        System.out.println("Liên kết của sản phẩm Tiki từ Client gửi đến: " + link);

        String idLink = getIdFromLink(link);

        // Mã hóa đoạn văn bản
        String encodedData = Base64.getEncoder().encodeToString(getDataById(idLink).getBytes());

        out.write(encodedData);
        out.newLine();
        out.flush();

        System.out.println("Đã trả về dữ liệu cho Client");
        System.out.println("***");
    }

    public void stopListening() {
        try {
            serverSocket.close();
            executor.shutdown();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
