package fr.kohen.alexandre.wip.mas;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import fr.kohen.alexandre.framework.components.MapComponent;

public class MasSystem extends EntityProcessingSystem {
	protected ComponentMapper<MapComponent> mapMapper;
	
	@SuppressWarnings("unchecked")
	public MasSystem() {
		super(Aspect.getAspectForAll(MapComponent.class));
		
	}
	
	@Override
	public void initialize() {
		mapMapper 	= ComponentMapper.getFor(MapComponent.class, world);
	}

	@Override
	protected void process(Entity e) {
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();
		
		MapLayer layer = mapMapper.get(e).tmap.getLayers().getLayer(0);
		layer.getName();
		//mapMapper.get(e).tmap.getLayers().getLayersByType(null);
		
		OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(mapMapper.get(e).tmap, 1 / 20f);
		renderer.setView(camera);
		renderer.render();

	}

}
