package day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Solution {
    private static final boolean testMode = false;
    private static final int puzzleNum = 2;

    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
//        fileName = "testInput2.txt";
        String filePath = "src/day9/" + fileName;

        switch (puzzleNum) {
            case 1 -> puzzle1(filePath);
            case 2 -> puzzle2(filePath);
        }
    }

    private static void puzzle1(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // coordinates of x, y
            IntPair head = new IntPair(0, 0);
            IntPair tail = new IntPair(0, 0);

            Set<IntPair> tailLocations = new HashSet<>();

            IntPair tailLoc = new IntPair(tail.first, tail.second);
            tailLocations.add(tailLoc);

            String line = br.readLine();

            while (line != null) {
                String[] instr = line.split(" ");
                int steps = Integer.parseInt(instr[1]);
                for (int i = steps; i > 0; i--) {
                    switch (instr[0]) {
                        case "R" -> head.first++;
                        case "L" -> head.first--;
                        case "U" -> head.second++;
                        case "D" -> head.second--;
                    }
                    if (Math.abs(head.first - tail.first) == 2 || Math.abs(head.second - tail.second) == 2) {
                        // Case where they are not touching and not in the same row or column
                        if (head.first != tail.first && head.second != tail.second) {
                            // H (3, 2) and T (5, 1) -> T (4, 2)
                            // H (3, 2) and T (1, 1) -> T (2, 2)
                            // Head is farther away on the x-axis
                            if (Math.abs(head.first - tail.first) == 2) {
                                tail.first += Integer.signum(head.first - tail.first);
                                tail.second = head.second;
                            } else {
                                // H (4, 2) and T (3, 0) -> T (4, 1)
                                // Head is farther away on the y-axis
                                tail.second += Integer.signum(head.second - tail.second);
                                tail.first = head.first;
                            }
                        } else {
                            // Case where head is directly 2 steps from the tail
                            switch (instr[0]) {
                                case "R" -> tail.first++;
                                case "L" -> tail.first--;
                                case "U" -> tail.second++;
                                case "D" -> tail.second--;
                            }
                        }
                    }
//                    System.out.println("Head: " + head.first + ", " + head.second);
//                    System.out.println("Tail: " + tail.first + ", " + tail.second);
                    IntPair curTailLoc = new IntPair(tail.first, tail.second);
                    tailLocations.add(curTailLoc);
                }
                line = br.readLine();
            }
//            for (IntPair i : tailLocations.stream().toList()) {
//                System.out.println(i.first + ", " + i.second);
//            }
            System.out.println(tailLocations.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void puzzle2(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // coordinates of x, y
            Knot head = new Knot(new IntPair(0, 0));
            Knot tail = new Knot(new IntPair(0, 0));

            // initialize rope
            Knot curKnot = head;
            for (int i = 0; i < 8; i++) {
                curKnot.next = new Knot(new IntPair(0, 0));
                curKnot = curKnot.next;
            }
            curKnot.next = tail;

            // length check
//            int test = 0;
//            curKnot = head;
//            while (curKnot != null) {
//                test++;
//                curKnot = curKnot.next;
//            }
//            System.out.println(test);

            // debugging
            int largestX = 0;
            int largestY = 0;
            int smallestX = 0;
            int smallestY = 0;

            Set<IntPair> tailLocations = new HashSet<>();

            IntPair tailLoc = new IntPair(tail.loc.first, tail.loc.second);
            tailLocations.add(tailLoc);

            String line = br.readLine();

            while (line != null) {
                String[] instr = line.split(" ");
                int steps = Integer.parseInt(instr[1]);
                for (int i = steps; i > 0; i--) {
                    switch (instr[0]) {
                        case "R" -> {
                            head.loc.first++;
                            if (head.loc.first > largestX) largestX = head.loc.first;
                        }
                        case "L" -> {
                            head.loc.first--;
                            if (head.loc.first < smallestX) smallestX = head.loc.first;
                        }
                        case "U" -> {
                            head.loc.second++;
                            if (head.loc.second > largestY) largestY = head.loc.second;
                        }
                        case "D" -> {
                            head.loc.second--;
                            if (head.loc.second < smallestY) smallestY = head.loc.second;
                        }
                    }
                    Knot prevKnot = head;
                    curKnot = head.next; // thinking of new variable names is too hard
                    while (curKnot != null) { // New case? H (2, 2) and T (0, 0) -> T (1, 1) not T (1, 2)
                        // H (0, 1) and T(2, 1) -> T (1, 1) not T(2, 2)
                        if (Math.abs(prevKnot.loc.first - curKnot.loc.first) == 2 || Math.abs(prevKnot.loc.second - curKnot.loc.second) == 2) {
                            // Case where they are not touching and not in the same row or column
                            if (prevKnot.loc.first != curKnot.loc.first && prevKnot.loc.second != curKnot.loc.second) {
                                // H (-3, 2) and T (-1, 1) -> T (-2, 2)
                                // H (-3, 2) and T (-5, 1) -> T (-4, 2)
                                // H (3, 2) and T (5, 1) -> T (4, 2)
                                // H (3, 2) and T (1, 1) -> T (2, 2)
                                if (Math.abs(prevKnot.loc.first - curKnot.loc.first) == 2 && Math.abs(prevKnot.loc.second - curKnot.loc.second) == 2) {
                                    curKnot.loc.first += Integer.signum(prevKnot.loc.first - curKnot.loc.first);
                                    curKnot.loc.second += Integer.signum(prevKnot.loc.second - curKnot.loc.second);
                                }
                                else if (Math.abs(prevKnot.loc.first - curKnot.loc.first) == 2) {
                                    curKnot.loc.first += Integer.signum(prevKnot.loc.first - curKnot.loc.first);
                                    curKnot.loc.second = prevKnot.loc.second;
                                } else {
                                    // H (4, 2) and T (3, 0) -> T (4, 1)
                                    // Head is farther away on the y-axis
                                    curKnot.loc.second += Integer.signum(prevKnot.loc.second - curKnot.loc.second);
                                    curKnot.loc.first = prevKnot.loc.first;
                                }
                            } else {
                                // Case where head is directly 2 steps from the tail

                                // The logic can absolutely be simplified given what's above.
                                // Am I going to touch this mess now that I spent a couple hours just to debug and solve this? no
                                if (prevKnot.loc.first == curKnot.loc.first) {
                                    curKnot.loc.second += Integer.signum(prevKnot.loc.second - curKnot.loc.second);
                                } else {
                                    curKnot.loc.first += Integer.signum(prevKnot.loc.first - curKnot.loc.first);
                                }
                            }
                        }
                        // At the tail
                        if (curKnot.next == null) {
                            IntPair curTailLoc = new IntPair(curKnot.loc.first, curKnot.loc.second);
                            tailLocations.add(curTailLoc);
                        }
                        prevKnot = curKnot;
                        curKnot = curKnot.next;
                    }

//                    // DEBUGGING PRINT
//                    StringBuilder row = new StringBuilder();
//                    for (int p = largestY; p >= smallestY; p--) {
//                        for (int j = smallestX; j <= largestX; j++) {
//                            IntPair curCoords = new IntPair(j, p);
//                            Knot debug = head;
//                            String res = ".";
//                            if (debug.loc.equals(curCoords)) {
//                                res = "H";
//                            }
//                            for (int q = 0; q < 9; q++) {
//                                if (debug.next.loc.equals(curCoords)) {
//                                    res = Integer.toString(q + 1);
//                                    break;
//                                }
//                                debug = debug.next;
//                            }
//                            row.append(res);
//                        }
//                        row.append("\n");
//                    }
//                    row.append("\n");
//                    System.out.print(row);
                }
                line = br.readLine();
            }
            System.out.println(tailLocations.size());

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

    private static class Knot {
        IntPair loc;
        Knot next;

        private Knot(IntPair loc) {
            this.loc = loc;
        }
    }


}
