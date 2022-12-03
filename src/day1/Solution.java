package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class Solution {
    private static final boolean testMode = true;
    private static final int puzzleNum = 2;

    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
        String filePath = "src/day1/" + fileName;

        switch (puzzleNum) {
            case 1 -> puzzle1(filePath);
            case 2 -> puzzle2(filePath);
        }
    }

    private static void puzzle1(String filePath) {
        int maxCal = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            int runningTotal = 0;
            while (true) {
                if (line == null || line.isEmpty()) {
                    if (maxCal < runningTotal) {
                        maxCal = runningTotal;
                    }
                    if (line == null) {
                        break;
                    }
                    runningTotal = 0;
                } else {
                    runningTotal += Integer.parseInt(line);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(maxCal);
    }

    private static void puzzle2(String filePath) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            int runningTotal = 0;
            while (true) {
                if (line == null || line.isEmpty()) {
                    int currentMin = pq.isEmpty() ? 0 : pq.peek();
                    if (currentMin < runningTotal) {
                        if (pq.size() == 3) {
                            pq.poll();
                        }
                        pq.add(runningTotal);
                    }
                    if (line == null) {
                        break;
                    }
                    runningTotal = 0;
                } else {
                    runningTotal += Integer.parseInt(line);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int totalCal = pq.stream()
                        .mapToInt(Integer::intValue)
                        .sum();

        System.out.println(totalCal);
    }

}
