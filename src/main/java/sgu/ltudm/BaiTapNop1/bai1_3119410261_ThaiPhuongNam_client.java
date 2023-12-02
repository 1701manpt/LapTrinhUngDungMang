package sgu.ltudm.BaiTapNop1;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class bai1_3119410261_ThaiPhuongNam_client extends Client {
    private final Socket clientSocket = super.clientSocket;
    private final BufferedReader in = super.in;
    private final BufferedWriter out = super.out;

    public String data;

    public bai1_3119410261_ThaiPhuongNam_client(String serverAddress, int serverPort) {
        super(serverAddress, serverPort);
    }

    public static void main(String[] args) throws Exception {
        boolean isContinue = true;
        while (isContinue) {
            System.out.println("--- Nhập liên kết sản phẩm của Tiki ---");
            System.out.println("( gửi \"exit\" để thoát )");
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String input = consoleInput.readLine();

            if (input.equalsIgnoreCase("exit")) {
                isContinue = false;
                return;
            }

            try {
                checkLinkTikiProduct(input);

                System.out.println("Liên kết bạn đã nhập: " + input);

                // bắt đầu tìm server để kết nối
                InetAddress inetAddress = InetAddress.getLocalHost(); //Host IP Address
                String host = inetAddress.getHostAddress();
                int port = 1071;
                bai1_3119410261_ThaiPhuongNam_client tikiClient = new bai1_3119410261_ThaiPhuongNam_client(host, port);
                tikiClient.startCommunication();

                // request lấy chức năng đó và gán dữ liệu vào biến tikiClient.data
                tikiClient.requestProductByLink(input);

                // Giải mã đoạn văn bản
                byte[] decodedBytes = Base64.getDecoder().decode(tikiClient.data);
                String decodedData = new String(decodedBytes);

                System.out.println("*****");
                System.out.println(decodedData);
                System.out.println("*****");
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
                System.out.println();
            }
        }
    }

    public static void checkLinkTikiProduct(String input) throws Exception {
        if (!input.startsWith("http://") && !input.startsWith("https://")) {
            throw new Exception("Không phải liên kết");
        }

        String specificDomain = "tiki.vn";
        URL url = new URL(input);
        String domain = url.getHost();

        // Tạo một mẫu regex
        Pattern pattern = Pattern.compile("-p.+\\.html");

        // Tạo một Matcher để kiểm tra chuỗi
        Matcher matcher = pattern.matcher(input);

        if (!domain.equals(specificDomain) || !matcher.find()) {
            throw new Exception("Không phải liên kết sản phẩm của Tiki");
        }
    }

    @Override
    public void startCommunication() throws IOException {
        String requestId = "requestTiki";
        out.write(requestId);
        out.newLine();
        out.flush();
    }

    public void requestProductByLink(String title) throws IOException {
        String requestId = "requestProductByLink";
        out.write(requestId);
        out.newLine();
        out.write(title);
        out.newLine();
        out.flush();

        data = in.readLine();
    }
}

class Client {
    protected Socket clientSocket;
    protected BufferedReader in;
    protected BufferedWriter out;

    public Client(String serverAddress, int serverPort) {
        try {
            clientSocket = new Socket(serverAddress, serverPort);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("Error: Could not connect to the server.");
            e.printStackTrace();
        }
    }

    public void startCommunication() throws Exception {

    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}