package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.kohen.alexandre.framework.model.Visual;

/**
 * Holds the visual representation of the entity
 * @author Alexandre
 *
 */
public class VisualComponent extends Component {
	public Visual visual;
	
	public VisualComponent(Visual visual) {
		this.visual = visual;
	}
	
	public void draw(Transform transform, SpriteBatch batch) {
		this.visual.draw(transform, batch);
	}
	
	public void update(float delta) {
		this.visual.update(delta);
	}
	
	public void updateDraw(float delta, Transform transform, SpriteBatch batch) {
		this.visual.updateDraw(delta,transform,batch);
	}
}
