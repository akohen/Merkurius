package fr.kohen.alexandre.framework.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import com.artemis.Component;

import fr.kohen.alexandre.framework.engine.resources.ResourceManager;
import fr.kohen.alexandre.framework.spatials.TextSpatial;

/**
 * Button component
 * @author Alexandre
 *
 */
public class Button extends Component {
	private Image imageDefault;
	private Image imageSelect;
	private String menuGroup;
	private String action;
	private TextSpatial text;
	
	public Button(int imageDef, int imageSel, String menuGroup, String action) {
		this.imageDefault = ResourceManager.getImage("buttons").getSubImage(0, 25*imageDef, 100, 25);
		this.imageSelect = ResourceManager.getImage("buttons").getSubImage(0, 25*imageSel, 100, 25);
		this.menuGroup = menuGroup;
		this.action = action;
	}
	
	
	public Button(String text, String menuGroup, String action) {
		this.imageDefault = null;
		this.imageSelect = null;
		this.text = new TextSpatial(text);
		this.menuGroup = menuGroup;
		this.action = action;
	}

	/**
	 * Action string
	 * @return
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * Display this button
	 */
	public void render( Transform loc, boolean selected) {
		if(selected) {
			if( imageSelect!= null )
				this.imageSelect.draw(loc.getX(), loc.getY());
			else
				this.text.render(null, loc, Color.red);
		}
		else {
			if( imageDefault!= null )
				this.imageDefault.draw(loc.getX(), loc.getY());
			else
				this.text.render(null, loc, Color.white);
		}
	}
	
	/**
	 * Return the name of the group
	 * @return
	 */
	public String getGroup() {
		return this.menuGroup;
	}
}
