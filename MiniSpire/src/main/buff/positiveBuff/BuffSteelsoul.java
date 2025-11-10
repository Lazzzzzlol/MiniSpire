package main.buff.positiveBuff;

import main.buff.Buff;
import main.Main;
import java.util.concurrent.TimeUnit;

public class BuffSteelsoul implements Buff {
	
	String name = "Steelsoul";
	int duration = 0;
	int absorbedDamage = 0; // 存储吸收的伤害
	
	public BuffSteelsoul(int duration) {
		this.duration = duration;
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
	
	@Override
	public int onReceiveDamage(int damage) {
		// 吸收所有收到的伤害
		if (damage > 0) {
			this.absorbedDamage += damage;
			Main.executor.schedule(() -> {
				System.out.println(" >> Steelsoul absorbed " + damage + " damage!");
			}, 1, TimeUnit.SECONDS);
		}
		return 0; // 返回 0 表示伤害被完全吸收
	}
	
	/**
	 * 获取吸收的伤害总量
	 */
	public int getAbsorbedDamage() {
		return absorbedDamage;
	}
	
	/**
	 * 返还所有吸收的伤害并重置计数器
	 * @return 需要返还的伤害总量
	 */
	public int returnAbsorbedDamage() {
		int damageToReturn = this.absorbedDamage;
		if (damageToReturn > 0) {
			Main.executor.schedule(() -> {
				System.out.println(" >> Steelsoul: Returning " + damageToReturn + " absorbed damage!");
			}, 1, TimeUnit.SECONDS);
			this.absorbedDamage = 0; // 重置
		}
		return damageToReturn;
	}
}
