 package main;

import main.card.Card;
import main.player.Player;

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
    public static final String DARK_GRAY = "\u001B[90m";
    
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";


    public static String colorOnAnyElse(String text, String colorCode) {
        if (!Player.getInstance().getColorViewStatus()){
            return text;
        }
        return colorCode + text + RESET;
        
    }

    public static String colorOnForCardRarity(Card card) {
        String rarity = card.getRarity();
        if (!Player.getInstance().getColorViewStatus()){
            return rarity;
        }
        if (card.getRarity() == "special"){
            return colorOnSpecialCardsAllInfo(card, rarity.toUpperCase());
        }

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
        if (!Player.getInstance().getColorViewStatus()){
            return SOLDOUT;
        }
        return YELLOW + SOLDOUT + RESET;
    }

    public static String colorOnForCardName(Card card) {   
        String name = card.getName();     
        if (!Player.getInstance().getColorViewStatus()){
            return name;
        }
        if (card.getRarity() == "special"){
            return colorOnSpecialCardsAllInfo(card, name);
        }

        String type = card.getType();
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

    public static String colorOnForCardCost(Card card) {
        String cost = String.valueOf(card.getCost());
        
        if (!Player.getInstance().getColorViewStatus()){
            return cost;
        }
        return YELLOW + cost + RESET;
    }

    public static String colorOnForCardInfo(Card card) {
        String info = card.getInfo();
        if (!Player.getInstance().getColorViewStatus()){
            return info;
        }
        if (card.getRarity() == "special"){
            return colorOnSpecialCardsAllInfo(card, info);
        }

        return info;
    }

    public static String colorOnSpecialCardsAllInfo(Card card, String text){
        String colorOnText = text;
        switch (card.getName()){
            case "Sacrificial Ritual - cOLoRS":
                colorOnText = getColorfulText(text, "rainbow");
                break;
            case "Death Brand":
                colorOnText = DARK_GRAY + text + RESET;
                break;
            case "Instant Flurry":
                colorOnText = getColorfulText(text, "redGrayWhite");
                break;
            default:
                break;
        }
        return colorOnText;
    }

    public static String getColorfulText(String text, String type) {
        if (!Player.getInstance().getColorViewStatus()){
            return text;
        }
        String[] colorPool;
        switch (type){
            case "rainbow":
                colorPool = new String[]{
                    BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE,
                    DARK_GRAY, BRIGHT_RED, BRIGHT_GREEN, BRIGHT_YELLOW, 
                    BRIGHT_BLUE, BRIGHT_PURPLE, BRIGHT_CYAN, BRIGHT_WHITE
                };
                break;
            case "redGrayWhite":
                colorPool = new String[]{
                    WHITE, BRIGHT_RED, DARK_GRAY
                };
                break;
            default:
                return text;
        }
        
        StringBuilder rainbowText = new StringBuilder();
        
        for (int i = 0; i < text.length(); i++) {
            int randomIndex = Main.random.nextInt(colorPool.length);
            String randomColor = colorPool[randomIndex];
            rainbowText.append(randomColor).append(text.charAt(i));
        }
        
        rainbowText.append(RESET);
        return rainbowText.toString();
    }

    public static String colorOnForEnemyName(String name, String type) {
        if (!Player.getInstance().getColorViewStatus()){
            return name;
        }

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
        if (!Player.getInstance().getColorViewStatus()){
            return String.valueOf(currentHp);
        }

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
        if (!Player.getInstance().getColorViewStatus()){
            return name;
        }
        
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
