package bg.uni.sofia.fmi.mjt.SimpleTasks;

public class ArrayAnalyzer {
    public static void main(String[] args) {
        System.out.println(isMountainArray(new int[]{2, 1}));
        System.out.println(isMountainArray(new int[]{3, 5, 5}));
        System.out.println(isMountainArray(new int[]{0,3,1,0}));
    }

    public static boolean isMountainArray(int[] array) {
        boolean isIncreasing = true;
        boolean haveIncreased = false;

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == array[i + 1]) {
                return false;
            } else {
                if (array[i] < array[i + 1]) {
                    if (!isIncreasing) {
                        return false;
                    }
                    haveIncreased = true;
                } else {
                    if(!haveIncreased) {return false;}
                    if (isIncreasing) {
                        isIncreasing = false;
                    }
                }
            }
        }
        return true;
    }
}
