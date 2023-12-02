package sgu.ltudm.BaiTapTuan3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BaiTapTuan3 {
    public void Bai1() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Nhập domain name (hoặc 'exit' để thoát): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Chương trình kết thúc.");
                break;
            }

            try {
                InetAddress inetAddress = InetAddress.getByName(input);
                String ipAddress = inetAddress.getHostAddress();
                System.out.println("IP của " + input + " là: " + ipAddress);
            } catch (UnknownHostException e) {
                System.out.println("Lỗi: Không tìm thấy IP cho domain name " + input);
            }
        }

        scanner.close();
    }

    public void Bai2() throws Exception {
        // Đường dẫn của tệp bạn muốn đọc
        String filePath = "src/BaiTapTuan3/domains.txt";

        // Mở tệp để đọc
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String domain;

        // Đọc từng dòng trong tệp cho đến khi hết tệp
        while ((domain = bufferedReader.readLine()) != null) {
            try {
                InetAddress inetAddress = InetAddress.getByName(domain);
                String ipAddress = inetAddress.getHostAddress();
                System.out.println("Domain name " + domain + " has IP " + ipAddress);
            } catch (UnknownHostException e) {
                System.out.println("Domain name " + domain + " is not valid");
            }
        }

        // Đóng tệp sau khi hoàn thành
        bufferedReader.close();
    }

    public void Bai3() throws Exception {
        // Đường dẫn của tệp bạn muốn đọc
        String filePath = "src/BaiTapTuan3/ips.txt";

        // Mở tệp để đọc
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String addrStr;

        // Đọc từng dòng trong tệp cho đến khi hết tệp
        while ((addrStr = bufferedReader.readLine()) != null) {
            try {
                String[] parts = addrStr.split("\\.");
                byte[] addr = new byte[4];

                for (int i = 0; i < 4; i++) {
                    addr[i] = (byte) Integer.parseInt(parts[i]);
                }

                InetAddress inetAddress = InetAddress.getByAddress(addr);
                boolean isReachable = inetAddress.isReachable(5000);
                if(isReachable) {
                    System.out.println("IP " + addrStr + " is reachable");
                } else {
                    System.out.println("IP " + addrStr + " is not reachable");
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }

        // Đóng tệp sau khi hoàn thành
        bufferedReader.close();
    }

    public void Bai4() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost(); //Host IP Address
        String ipAddress = inetAddress.getHostAddress();

        String subnet = ipAddress.substring(0, ipAddress.lastIndexOf('.'));
        int timeout = 1000; // Thời gian timeout cho mỗi yêu cầu ping (1 giây)

        System.out.println("Các thiết bị mạng đang online trong mạng " + ipAddress);

        for (int i = 1; i < 255; i++) {
            String host = subnet + "." + i;
            try {
                InetAddress inetAddress1 = InetAddress.getByName(host);
                if (inetAddress1.isReachable(timeout)) {
                    System.out.println(host);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}