package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.TextComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.TextDrawSystem;

public class DefaultTextSystem extends EntityProcessingSystem implements TextDrawSystem {
	private ComponentMapper<TextComponent> textMapper;
	private ComponentMapper<Transform> transformMapper;
	private CameraSystem cameraSystem;
	private BitmapFont font;


	@SuppressWarnings("unchecked")
	public DefaultTextSystem() {
		super( Aspect.getAspectForAll(TextComponent.class) );
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		
	}
	
	@Override
	public void initialize() {
		textMapper 		= ComponentMapper.getFor(TextComponent.class, world);
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		cameraSystem	= Systems.get(CameraSystem.class, world);
		
		if( cameraSystem == null ) throw new RuntimeException("A required system is not loaded");
	}	
	
	@Override
	protected void removed(Entity e) {
		ImmutableBag<Entity> cameras = cameraSystem.getCameras();
		
		for (int c = 0, s = cameras.size(); s > c; c++) {
			Entity camera = cameras.get(c);
			if( transformMapper.get(camera).mapId == transformMapper.get(e).mapId ) {
				cameraSystem.removeFromCamera(camera, e);
			}
		}
	}
	

	@Override
	protected void process(Entity e) {
		ImmutableBag<Entity> cameras = cameraSystem.getCameras();

		for (int c = 0, s = cameras.size(); s > c; c++) {
			Entity camera = cameras.get(c);
			if( transformMapper.get(camera).mapId == transformMapper.get(e).mapId ) {
				cameraSystem.addToCamera(camera, e);
			}
		}
	}

	@Override
	public void draw(Entity e, SpriteBatch batch) {
		font.draw(batch, textMapper.get(e).text, transformMapper.get(e).getPosition().x, transformMapper.get(e).getPosition().y);
	}

	@Override
	public boolean canProcess(Entity e) {
		return textMapper.has(e);
	}
	
}