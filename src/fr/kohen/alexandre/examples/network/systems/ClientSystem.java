package fr.kohen.alexandre.examples.network.systems;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Map.Entry;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.base.C.STATES;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;

public class ClientSystem extends DefaultSyncSystem {
	private boolean connected = false;
	private ComponentMapper<Synchronize> 	syncMapper;
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<EntityState> 	stateMapper;


	public ClientSystem() {
		super(0);
	}
	
	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper.getSafe(e);
		EntityState state 		= stateMapper.getSafe(e);
		Transform 	transform 	= transformMapper.getSafe(e);
		Synchronize sync 		= syncMapper.get(e);
		
		if( updates.containsKey(sync.getId()) ) {
			EntityUpdate update = updates.get( sync.getId() );
			/*if( data[1].equalsIgnoreCase("remove") ) {
				world.deleteEntity(e);
			}
			if( data[0].equalsIgnoreCase("set_playership") ) {
				e.addComponent( new Player() );
				e.refresh();
			}*/
			
			if( state != null ) {
				state.setState( STATES.valueOf(update.getNext()) );	
			}
			
			if( transform != null ) {
				transform.position.x 	= update.getNextFloat();
				transform.position.y 	= update.getNextFloat();
				transform.rotation 		= update.getNextFloat();
			}
			
			if( velocity != null ) {
				
				velocity.setSpeed( update.getNextFloat(), update.getNextFloat() );
				velocity.setRotation( update.getNextFloat() );	
			}
			
			updates.remove(sync.getId());
		}
		
	}
	
	protected void end() {
		// If the message has not been removed, the entity doesn't exist yet, so we're creating it here.
		for (Entry<Integer, EntityUpdate> entry : updates.entrySet()) {
			Gdx.app.log("creating: ", entry.getValue().getType() );
			EntityUpdate update = entry.getValue();
			
			if( update.getType().equalsIgnoreCase("player") && connected  ) {
				Entity e = EntityFactoryExamples.newClientOtherPlayer( world, 1, Float.parseFloat(update.getData()[5]), Float.parseFloat(update.getData()[6]) );
				e.addToWorld();
				Synchronize sync = syncMapper.get(e);
				sync.setId(entry.getKey());
			}
		}
		
		for( DatagramPacket event : events ) {
			String message = new String( event.getData(), 0, event.getLength() );
			String[] data = message.split(" ");
			
			if( data[0].equalsIgnoreCase("connected") ) {
				Gdx.app.log("ClientSystem", "connected");
				EntityFactoryExamples.newClientActivePlayer( world, Integer.parseInt(data[2]), Integer.parseInt(data[3]) ).addToWorld();
				this.connected  = true;
			} 
		}	
	}

	@Override
	public GameClient newClient(DatagramPacket packet, int port) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GameClient addHost(InetAddress inetAddress, int port) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void connected(int clientId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void newEntity(EntityUpdate entityUpdate, int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateEntity(Entity e, EntityUpdate update) {
		// TODO Auto-generated method stub
		
	}
	
}
