package fr.kohen.alexandre.framework.systems.interfaces;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

public interface RenderSystem {
	public OrthographicCamera setCamera(Entity cameraEntity);
}
