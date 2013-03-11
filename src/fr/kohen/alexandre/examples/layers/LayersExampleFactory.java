package fr.kohen.alexandre.examples.layers;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.physics.box2d.Filter;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.base.C;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.VisualComponent;
import fr.kohen.alexandre.framework.model.physicsBodies.BoxBody;

public class LayersExampleFactory extends EntityFactoryExamples {
	
	public static Entity newFrontBox(World world, int mapId, float x, float y, int size) {
		VisualComponent visualComponent = new VisualComponent("example_box_" + size);
		visualComponent.depth = 1;
		
		Filter filter = new Filter();
		filter.categoryBits = C.CATEGORY_FOREGROUND;
		filter.maskBits = ~C.CATEGORY_PLAYER;
		
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) )
			.addComponent( visualComponent )
			.addComponent( new PhysicsBodyComponent(new BoxBody(size, C.CATEGORY_1, (short) ~C.CATEGORY_PLAYER)) )
			.addComponent( new EntityState() );
		return e;
	}
	
	public static Entity newBackBox(World world, int mapId, float x, float y, int size) {
		VisualComponent visualComponent = new VisualComponent("example_box_" + size);
		visualComponent.depth = -1;
		
		Filter filter = new Filter();
		filter.categoryBits = C.CATEGORY_BACKGROUND;
		filter.maskBits = ~C.CATEGORY_PLAYER;
		
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) )
			.addComponent( visualComponent )
			.addComponent( new PhysicsBodyComponent(new BoxBody(size, C.CATEGORY_1, (short) ~C.CATEGORY_PLAYER)) )
			.addComponent( new EntityState() );
		return e;
	}
	
}
