package main.buff.positiveBuff;

import main.buff.Buff;

public class BuffSteelsoul implements Buff{
    
    String name = "Steelsoul";
	int duration = 0;
	private int actionPointsToDeduct = 0; // 需要扣除的行动点数
	
	public BuffSteelsoul(int duration) {
		this.duration = duration;
	}
	
	public BuffSteelsoul(int duration, int actionPointsToDeduct) {
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
	
	@Override
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public int getDuration() {
		return this.duration;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void extendDuration(int duration) {
		this.duration += duration;
	}
}
