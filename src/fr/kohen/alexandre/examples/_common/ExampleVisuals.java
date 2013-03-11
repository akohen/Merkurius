package fr.kohen.alexandre.examples._common;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.examples._common.visuals.LordLardVisual;
import fr.kohen.alexandre.framework.model.Visual;
import fr.kohen.alexandre.framework.model.visuals.BoxVisual;

public class ExampleVisuals {
	public static Map<String, Visual> visuals = new HashMap<String, Visual>();
	
	static {
		visuals.put( "example_player_visual", new BoxVisual(25, 25, Color.BLUE) );
		visuals.put( "lord_lard", new LordLardVisual() );
		visuals.put( "example_box_50", new BoxVisual(50, 50, Color.RED) );
		visuals.put( "example_box_100", new BoxVisual(100, 100, Color.RED) );
	}
}