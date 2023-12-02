package sgu.ltudm.BaiTapNop3;

import java.io.*;
import java.net.Socket;

public class bai3_3119410261_ThaiPhuongNam_client {
    private static final String host = "localhost";
    private static final int port = 1234;
    private static Socket socket;

    public static void main(String[] args) throws IOException {
        try {
            socket = new Socket(host, port);
            System.out.println("Client connected");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String input;
            while (true) {
                System.out.print("Client sent: ");
                input = stdIn.readLine();
                out.write(input + '\n');
                out.flush();
                if (input.equals("bye")) break;

                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (socket != null) {
                socket.close();
                System.out.println("Client socket closed");
            }
        }
    }
}
