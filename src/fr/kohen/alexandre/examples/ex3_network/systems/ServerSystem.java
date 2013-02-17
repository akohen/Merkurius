package fr.kohen.alexandre.examples.ex3_network.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.examples.ex3_network.EntityFactoryEx3;
import fr.kohen.alexandre.examples.ex3_network.GameClientImpl;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.systems.SyncSystem;


public class ServerSystem extends SyncSystem {

	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<EntityState> 	stateMapper;
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Synchronize> 	syncMapper;
	private int								playerId = 0;
	

	public ServerSystem(int delta, int port) { super(delta, port); }

	@Override
	public void initialize() {
		super.initialize();

		transformMapper = ComponentMapper.getFor(Transform.class, world);
		velocityMapper 	= ComponentMapper.getFor(Velocity.class, world);
		stateMapper 	= ComponentMapper.getFor(EntityState.class, world);
		syncMapper 		= ComponentMapper.getFor(Synchronize.class, world);
	}
	
	@Override
	protected void end() {
		for ( String event : events ) {
			String[] data = event.split(" ");
			if( data[0].equalsIgnoreCase("connect") ) {
				Entity player = EntityFactoryEx3.addPlayer(world, 1, 100+50*playerId, 100+50*playerId);
				GameClientImpl client = new GameClientImpl(packet.getAddress(), Integer.parseInt(data[1]), packet.getPort(), playerId++, player);
				send(client, "connected " + client.getId() + " " + player.getId() );
				clientList.add( client );
				Gdx.app.log("New connection: ", packet.getAddress().toString() + ":" + packet.getPort() + " " + data[1] );
			}
		}
		
		events.clear();
		
		/*
		if( data[0].equalsIgnoreCase("turn") ) {
			
			for( GameClient client : clientList) {
				if( client.checkPacket(packet) ) {
					if( data[1].equalsIgnoreCase("left") )
						destinationMapper.get(((GameClientImpl) client).getShip()).addRotation(-45);
					else if( data[1].equalsIgnoreCase("right") )
						destinationMapper.get(((GameClientImpl) client).getShip()).addRotation(45);
				}
			} // clients
			
		} // if turn
		*/
	}


	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper	.get(e);
		EntityState	state		= stateMapper		.get(e);
		Transform	transform	= transformMapper	.get(e);
		Synchronize	sync		= syncMapper		.get(e);

		// Creating the message
		String message = e.getId() + " " + sync.getType();
		
		if( state != null )
			message += " " + state.getState();
		
		if( transform != null )
			message += " " + transform.x + " " + transform.y + " " + transform.getRotation();
		
		if( velocity != null )
			message += " " + velocity.getY() + " " + velocity.getY() + " " + velocity.getRotation();
		

		// Sending the message for each client
		send(message);	
	}

}
