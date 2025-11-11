package main.card;

public abstract class Card {
    protected String name;
    protected String info;
    protected int cost;
    protected String type;
    protected String rarity;
    protected Boolean canPlay;

    public abstract void onPlay(main.player.Player player, main.enemy.Enemy enemy);
    
    public Boolean getCanPlay() {
        return canPlay;
    }

    public String getName() {
        String colorCode;
        switch (this.type.toLowerCase()) {
            case "attack":
                colorCode = "\u001B[91m";
                break;
            case "effect":
                colorCode = "\u001B[36m";
                break;
            case "passive":
                colorCode = "\u001B[92m";
                break;
            default:
                colorCode = "\u001B[37m";
        }
        return colorCode + name + "\u001B[0m";
    }

    public String getInfo(){
        return info;
    }

    public int getCost() {
        return cost;
    }
    
    public String getType() {
        return type;
    }

    public String getRarity(){
        switch (this.rarity.toLowerCase()) {
            case "normal":
                return "\u001B[34mNORMAL\u001B[0m";
            case "rare":
                return "\u001B[35mRARE\u001B[0m";
            case "epic":
                return "\u001B[33mEPIC\u001B[0m";
            case "legendary":
                return "\u001B[31mLEGENDARY\u001B[0m";
            default:
                return this.rarity;
        }
    }
}
