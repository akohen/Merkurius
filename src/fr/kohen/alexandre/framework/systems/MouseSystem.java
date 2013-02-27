package fr.kohen.alexandre.framework.systems;

import java.awt.geom.AffineTransform;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;

import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.CameraComponent;
import fr.kohen.alexandre.framework.components.Mouse;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.systems.interfaces.ICameraSystem;

public class MouseSystem extends EntityProcessingSystem {
	protected ICameraSystem 					cameraSystem;
	protected ComponentMapper<Transform> 		transformMapper;
	protected ComponentMapper<CameraComponent> 	cameraMapper;
	protected ComponentMapper<Mouse> mouseMapper;

	@SuppressWarnings("unchecked")
	public MouseSystem() {
		super( Aspect.getAspectForAll(CameraComponent.class) );
	}

	@Override
	public void initialize() {
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		cameraMapper 	= ComponentMapper.getFor(CameraComponent.class, world);
		mouseMapper 	= ComponentMapper.getFor(Mouse.class, world);
		cameraSystem	= Systems.get(ICameraSystem.class, world);
	}
	
	@Override
	protected void begin() {
	}

	@Override
	protected void process(Entity camera) {

		Entity mouse;
		double[] pt = {
				Gdx.input.getX() - cameraMapper.get(camera).position.x + transformMapper.get(camera).x - Gdx.graphics.getWidth()/2, 
				- Gdx.input.getY() - cameraMapper.get(camera).position.y + transformMapper.get(camera).y + Gdx.graphics.getHeight()/2
			};
		AffineTransform.getRotateInstance(
				Math.toRadians(cameraMapper.get(camera).rotation+transformMapper.get(camera).rotation), 
				transformMapper.get(camera).x, 
				transformMapper.get(camera).y
			).transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
		float newX = (float) pt[0];
		float newY = (float) pt[1];
		
		// Creating a cameraBox object to check if the mouse in the camera viewport
		Polygon cameraBox = new Polygon( new float[]{0,0,
				0, cameraMapper.get(camera).size.y,
				cameraMapper.get(camera).size.x, cameraMapper.get(camera).size.y,
				cameraMapper.get(camera).size.x, 0
			} );
		cameraBox.translate(
				transformMapper.get(camera).x - cameraMapper.get(camera).size.x/2, 
				transformMapper.get(camera).y - cameraMapper.get(camera).size.y/2);
		cameraBox.rotate( -transformMapper.get(camera).rotation );
		
		Transform mouseTransform = new Transform(transformMapper.get(camera).getMapId(), newX, newY);

		// Creating and selecting the mouse entity for the active camera
		if( cameraMapper.get(camera).mouse == null ) {
			mouse = EntityFactory.addMouse( world, mouseTransform, camera );
			cameraMapper.get(camera).mouse = mouse;
		} else {
			mouse = cameraMapper.get(camera).mouse;
		}
		
		// Updating the mouse entity position
		if( cameraBox.contains(newX, newY) ) {
			mouse = cameraMapper.get(camera).mouse;
			transformMapper.get(mouse).setMapId( transformMapper.get(camera).getMapId() );
			transformMapper.get(mouse).x = newX;
			transformMapper.get(mouse).y = newY;
			mouse.enable();
		} else {
			cameraMapper.get(camera).mouse.disable();
		}
		
		// Updating mouse input
		if ( Gdx.input.isButtonPressed(0) ) {
			mouseMapper.get(mouse).clicked = true;
		} else {
			mouseMapper.get(mouse).clicked = false;
		}
		
	}

}
