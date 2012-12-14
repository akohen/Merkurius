package fr.kohen.alexandre.framework.spatials;

import org.newdawn.slick.Color;

import fr.kohen.alexandre.framework.engine.Spatial;
import fr.kohen.alexandre.framework.engine.resources.ResourceManager;


public class DialogBox extends Spatial {

	public DialogBox() {
		super();
		color = new Color(150,150,150,255);
		//color = Color.white;
	}

	@Override
	public void initalize() {
		setGraphic(ResourceManager.getImage("talk"));
	}

}
