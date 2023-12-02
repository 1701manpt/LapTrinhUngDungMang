package sgu.ltudm.BaiTapTuan2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BaiTapTuan2 {
    public void Bai1(String input) throws Exception {
        try {
            StringTokenizer st = new StringTokenizer(input, "+-*/", true);
            int s = 0;
            s += Integer.parseInt(st.nextToken());

            while (st.hasMoreTokens()) {
                var token = st.nextToken();

                switch (token) {
                    case "+" -> s += Integer.parseInt(st.nextToken());
                    case "-" -> s -= Integer.parseInt(st.nextToken());
                    case "*" -> s *= Integer.parseInt(st.nextToken());
                    case "/" -> {
                        if(Integer.parseInt(st.nextToken()) == 0) {
                            throw new Exception("Không thể chia cho số 0");
                        }
                        s /= Integer.parseInt(st.nextToken());
                    }
                }
            }

            System.out.println(String.join(" ", input) + "=" + s);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void Bai2(String input) throws Exception {
        try {
            String inputOnlyOneSpace = input.trim().replaceAll("\\s+", " ");

            var arr = inputOnlyOneSpace.split(" ");
            LinkedList<String> arrOutput = new LinkedList<>();

            arrOutput.add(arr[0]);

            for (String s : arr) {
                boolean isAdd = true;

                for (String st : arrOutput) {
                    boolean isExist = st.equalsIgnoreCase(s);

                    if (isExist) {
                        isAdd = false;
                        break;
                    }
                }

                if (isAdd) {
                    arrOutput.add(s);
                }
            }

            System.out.println(String.join(" ", arrOutput));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void Bai3(String word) throws Exception {
        try {
            // Đường dẫn của tệp bạn muốn đọc
            String filePath = "src/BaiTapTuan2/dictionary.txt";

            // Mở tệp để đọc
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            String wordMean = "";

            // Đọc từng dòng trong tệp cho đến khi hết tệp
            while ((line = bufferedReader.readLine()) != null) {
                String[] wordArray = line.split(";");

                if(wordArray[0].equals(word)) {
                    wordMean = wordArray[1];
                    break;
                }

                if(wordArray[1].equals(word)) {
                    wordMean = wordArray[0];
                    break;
                }

                throw new Exception("Từ '" + word + "' không có trong từ điển");
            }

            // Đóng tệp sau khi hoàn thành
            bufferedReader.close();

            System.out.println(wordMean);
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void HienThi() throws Exception {
        try {
            System.out.println("Tuần 2:");
            System.out.println("Bài 1:");
            Bai1("111/0");
            System.out.println("Bài 2:");
            Bai2("Dai hoc sai gon la mot trong nhung truong dai hoc lau doi nhat sai  gon");
            System.out.println("Bài 3:");
            Bai3("home");
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
