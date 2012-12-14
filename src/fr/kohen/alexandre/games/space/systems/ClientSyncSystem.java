package fr.kohen.alexandre.games.space.systems;

import java.util.Map.Entry;

import org.newdawn.slick.util.Log;

import com.artemis.ComponentMapper;
import com.artemis.Entity;

import fr.kohen.alexandre.framework.components.Destination;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.engine.C.STATES;
import fr.kohen.alexandre.framework.systems.base.SyncSystemBase;
import fr.kohen.alexandre.games.space.EntityFactorySpaceGame;

public class ClientSyncSystem extends SyncSystemBase {

	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<EntityState> 	stateMapper;
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Destination> 	destinationMapper;
	private ComponentMapper<Synchronize> 	syncMapper;
	
	public ClientSyncSystem() {
		super(0);
	}

	@Override
	public void initialize() {
		super.initialize();
		
		this.velocityMapper 	= new ComponentMapper<Velocity>(Velocity.class, world);
		this.stateMapper 		= new ComponentMapper<EntityState>(EntityState.class, world);
		this.transformMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		this.destinationMapper	= new ComponentMapper<Destination>(Destination.class, world);
		this.syncMapper 		= new ComponentMapper<Synchronize>(Synchronize.class, world);
	}
	
	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper.get(e);
		EntityState state 		= stateMapper.get(e);
		Transform 	transform 	= transformMapper.get(e);
		Destination	destination	= destinationMapper	.get(e);
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
			
			if( destination != null ) {
				destination.setLocation( Float.parseFloat(data[i++]), Float.parseFloat(data[i++]) );
				destination.setRotation( Float.parseFloat(data[i++]) );	
			}
			messages.remove(sync.getId());
		}
		
		
		for( String event : events ) {
			String[] data = event.split(" ");
			if( data[0].equalsIgnoreCase("connected") && Integer.parseInt(data[2]) == sync.getId() ) {
				e.addComponent( new Player() );
				e.refresh();
			}
		}
	}
	
	protected void end() {
		for( String i : eventsRemoved )
			events.remove(i);
		eventsRemoved.clear();
		
		// If the message has not been removed, the entity doesn't exist yet, so we're creating it here.
		for (Entry<Integer, String> entry : messages.entrySet()) {
			Log.info("creating: " + entry.getValue() );
			String[] data = entry.getValue().split(" ");
			
			if( data[1].equalsIgnoreCase("player") ) {
				Entity e = EntityFactorySpaceGame.createPlayerClient(world, 1, Float.parseFloat(data[3]), Float.parseFloat(data[4]) );
				Synchronize sync = syncMapper.get(e);
				sync.setId(entry.getKey());
			}
			else if( data[1].equalsIgnoreCase("box") ) {
				Entity e = EntityFactorySpaceGame.createBox(world, 1, Float.parseFloat(data[3]), Float.parseFloat(data[4]), 50 );
				Synchronize sync = syncMapper.get(e);
				sync.setId(entry.getKey());
			}
		}
	}
	
}
