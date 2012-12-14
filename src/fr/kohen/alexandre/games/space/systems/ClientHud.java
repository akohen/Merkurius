package fr.kohen.alexandre.games.space.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Destination;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.games.space.EntityFactorySpaceGame;

public class ClientHud extends EntityProcessingSystem {

	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Destination>	destinationMapper;
	private ComponentMapper<SpatialForm> 	spatialMapper;
	private SpatialForm destText;
	private SpatialForm hdgText;
	
	@SuppressWarnings("unchecked")
	public ClientHud() {
		super(Player.class);
	}

	@Override
	public void initialize() {
		this.transformMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		this.destinationMapper 	= new ComponentMapper<Destination>(Destination.class, world);
		this.spatialMapper 		= new ComponentMapper<SpatialForm>(SpatialForm.class, world);

		Entity e		= EntityFactorySpaceGame.createGuiText(world, 200, 450, "test");
		this.destText	= spatialMapper.get(e);
		e				= EntityFactorySpaceGame.createGuiText(world, 200, 400, "test");
		this.hdgText	= spatialMapper.get(e);
	}

	@Override
	protected void process(Entity e) {
		Transform 	transform 	= transformMapper.get(e);
		Destination destination	= destinationMapper.get(e);

		destText.getSpatial().setText("" + destination.getRotation() );
		hdgText.getSpatial().setText("" + transform.getRotation() );
		
	}
	

}
