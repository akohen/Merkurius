package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;

public class Expires extends Component implements Syncable {
	private int lifeTime;
	
	public Expires(int lifeTime) {
		this.lifeTime = lifeTime;
	}
	
	public int getLifeTime() {
		return lifeTime;
	}
	
	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}
	
	public void reduceLifeTime(int lifeTime) {
		this.lifeTime -= lifeTime;
	}
	
	public void increaseLifeTime(int lifeTime) {
		this.lifeTime += lifeTime;
	}
	
	public boolean isExpired() {
		return lifeTime <= 0;
	}

	@Override
	public void sync(EntityUpdate update) {
		this.lifeTime = update.getNextInteger();
	}

	@Override
	public StringBuilder getMessage() {
		return new StringBuilder().append(this.lifeTime);
	}

	

}
