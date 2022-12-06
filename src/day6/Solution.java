package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    private static final boolean testMode = false;
    private static final int puzzleNum = 1;

    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
        String filePath = "src/day6/" + fileName;

        switch (puzzleNum) {
            case 1 -> puzzle1(filePath);
//            case 2 -> puzzle2(filePath);
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
}
