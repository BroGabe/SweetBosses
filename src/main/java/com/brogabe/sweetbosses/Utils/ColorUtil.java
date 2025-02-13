package com.brogabe.sweetbosses.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public class ColorUtil {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static Color colorFromString(String string) {
        switch (string.toUpperCase()) {
            case "MAROON": return Color.MAROON;
            case "AQUA": return Color.AQUA;
            case "BLACK": return Color.BLACK;
            case "BLUE": return Color.BLUE;
            case "GREEN": return Color.GREEN;
            case "LIME": return Color.LIME;
            case "OLIVE": return Color.OLIVE;
            case "NAVY": return Color.NAVY;
            case "PURPLE": return Color.PURPLE;
            case "ORANGE": return Color.ORANGE;
            case "RED": return Color.RED;
            case "FUCHSIA": return Color.FUCHSIA;
            case "SILVER": return Color.SILVER;
            case "TEAL": return Color.TEAL;
            case "YELLOW": return Color.YELLOW;
            case "WHITE": return Color.WHITE;
            default: return Color.GRAY;
        }
    }
}
