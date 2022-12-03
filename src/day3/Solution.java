package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    private static final boolean testMode = false;
    private static final int puzzleNum = 2;

    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
        String filePath = "src/day3/" + fileName;

        switch (puzzleNum) {
            case 1 -> puzzle1(filePath);
            case 2 -> puzzle2(filePath);
        }
    }

    private static void puzzle1(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int prioritySum = 0;

            String line = br.readLine();
            while (line != null) {
                int halfPos = line.length() / 2;
                String firstHalf = line.substring(0, halfPos);
                String secondHalf = line.substring(halfPos);

                Set<Character> firstHalfChars = firstHalf.chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toCollection(HashSet::new));

                Character dupeChar = secondHalf.chars()
                        .mapToObj(c -> (char) c)
                        .filter(firstHalfChars::contains)
                        .findFirst()
                        .orElse(null);
                int charPriority = priorityHelper(dupeChar);
                prioritySum += charPriority;
                line = br.readLine();
            }
            System.out.println(prioritySum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void puzzle2(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int prioritySum = 0;
            Set<Character> commonChars = new HashSet<>();
            int lineNum = 1;

            String line = br.readLine();
            while (line != null) {
                if (commonChars.isEmpty()) {
                    commonChars = line.chars().mapToObj(c -> (char) c).collect(Collectors.toCollection(HashSet::new));
                } else {
                    commonChars = line
                            .chars()
                            .mapToObj(c -> (char) c)
                            .filter(commonChars::contains)
                            .collect(Collectors.toCollection(HashSet::new));
                }
                if (lineNum % 3 == 0) {
                    Character badgeChar = commonChars.stream().findFirst().orElse(null);
                    prioritySum += priorityHelper(badgeChar);
                    commonChars.clear();
                }
                line = br.readLine();
                lineNum++;
            }
            System.out.println(prioritySum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int priorityHelper(Character c) {
        int asciiCode = (int) c;
        int priority;

        // uppercase check (it's truly devilish that uppercase characters have higher priority than lowercase characters)
        if (asciiCode <= 90) {
            priority = asciiCode - 65 + 27;
        } else {
            priority = asciiCode - 97 + 1;
        }
        return priority;
    }

}
