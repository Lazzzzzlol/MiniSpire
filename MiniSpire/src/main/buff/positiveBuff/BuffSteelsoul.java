package main.buff.positiveBuff;

public class BuffSteelsoul extends PositiveBuff {
    
	private int absorbedDamage = 0;
	private int actionPointsToDeduct = 0;

    public BuffSteelsoul(int duration) {
        this.name = "Steelsoul";
        this.duration = duration;
    }
	public BuffSteelsoul(int duration, int actionPointsToDeduct) {
		this.name = "Steelsoul";
		this.duration = duration;
		this.actionPointsToDeduct = actionPointsToDeduct;
	}

	public void absorb(int damage) {
        this.absorbedDamage += damage;
    }

    public int getAbsorbedDamageAndReset() {
        int temp = this.absorbedDamage;
        this.absorbedDamage = 0;
        return temp;
    }

    public int getActionPointsToDeduct() {
        return actionPointsToDeduct;
    }

    public void setActionPointsToDeduct(int value) {
        this.actionPointsToDeduct = value;
    }
}