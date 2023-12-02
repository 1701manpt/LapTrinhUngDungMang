package sgu.ltudm.BaiTapTuan7.Bai2;

import java.io.BufferedReader;
import java.io.FileReader;

class Bai2 {
    public Bai2() {

    }

    public boolean check(int number, int n) {
        boolean isTrue = false;
        int s = 0;

        while (number > 0) {
            s += number % 10;
            number /= 10;
        }

        if (s == n) {
            isTrue = true;
        }

        return isTrue;
    }

    public int count(int n) throws Exception {
        // Đường dẫn của tệp bạn muốn đọc
        String filePath = "src/main/java/sgu/ltudm/BaiTapTuan7/Bai2/hehe.txt";

        // Mở tệp để đọc
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        int count = 0;

        // Đọc từng dòng trong tệp cho đến khi hết tệp
        while ((line = bufferedReader.readLine()) != null) {
            try {
                int tmp = Integer.parseInt(line);

                boolean check = check(tmp, n);
                System.out.println(tmp + "\n" + check);

                if (check) {
                    count++;
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        // Đóng tệp sau khi hoàn thành
        bufferedReader.close();

        return count;
    }
}
