package Day7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

public class Day7 {
    File f;
    SysDir root;

    Integer part1;
    List<Integer> part2 ;
    public static void main(String[] args) throws IOException {
        Day7 day = new Day7();
        day.calculate();
    }
    public Day7() {
        this.f = new File("src/main/java/Day7/inputday7.txt");
        this.root = new SysDir("/", Optional.ofNullable(null));
        this.part1 = 0;
        this.part2 = new ArrayList<Integer>();
    }


    public void initiateObject() throws IOException {
        System.out.println("Hello calculate day 7!");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();
        SysDir currentDir = root;
        while (line != null) {
            if (line.indexOf("$") == 0) {
                String cmd = line.substring(2);
                if (cmd.contains("cd")) {
                    String dir = cmd.substring(3);
                    System.out.println("IN : "+currentDir.getPath());
                    System.out.println("cd : " + dir);
                    SysDir nextDir = getSysDir(dir, currentDir);
                    currentDir = nextDir != null ? nextDir : currentDir;
                    //System.out.println("cible "+dir+" choosen dir "+currentDir.getName());         
                    System.out.println("OUT : "+currentDir.getPath());
                }
                if (cmd.contains("ls")) {
                    System.out.println("ls");
                }
            } else {
                if (line.indexOf("dir") == 0) {
                    String dir = line.substring(4);
                    createSysDir(dir, currentDir);
                    //rien
                } else {
                    String[] fileInfo = line.split(" ");
                    //System.out.println("Size : " + fileInfo[0]);
                    //System.out.println("Name : " + fileInfo[1]);
                    currentDir.addFile(fileInfo[1], Integer.parseInt(fileInfo[0]));
                }
            }
            line = bufferedReader.readLine();
        }
    }

    public void calculate() throws IOException {
        initiateObject();
//        getSizeSubDirsIfMaxSize(root, 100000);
//        part1 = root.getFilesSize() < 100000 ? part1 + root.getFilesSize() : part1;
        System.out.println(root.getSizeOfDir());
        getDirSizeIfBelowMax(root, 100000);
        System.out.println("Part1 : "+part1);
        System.out.println("#########################");
        printAll(root, "", 2);
        getSmallestDirToDelete(root, 70000000);
        Collections.sort(part2);
        System.out.println(part2.get(0));
        System.out.println(part2.get(part2.size()-1));        
    }
    
    public void getSmallestDirToDelete(SysDir dir, Integer diskSpace){
        Integer spaceToFree = dir.getSizeOfDir() + 30000000 - diskSpace;
        System.out.println("Space to free : "+spaceToFree);
        getDirSizeIfBelowMaxWithResult(dir,spaceToFree);
    }
    private void getDirSizeIfBelowMaxWithResult(SysDir dir, Integer maxSize){
        Integer size = dir.getSizeOfDir();
        if(size > maxSize){
            System.out.println("getDirSizeIfBelowMaxWithResult : "+dir.getPath()+" "+size);
            part2.add(size);
        }
        if(dir.getSysDirs().size() > 0){
            for (int i = 0; i < dir.getSysDirs().size(); i++) {
                getDirSizeIfBelowMaxWithResult(dir.getSysDirs().get(i), maxSize);
            }
        }
    }
    private void getDirSizeIfBelowMax(SysDir dir, Integer maxSize){
        Integer size = dir.getSizeOfDir();
        if(size < maxSize){
            System.out.println(dir.getName() +" : "+size);
            part1 = part1 + size;
        }else {
            System.out.println("     ## "+dir.getName() +" : "+size);
        }
        if(dir.getSysDirs().size() > 0){
            for (int i = 0; i < dir.getSysDirs().size(); i++) {
                getDirSizeIfBelowMax(dir.getSysDirs().get(i), maxSize);
            }
        }
    }
    private void printAll(SysDir dir, String prompt, Integer depth){
        System.out.println(prompt+dir.toString());
        prompt = prompt + "    ";
        depth--;
        if(depth > 0) {
            if (dir.getSysDirs().size() > 0) {
                for (int i = 0; i < dir.getSysDirs().size(); i++) {
                    printAll(dir.getSysDirs().get(i), prompt, depth);
                }
            }
        }
    }


    private SysDir createSysDir(String dir, SysDir currentDir) {

        SysDir subDir = getSysDir(dir, currentDir);
        if (subDir == null) {
            subDir = new SysDir(dir, Optional.ofNullable(currentDir));
            System.out.println("getSysDirOrCreate creation "+subDir.getName());
            currentDir.sysDirs.add(subDir);
        }
        return subDir;
    }

    private SysDir getSysDir(String dir, SysDir currentDir) {
        if (dir.equals("..")) {
            System.out.println("getSysDir return Parent " + currentDir.getParent().getName());
            return currentDir.getParent();
        } 
//        if (currentDir.getName().equals(dir)) {
//            System.out.println("getSysDir return current (no change) " + dir);
//            return currentDir;
//        } 
        else {
            List<SysDir> subDir = currentDir.getSysDirs();
            for (int i = 0; i < subDir.size(); i++) {
                if (subDir.get(i).getName().equals(dir)) {
                    System.out.println("getSysDir return subDir " + dir);
                    return subDir.get(i);
                }
            }
        }
        return null;
    }
}
