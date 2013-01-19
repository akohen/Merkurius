package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Visual extends Component {
	private Sprite sprite;

	public Visual(Sprite sprite) {
		setSprite(sprite);
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
}
