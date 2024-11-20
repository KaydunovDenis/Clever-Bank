package com.github.kaydunov.util;

public class StringUtils {

    public static String centerString(String s, int width) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public static String centerStringLn(String s, int width) {
        return centerString(s, width) + "\n";
    }


    /**
     * Aligns the given text to the center within a line of specified width.
     * <p>
     * This method calculates the necessary padding on both sides of the text
     * to center it within the given line width. If the line width is odd and
     * the text length is even (or vice versa), the extra space is added to the
     * left side.
     *
     * @param text      The text to be centered.
     * @param lineWidth The total width of the line in which to center the text.
     * @return A new string with the text centered within the specified line width.
     * If the line width is less than the text length, the original text
     * is returned unchanged.
     * @throws IllegalArgumentException if the text is null or if the lineWidth is negative.
     */
    public static String alignByCentre(String text, int lineWidth) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        if (lineWidth < 0) {
            throw new IllegalArgumentException("Line width cannot be negative");
        }

        int emptyCharactersCount = lineWidth - text.length();
        if (emptyCharactersCount <= 0) {
            return text;
        }

        int rightPadding = emptyCharactersCount / 2;
        int leftPadding = rightPadding + (emptyCharactersCount % 2);

        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }
}
