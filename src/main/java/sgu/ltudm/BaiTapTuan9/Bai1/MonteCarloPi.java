package sgu.ltudm.BaiTapTuan9.Bai1;

import java.util.Random;

public class MonteCarloPi {
    private static long totalPoints = 0;
    private static long pointsInCircle = 0;

    public static void main(String[] args) {
        final int numThreads = 2;
        final int numPointsPerThread = 1_000_000;

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new MonteCarloTask(numPointsPerThread));
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double pi = 4.0 * pointsInCircle / totalPoints;
        System.out.println("Estimated Pi: " + pi);
    }

    static class MonteCarloTask implements Runnable {
        private final int numPoints;

        public MonteCarloTask(int numPoints) {
            this.numPoints = numPoints;
        }

        @Override
        public void run() {
            Random random = new Random();
            long localPointsInCircle = 0;

            for (int i = 0; i < numPoints; i++) {
                double x = random.nextDouble();
                double y = random.nextDouble();
                double distance = x * x + y * y;

                if (distance <= 1) {
                    localPointsInCircle++;
                }
            }

            synchronized (MonteCarloPi.class) {
                pointsInCircle += localPointsInCircle;
                totalPoints += numPoints;
            }
        }
    }
}
