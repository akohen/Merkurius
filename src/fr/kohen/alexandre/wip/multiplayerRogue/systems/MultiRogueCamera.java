package fr.kohen.alexandre.wip.multiplayerRogue.systems;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;

public class MultiRogueCamera extends DefaultCameraSystem {
	
	private ChatSystem chatSystem;

	protected void initialize() {
		super.initialize();
		chatSystem = Systems.get(ChatSystem.class, world);
	}
	
	protected void processCamera(Entity camera) {
		super.processCamera(camera);
		if( cameraMapper.get(camera).name.startsWith("cameraChatBox") ) {
			transformMapper.get(camera).position.y = chatSystem.getCameraShift() + 60;
		}
	}
}
