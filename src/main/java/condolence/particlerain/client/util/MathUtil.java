package condolence.particlerain.client.util;

import java.util.Random;

public class MathUtil {
    /**
     * Returns a random integer in a given range.
     *
     * @param rand the random object that nextInt() will be invoked on
     * @param min the lower bound of the range (inclusive)
     * @param max the upper bound of the range (inclusive)
     * @return an integer equal to a random number in the range min - max
     */
    public static int nextIntInRange(Random rand, int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
