package sgu.ltudm.BaiTapNop3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class bai3_3119410261_ThaiPhuongNam_server {
    public static int port = 1234;
    public static int numThread = 2;
    private static ServerSocket server = null;

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        try {
            server = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            while (true) {
                Socket socket = server.accept();
                executor.execute(new bai3_3119410261_ThaiPhuongNam_thread(socket));
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (server != null) server.close();
        }
    }
}