package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    private static final boolean testMode = false;
    private static final int puzzleNum = 2;

    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
        String filePath = "src/day5/" + fileName;

        switch (puzzleNum) {
            case 1 -> puzzle1(filePath);
            case 2 -> puzzle2(filePath);
        }
    }

    private static void puzzle1(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<ArrayDeque<Character>> crates = new ArrayList<>(new Stack<>());
            boolean isInstructions = false;

            String line = br.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    isInstructions = true;
                } else if (isInstructions) {
                    // i love regex
                    int[] numbers= Arrays.stream(line.trim().split("\\D+\\s?"))
                            .filter(s -> s.matches("\\d+"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    // Subtract 1 to account for zero-indexing vs one-indexing
                    ArrayDeque<Character> fromStack = crates.get(numbers[1] - 1);
                    ArrayDeque<Character> toStack = crates.get(numbers[2] - 1);
                    int moveAmt = numbers[0];
                    for (int i = 0; i < moveAmt; i++) {
                        Character curCrate = fromStack.pop();
                        toStack.push(curCrate);
//                        System.out.println("Moved " + curCrate + " to stack " + (numbers[2]) + " from stack " + (numbers[1]));
                    }
                } else if (line.contains("[")){ // Only care about the crates and not the number sequence beneath them.
                    int curStack = 0;
                    int charLoc = 1;
                    while (charLoc < line.length()) {
                        char curChar = line.charAt(charLoc);
                        if (crates.size() <= curStack) {
                            crates.add(new ArrayDeque<>());
                        }
                        if (curChar != ' ') {
                            crates.get(curStack).addLast(line.charAt(charLoc));
                        }
                        // Assumption of constant format [x] [y] etc
                        charLoc += 4;
                        curStack++;
                    }
                }
                line = br.readLine();
            }
            crates.forEach(stack -> System.out.print(stack.getFirst()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Only a small part that's changed from puzzle1 but too lazy to refactor the whole thing so enjoy the copy-paste...
    private static void puzzle2(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<ArrayDeque<Character>> crates = new ArrayList<>(new Stack<>());
            boolean isInstructions = false;

            String line = br.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    isInstructions = true;
                } else if (isInstructions) {
                    // i love regex
                    int[] numbers= Arrays.stream(line.trim().split("\\D+\\s?"))
                            .filter(s -> s.matches("\\d+"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    // Subtract 1 to account for zero-indexing vs one-indexing
                    ArrayDeque<Character> fromStack = crates.get(numbers[1] - 1);
                    ArrayDeque<Character> toStack = crates.get(numbers[2] - 1);
                    int moveAmt = numbers[0];
                    // puzzle 2 new stuff :)
                    ArrayDeque<Character> cratesToMove = new ArrayDeque<>();
                    for (int i = 0; i < moveAmt; i++) {
                        Character curCrate = fromStack.pop();
                        cratesToMove.push(curCrate);
                    }
                    for (Character crate : cratesToMove) { // foreach works, god bless
                        toStack.push(crate);
                    }

                } else if (line.contains("[")){ // Only care about the crates and not the number sequence beneath them.
                    int curStack = 0;
                    int charLoc = 1;
                    while (charLoc < line.length()) {
                        char curChar = line.charAt(charLoc);
                        if (crates.size() <= curStack) {
                            crates.add(new ArrayDeque<>());
                        }
                        if (curChar != ' ') {
                            crates.get(curStack).addLast(line.charAt(charLoc));
                        }
                        // Assumption of constant format [x] [y] etc
                        charLoc += 4;
                        curStack++;
                    }
                }
                line = br.readLine();
            }
            crates.forEach(stack -> System.out.print(stack.getFirst()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
