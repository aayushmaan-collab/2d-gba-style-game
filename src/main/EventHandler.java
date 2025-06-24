package main;

public class EventHandler 
{
	GamePanel gp;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp)
	{
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow)
		{
		eventRect[col][row] = new EventRect();
		eventRect[col][row].x = 23;
		eventRect[col][row].y = 23;
		eventRect[col][row].width = 2;
		eventRect[col][row].height = 2;
		eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
		eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
		
		col++;
		if(col == gp.maxWorldCol)
		{
			col = 0;
			row++;
		}
		}
	}
	public void checkEvent()
	{   
		// Check if the player character is away more than 1 tile from the last event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance > gp.tileSize)
		{
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) 
		{
			// DAMAGE PIT
			if(hit(17,25,"down") == true){damagePit(17,25,gp.dialogueState);}
			if(hit(23,19,"any") == true){damagePit(23,19,gp.dialogueState);}
			
			// TELEPORT
			if(hit(27,16,"right") == true){teleport(gp.dialogueState);}
	        
			// HEALING POOL
			if(hit(21,12,"up") == true){healingPool(21,12,gp.dialogueState);}
			if(hit(22,12,"up") == true){healingPool(22,12,gp.dialogueState);}
			if(hit(23,12,"up") == true){healingPool(23,12,gp.dialogueState);}
			if(hit(24,12,"up") == true){healingPool(24,12,gp.dialogueState);}
			if(hit(25,12,"up") == true){healingPool(25,12,gp.dialogueState);}
			if(hit(20,11,"right") == true){healingPool(20,11,gp.dialogueState);}
			if(hit(20,10,"right") == true){healingPool(20,10,gp.dialogueState);}
			if(hit(20,9,"right") == true){healingPool(20,9,gp.dialogueState);}
			if(hit(20,8,"right") == true){healingPool(20,8,gp.dialogueState);}
			if(hit(21,7,"down") == true){healingPool(21,7,gp.dialogueState);}
			if(hit(22,7,"down") == true){healingPool(22,7,gp.dialogueState);}
			if(hit(23,7,"down") == true){healingPool(23,7,gp.dialogueState);}
			if(hit(24,7,"down") == true){healingPool(24,7,gp.dialogueState);}
			if(hit(25,7,"down") == true){healingPool(25,7,gp.dialogueState);}
			if(hit(26,7,"left") == true){healingPool(26,7,gp.dialogueState);}
			if(hit(26,8,"left") == true){healingPool(26,8,gp.dialogueState);}
			if(hit(26,9,"left") == true){healingPool(26,9,gp.dialogueState);}
			if(hit(26,10,"left") == true){healingPool(26,10,gp.dialogueState);}
			if(hit(26,11,"left") == true){healingPool(26,11,gp.dialogueState);}





	
		}
		
		
	}
	
	public boolean hit(int col, int row, String reqDirection)
	{
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;
		
		if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false)
		{
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any"))
			{
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

		return hit;
	}
	public void teleport(int gameState)
	{
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Teleport!";
		gp.player.worldX = gp.tileSize * 37;
		gp.player.worldY = gp.tileSize * 10;
		
	}
	public void damagePit(int col, int row, int gameState)
	{
		gp.gameState = gameState;
		gp.playSE(6);
		gp.ui.currentDialogue = "You fall into a pit!";
		gp.player.life -= 1;
//		eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	public void healingPool(int col, int row, int gameState)
	{
		if(gp.keyH.enterPressed == true)
		{
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.playSE(2);
			gp.ui.currentDialogue = "You drink the water.\nYour life has been recovered";
			gp.player.life = gp.player.maxLife;
		}
	}

}
