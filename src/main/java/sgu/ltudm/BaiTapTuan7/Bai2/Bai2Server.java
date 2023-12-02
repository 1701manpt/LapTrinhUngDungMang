package sgu.ltudm.BaiTapTuan7.Bai2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Bai2Server {

    static int port = 1234;

    public static void main(String[] args) throws Exception {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket send, recv;
            int buffSize = 512;
            while (true) {
                recv = new DatagramPacket(new byte[buffSize], buffSize);
                socket.receive(recv);
                String data = new String(recv.getData(), 0, recv.getLength());
                System.out.println("bai2_3119410261_ThaiPhuongNam_server nhận: " + data);
                if (data.equals("bye")) break;
                Bai2 b2 = new Bai2();
                int count = b2.count(Integer.parseInt(data));
                byte[] output = Integer.toString(count).getBytes();
                send = new DatagramPacket(output, output.length, recv.getAddress(), recv.getPort());
                socket.send(send);
            }
            System.out.println("bai2_3119410261_ThaiPhuongNam_server đóng kết nối đến client " + recv.getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
