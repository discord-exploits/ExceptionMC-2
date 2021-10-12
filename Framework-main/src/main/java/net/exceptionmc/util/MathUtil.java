package net.exceptionmc.util;

import java.util.Random;

@SuppressWarnings("all")
public class MathUtil {

    public final float nanoToSec = 1 / 1000000000f;

    public final float FLOAT_ROUNDING_ERROR = 0.000001f; // 32 bits
    public final float PI = 3.1415927f;
    public final float PI2 = PI * 2;

    public final float SQRT_3 = 1.73205f;

    public final float E = 2.7182818f;

    /**
     * multiply by this to convert from radians to degrees
     */
    public final float radiansToDegrees = 180f / PI;

    public final float radDeg = radiansToDegrees;

    /**
     * multiply by this to convert from degrees to radians
     */
    public final float degreesToRadians = PI / 180;

    public final float degRad = degreesToRadians;
    public final Random random = new Random();
    private final int SIN_BITS = 14; // 16KB. Adjust for accuracy.
    private final int SIN_MASK = ~(-1 << SIN_BITS);
    private final int SIN_COUNT = SIN_MASK + 1;
    private final float radFull = PI * 2;
    private final float degFull = 360;
    private final float radToIndex = SIN_COUNT / radFull;
    private final float degToIndex = SIN_COUNT / degFull;
    // ---
    private final int ATAN2_BITS = 7; // Adjust for accuracy.
    private final int ATAN2_BITS2 = ATAN2_BITS << 1;
    private final int ATAN2_MASK = ~(-1 << ATAN2_BITS2);
    private final int ATAN2_COUNT = ATAN2_MASK + 1;
    final int ATAN2_DIM = (int) Math.sqrt(ATAN2_COUNT);
    private final float INV_ATAN2_DIM_MINUS_1 = 1.0f / (ATAN2_DIM - 1);
    private final int BIG_ENOUGH_INT = 16 * 1024;

    /**
     * Returns the sine in radians from a lookup table.
     */
    public float sin(float radians) {
        return new Sin().table[(int) (radians * radToIndex) & SIN_MASK];
    }

    /**
     * Returns the cosine in radians from a lookup table.
     */
    public float cos(float radians) {
        return new Sin().table[(int) ((radians + PI / 2) * radToIndex) & SIN_MASK];
    }

    /**
     * Returns the sine in radians from a lookup table.
     */
    public float sinDeg(float degrees) {
        return new Sin().table[(int) (degrees * degToIndex) & SIN_MASK];
    }

    /**
     * Returns the cosine in radians from a lookup table.
     */
    public float cosDeg(float degrees) {
        return new Sin().table[(int) ((degrees + 90) * degToIndex) & SIN_MASK];
    }

    /**
     * Returns atan2 in radians from a lookup table.
     */
    public float atan2(float y, float x) {
        float add, mul;

        if (x < 0) {
            if (y < 0) {

                y = -y;
                mul = 1;
            } else
                mul = -1;

            x = -x;
            add = -PI;
        } else {
            if (y < 0) {

                y = -y;
                mul = -1;
            } else
                mul = 1;

            add = 0;
        }

        float invDiv = 1 / ((Math.max(x, y)) * INV_ATAN2_DIM_MINUS_1);

        if (invDiv == Float.POSITIVE_INFINITY)
            return ((float) Math.atan2(y, x) + add) * mul;

        int xi = (int) (x * invDiv);
        int yi = (int) (y * invDiv);

        return (new Atan2().table[yi * ATAN2_DIM + xi] + add) * mul;
    }

    /**
     * Returns a random number between 0 (inclusive) and the specified value (inclusive).
     */
    public final int random(int range) {
        return random.nextInt(range + 1);
    }

    /**
     * Returns a random number between start (inclusive) and end (inclusive).
     */
    public final int random(int start, int end) {
        return start + random.nextInt(end - start + 1);
    }

    /**
     * Returns a random boolean value.
     */
    public final boolean randomBoolean() {
        return random.nextBoolean();
    }

    /**
     * Returns true if a random value between 0 and 1 is less than the specified value.
     */
    public final boolean randomBoolean(float chance) {
        return new MathUtil().random() < chance;
    }

    /**
     * Returns random number between 0.0 (inclusive) and 1.0 (exclusive).
     */
    public float random() {
        return random.nextFloat();
    }

    /**
     * Returns a random number between 0 (inclusive) and the specified value (exclusive).
     */
    public final float random(float range) {
        return random.nextFloat() * range;
    }

    /**
     * Returns a random number between start (inclusive) and end (exclusive).
     */
    public final float random(float start, float end) {
        return start + random.nextFloat() * (end - start);
    }

