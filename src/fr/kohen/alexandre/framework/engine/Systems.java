package fr.kohen.alexandre.framework.engine;

import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.utils.Bag;

/**
 * Used to retrieve easily a system when different versions of the same system can exist.
 * @author Alexandre
 *
 */
public class Systems {
	
	/**
	 * Returns a system implementing the selected interface. Use only in system initialization
	 * @param world
	 * @param type
	 * @return 
	 * @return EntitySystem
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> type, World world) {
		Bag<EntitySystem> systems = world.getSystemManager().getSystems();
		for( int i=0; i<systems.size();i++ ) {
			EntitySystem system = systems.get(i);
			Class<? extends EntitySystem> systemClass = system.getClass();
			
			do {
				for( Class<?> inter : systemClass.getInterfaces() ) {
					if( inter.equals(type) )
						return type.cast(system);
				}
				systemClass = (Class<? extends EntitySystem>) systemClass.getSuperclass();
			} while(systemClass != null);
			
		}
		return null;
	}

}
