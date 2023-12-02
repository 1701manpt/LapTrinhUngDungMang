package sgu.ltudm.BaiTapNop3;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;

public class bai3_3119410261_ThaiPhuongNam_thread implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public bai3_3119410261_ThaiPhuongNam_thread(Socket s) {
        this.socket = s;
    }

    public void run() {
        System.out.println("Client " + socket.toString() + " accepted");
        while (true) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String input = "";
                input = in.readLine();
                System.out.println("Server received: " + input + " from " + socket);
                if (input.equals("bye")) break;

                String[] arr = input.trim().split("\\s+");

                switch (arr[0]) {
                    case "weather": {
                        out.write(weather(in.readLine()));
                        out.newLine();
                        out.flush();
                        break;
                    }

                    case "calc": {
                        out.write(calc(arr[1]));
                        out.newLine();
                        out.flush();
                        break;
                    }

                    default: {
                        out.write("?");
                        out.newLine();
                        out.flush();
                    }
                }
                System.out.println("Closed socket for client " + socket.toString());
                in.close();
                out.close();
                socket.close();
            } catch (Exception e) {
                try {
                    System.err.println(e.getMessage());
                    out.write(e.getMessage());
                    out.newLine();
                    out.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private String weather(String str) {
        return "";
    }

    private String calc(String str) throws Exception {
        str = URLEncoder.encode(str, StandardCharsets.UTF_8);
        String url = "https://api.mathjs.org/v4/?expr=" + str;
        Document document = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).get();
        System.out.println(document.text());
        String result = document.text();

        if (!isNumber(result)) {
            throw new Exception("Lỗi cú pháp");
        }

        return result;
    }

    public boolean isNumber(String str) throws Exception {
        try {
            Number num = NumberFormat.getInstance().parse(str);
            if (num instanceof Integer || num instanceof Long || num instanceof Float || num instanceof Double) {
                return true;
            }
        } catch (ParseException e) {
            throw new Exception("Lỗi cú pháp");
        }
        return false;
    }
}
