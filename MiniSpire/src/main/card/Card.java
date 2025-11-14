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
        return name;
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
        return rarity;
    }
}

