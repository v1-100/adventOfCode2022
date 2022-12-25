package Day13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pair {
    String left;
    String right;

    List leftList;
    List rightList;
    static int index = 0;

    public Pair(String left, String right) {
        this.left = left;
        this.right = right;
        index = 0;
        this.leftList = (List) buildList(left).get(0);
        index = 0;
        this.rightList = (List) buildList(right).get(0);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left='" + left + '\'' +
                ", right='" + right + '\'' +
                '}';
    }


    //https://stackoverflow.com/questions/24899052/parsing-nested-array-string
    public List buildList(String nestedList) {
        List<Object> list = new ArrayList<>();

        while (index < nestedList.length()) {
            char c = nestedList.charAt(index++);

            if (c == '[') // add a sub-list via a recursive call
                list.add(buildList(nestedList));
            else if (c == ']') // stop building the list
                break;
            else if (c == ',') {
            } // do nothing
            else // add an element to the list
                if(c == '1' && nestedList.charAt(index) == '0'){
                    // y'a des 10 ... hack dégueulasse
                    index++;
                    list.add(10);
                }else {
                    list.add(Character.getNumericValue(c));
                }
        }

        return list;
    }

    public static boolean isPairsInTheRightOrder(List leftList, List rightList) {
        System.out.println("IN isPairsInTheRightOrder " + leftList + " / " + rightList);
        
        if (leftList.isEmpty() && !rightList.isEmpty()){
            System.out.println("Left side ran out of items, so inputs are in the right order");
            return true;
        }
        
        for (int i = 0; i < leftList.size(); i++) {
            if (leftList.get(i) instanceof Integer) {
                System.out.println("Integer : " + leftList.get(i));
                Integer iLeft = (Integer) leftList.get(i);
                Integer iRight = getRightIntegerToCompare(i, rightList);
                
                System.out.println("\t Compare iLeft : " + iLeft + " | iRight : " + iRight);
                if (iRight == null) {
                    System.out.println("Right side ran out of items, so inputs are not in the right order | leftList " + leftList + " | rightList " + rightList + " false");
                    return false;
                }
                if (iLeft < iRight) {
                    System.out.println("Left side is smaller, so inputs are in the right order | leftList " + leftList + " | rightList " + rightList + " true");
                    return true;
                }
                if (iLeft > iRight) {
                    // Not right order
                    System.out.println("Right side is smaller, so inputs are not in the right order | leftList " + leftList + " | rightList " + rightList + " false");
                    return false;
                }
            } else if (leftList.get(i) instanceof List) {
                System.out.println("List.get : " + leftList.get(i));
                System.out.println("List leftList " + leftList + " | rightList " + rightList);
                int finalI = i;
                
                if (rightList.isEmpty() || i >= rightList.size()){
                    System.out.println("Right side ran out of items, so inputs are not in the right order");
                    return false;
                }

                try {
                    Boolean res = isPairsInTheRightOrder((List) leftList.get(i),
                            rightList.get(i) instanceof List ?
                                    (List) rightList.get(i) :
                                    new ArrayList() {{
                                        add(rightList.get(finalI));
                                    }});
                    System.out.println("Result Recursion  : " + res);
                    return res;
                } catch (Exception e) {
                    System.out.println("On ignore " + e);
                }
            }
            //on n'a plus de left mais il reste des rights
            if (i == leftList.size() - 1 && rightList.size() > leftList.size()) {
                System.out.println("Left side ran out of items, so inputs are in the right order. leftList " + leftList + " | rightList " + rightList + " false");
                return true;
            }
        }
        throw new RuntimeException("NO CHOICE");
    }

    private static Integer getRightIntegerToCompare(int i, List rightList) {
        //System.out.println("getRightIntegerToCompare "+i+" : "+rightList);
        if (!rightList.isEmpty() && rightList.size() > i) {
            if (rightList.get(i) instanceof Integer) return (Integer) rightList.get(i);
            return getRightIntegerToCompare(0, (List) rightList.get(i));
        }
        return null;
    }
    
}