    /**
     * Returns the next power of two. Returns the specified value if the value is already a power of two.
     */
    public int nextPowerOfTwo(int value) {
        if (value == 0)
            return 1;

        value--;

        value |= value >> 1;
        value |= value >> 2;
        value |= value >> 4;
        value |= value >> 8;
        value |= value >> 16;

        return value + 1;
    }

    // ---

    public boolean isPowerOfTwo(int value) {
        return value != 0 && (value & value - 1) == 0;
    }

    // ---
    public int clamp(int value, int min, int max) {
        if (value < min)
            return min;

        return Math.min(value, max);
    }

    public short clamp(short value, short min, short max) {
        if (value < min)
            return min;

        if (value > max)
            return max;

        return value;
    }

    public float clamp(float value, float min, float max) {
        if (value < min)
            return min;

        return Math.min(value, max);
    }

    /**
     * Returns the largest integer less than or equal to the specified float. This method will only properly floor
     * floats from -(2^14) to (Float.MAX_VALUE - 2^14).
     */
    public int floor(float x) {
        return (int) (x + (double) BIG_ENOUGH_INT) - BIG_ENOUGH_INT;
    }

    /**
     * Returns the largest integer less than or equal to the specified float. This method will only properly floor
     * floats that are positive. Note this method simply casts the float to int.
     */
    public int floorPositive(float x) {
        return (int) x;
    }

    /**
     * Returns the smallest integer greater than or equal to the specified float. This method will only properly ceil
     * floats from -(2^14) to (Float.MAX_VALUE - 2^14).
     */
    public int ceil(float x) {
        double BIG_ENOUGH_CEIL = 16384.999999999996;

        return (int) (x + BIG_ENOUGH_CEIL) - BIG_ENOUGH_INT;
    }

    /**
     * Returns the smallest integer greater than or equal to the specified float. This method will only properly ceil
     * floats that are positive.
     */
    public int ceilPositive(float x) {
        double CEIL = 0.9999999;

        return (int) (x + CEIL);
    }

    /**
     * Returns the closest integer to the specified float. This method will only properly round floats from -(2^14) to
     * (Float.MAX_VALUE - 2^14).
     */
    public int round(float x) {
        double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5f;

        return (int) (x + BIG_ENOUGH_ROUND) - BIG_ENOUGH_INT;
    }

    /**
     * Returns the closest integer to the specified float. This method will only properly round floats
     * that are positive.
     */
    public int roundPositive(float x) {
        return (int) (x + 0.5f);
    }

    /**
     * Returns true if the value is zero (using the default tolerance as upper bound)
     */
    public boolean isZero(float value) {
        return Math.abs(value) <= FLOAT_ROUNDING_ERROR;
    }

    /**
     * Returns true if the value is zero.
     *
     * @param tolerance represent an upper bound below which the value is considered zero.
     */
    public boolean isZero(float value, float tolerance) {
        return Math.abs(value) <= tolerance;
    }

    /**
     * Returns true if a is nearly equal to b. The function uses the default floating error tolerance.
     *
     * @param a the first value.
     * @param b the second value.
     */
    public boolean isEqual(float a, float b) {
        return Math.abs(a - b) <= FLOAT_ROUNDING_ERROR;
    }

    /**
     * Returns true if a is nearly equal to b.
     *
     * @param a         the first value.
     * @param b         the second value.
     * @param tolerance represent an upper bound below which the two values are considered equal.
     */
    public boolean isEqual(float a, float b, float tolerance) {
        return Math.abs(a - b) <= tolerance;
    }

    /**
     * Compatiblity method for Double.isFinite from Java 1.7 and up
     */
    public boolean isFinite(double value) {
        return !Double.isNaN(value) && !Double.isInfinite(value);
    }

    private class Sin {
        final float[] table = new float[SIN_COUNT];

        {
            for (int i = 0; i < SIN_COUNT; i++)
                table[i] = (float) Math.sin((i + 0.5f) / SIN_COUNT * radFull);

            for (int i = 0; i < 360; i += 90)
                table[(int) (i * degToIndex) & SIN_MASK] = (float) Math.sin(i * degreesToRadians);
        }
    }

    private class Atan2 {
        final float[] table = new float[ATAN2_COUNT];

        {
            for (int i = 0; i < ATAN2_DIM; i++) {
                for (int j = 0; j < ATAN2_DIM; j++) {
                    float x0 = (float) i / ATAN2_DIM;
                    float y0 = (float) j / ATAN2_DIM;

                    table[j * ATAN2_DIM + i] = (float) Math.atan2(y0, x0);
                }
            }
        }
    }
}
