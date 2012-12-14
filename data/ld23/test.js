/* global variables and function */
var world = 1; // current world


function start() { /* quick start for testing*/
	w1_started = true; 
	createEntity("goose"); 
	w1_goto_w2_2(); 
	w2_goto_w3_2(); 
	//setPosition("player", 3, 348, 2550);
	setPosition("player", 3, 700, 500);	
	w3_ted_answer = true;
	//w3_goto_w4_2(); 
	w1_intro_done = true;  
}

/* World 1 variables */
var w1_sunsets = 0;
var w1_started = false;
var w1_intro_done = false;
var w1_quest = false;
var w1_know_planet = false;
var w1_lose = false;
var w1_quest_complete = false;

/* World 1 functions*/
function w1_start() 	{ 
	if(w1_started == false) {
		w1_started = true; 
		log("w1 spawn");
		waitAndExec(5000, "w1_spawn_1", "createEntity(\"geeseFlight\");");
		waitAndExec(6000, "w1_spawn_2", "displayText(\"Hey!\", 512, 475, 3000);");
		waitAndExec(8000, "w1_spawn_3", "displayText(\"Wait!\", 512, 500, 3000);");
		waitAndExec(10000, "w1_spawn_4", "displayText(\"Stop!\", 512, 475, 3000);");
		waitAndExec(12000, "spawngoose", "createEntity(\"goose\");");
	} 
}

function w1_goto_w2() 	{ 
	fadeOut(500); 
	waitAndExec(1000, "timer_gotoworld2", "w1_goto_w2_2();");
	setFollow("npc_akka", false);
	}
function w1_goto_w2_2() 	{ 
	setPosition("player", 2, 300, 400);
	setPosition("npc_akka", 2, 998, 300); 
	world = 2;
	w1_quest = false;
	setTime(40);
	//removeEntity("npc_akka");
	//createEntity("akka");
	}
function w1_seeSunset() { log("test"); w1_sunsets = 1;  }
function w1_addGoose() 	{  }
function w1_lose_1()	{ 
	goTo("npc_akka", (530+getX("player"))%1280, 0);
	setState("no move state");
	waitAndExec(2500, "w1_lose_2", "w1_lose_2();");
}
function w1_lose_2()	{ 
	removeEntity("npc_akka");
	createEntity("geeseFlightout");
	setState("play state");
	waitAndExec(4000, "w1_lose_3", "w1_lose_3();");
}
function w1_lose_3()	{ 
	fadeOut(5000);
	waitAndExec(1000, "w1_lose_4", "w1_lose_4();");
	
}
function w1_lose_4()	{ 
	displayText("And that's the end of Max's adventures!", 250, 200, 5000);
	waitAndExec(4500, "end", "exit();");
}


/* World 2 variables */

/* World 2 functions */
function w2_goto_w3() 	{ 
	fadeOut(500); 
	waitAndExec(1000, "timer_gotoworld3", "w2_goto_w3_2();"); 
}
function w2_goto_w3_2() 	{ 
	setPosition("player", 3, 5800, 600);
	setPosition("npc_akka", 3, 5312, 500);
	setTime(73);
	world = 3;
	waitAndExec(500, "timer_gotoworld3", "w2_goto_w3_3();"); 
}
function w2_goto_w3_3() 	{ 
	removeEntity("rainbow");
	removeEntity("map_1");
	removeEntity("map_2");
}

/* World 3 variables */
var w3_ted_hey = false;
var w3_ted_intro = false;
var w3_ted_quest = false;
var w3_ted_quest2 = false;
var w3_ted_answer = false;
var w3_ted_down = false;
var w3_ted_down2 = false;
var w3_ted_down3 = false;


/* World 3 function */

/* World 4 function */
function w3_goto_w4() 	{ 
	fadeOut(500); 
	waitAndExec(1000, "timer_gotoworld3", "w3_goto_w4_2();"); 
}
function w3_goto_w4_2() 	{ 
	setPosition("player", 4, 100, 300);
	setPosition("npc_akka", 4, 200, 300);
	setTime(30);
	world = 4;
	if(w3_ted_answer == true) {
		setPosition("npc_ted", 4, -300, 300);
		setRandom("npc_ted", true);
		w3_ted_down = false;
		w3_ted_down2 = false;
		w3_ted_down3 = false;
		w3_ted_intro = true;
		w3_ted_hey = true;
		w3_ted_quest = true;
	}
	else {  removeEntity("npc_ted");  }
	waitAndExec(1000, "timer_gotoworld3", "w3_goto_w4_3();"); 
	createBgSheeps();
}
function w3_goto_w4_3() 	{  
	
	removeEntity("map_3");
}


