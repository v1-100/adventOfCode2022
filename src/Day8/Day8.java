package Day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Day8 {
    File f;
    ArrayList<ArrayList<Integer>> forest;
    ArrayList<ArrayList<Integer>> visibleForest;

    public Day8() {
        this.f = new File("src/Day8/inputday8.txt");
        forest = new ArrayList<>();
        visibleForest = new ArrayList<>();
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day 8!");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();
        while (line != null) {
            ArrayList<Integer> horizontal = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                horizontal.add(Integer.parseInt(line.substring(i,i+1)));
            }
            forest.add(horizontal);
            line = bufferedReader.readLine();
        }
        printForest();
        setEdgeOfVisibleForest();
        setInsideTheVisibleForest();
        System.out.println("--------------------------------------");
        printVisibleForest();
        printNumberOrVisibletree();

        printMaxScenicScore();
        System.out.println("End ");
    }

    private void printMaxScenicScore() {
        Integer max = 0;
        for (int i = 1; i < forest.size()-1; i++) {
            ArrayList<Integer> horizontal = forest.get(i);
            for (int j = 1; j < horizontal.size() - 1; j++) {
                Integer score = getScenicScoreOfTree(i,j);
                //System.out.println(score +" "+i+"/"+j);
                max = score > max ? score : max;
            }
            
        }
        System.out.println(max);
    }

    private Integer getScenicScoreOfTree(Integer x, Integer y) {
        Integer up = 0, down = 0, left = 0, right = 0;
        //Looking Up
        ArrayList<Integer> vertical = (ArrayList) forest.stream().map(arr -> arr.get(x)).collect(Collectors.toList());
        Integer treeToCompare = vertical.get(y);
        for (int i = y-1; i >= 0; i--) {
            Integer nextTree = vertical.get(i);
            if(treeToCompare > nextTree){
                up++;
            }
            if(treeToCompare <= nextTree){
                up++;
                break;
            }            
        }
        //looking down
        treeToCompare = vertical.get(y);
        for (int i = y+1; i < vertical.size(); i++) {
            Integer nextTree = vertical.get(i);
            if(treeToCompare > nextTree){
                down++;
            }
            if(treeToCompare <= nextTree){
                down++;
                break;
            }            
        }        
        //looking left
        ArrayList<Integer> horizontal = forest.get(y);
        treeToCompare = horizontal.get(x);
        for(int i = x-1; i >= 0; i--){
            Integer nextTree = horizontal.get(i);
            if(treeToCompare > nextTree){
                left++;
            }
            if(treeToCompare <= nextTree){
                left++;
                break;
            }                 
        }
        //looking right
        treeToCompare = horizontal.get(x);
        for(int i = x+1; i < horizontal.size(); i++){
            Integer nextTree = horizontal.get(i);
            if(treeToCompare > nextTree){
                right++;
            }
            if(treeToCompare <= nextTree){
                right++;
                break;
            }                 
        }        
        return up * down * left * right;
    }

    private void printNumberOrVisibletree() {
        System.out.println(visibleForest.stream().map(arr -> arr.stream().filter(i -> i > 0).count())
                .mapToLong(i -> i).sum());
    }

    private void setInsideTheVisibleForest() {
        //Horizontal
        //Right to left
        for (int i = 0; i < forest.size(); i++) {
            ArrayList<Integer> horizontal = forest.get(i);
            ArrayList<Integer> horizontalVisible = visibleForest.get(i);
            Integer treeToCompare = horizontal.get(0);
            for (int itree = 1; itree < horizontal.size(); itree++) {
                Integer treeTomark = horizontal.get(itree);
                if(treeTomark > treeToCompare){
                    horizontalVisible.set(itree, 1);
                    treeToCompare = treeTomark;
                }
            }
        }
        
        //left to right
        for (int i = 0; i < forest.size(); i++) {
            ArrayList<Integer> horizontal = forest.get(i);
            ArrayList<Integer> horizontalVisible = visibleForest.get(i);
            Integer treeToCompare = horizontal.get(horizontal.size()-1);
            for (int itree = horizontal.size()-2; itree > 0; itree--) {
                Integer treeTomark = horizontal.get(itree);
                if(treeTomark > treeToCompare){
                    horizontalVisible.set(itree, 1);
                    treeToCompare = treeTomark;
                }
            }
        }  
        //Vertical
        Integer horizontalSize = forest.get(0).size();
        //Top to bottom
        for (int j = 1; j < horizontalSize - 1; j++) {
            int axisPosition = j;
            ArrayList<Integer> vertical = (ArrayList) forest.stream().map(arr -> arr.get(axisPosition)).collect(Collectors.toList());
            Integer treeToCompare = vertical.get(0);
            for (int itree = 1; itree < vertical.size()-1; itree++) {
                Integer treeTomark = vertical.get(itree);
                if(treeTomark > treeToCompare){
                    visibleForest.get(itree).set(j, 1);
                    treeToCompare = treeTomark;
                }
            }
        }
        //Bottom to top
        for (int j = 1; j < horizontalSize - 1; j++) {
            int axisPosition = j;
            ArrayList<Integer> vertical = (ArrayList) forest.stream().map(arr -> arr.get(axisPosition)).collect(Collectors.toList());
            Integer treeToCompare = vertical.get(vertical.size()-1);
            for (int itree = vertical.size()-2; itree > 0; itree--) {
                Integer treeTomark = vertical.get(itree);
                if(treeTomark > treeToCompare){
                    visibleForest.get(itree).set(j, 1);
                    treeToCompare = treeTomark;
                }
            }
        }        
    }

    private void setEdgeOfVisibleForest() {
        for (int i = 0; i < forest.size(); i++) {
            ArrayList horizontal = forest.get(i);
            ArrayList<Integer> horizontalVisible = new ArrayList<>();
            
            for (int itree = 0; itree < horizontal.size(); itree++) {
                if(i == 0 || i == forest.size()-1
                        || itree == 0 || itree == horizontal.size()-1){
                    horizontalVisible.add(1);
                }
                else {
                    horizontalVisible.add(0);
                }
            }
            visibleForest.add(horizontalVisible);            

        }        
    }

    private void printForest() {
        for (int i = 0; i < forest.size(); i++) {
            ArrayList horizontal = forest.get(i);
            System.out.println(horizontal.toString());
        }
    }
    private void printVisibleForest() {
        for (int i = 0; i < visibleForest.size(); i++) {
            ArrayList horizontal = visibleForest.get(i);
            System.out.println(horizontal.toString());
        }
    }
}

