package bg.uni.sofia.fmi.mjt.Labs.Intro;

import java.util.Arrays;

public class WeatherForecaster {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(getsWarmerIn(new int[]{3, 4, 5, 1, -1, 2, 6, 3})));
        System.out.println(Arrays.toString(getsWarmerIn(new int[]{3, 4, 5, 6})));
        System.out.println(Arrays.toString(getsWarmerIn(new int[]{3, 6, 9})));
    }

    private static int[] getsWarmerIn(int[] ints) {
        int length = ints.length;

        int[] nextWarmDays = new int[length];

        for (int i = 0; i < length; i++) {
            nextWarmDays[i] = nextWarmDay(ints, i);
        }

        return nextWarmDays;
    }

    private static int nextWarmDay(int[] ints, int idx) {
        int len = ints.length;
        for (int i = idx; i < len; i++) {
            if (ints[i] > ints[idx]) {
                return i - idx;
            }
        }
        return 0;
    }
}
