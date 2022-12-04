package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


// Trying to go for speed this time since I started right as it released

public class Solution {
    private static final boolean testMode = false;
    private static final int puzzleNum = 2;

    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
        String filePath = "src/day4/" + fileName;

        switch (puzzleNum) {
            case 1 -> puzzle1(filePath);
            case 2 -> puzzle2(filePath);
        }
    }

    private static void puzzle1(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            AtomicInteger overlapCount = new AtomicInteger();
            br.lines().forEach(line -> {
                if (findCompleteOverlap(line)) {
                    overlapCount.getAndIncrement();
                }
            });
            System.out.println(overlapCount.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void puzzle2(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            AtomicInteger overlapCount = new AtomicInteger();
            br.lines().forEach(line -> {
                if (findPartialOverlap(line)) {
                    overlapCount.getAndIncrement();
                }
            });
            System.out.println(overlapCount.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private record IntPair(int first, int second){}

    private static boolean findCompleteOverlap(String line) {
        String[] pairs = line.split(",");
        String[] firstPairStr = pairs[0].split("-");
        String[] secondPairStr = pairs[1].split("-");
        IntPair firstPair = new IntPair(Integer.parseInt(firstPairStr[0]), Integer.parseInt(firstPairStr[1]));
        IntPair secondPair = new IntPair(Integer.parseInt(secondPairStr[0]), Integer.parseInt(secondPairStr[1]));
        if (firstPair.first <= secondPair.first && firstPair.second >= secondPair.second) {
            return true;
        } else return secondPair.first <= firstPair.first && secondPair.second >= firstPair.second;
    }

    private static boolean findPartialOverlap(String line) {
        String[] pairs = line.split(",");
        String[] firstPairStr = pairs[0].split("-");
        String[] secondPairStr = pairs[1].split("-");
        IntPair firstPair = new IntPair(Integer.parseInt(firstPairStr[0]), Integer.parseInt(firstPairStr[1]));
        IntPair secondPair = new IntPair(Integer.parseInt(secondPairStr[0]), Integer.parseInt(secondPairStr[1]));
        // 2-4, 3-5
        if (firstPair.first <= secondPair.first && firstPair.second >= secondPair.first) {
            return true;
        } else if (secondPair.first <= firstPair.first && secondPair.second >= firstPair.first) {
            return true;
        } else return false;
    }
}
