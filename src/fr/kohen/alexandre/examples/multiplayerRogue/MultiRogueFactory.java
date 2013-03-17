package fr.kohen.alexandre.examples.multiplayerRogue;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.examples.multiplayerRogue.components.Destination;
import fr.kohen.alexandre.examples.multiplayerRogue.components.Input;
import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.model.*;
import fr.kohen.alexandre.framework.model.physicsBodies.BoxBody;
import fr.kohen.alexandre.framework.model.visuals.BoxVisual;

public class MultiRogueFactory extends EntityFactory {

public static Map<String, Visual> visuals = new HashMap<String, Visual>();
public static Map<String, Action> actions = new HashMap<String, Action>();
	
	static {
		visuals.put( "example_player_visual", new BoxVisual(25, 25, Color.BLUE) );
		visuals.put( "example_box_50", new BoxVisual(50, 50, Color.RED) );
		visuals.put( "example_box_100", new BoxVisual(100, 100, Color.RED) );
		visuals.put( "example_box_green_50", new BoxVisual(50, 50, Color.GREEN) );
		visuals.put( "example_box_green_100", new BoxVisual(100, 100, Color.GREEN) );

		actions.put( "client_action", new ClientScreenAction() );
		actions.put( "server_action", new ServerScreenAction() );
	}

	public static Entity newBox(World world, int mapId, float x, float y, int size) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent("example_box_" + size) );
		e.addComponent( new PhysicsBodyComponent(new BoxBody(size)) );
		e.addComponent( new EntityState() );
		return e;		
	}
	
	public static Entity newText(World world, int mapId, float x, float y, String text) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new TextComponent(text) );
		e.addComponent( new EntityState() );
		e.addComponent( new DepthComponent(10) );
		return e;
	}
	
	
	public static Entity newServerPlayer(World world, int playerId) {
		Entity e = world.createEntity();
		
		e.addComponent( new Transform(1, 0, 0) );
		e.addComponent( new VisualComponent("example_player_visual") );
		e.addComponent( new PhysicsBodyComponent(new PlayerBody()) );
		e.addComponent( new Destination() );
		
		e.addComponent( new Input() );
		
		e.addComponent( new Player(playerId) );
		e.addComponent( new EntityState() );
		
		e.addComponent( new Synchronize("player") );
		return e;
	}
	
	public static Entity newClientActivePlayer(World world, int playerId, int syncId) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		
		e.addComponent( new Transform(1, 0, 0) );
		e.addComponent( new VisualComponent("example_player_visual") );
		e.addComponent( new PhysicsBodyComponent(new PlayerBody()) );
		e.addComponent( new Destination() );
		
		e.addComponent( new Input() );
		
		e.addComponent( new Player(playerId) );
		e.addComponent( new EntityState() );
		
		e.addComponent( new Synchronize("player", syncId) );
		return e;
	}
	
	public static Entity newClientOtherPlayer(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent("example_player_visual") );
		e.addComponent( new PhysicsBodyComponent(new PlayerBody()) );
		e.addComponent( new Destination() );
		
		e.addComponent( new Player() );
		e.addComponent( new EntityState() );
		e.addComponent( new Synchronize("player") );
		return e;
	}
	
	public static Entity newServerButton(World world, int mapId, float x, float y) {
		Entity e = newBox(world, mapId, x, y, 50);
		e.addComponent( new ActionsComponent("server_action") );
		return e;		
	}
	
	public static Entity newClientButton(World world, int mapId, float x, float y) {
		Entity e = newBox(world, mapId, x, y, 50);
		e.addComponent( new ActionsComponent("client_action") );
		return e;		
	}
}
