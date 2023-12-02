package sgu.ltudm.BaiTapNop2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;

public class bai2_3119410261_ThaiPhuongNam_client extends Client {
    public int buffSize = Client.buffSize;
    public String result;
    private final DatagramSocket client = super.client;
    private final DatagramPacket recv = super.recv;
    private DatagramPacket send = super.send;
    private final InetAddress inetAddress;
    private final int port;

    public bai2_3119410261_ThaiPhuongNam_client(InetAddress inetAddress, int port) {
        super();
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        int port = 1071;
        bai2_3119410261_ThaiPhuongNam_client client = new bai2_3119410261_ThaiPhuongNam_client(inetAddress, port);
        while (true) {
            try {
                System.out.print("--- Nhập lựa chọn ---");
                System.out.print("   1. Hash MD5");
                System.out.print("   2. Base Conversion");
                System.out.print("   ( gửi \"exit\" để thoát ):   ");
                BufferedReader consoleInputRoute = new BufferedReader(new InputStreamReader(System.in));
                String inputRoute = consoleInputRoute.readLine();

                if (inputRoute.equalsIgnoreCase("exit")) {
                    System.out.println("Kết thúc");
                    return;
                }

                switch (inputRoute) {
                    case "1": {
                        System.out.print("--- Nhập cú pháp Hash MD5 ---");
                        System.out.print("   (ví dụ \"enc;123456\" để băm hoặc \"dec;e10adc3949ba59abbe56e057f20f883e\" để dịch ngược):   ");
                        BufferedReader consoleInputData = new BufferedReader(new InputStreamReader(System.in));
                        String inputData = consoleInputData.readLine();
                        client.requestHashMD5(inputData);
                        break;
                    }
                    case "2": {
                        System.out.print("--- Nhập cú pháp Base Conversion ---");
                        System.out.print("   (ví dụ \"2;16;101010\" để đổi từ hệ nhị phân sang hệ thập lục phân):   ");
                        BufferedReader consoleInputData = new BufferedReader(new InputStreamReader(System.in));
                        String inputData = consoleInputData.readLine();
                        client.requestBaseConversion(inputData);
                        break;
                    }
                    default:
                        throw new Exception("Lựa chọn không hợp lệ");
                }

                if (!client.result.isEmpty()) {
                    if (client.result.split("\\|")[0].equalsIgnoreCase("error")) {
                        throw new Exception("Server gửi: " + client.result.split("\\|")[1]);
                    } else {
                        System.out.println("Kết quả: " + client.result);
                    }
                }
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
                System.out.println("***");
            }
        }
    }

    public void requestHashMD5(String data) throws Exception {
        // gửi yêu cầu chức năng hash md5
        String requestRoute = "requestHashMD5";
        byte[] byteRoute = requestRoute.getBytes();
        send = new DatagramPacket(byteRoute, byteRoute.length, inetAddress, port);
        client.send(send);

        // gửi dữ liệu
        byte[] byteData = data.getBytes();
        send = new DatagramPacket(byteData, byteData.length, inetAddress, port);
        client.send(send);

        DatagramPacket recv = new DatagramPacket(new byte[buffSize], buffSize);
        client.receive(recv);
        result = new String(recv.getData(), 0, recv.getLength());
    }

    public void requestBaseConversion(String data) throws Exception {
        // gửi yêu cầu chức năng base conversion
        String requestRoute = "requestBaseConversion";
        byte[] byteRoute = requestRoute.getBytes();
        send = new DatagramPacket(byteRoute, byteRoute.length, inetAddress, port);
        client.send(send);

        // gửi dữ liệu
        byte[] byteData = data.getBytes();
        send = new DatagramPacket(byteData, byteData.length, inetAddress, port);
        client.send(send);

        DatagramPacket recv = new DatagramPacket(new byte[buffSize], buffSize);
        client.receive(recv);
        result = new String(recv.getData(), 0, recv.getLength());
    }
}

class Client {
    protected static int buffSize = 512;
    protected DatagramSocket client;
    protected ExecutorService executor;
    protected DatagramPacket recv, send;

    public Client() {
        try {
            client = new DatagramSocket();
        } catch (Exception e) {
            System.err.println("Error: Could not create server socket");
        }
    }

    public void startCommunication() throws Exception {
    }

    public void closeConnection() {
        try {
            client.close();
            executor.shutdown();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
