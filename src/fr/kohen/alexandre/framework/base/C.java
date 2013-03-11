package fr.kohen.alexandre.framework.base;

public class C {
	public static final String STAND_DOWN 		= "stand_down";
	public static final String STAND_UP 		= "stand_up";
	public static final String STAND_RIGHT 		= "stand_right";
	public static final String STAND_LEFT 		= "stand_left";
	public static final String STAND_DOWN_LEFT	= "stand_down_left";
	public static final String STAND_DOWN_RIGHT = "stand_down_right";
	public static final String STAND_UP_LEFT 	= "stand_up_left";
	public static final String STAND_UP_RIGHT 	= "stand_up_right";
	public static final String WALK_DOWN 		= "walk_down";
	public static final String WALK_UP 			= "walk_up";
	public static final String WALK_RIGHT 		= "walk_right";
	public static final String WALK_LEFT 		= "walk_left";
	public static final String WALK_DOWN_LEFT 	= "walk_down_left";
	public static final String WALK_DOWN_RIGHT 	= "walk_down_right";
	public static final String WALK_UP_LEFT 	= "walk_up_left";
	public static final String WALK_UP_RIGHT 	= "walk_up_right";
	public static enum STATES { IDLE, MOVING, TALKING }
	public static enum SELECTION_STATE { INACTIVE, ACTIVE, SELECTED, HOVER }
	public static final int MENU_STATE = 1;
	public static final int GAME_STATE = 0;

	public static final String ACTION_ENTERGAMESTATE = "enter game state";
	public static final String ACTION_EXIT = "exit game";
	
	public static final String STATE_PLAYING	= "play state";
	public static final String STATE_NOMOVE		= "no move state";
	public static final String STATE_FROZEN		= "frozen state";
	public static final String STATE_FADE_OUT	= "fade out state";
	public static final String STATE_FADE_IN	= "fade in state";
	public static final String STATE_EXIT		= "exit";
	public static final String STATE_INIT		= "init state";

	public static final short	CATEGORY_DEFAULT	= 1;
	public static final short	CATEGORY_PLAYER		= 2;
	public static final short	CATEGORY_ENEMY		= 4;
	public static final short	CATEGORY_ACTOR		= 8;
	public static final short	CATEGORY_ACTOR2		= 16;
	public static final short	CATEGORY_SCENERY	= 32;
	public static final short	CATEGORY_FOREGROUND	= 64;
	public static final short	CATEGORY_BACKGROUND	= 128;
	public static final short	CATEGORY_ITEM		= 256;
	public static final short	CATEGORY_ITEM2		= 512;
	public static final short	CATEGORY_1			= 1024;
	public static final short	CATEGORY_2			= 2048;
	public static final short	CATEGORY_3			= 4096;
	public static final short	CATEGORY_4			= 8192;
	public static final short	CATEGORY_5			= 16384;

}
