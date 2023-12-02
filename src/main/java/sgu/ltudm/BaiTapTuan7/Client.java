package sgu.ltudm.BaiTapTuan7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) throws Exception {
        DatagramPacket send, recv;
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getLocalHost();
        int port = 1071;
        int buffSize = 512;
        while (true) {
            try {
                System.out.println("--- Nhập lựa chọn ---");
                System.out.println("1. Hash MD5");
                System.out.println("2. Base Conversion");
                System.out.println("( gửi \"exit\" để thoát )");
                BufferedReader consoleInputRoute = new BufferedReader(new InputStreamReader(System.in));
                String inputRoute = consoleInputRoute.readLine();

                if (inputRoute.equalsIgnoreCase("exit")) {
                    return;
                }

                send = new DatagramPacket(inputRoute.getBytes(), inputRoute.getBytes().length, host, port);
                socket.send(send);

                recv = new DatagramPacket(new byte[buffSize], buffSize);
                socket.receive(recv);

                String tmp = new String(recv.getData(), 0, recv.getLength());

                if(tmp.split("\\|")[0].equals("error")) {
                    throw new Exception(tmp.split("\\|")[1]);
                }

                if(tmp.equals(inputRoute)) {
                    recv = new DatagramPacket(new byte[buffSize], buffSize);
                    socket.receive(recv);
                    String msg = new String(recv.getData(), 0, recv.getLength());

                    System.out.println(msg);

                    BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
                    String input = consoleInput.readLine();

                    send = new DatagramPacket(input.getBytes(), input.getBytes().length, host, port);
                    socket.send(send);

                    DatagramPacket recvData = new DatagramPacket(new byte[buffSize], buffSize);
                    socket.receive(recvData);
                    String data = new String(recvData.getData(), 0, recvData.getLength());

//                    if(data.split("\\|")[0].equals("error")) {
//                        throw new Exception("Server: " + tmp.split("\\|")[0]);
//                    }

                    System.out.println(data);
                }
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        }
    }
}
