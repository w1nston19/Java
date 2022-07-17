package bg.uni.sofia.fmi.mjt.Labs.Intro;

public class ArrayAnalyzer {
    public static void main(String[] args) {
        System.out.println(isMountainArray(new int[]{2, 1}));
        System.out.println(isMountainArray(new int[]{3, 5, 5}));
        System.out.println(isMountainArray(new int[]{0, 3, 2, 1}));
    }

    static boolean isMountainArray(int[] array) {
        boolean isIncreasing = true;
        boolean hasPeeked = false;

        final int size = array.length;

        for (int i = 0; i < size - 1; i++) {

            if (array[i] == array[i + 1]) {
                return false;
            } else {
                if (array[i] > array[i + 1]) {
                    if (i == 0) {
                        return false;
                    }
                    if (!hasPeeked) {
                        hasPeeked = true;
                    }
                } else {
                    if (hasPeeked) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
