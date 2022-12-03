package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    private static final boolean testMode = false;
    private static final int puzzleNum = 2;
    private enum RPSType {
        ROCK,
        PAPER,
        SCISSORS
    }

    private enum RPSResult {
        WIN,
        LOSS,
        DRAW
    }

    private record RPSInfo(int score, RPSType winsTo, RPSType losesTo) {}

    private static final Map<Character, RPSType> rpsTypeMap = initializeRPSTypeMap();
    private static final Map<Character, RPSResult> rpsResultMap = initializeRPSResultMap();
    private static final Map<RPSType, RPSInfo> rpsInfo = initializeRPSInfo();


    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
        String filePath = "src/day2/" + fileName;

        switch (puzzleNum) {
            case 1 -> puzzle1(filePath);
            case 2 -> puzzle2(filePath);
        }
    }
    private static void puzzle1(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            int yourScore = 0;
            int opponentScore = 0;
            while (line != null) {
                RPSType opponentType = rpsTypeMap.get(line.charAt(0));
                RPSType yourType = rpsTypeMap.get(line.charAt(2));
                RPSInfo opponentInfo = rpsInfo.get(opponentType);
                RPSInfo yourInfo = rpsInfo.get(yourType);

                opponentScore += opponentInfo.score;
                yourScore += yourInfo.score;

                if (opponentInfo.winsTo == yourType){
                    opponentScore += 6;
                } else if (opponentInfo.losesTo == yourType) {
                    yourScore += 6;
                } else {
                    opponentScore += 3;
                    yourScore += 3;
                }

                line = br.readLine();
            }
            System.out.println("Your score: " + yourScore);
            System.out.println("Opponent's score: " + opponentScore); // didn't notice opponent score wasn't needed but w/e lol
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void puzzle2(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            int yourScore = 0;
            int opponentScore = 0;
            while (line != null) {
                RPSType opponentType = rpsTypeMap.get(line.charAt(0));
                RPSResult yourResult = rpsResultMap.get(line.charAt(2));
                RPSInfo opponentInfo = rpsInfo.get(opponentType);

                RPSType yourType;
                switch (yourResult) {
                    case WIN -> {
                        yourType = opponentInfo.losesTo;
                        yourScore += 6;
                    }
                    case LOSS -> {
                        yourType = opponentInfo.winsTo;
                        opponentScore += 6;
                    }
                    default -> {
                        yourType = opponentType;
                        opponentScore += 3;
                        yourScore += 3;
                    }
                }

                RPSInfo yourInfo = rpsInfo.get(yourType);

                opponentScore += opponentInfo.score;
                yourScore += yourInfo.score;

                line = br.readLine();
            }
            System.out.println("Your score: " + yourScore);
            System.out.println("Opponent's score: " + opponentScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Map<Character, RPSType> initializeRPSTypeMap() {
        Map<Character, RPSType> rpsMap = new HashMap<>();
        rpsMap.put('A', RPSType.ROCK);
        rpsMap.put('B', RPSType.PAPER);
        rpsMap.put('C', RPSType.SCISSORS);
        rpsMap.put('X', RPSType.ROCK);
        rpsMap.put('Y', RPSType.PAPER);
        rpsMap.put('Z', RPSType.SCISSORS);
        return rpsMap;
    }

    private static Map<Character, RPSResult> initializeRPSResultMap() {
        Map<Character, RPSResult> rpsMap = new HashMap<>();
        rpsMap.put('X', RPSResult.LOSS);
        rpsMap.put('Y', RPSResult.DRAW);
        rpsMap.put('Z', RPSResult.WIN);
        return rpsMap;
    }

    private static Map<RPSType, RPSInfo> initializeRPSInfo() {
        Map<RPSType, RPSInfo> rpsMap = new HashMap<>();
        RPSInfo rockMatchup = new RPSInfo(1, RPSType.SCISSORS, RPSType.PAPER);
        RPSInfo paperMatchup = new RPSInfo(2, RPSType.ROCK, RPSType.SCISSORS);
        RPSInfo scissorsMatchup = new RPSInfo(3, RPSType.PAPER, RPSType.ROCK);
        rpsMap.put(RPSType.ROCK, rockMatchup);
        rpsMap.put(RPSType.PAPER, paperMatchup);
        rpsMap.put(RPSType.SCISSORS, scissorsMatchup);
        return rpsMap;
    }
}
