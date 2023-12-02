package sgu.ltudm.BaiTapNop2;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class bai2_3119410261_ThaiPhuongNam_server {
    public static int buffSize = 512;
    private DatagramSocket serverSocket;
    private DatagramPacket recv;

    public bai2_3119410261_ThaiPhuongNam_server(int port) {
        try {
            serverSocket = new DatagramSocket(port);
            System.out.println("Server listening on port " + port);
        } catch (Exception e) {
            System.err.println("Error: Could not create server socket on port " + port);
        }
    }

    public static void main(String[] args) {
        try {
            int port = 1071;
            bai2_3119410261_ThaiPhuongNam_server server = new bai2_3119410261_ThaiPhuongNam_server(port);
            server.startListening();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void startListening() {
        while (true) {
            try {
                recv = new DatagramPacket(new byte[buffSize], buffSize);
                serverSocket.receive(recv);
                String route = new String(recv.getData(), 0, recv.getLength());
                System.out.println("Client connected: " + recv.getAddress());

                handleClient(route);
            } catch (Exception e) {
                System.err.println("Error: Client connection failed.");
            }
        }
    }

    public void handleClient(String route) throws Exception {
        try {
            System.out.println("Request ID: " + route);
            switch (route) {
                case "requestHashMD5": {
                    HashMD5Server hashMD5Server = new HashMD5Server();
                    hashMD5Server.requestHashMD5(serverSocket);
                    break;
                }
                case "requestBaseConversion": {
                    BaseConversionServer baseConversionServer = new BaseConversionServer();
                    baseConversionServer.requestBaseConversion(serverSocket);
                    break;
                }
                default:
                    throw new Exception("Yêu cầu không hợp lệ");
            }
        } catch (Exception e) {
            String header = "error";
            String msg = header + "|" + e.getMessage();
            byte[] output = msg.getBytes();
            DatagramPacket send = new DatagramPacket(output, output.length, recv.getAddress(), recv.getPort());
            serverSocket.send(send);
        }
    }

    public void stopListening() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

class HashMD5Server {
    private String generateHashMD5(String text) throws Exception {
        // https://hashtoolkit.com/generate-hash/?text=oaxyrdoocm
        String hash = "";

        String url = "https://hashtoolkit.com/generate-hash/?text=" + text;

        Document document = Jsoup.connect(url).ignoreContentType(true).get();

        Elements elements = document.select("a:containsOwn(md5)");

        for (Element element : elements) {
            if (element.text().equals("md5")) {
                hash = element.attr("href").split("/")[2];
                break;
            }
        }

        return "Băm chuỗi \"" + text + "\" thành mã băm \"" + hash + "\"";
    }

    private String decryptHashMD5(String hash) throws Exception {
        // https://hashtoolkit.com/decrypt-hash/?hash=e10adc3949ba59abbe56e057f20f883e
        String text = "";

        String url = "https://hashtoolkit.com/decrypt-hash/?hash=" + hash;

        Document document = Jsoup.connect(url).ignoreContentType(true).get();

        // Sử dụng phương thức select để lấy phần tử <span> có title="decrypted md5 hash"
        Element spanElement = document.select("span[title='decrypted md5 hash']").first();

        if (spanElement != null) {
            Element aElement = spanElement.select("a").first();
            if (aElement != null) {
                text = aElement.attr("href").split("=")[1];
            } else {
                throw new Exception("Không tìm thấy bản dịch ngược của hàm băm: \"" + hash + "\"");
            }
        } else {
            throw new Exception("Không tìm thấy bản dịch ngược của hàm băm: \"" + hash + "\"");
        }

        String decodedString = URLDecoder.decode(text, StandardCharsets.UTF_8);

        return "Dịch ngược mã băm \"" + hash + "\" thành chuỗi \"" + decodedString + "\"";
    }

    public void requestHashMD5(DatagramSocket serverSocket) throws Exception {
        DatagramPacket recv = new DatagramPacket(new byte[bai2_3119410261_ThaiPhuongNam_server.buffSize], bai2_3119410261_ThaiPhuongNam_server.buffSize);
        serverSocket.receive(recv);
        String data = new String(recv.getData(), 0, recv.getLength());

        String msg = "";
        String[] arr = data.split(";");

        if (arr[0].equals("enc")) {
            msg = generateHashMD5(arr[1]);
        } else if (arr[0].equals("dec")) {
            msg = decryptHashMD5(arr[1]);
        } else {
            throw new Exception("Sai cú pháp");
        }

        byte[] output = msg.getBytes();
        DatagramPacket send = new DatagramPacket(output, output.length, recv.getAddress(), recv.getPort());
        serverSocket.send(send);
    }
}

class BaseConversionServer {

    private String convertBaseString(int baseInt) {
        String baseString = "";

        if (baseInt == 2) {
            baseString = "hệ nhị phân";
        }
        if (baseInt == 8) {
            baseString = "hệ bát phân";
        }
        if (baseInt == 10) {
            baseString = "hệ thập phân";
        }
        if (baseInt == 16) {
            baseString = "hệ thập lục phân";
        }

        return baseString;
    }

    private String convert(String data, String from, String to) throws Exception {
        String converted = "";

        // https://networkcalc.com/api/binary/1e7d6d?from=16&to=2
        String url = "https://networkcalc.com/api/binary/" + data + "?from=" + from + "&to=" + to;

        // Gửi yêu cầu HTTP và lấy dữ liệu JSON
        Document document = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).get();

        // Lấy dữ liệu JSON từ phần body
        String json = document.body().text();

        JSONObject object = new JSONObject(json);

        String status = object.getString("status");

        if (status.equals("OK")) {
            converted = object.getString("converted");
        } else if (status.equals("INVALID_NUMBER_FORMAT")) {
            throw new Exception("Định dạng số không hợp lệ");
        } else if (status.equals("INVALID_TO_BASE") || status.equals("INVALID_FROM_BASE")) {
            throw new Exception("Cơ số không hợp lệ");
        } else {
            throw new Exception("Sai cú pháp");
        }

        return data + " (" + convertBaseString(Integer.parseInt(from)) + ") <=> " + converted + " (" + convertBaseString(Integer.parseInt(to)) + ")";
    }

    public void requestBaseConversion(DatagramSocket serverSocket) throws Exception {
        DatagramPacket recv = new DatagramPacket(new byte[bai2_3119410261_ThaiPhuongNam_server.buffSize], bai2_3119410261_ThaiPhuongNam_server.buffSize);
        serverSocket.receive(recv);
        String data = new String(recv.getData(), 0, recv.getLength());

        String[] arr;

        if (data.contains(";")) {
            arr = data.split(";");
        } else {
            throw new Exception("Sai cú pháp");
        }

        if (arr.length != 3) {
            throw new Exception("Sai cú pháp");
        }

        String msg = convert(arr[2], arr[0], arr[1]);

        byte[] output = msg.getBytes();

        DatagramPacket send = new DatagramPacket(output, output.length, recv.getAddress(), recv.getPort());
        serverSocket.send(send);
    }
}
