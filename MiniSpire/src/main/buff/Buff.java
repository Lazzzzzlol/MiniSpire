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

    public String getColorName() {
        String colorCode;
        switch (this.type.toLowerCase()) {
            case "positive":
                colorCode = "\u001B[34m";
                break;
            case "negative":
                colorCode = "\u001B[31m";
                break;
            default:
                colorCode = "\u001B[34m";
        }
        return colorCode + name + "\u001B[0m";
    }
    
    public String getType() {
        return type;
    }
}