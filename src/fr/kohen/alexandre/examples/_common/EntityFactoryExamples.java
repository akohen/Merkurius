package fr.kohen.alexandre.examples._common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.examples._common.visuals.LordLardVisual;
import fr.kohen.alexandre.examples.mouse.*;
import fr.kohen.alexandre.examples.screens.ScreenExampleAction;
import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.model.*;
import fr.kohen.alexandre.framework.model.actions.ActionList;
import fr.kohen.alexandre.framework.model.physicsBodies.BoxBody;
import fr.kohen.alexandre.framework.model.physicsBodies.CameraBody;
import fr.kohen.alexandre.framework.model.physicsBodies.LordLardBody;
import fr.kohen.alexandre.framework.model.visuals.BoxVisual;

public class EntityFactoryExamples extends EntityFactory {

public static Map<String, Visual> visuals = new HashMap<String, Visual>();
public static Map<String, Action> actions = new HashMap<String, Action>();
	
	static {
		visuals.put( "example_player_visual", new BoxVisual(25, 25, Color.BLUE) );
		visuals.put( "lord_lard", new LordLardVisual() );
		visuals.put( "example_box_50", new BoxVisual(50, 50, Color.RED) );
		visuals.put( "example_box_100", new BoxVisual(100, 100, Color.RED) );
		visuals.put( "example_box_green_50", new BoxVisual(50, 50, Color.GREEN) );
		visuals.put( "example_box_green_100", new BoxVisual(100, 100, Color.GREEN) );

		actions.put( "mouse_example_action", new ExampleAction() );
		
		ArrayList<Action> actionList = new ArrayList<Action>();
		actionList.add( new ExampleAction() );
		actionList.add( new ExampleActionRight() );
		actions.put( "mouse_example_action_list", new ActionList(actionList) );
		actions.put( "screen_example_action", new ScreenExampleAction() );
	}
	
	public static Entity newPlayer(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Velocity(200,200) );
		e.addComponent( new VisualComponent("example_player_visual") );
		e.addComponent( new PhysicsBodyComponent(new PlayerBody()) );
		e.addComponent( new Player() );
		e.addComponent( new EntityState() );
		return e;
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
		e.addComponent( new Transform(mapId, x, y, 10) );
		e.addComponent( new TextComponent(text) );
		e.addComponent( new EntityState() );
		return e;
	}
	
	
	
	public static Entity newMap(World world, int mapId, String mapName) {
		Entity e = world.createEntity();
		e.addComponent( new MapComponent(mapId,mapName) );
		e.addComponent( new Transform(mapId, 0, 0, 0) );
		return e;
	}
	
	
	public static Entity testCamera(World world, int mapId, float x, float y, float rotation, int screenX, int screenY, int width, int height, float screenRotation, String name) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y, 1, rotation) );
		e.addComponent( new CameraComponent(width, height, screenX, screenY, 1.0f, screenRotation, name) );
		e.addComponent( new PhysicsBodyComponent(new CameraBody(150, 100)) );
		return e;
	}

	
	public static Entity newLordLard(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Velocity(100,100) );
		
		e.addComponent( new VisualComponent("lord_lard") );
		e.addComponent( new PhysicsBodyComponent(new LordLardBody()) );
		
		e.addComponent( new EntityState() );
		e.addComponent( new Player() );
		return e;
	}
	
	public static Entity newMouseExamplePlayer(World world, int mapId, float x, float y) {
		Entity e = newPlayer(world, mapId, x, y);
		e.addComponent( new ActionsComponent("mouse_example_action") );
		return e;
	}

	public static Entity newMouseExampleBox(World world, int mapId, int x, int y, int size) {
		Entity e = newBox(world, mapId, x, y, size);
		e.addComponent( new ActionsComponent("mouse_example_action") );
		return e;		
	}

	public static Entity newMouseExampleBoxWithRightClick(World world, int mapId, int x, int y, int size) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent("example_box_green_" + size) );
		e.addComponent( new PhysicsBodyComponent(new BoxBody(size)) );
		e.addComponent( new EntityState() );
		e.addComponent( new ActionsComponent("mouse_example_action_list") );
		return e;		
	}
	
	
	public static Entity newScreenExampleButton(World world, int mapId, float x, float y) {
		Entity e = newBox(world, mapId, x, y, 50);
		e.addComponent( new ActionsComponent("screen_example_action") );
		return e;		
	}
}
