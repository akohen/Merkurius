package fr.kohen.alexandre.framework.network;

import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;

public interface Syncable {

	public void sync(EntityUpdate update);
	public StringBuilder getMessage();
}
