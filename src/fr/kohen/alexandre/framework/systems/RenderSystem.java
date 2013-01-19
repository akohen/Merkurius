package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Visual;

public class RenderSystem extends EntityProcessingSystem {
	protected ComponentMapper<Visual> 		visualMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected OrthographicCamera 			camera;
	protected SpriteBatch 					batch;
	protected Sprite 						sprite;
	
	
	@SuppressWarnings("unchecked")
	public RenderSystem() {
		super(Aspect.getAspectForAll(Transform.class, Visual.class));
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera 			= new OrthographicCamera(w, h);
		batch 			= new SpriteBatch();
	}
	
	@Override
	public void initialize() {
		visualMapper 	= ComponentMapper.getFor(Visual.class, world);
		transformMapper = ComponentMapper.getFor(Transform.class, world);
	}

	
	@Override
	protected void begin() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}
	
	
	@Override
	protected void process(Entity e) {
		sprite = visualMapper.get(e).getSprite();
		
		sprite.setPosition(
				transformMapper.get(e).getX() - sprite.getOriginX(), 
				transformMapper.get(e).getY() - sprite.getOriginY()
			);
		sprite.draw(batch);
	}

	
	@Override
	protected void end() {
		batch.end();
	}
}
