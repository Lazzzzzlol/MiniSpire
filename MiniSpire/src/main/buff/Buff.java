package main.buff;

public abstract class Buff {
    protected int duration;
    protected String name;
    protected String type;
    
    public abstract void onEndTurn();
    
    public void setDuration(int d) {
        duration = d;
    }
    
    public void extendDuration(int d) {
        duration += d;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
}