package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

/**
 * Holds a text string
 * @author Alexandre
 *
 */
public class TextComponent extends Component {
	public StringBuffer text;
	public Color color = Color.BLACK;
	
	public TextComponent() {
		this.text = new StringBuffer();
	}
	
	public TextComponent(String text) {
		this.text = new StringBuffer(text);
	}
	
	public TextComponent(String text, Color color) {
		this.text = new StringBuffer(text);
		this.color = color;
	}
}
