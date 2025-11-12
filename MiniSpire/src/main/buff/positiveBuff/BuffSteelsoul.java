package main.buff.positiveBuff;

public class BuffSteelsoul extends PositiveBuff {
    
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

	public int getActionPointsToDeduct() {
		return actionPointsToDeduct;
	}
	
	public void setActionPointsToDeduct(int actionPointsToDeduct) {
		this.actionPointsToDeduct = actionPointsToDeduct;
	}

    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}