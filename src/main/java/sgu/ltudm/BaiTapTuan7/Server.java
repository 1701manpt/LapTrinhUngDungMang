package sgu.ltudm.BaiTapTuan7;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void hashMd5() {

    }
    public static void main(String[] args) throws Exception {
        int port = 1071;
        DatagramSocket socket = new DatagramSocket(port);
        DatagramPacket send, recv = null;
        int buffSize = 512;
        String header = "info";
        while (true) {
            try {
                recv = new DatagramPacket(new byte[buffSize], buffSize);
                socket.receive(recv);
                String route = new String(recv.getData(), 0, recv.getLength());

                if (route.equals("1")) {
                    byte[] output = route.getBytes();
                    send = new DatagramPacket(output, output.length, recv.getAddress(), recv.getPort());
                    socket.send(send);

                    byte[] msg = "Nhập cú pháp băm MD5 hoặc dịch ngược MD5: ".getBytes();
                    send = new DatagramPacket(msg, msg.length, recv.getAddress(), recv.getPort());
                    socket.send(send);
                } else if (route.equals("2")) {
                    byte[] output = route.getBytes();
                    send = new DatagramPacket(output, output.length, recv.getAddress(), recv.getPort());
                    socket.send(send);

                    byte[] msg = "Nhập cú pháp chuyển đổi cơ số: ".getBytes();
                    send = new DatagramPacket(msg, msg.length, recv.getAddress(), recv.getPort());
                    socket.send(send);
                } else {
                    throw new Exception("Lựa chọn không hợp lệ");
                }
            } catch (Exception e) {
                header = "error";
                String err = header + "|" + e.getMessage();
                byte[] errByte = err.getBytes();
                send = new DatagramPacket(errByte, errByte.length, recv.getAddress(), recv.getPort());
                socket.send(send);
            }
        }
    }
}