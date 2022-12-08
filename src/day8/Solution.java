package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    private static final boolean testMode = true;

    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
        String filePath = "src/day8/" + fileName;

        puzzle(filePath);

    }

    private static void puzzle(String filePath) {
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


            // puzzle 2 stuff

            HashMap<IntPair, Tree> coordsMap = new HashMap<>();

            // create map of int pairs to trees

            for (int r = 0; r < treeGrid.size(); r++) {
                List<Integer> treeRow = treeGrid.get(r);
                for (int c = 0; c < rowLength; c++) {
                    IntPair coords = new IntPair(r, c);
                    coordsMap.put(coords, new Tree(treeRow.get(c)));
                }
            }

            // assign neighbors
            for (int r = 0; r < treeGrid.size(); r++) {
                for (int c = 0; c < rowLength; c++) {
                    IntPair coords = new IntPair(r, c);
                    // get neighbors
                    IntPair north = null;
                    IntPair south = null;
                    IntPair east = null;
                    IntPair west = null;

                    if (r != 0) {
                        north = new IntPair(r - 1, c);
                    }
                    if (r != treeGrid.size() - 1) {
                        south = new IntPair(r + 1, c);
                    }
                    if (c != 0) {
                        west = new IntPair(r, c - 1);
                    }
                    if (c != rowLength - 1) {
                        east = new IntPair(r, c + 1);
                    }

                    Tree tree = coordsMap.get(coords);
                    tree.setNeighbors(coordsMap.getOrDefault(north, null),
                            coordsMap.getOrDefault(south, null),
                            coordsMap.getOrDefault(west, null),
                            coordsMap.getOrDefault(east, null));
                }
            }

            int highestScore = 0;
            for (Tree t : coordsMap.values()) {
                int treeScore = t.getScenicScore();
                if (highestScore < treeScore) {
                    highestScore = treeScore;
                }
            }

            System.out.println(highestScore);
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

    private static class Tree {
        Tree north;
        Tree south;
        Tree west;
        Tree east;

        int height;

        private Tree(int height) {
            this.height = height;
        }

        private void setNeighbors(Tree north, Tree south, Tree west, Tree east) {
            this.north = north;
            this.south = south;
            this.west = west;
            this.east = east;
        }

        private int getScenicScore() {
            return getViewDistanceWest(this.height)
                    * getViewDistanceEast(this.height)
                    * getViewDistanceNorth(this.height)
                    * getViewDistanceSouth(this.height);
        }

        private int getViewDistanceNorth(int startingHeight) {
            if (this.north == null) {
                return 0;
            } else if (this.north.height >= startingHeight) {
                return 1;
            } else {
                return 1 + this.north.getViewDistanceNorth(startingHeight);
            }
        }

        private int getViewDistanceSouth(int startingHeight) {
            if (this.south == null) {
                return 0;
            } else if (this.south.height >= startingHeight) {
                return 1;
            } else {
                return 1 + this.south.getViewDistanceSouth(startingHeight);
            }
        }

        private int getViewDistanceWest(int startingHeight) {
            if (this.west == null) {
                return 0;
            } else if (this.west.height >= startingHeight) {
                return 1;
            } else {
                return 1 + this.west.getViewDistanceWest(startingHeight);
            }
        }

        private int getViewDistanceEast(int startingHeight) {
            if (this.east == null) {
                return 0;
            } else if (this.east.height >= startingHeight) {
                return 1;
            } else {
                return 1 + this.east.getViewDistanceEast(startingHeight);
            }
        }
    }


}
