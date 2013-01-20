package fr.kohen.alexandre.framework.components;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;

public class CameraComponent extends Component {
	public Vector2 size;
	public Vector2 position;
	public float zoom;
	public float rotation;
	public String name;
	public List<Entity> entities;
	
	public CameraComponent(int width, int height, String name) {
		this.size 		= new Vector2(width,height);
		this.position 	= new Vector2(0,0);
		this.zoom 		= 1;
		this.rotation 	= 0;
		this.name		= name;
		this.entities	= new ArrayList<Entity>();
	}
	
	public CameraComponent(int width, int height, int posx, int posy, float zoom, float rotation, String name) {
		this.size 		= new Vector2( width, height );
		this.position 	= new Vector2( posx, posy );
		this.zoom 		= zoom;
		this.rotation 	= rotation;
		this.name		= name;
		this.entities	= new ArrayList<Entity>();
	}

}
