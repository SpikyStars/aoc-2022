package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    private static final boolean testMode = false;
    private static final int puzzleNum = 1;

    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
        String filePath = "src/day8/" + fileName;

        switch (puzzleNum) {
            case 1 -> puzzle1(filePath);
//            case 2 -> puzzle2(filePath);
        }
    }

    private static void puzzle1(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            int numVisible = 0;
            String line = br.readLine();
            List<List<Integer>> treeGrid = new ArrayList<>();

            // Initialize 2D array
            while (line != null) {

                List<Integer> treeRow = new ArrayList<>();
                treeGrid.add(treeRow);
                for (Character c : line.toCharArray()) {
                    treeRow.add(Integer.parseInt(Character.toString(c)));
                }
                line = br.readLine();
            }

            Set<IntPair> visibleTrees = new HashSet<>();

            int rowLength = treeGrid.get(0).size(); // same for all rows

            for (int r = 0; r < treeGrid.size(); r++) {
                List<Integer> treeRow = treeGrid.get(r);
                int tallestTree = -1;
                for (int c = 0; c < treeRow.size(); c++) { // left to right
                    IntPair coords = new IntPair(r, c);
                    int treeValue = treeRow.get(c);
                    if (treeValue > tallestTree) {
                        if (!visibleTrees.contains(coords)) {
                            visibleTrees.add(coords);
                            numVisible++;
                        }
                        tallestTree = treeValue;
                    }
                }
                tallestTree = -1;
                for (int c = treeRow.size() - 1; c >= 0; c--) { // right to left. lazy so im keeping the copy paste...
                    IntPair coords = new IntPair(r, c);
                    int treeValue = treeRow.get(c);
                    if (treeValue > tallestTree) {
                        if (!visibleTrees.contains(coords)) {
                            visibleTrees.add(coords);
                            numVisible++;
                        }
                        tallestTree = treeValue;
                    }
                }
            }

            for (int c = 0; c < rowLength; c++) { // up to down
                int tallestTree = -1;
                for (int r = 0; r < treeGrid.size(); r++) {
                    IntPair coords = new IntPair(r, c);
                    int treeValue = treeGrid.get(r).get(c);
                    if (treeValue > tallestTree) {
                        if (!visibleTrees.contains(coords)) {
                            visibleTrees.add(coords);
                            numVisible++;
                        }
                        tallestTree = treeValue;
                    }
                }
                tallestTree = -1;
                for (int r = treeGrid.size() - 1; r >= 0; r--) { // down to up
                    IntPair coords = new IntPair(r, c);
                    int treeValue = treeGrid.get(r).get(c);
                    if (treeValue > tallestTree) {
                        if (!visibleTrees.contains(coords)) {
                            visibleTrees.add(coords);
                            numVisible++;
                        }
                        tallestTree = treeValue;
                    }
                }
            }

            System.out.println(numVisible);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class IntPair {
        int first;
        int second;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntPair intPair = (IntPair) o;
            return first == intPair.first && second == intPair.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        private IntPair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }


}
