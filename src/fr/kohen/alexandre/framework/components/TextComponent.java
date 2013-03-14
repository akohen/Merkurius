package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

/**
 * Holds a text string
 * @author Alexandre
 *
 */
public class TextComponent extends Component {
	public StringBuffer text;
	
	public TextComponent() {
		this.text = new StringBuffer();
	}
	
	public TextComponent(String text) {
		this.text = new StringBuffer(text);
	}
}
