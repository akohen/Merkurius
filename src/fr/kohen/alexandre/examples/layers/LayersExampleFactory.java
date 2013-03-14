package fr.kohen.alexandre.examples.layers;

import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.base.C;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.model.physicsBodies.BoxBody;

public class LayersExampleFactory extends EntityFactoryExamples {
	
	public static Entity newFrontBox(World world, int mapId, float x, float y, int size) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) )
			.addComponent( new VisualComponent("example_box_" + size) )
			.addComponent( new DepthComponent(1) )
			.addComponent( new PhysicsBodyComponent(new BoxBody(size, C.CATEGORY_1, (short) ~C.CATEGORY_PLAYER)) )
			.addComponent( new EntityState() );
		return e;
	}
	
	public static Entity newBackBox(World world, int mapId, float x, float y, int size) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) )
			.addComponent( new VisualComponent("example_box_" + size) )
			.addComponent( new DepthComponent(-1) )
			.addComponent( new PhysicsBodyComponent(new BoxBody(size, C.CATEGORY_1, (short) ~C.CATEGORY_PLAYER)) )
			.addComponent( new EntityState() );
		return e;
	}
	
}
