package fr.kohen.alexandre.games.space.systems;

import java.net.DatagramPacket;

import org.newdawn.slick.util.Log;

import com.artemis.ComponentMapper;
import com.artemis.Entity;

import fr.kohen.alexandre.framework.components.Destination;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.engine.network.GameClient;
import fr.kohen.alexandre.framework.systems.base.SyncSystemBase;
import fr.kohen.alexandre.games.space.EntityFactorySpaceGame;
import fr.kohen.alexandre.games.space.GameClientImpl;

public class ServerSyncSystem extends SyncSystemBase {

	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<EntityState> 	stateMapper;
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Destination> 	destinationMapper;
	private ComponentMapper<Synchronize> 	syncMapper;
	private int								playerId = 0;
	

	public ServerSyncSystem(int delta, int port) { super(delta, port); }

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
	public void receive(DatagramPacket packet) {
		String message = new String(packet.getData(), 0, packet.getLength());
		String[] data = message.split(" ");
		
		if( data[0].equalsIgnoreCase("connect") ) {
			Entity ship = EntityFactorySpaceGame.createPlayer(world, 1, 100+50*playerId, 100+50*playerId);
			GameClientImpl client = new GameClientImpl(packet.getAddress(), Integer.parseInt(data[1]), packet.getPort(), playerId++, ship);
			send(client, "connected " + client.getId() + " " + ship.getId() );
			clientList.add( client );
			Log.info("New connection: " + packet.getAddress().toString() + ":" + packet.getPort() + " " + data[1] );
		}
		
		
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
		
	}


	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper	.get(e);
		EntityState	state		= stateMapper		.get(e);
		Transform	transform	= transformMapper	.get(e);
		Destination	destination	= destinationMapper	.get(e);
		Synchronize	sync		= syncMapper		.get(e);

		// Creating the message
		String message = e.getId() + " " + sync.getType();
		
		if( state != null )
			message += " " + state.getState();
		
		if( transform != null )
			message += " " + transform.getX() + " " + transform.getY() + " " + transform.getRotation();
		
		if( velocity != null )
			message += " " + velocity.getY() + " " + velocity.getY() + " " + velocity.getRotation();
		
		if( destination != null )
			message += " " + destination.getX() + " " + destination.getY() + " " + destination.getRotation();

		// Sending the message for each client
		send(message);	
	}

}
