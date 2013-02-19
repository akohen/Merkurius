package fr.kohen.alexandre.examples.ex3_network.systems;

import java.util.Map.Entry;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.examples.ex3_network.EntityFactoryEx3;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.systems.SyncSystem;
import fr.kohen.alexandre.framework.C.STATES;

public class ClientSystem extends SyncSystem {
	public ClientSystem() {
		super(0);
	}
	
	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper.getSafe(e);
		EntityState state 		= stateMapper.getSafe(e);
		Transform 	transform 	= transformMapper.getSafe(e);
		Synchronize sync 		= syncMapper.get(e);
		
		if( messages.containsKey(sync.getId()) ) {
			String[] data = messages.get(sync.getId()).split(" ");
			/*if( data[1].equalsIgnoreCase("remove") ) {
				world.deleteEntity(e);
			}
			if( data[0].equalsIgnoreCase("set_playership") ) {
				e.addComponent( new Player() );
				e.refresh();
			}*/
			
			int i=2;
			if( state != null ) {
				state.setState( STATES.valueOf(data[i++]) );	
			}
			
			if( transform != null ) {
				transform.setLocation( Float.parseFloat(data[i++]), Float.parseFloat(data[i++]) );
				transform.setRotation( Float.parseFloat(data[i++]) );
			}
			
			if( velocity != null ) {
				
				velocity.setSpeed( Float.parseFloat(data[i++]), Float.parseFloat(data[i++]) );
				velocity.setRotation( Float.parseFloat(data[i++]) );	
			}
			
			messages.remove(sync.getId());
		}
		
		
		for( String event : events ) {
			String[] data = event.split(" ");
			if( data[0].equalsIgnoreCase("connected") && Integer.parseInt(data[2]) == sync.getId() ) {
				e.addComponent( new Player() ).changedInWorld();
			}
		}
		
	}
	
	protected void end() {
		// If the message has not been removed, the entity doesn't exist yet, so we're creating it here.
		for (Entry<Integer, String> entry : messages.entrySet()) {
			Gdx.app.log("creating: ", entry.getValue() );
			String[] data = entry.getValue().split(" ");
			
			if( data[1].equalsIgnoreCase("player") ) {
				Entity e = EntityFactoryEx3.addPlayerClient(world, 1, Float.parseFloat(data[3]), Float.parseFloat(data[4]) );
				Synchronize sync = syncMapper.get(e);
				sync.setId(entry.getKey());
			}
			else if( data[1].equalsIgnoreCase("box") ) {
				Entity e = EntityFactoryEx3.createBox(world, 1, Float.parseFloat(data[3]), Float.parseFloat(data[4]), 50 );
				Synchronize sync = syncMapper.get(e);
				sync.setId(entry.getKey());
			}
		}
	}
	
}
