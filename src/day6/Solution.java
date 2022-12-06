package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    private static final boolean testMode = false;
    private static final int puzzleNum = 2;

    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
        String filePath = "src/day6/" + fileName;

        switch (puzzleNum) {
            case 1 -> puzzle1(filePath);
            case 2 -> puzzle2(filePath);
        }
    }

    private static void puzzle1(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            Set<Character> uniqueChars = new HashSet<>();

            for (int i = 0; i < line.length(); i++) {
                char curChar = line.charAt(i);
                if (uniqueChars.add(curChar)) {
                    if (uniqueChars.size() == 4) {
                        System.out.println(i);
                        break;
                    }
                } else {
                    uniqueChars.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void puzzle2(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            int idx = 0;

            System.out.println(puzzle2Helper(line, idx) + 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int puzzle2Helper (String line, int idx) {
        int result = Integer.MAX_VALUE;
        if (idx >= line.length()) {
            return Integer.MAX_VALUE;
        } else {
            Set<Character> uniqueChars = new HashSet<>();
            for (int i = idx; i < line.length(); i++) {
                char curChar = line.charAt(i);
                if (uniqueChars.add(curChar)) {
                    if (uniqueChars.size() == 14) {
                        result = i;
//                        System.out.println("Found solution " + uniqueChars + " at index " + i);
                        break;
                    }
                } else {
                    uniqueChars.clear();
                }
            }
        }
        return Math.min(result, puzzle2Helper(line, idx + 1));
    }
}
