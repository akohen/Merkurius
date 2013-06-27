package fr.kohen.alexandre.wip.spacePhysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import fr.kohen.alexandre.framework.base.C;
import fr.kohen.alexandre.framework.model.PhysicsBody;

public class SatelliteBody extends PhysicsBody {

	private short categoryBits = C.CATEGORY_1;
	private short maskBits = -1;
	
	public SatelliteBody() {
	}
	
	@Override
	public void initialize(World box2dworld) {
		// Create our body definition
		BodyDef bodyDef = new BodyDef();    
		bodyDef.type = BodyType.DynamicBody;
		// Set its world position
		bodyDef.position.set(new Vector2(100, 100));
		
		// Create a body from the defintion and add it to the world
		body = box2dworld.createBody(bodyDef);  
		//body.setLinearDamping(5f);
		
		// Create a polygon shape
		CircleShape shape = new CircleShape(); 
		shape.setRadius(10);
		Fixture fixture = body.createFixture(shape, 1f); 
		
		Filter filter = new Filter();
		filter.categoryBits = this.categoryBits;
		filter.maskBits = this.maskBits;
		
		fixture.setFilterData(filter);
		
		// Clean up after ourselves
		shape.dispose();
	}

}
