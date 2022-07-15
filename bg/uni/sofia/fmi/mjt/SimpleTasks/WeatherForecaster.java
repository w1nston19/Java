package bg.uni.sofia.fmi.mjt.SimpleTasks;

import java.util.Arrays;

public class WeatherForecaster {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(getsWarmerIn(new int[]{3, 4, 5, 1, -1, 2, 6, 3})));
        System.out.println(Arrays.toString(getsWarmerIn(new int[]{6,5,4,3})));
        System.out.println(Arrays.toString(getsWarmerIn(new int[]{3, 4, 5, 6})));
    }

    public static int[] getsWarmerIn(int[] info) {
        int[] result = new int[info.length];
        for (int i = 0; i < info.length; i++) {
            result[i] = getDay(info, i);
        }
        return result;
    }

    private static int getDay(int[] info, int idx) {
        for (int i = idx; i < info.length; i++) {
            if (info[i] > info[idx]) {
                return i-idx;
            }
        }
        return 0;
    }
}
