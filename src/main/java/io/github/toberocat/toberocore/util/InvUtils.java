package io.github.toberocat.toberocore.util;

import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public final class InvUtils {

    private final static int[][] slots = new int[][]{
            new int[]{
                    0, 1, 2, 3, 4, 5, 6, 7, 8
            },
            new int[]{
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    9, 10, 11, 12, 13, 14, 15, 16, 17
            },
            new int[]{
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26
            },
            new int[]{
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35
            },
            new int[]{
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35,
                    36, 37, 38, 39, 40, 41, 42, 43, 44
            },
            new int[]{
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35,
                    36, 37, 38, 39, 40, 41, 42, 43, 44,
                    45, 46, 47, 48, 49, 50, 51, 52, 53
            }
    };

    private final static int[][] centreSlots = new int[][]{
            new int[]{
                    1, 2, 3, 4, 5, 6, 7
            },
            new int[]{
                    1, 2, 3, 4, 5, 6, 7,
                    10, 11, 12, 13, 14, 15, 16,
            },
            new int[]{
                    10, 11, 12, 13, 14, 15, 16
            },
            new int[]{
                    10, 11, 12, 13, 14, 15, 16,
                    19, 20, 21, 22, 23, 24, 25
            },
            new int[]{
                    10, 11, 12, 13, 14, 15, 16,
                    19, 20, 21, 22, 23, 24, 25,
                    28, 29, 30, 31, 32, 33, 34
            },
            new int[]{
                    10, 11, 12, 13, 14, 15, 16,
                    19, 20, 21, 22, 23, 24, 25,
                    28, 29, 30, 31, 32, 33, 34,
                    37, 38, 39, 40, 41, 42, 43
            }
    };

    private final static int[][] borderSlots = new int[][]{
            new int[]{
                    0, 8
            },
            new int[]{
                    0, 8,
                    9, 17,
            },
            new int[]{
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    9, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26
            },
            new int[]{
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    9, 17,
                    18, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35
            },
            new int[]{
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    9, 17,
                    18, 26,
                    27, 35,
                    36, 37, 38, 39, 40, 41, 42, 43, 44
            },
            new int[]{
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    9, 17,
                    18, 26,
                    27, 35,
                    36, 44,
                    45, 46, 47, 48, 49, 50, 51, 52, 53
            }
    };

    /**
     * Gets the slots of an inventory with the specified size.
     *
     * @param rows the number of rows that the inventory to get slots for has.
     * @return the slots that this inventory should have.
     */
    public static int[] getSlots(int rows) {
        try {
            return slots[rows - 1];
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Rows must be from 1 to 6");
        }
    }

    public static int getRows(@NotNull Inventory inventory) {
        return inventory.getSize() / 9;
    }

    /**
     * Gets the centre slots of an inventory with the provided size.
     *
     * @param rows the number of rows in the inventory whose centre slots to get.
     * @return the centre slots of an inventory.
     * @throws IllegalArgumentException if the given number of rows is less than 1 or greater than 6.
     */
    public static int[] getCentreSlots(int rows) throws IllegalArgumentException {
        try {
            return centreSlots[rows - 1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Rows must be from 1 to 6");
        }
    }

    /**
     * Gets the border slots of an inventory with the provided size.
     *
     * @param rows the number of rows in the inventory whose border slots to get.
     * @return the border slots of an inventory.
     * @throws IllegalArgumentException if the given number of rows is less than 1 or greater than 6.
     */
    public static int[] getBorderSlots(int rows) throws IllegalArgumentException {
        try {
            return borderSlots[rows - 1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Rows must be from 1 to 6");
        }
    }

    /**
     * Calculates the smallest number of rows needed to fit a number of items in the centre slots of an inventory.
     * If the specified number of items is equal to 0 then 0 will be returned.
     * Otherwise, the returned value will always be greater than or equal to 3 and less than or equal to 6.
     *
     * @param amount the number of items to fit.
     * @return the minimum number of rows needed to fit the given number of items.
     */
    public static int getMinCentreRows(int amount) {
        if (amount <= 0) return 0;
        int rows = (int) Math.ceil((double) amount / 7);
        rows += 2;
        return Math.min(rows, 6);
    }
}
