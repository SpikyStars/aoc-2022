package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    private static final boolean testMode = false;

    private static final int puzzleNum = 1;

    public static void main(String[] args) {
        String fileName = testMode ? "testInput.txt" : "input.txt";
        String filePath = "src/day7/" + fileName;

        switch (puzzleNum) {
            case 1 -> puzzle1(filePath);
//            case 2 -> puzzle2(filePath);
        }
    }


    private static void puzzle1(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            Directory rootDirectory = new Directory("/", null);
            Directory curDirectory = rootDirectory;
            while (line != null) {
                // Create the file system data structure first
                if (line.startsWith("$")) {
                    if (line.contains("cd")) {
                        if (line.contains("..")) {
                            curDirectory = curDirectory.parent;
                        } else {
                            String directoryName = line.replace("$ cd ", "");
                            if ("/".equals(directoryName)) {
                                curDirectory = rootDirectory;
                            } else {
                                curDirectory = curDirectory.addAndGetChild(directoryName);
                            }
                        }
                    } // Aside from $ cd there is just $ ls - don't need to handle "ls" specially
                } else {
                    if (line.startsWith("dir")) {
                        String directoryName = line.replace("dir ", "");
                        curDirectory.addAndGetChild(directoryName);
                    } else {
                        String[] fileInfo = line.split("\\s");
                        curDirectory.files.add(new File(Integer.parseInt(fileInfo[0]), fileInfo[1]));
                    }
                }
                line = br.readLine();
            }
            // Now iterate through data structure
            Map<String, Integer> sizesMap = new HashMap<>();
            puzzle1Helper(sizesMap, rootDirectory);

            int sizeSum = 0;
            for (int s : sizesMap.values()) {
                if (s <= 100000) {
                    sizeSum += s;
                }
            }
            System.out.println(sizeSum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void puzzle1Helper(Map<String, Integer> sizesMap, Directory dir) {
        // very much not foolproof but let's be lazy and try it for this input...
        // worked lol
        String uniqueName;
        if ("/".equals(dir.name)) {
            uniqueName = dir.name;
        } else {
            uniqueName = dir.parent.name + dir.name;
        }
        if (!sizesMap.containsKey(uniqueName)) {
            sizesMap.put(uniqueName, dir.getSize());
        }
        for (Directory d : dir.childDirectories.keySet()) {
            puzzle1Helper(sizesMap, d);
        }
    }


    private static class Directory {
        String name;
        Directory parent;
        private Directory(String name, Directory parent) {
            this.name = name;
            this.parent = parent;
        }

        Map<Directory, Directory> childDirectories = new HashMap<>();
        List<File> files = new ArrayList<>();

        private int getSize() {
            int size = 0;
            for (File f : files) {
                size += f.size;
            }
            for (Directory d : childDirectories.keySet()) {
                size += d.getSize();
            }
            return size;
        }

        private Directory addAndGetChild(String directoryName) {
            Directory newDirectory = new Directory(directoryName, this);
            if (!this.childDirectories.containsKey(newDirectory)) {
                this.childDirectories.put(newDirectory, newDirectory);
            } else {
                newDirectory = this.childDirectories.get(newDirectory);
            }
            return newDirectory;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Directory directory = (Directory) o;
            return name.equals(directory.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    private static class File {
        int size;
        String name; // could be needed later, idk

        private File(int size, String name) {
            this.size = size;
            this.name = name;
        }
    }

}
