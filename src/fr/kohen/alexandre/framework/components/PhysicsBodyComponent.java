package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Body;

import fr.kohen.alexandre.framework.PhysicsBody;

/**
 * Holds the information about the body to be used by the physics system
 * @author Alexandre
 *
 */
public class PhysicsBodyComponent extends Component {
	public PhysicsBody physicsBody;
	
	public PhysicsBodyComponent(PhysicsBody physicsBody) {
		this.physicsBody = physicsBody;
	}
	
	public Body getBody() { return this.physicsBody.body; }

}
