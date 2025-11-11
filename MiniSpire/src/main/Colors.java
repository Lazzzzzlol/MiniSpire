package main;

public class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    
    public static final String BRIGHT_BLACK = "\u001B[90m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";

    public static String colorOnForCardRarity(String rarity) {
        String colorCode;
        switch (rarity.toLowerCase()) {
            case "normal":
                colorCode = WHITE;
                break;
            case "rare":
                colorCode = BRIGHT_BLUE;
                break;
            case "epic":
                colorCode = BRIGHT_PURPLE;
                break;
            case "legendary":
                colorCode = BRIGHT_YELLOW;
                break;
            default:
                return rarity;
        }
        return colorCode + rarity.toUpperCase() + RESET;
    }

    public static String colorOnForSOLDOUT(String SOLDOUT) {
        return YELLOW + SOLDOUT + RESET;
    }

    public static String colorOnForCardName(String name, String type) {
        String colorCode;
        switch (type.toLowerCase()) {
            case "attack":
                colorCode = BRIGHT_RED;
                break;
            case "effect":
                colorCode = CYAN;
                break;
            case "passive":
                colorCode = BRIGHT_GREEN;
                break;
            default:
                colorCode = WHITE;
        }
        return colorCode + name + RESET;
    }

    public static String colorOnForCardCost(int cost) {
        return YELLOW + cost + RESET;
    }

    public static String colorOnForEnemyName(String name, String type) {
        String colorCode;
        switch (type.toLowerCase()) {
            case "boss":
                colorCode = BRIGHT_RED;
                break;
            case "elite":
                colorCode = YELLOW;
                break;
            case "normal":
                colorCode = WHITE;
                break;
            default:
                return name;
        }
        return colorCode + name + RESET;
    }

    public static String colorOnForHP(int currentHp, int maxHp) {
        double ratio = (double) currentHp / maxHp;
        String colorCode;
        
        if (ratio >= 0.7) {
            colorCode = GREEN;
        } else if (ratio >= 0.3) {
            colorCode = BRIGHT_YELLOW;
        } else {
            colorCode = RED;
        }
        return colorCode + currentHp + RESET;
    }

    public static String colorOnForBuff(String name, String type) {
        String colorCode;
        switch (type.toLowerCase()) {
            case "positive":
                colorCode = BLUE;
                break;
            case "negative":
                colorCode = RED;
                break;
            default:
                return name;
        }
        return colorCode + name + RESET;
    }
}
