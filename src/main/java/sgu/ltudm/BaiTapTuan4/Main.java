package sgu.ltudm.BaiTapTuan4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 1071;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("bai2_3119410261_ThaiPhuongNam_server đang chờ kết nối từ client...");

            while (true) {
                // Chấp nhận kết nối từ client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client đã kết nối từ " + clientSocket.getInetAddress().getHostAddress());

                // Tạo một luồng riêng cho client này
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lớp xử lý kết nối từ client
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    // Xử lý dữ liệu ở đây (đọc từ client và gửi lại, hoặc thực hiện các tác vụ khác)

                    // Tạo luồng đọc dữ liệu từ client
                    BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // Tạo luồng ghi dữ liệu đến client
                    BufferedWriter clientOutput = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    String clientJson;
                    while ((clientJson = clientInput.readLine()) != null) {
                        System.out.println("Dữ liệu từ client: " + clientJson);

                        BaiTapTuan4 btt4 = new BaiTapTuan4();

                        // Gửi phản hồi cho client
                        String response = clientJson + "\n";
                        clientOutput.write(response);
                        clientOutput.flush();
                    }
                }
            } catch (Exception e) {
                // Xử lý ngoại lệ khi client đóng kết nối
                System.out.println("Client đã đóng kết nối từ " + clientSocket.getInetAddress().getHostAddress());
            }
        }
    }
}
