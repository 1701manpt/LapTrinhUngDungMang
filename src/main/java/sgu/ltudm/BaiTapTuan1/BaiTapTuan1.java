package sgu.ltudm.BaiTapTuan1;

import java.util.HashMap;
import java.util.LinkedList;

public class BaiTapTuan1 {
    public int Bai1(int n) throws Exception {
        try {
            if (n < 0) {
                throw new Exception("Số n nhập vào phải là số nguyên dương.");
            }

            int s = 0;
            for (int i = 0; i <= n; i++) {
                s += i;
            }

            return s;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public int Bai2(int n) throws Exception {
        try {
            if (n < 3) {
                throw new Exception("Số n nhập vào phải lớn hơn hoặc bằng 3.");
            }

            int s = 0;
            for (int i = 1; i <= n; i++) {
                if (n % i == 0) {
                    s += i;
                }
            }

            return s;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public int Bai3(int n) throws Exception {
        try {
            if (n < 3) {
                throw new Exception("Số n nhập vào phải lớn hơn hoặc bằng 3.");
            }

            int s = 0;

            for (int i = 2; i <= n; i++) {
                int count = 0;

                for (int j = 1; j <= i; j++) {
                    if (n % j == 0) {
                        count++;
                    }
                }

                if (count == 2) {
                    s += i;
                }
            }

            return s;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String Bai4(int n) throws Exception {
        try {
            if (n < 0) {
                throw new Exception("Số n nhập vào phải là số nguyên dương.");
            }

            String s = "";

            HashMap<Integer, Integer> hashmap = new HashMap<>();

            for (int i = 2; i < n; i++) {
                if (n % i == 0) {
                    hashmap.put(i, hashmap.getOrDefault(i, 0) + 1);
                }
            }

            LinkedList<String> outputStr = new LinkedList<>();
            for (int item : hashmap.keySet()) {
                outputStr.add(item + "^" + hashmap.get(item));
            }

            return String.join("*", outputStr);

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void Bai5(int n) throws Exception {
        try {
            System.out.println(Bai1(n));
            System.out.println(Bai2(n));
            System.out.println(Bai3(n));
            System.out.println(Bai4(n));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void HienThi() throws Exception {
        try {
            System.out.println("Tuần 1:");
            System.out.println("Bài 1:");
            System.out.println(Bai1(10));
            System.out.println("Bài 2:");
            System.out.println(Bai2(10));
            System.out.println("Bài 3:");
            System.out.println(Bai3(10));
            System.out.println("Bài 4:");
            System.out.println(Bai4(10));
            System.out.println("Bài 5:");
            Bai5(10);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}