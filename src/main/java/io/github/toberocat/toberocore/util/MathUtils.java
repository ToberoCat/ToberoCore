package io.github.toberocat.toberocore.util;

import org.apache.commons.lang.math.NumberUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class MathUtils {

    public static double avg(long[] values) {
        return Arrays.stream(values).average().orElse(Double.NaN);
    }

    /**
     * If the string is null, return false. Otherwise, try to parse the string as an integer. If it fails, return false.
     * Otherwise, return true
     *
     * @param string The string to check.
     * @return A boolean value.
     */
    public static boolean isNumeric(String string) {
        if (string == null) return false;
        return NumberUtils.isNumber(string); // Apache is the most performant function for that
    }

    /**
     * > Remap a value from one range to another
     *
     * @param value    The value you want to remap.
     * @param valueMin The minimum value of the original range.
     * @param valueMax The maximum value of the original range.
     * @param newMin   The minimum value of the new range
     * @param newMax   The maximum value of the new range
     * @return The value of the remapped value.
     */
    public static float remap(float value, float valueMin, float valueMax, float newMin, float newMax) {
        return Math.max(value - valueMin, valueMin) / (valueMax - valueMin) * (newMax - newMin) + newMin;
    }


    /**
     * Lerp returns a value between two points based on a percentage.
     *
     * @param point1 The first point.
     * @param point2 The second point
     * @param alpha  The amount of interpolation to use. 0.0 will cause lerp to return point1; 0.5 will cause it to return
     *               the midpoint between point1 and point2; 1.0 will cause it to return point2.
     * @return The value of point1 plus the value of alpha times the value of point2 minus the value of point1.
     */
    public static float lerp(float point1, float point2, float alpha) {
        return point1 + alpha * (point2 - point1);
    }

    /**
     * Given two points, and a value between 0 and 1, return a point that is a linear interpolation between the two points.
     *
     * @param point1 The first point
     * @param point2 The second point
     * @param alpha  the percentage between the two values, where 0.0 is equal to the first point, 0.1 is very near the
     *               first point, 0.5 is half-way in between, etc.
     * @return The value of the point between point1 and point2.
     */
    public static double lerp(double point1, double point2, double alpha) {
        return point1 + alpha * (point2 - point1);
    }

    /**
     * If the value is less than the minimum, return the minimum. If the value is greater than the maximum, return the
     * maximum. Otherwise, return the value.
     *
     * @param value The value to clamp.
     * @param min   The minimum value that the value can be.
     * @param max   The maximum value that the returned value can be.
     * @return The maximum of the minimum of value and max, and min.
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }


    /**
     * If the value is less than the minimum, return the minimum. If the value is greater than the maximum, return the
     * maximum. Otherwise, return the value.
     *
     * @param value The value to clamp.
     * @param min   The minimum value that the returned value can be.
     * @param max   The maximum value that the returned value can be.
     * @return The max of the min of value and max, and min.
     */
    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }

    /**
     * If the value is less than the minimum, return the minimum. If the value is greater than the maximum, return the
     * maximum. Otherwise, return the value.
     *
     * @param value The value to clamp.
     * @param min   The minimum value that the value can be.
     * @param max   The maximum value that the returned value can be.
     * @return The maximum of the minimum of value and max, and min.
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(Math.min(value, max), min);
    }

    /*
        Code taken from here:
        https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
     */
    public static double eval(@NotNull String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') {
                    nextChar();
                }

                if (ch == charToEat) {
                    nextChar();
                    return true;
                }

                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    x = switch (func) {
                        case "sqrt" -> Math.sqrt(x);
                        case "sin" -> Math.sin(Math.toRadians(x));
                        case "cos" -> Math.cos(Math.toRadians(x));
                        case "tan" -> Math.tan(Math.toRadians(x));
                        case "floor" -> Math.floor(x);
                        case "ceil" -> Math.ceil(x);
                        default -> throw new RuntimeException("Unknown function: " + func);
                    };
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public static double evalLimits(@NotNull String str) {
        String[] parts = str.split(",");
        double val = eval(parts[0]);
        double min = 0;
        double max = 0;

        if (parts.length >= 3) {
            try {
                min = Double.parseDouble(parts[1]);
            } catch (NumberFormatException ignored) {
                // ignored
            }

            try {
                max = Double.parseDouble(parts[2]);
            } catch (NumberFormatException ignored) {
                // ignored
            }
        }

        if (min != 0 && val < min) return min;
        if (max != 0 && val > max) return max;

        return val;
    }
}
