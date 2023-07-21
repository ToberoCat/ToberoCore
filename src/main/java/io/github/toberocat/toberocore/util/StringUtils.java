package io.github.toberocat.toberocore.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.Normalizer;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public final class StringUtils {

    private static final Pattern pattern = Pattern.compile("(#[a-fA-F\\d]{6})");

    /**
     * This function will convert any text with numbers instead of letters (e.g: e => 3) back into normal language.
     * Note that this will also replace numbers not meant as leeters. E.g: I'm number 1 => I'm number i
     *
     * @param leetString The string with the numbers (e.g: 4bcd3f6h1jklmn0pqr57uvwxyz)
     * @return The string without the numbers (e.g: abcdefghijklmnopqrstuvwxyz)
     */
    public static String simpleLeetToEnglish(String leetString) {
        return leetString
                .replace("4", "a")
                .replace("3", "e")
                .replace("6", "g")
                .replace("1", "i")
                .replace("0", "o")
                .replace("5", "s")
                .replace("7", "t");
    }

    /**
     * Does the same as @see {@link #similarity(String, String)}, but now also removes stylised text and leetspeak
     *
     * @param _s1 first string for comparison
     * @param _s2 second string for comparison
     * @return The difference between the two string as double
     */
    public static float similarityExtended(String _s1, String _s2) {
        String s1 = simpleLeetToEnglish(_s1);
        String s2 = simpleLeetToEnglish(_s2);

        return similarity(s1, s2);
    }

    /**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     * It won't do anything beside
     *
     * @param s1 first string for comparison
     * @param s2 second string for comparison
     * @return The difference between the two string as double
     */
    public static float similarity(String s1, String s2) {
        String longer = Normalizer.normalize(s1, Normalizer.Form.NFKC), shorter = Normalizer.normalize(s2, Normalizer.Form.NFKC);
        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0f;
        }

        return (longerLength - editDistance(longer, shorter)) / (float) longerLength;

    }

    /**
     * For each letter in s1, calculate the cost of inserting, deleting, and replacing each letter in s2
     *
     * @param s1 The first string to compare
     * @param s2 The string to compare against
     * @return The edit distance between two strings.
     */
    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    /**
     * It takes a string, and replaces all the color codes with the correct color
     * I would personally add it as a static import
     *
     * @param _msg The message you want to format.
     * @return The message with the color codes replaced with the color.
     */
    public static String format(final String _msg) {
        String msg = _msg;

        // Converting hex in text to color
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            String color = msg.substring(matcher.start(), matcher.end());
            msg = msg.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(msg);
        }

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static @NotNull Component miniMessageFormat(@NotNull String msg) {
        return MiniMessage.miniMessage().deserialize(msg);
    }

    /**
     * It replaces all the characters that are special in JSON with their escaped versions
     *
     * @param s The string to escape.
     * @return A string with all the characters escaped.
     */
    public static String escape(@NotNull String s) {
        return s.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("{", "\\{")
                .replace("}", "\\}");
    }

    /**
     * Replaces a string with the provided placeholders.
     *
     * @param string        the string to replace
     * @param placeholders  the placeholders to replace the string with
     * @param caseSensitive whether the placeholders should be case-sensitive
     * @return the string replaced with the placeholders
     */
    @Contract("!null, _, _ -> !null")
    public static @Nullable String replace(@Nullable String string, @NotNull Map<String, String> placeholders, boolean caseSensitive) {
        if (string == null) {
            return null;
        }

        for (Entry<String, String> entry : placeholders.entrySet()) {
            String key = entry.getKey();
            if (key == null) continue;

            String val = entry.getValue();
            if (val == null) continue;

            if (caseSensitive) {
                string = string.replace(key, val);
            } else {
                if (key.startsWith("{")) key = "\\{" + key.substring(1);
                Matcher match = Pattern.compile(key, Pattern.CASE_INSENSITIVE).matcher(string);
                while (match.find()) string = string.replace(match.group(), val);
            }

        }

        return string;
    }

    /**
     * Replaces a string with the provided placeholders.
     *
     * @param string       the string to replace
     * @param placeholders the placeholders to replace the string with
     * @return the string replaced with the placeholders
     */
    @Contract("!null, _ -> !null")
    public static @Nullable String replace(@Nullable String string, @NotNull Map<String, String> placeholders) {
        return replace(string, placeholders, false);
    }
}
