package main.card;

public abstract class Card {
    protected String name;
    protected String info;
    protected int cost;
    protected String type;
    protected String rarity;
    protected Boolean canPlay;
    protected boolean needRemove = false;
    protected boolean disposable = false;
    protected boolean Temporary = false;

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
    
    public void setNeedRemove(boolean b){
        this.needRemove = b;
    }

    public boolean getNeedRemove(){
        return needRemove;
    }

    public boolean getDisposable(){
        return disposable;
    }

    public boolean getTemporary(){
        return Temporary;
    }
}
