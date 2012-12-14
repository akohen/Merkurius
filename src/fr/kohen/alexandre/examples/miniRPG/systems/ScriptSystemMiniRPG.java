package fr.kohen.alexandre.examples.miniRPG.systems;

import org.newdawn.slick.GameContainer;

import com.artemis.ComponentMapper;
import com.artemis.Entity;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.resources.ResourceManager;
import fr.kohen.alexandre.framework.systems.base.ScriptSystemBase;

public class ScriptSystemMiniRPG extends ScriptSystemBase {

	private 	ComponentMapper<Transform> 		transformMapper;
    
	public ScriptSystemMiniRPG(GameContainer container) {
		super(container);
		this.scriptClass = ScriptMiniRPG.class;
	}
	
	public void initialize() {
		super.initialize();
		transformMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		loadFile(ResourceManager.getFile("script"));
	}

	
	public class ScriptMiniRPG extends ScriptProxy {

		private static final long serialVersionUID = 7974142082092886609L;
		public ScriptMiniRPG() {super();}
		
		public void setPosition(String name, int mapId, int x, int y) {
    		Entity entity = world.getTagManager().getEntity(name);
    		if( entity != null ) {
	    		transformMapper.get(entity).setLocation(x,y);
	    		transformMapper.get(entity).setMapId(mapId);
    		}
    		else log("Entity not found");
    	}
		
		
	}
	
}
