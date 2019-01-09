
public class Missile extends Thread {

	static int RIGHTUP = 0;
	static int RIGHTDOWN = 1;
	static int LEFTDOWN = 2;
	static int LEFTUP = 3;
	
	private int y;
	private int x;
	private int direction;
	private int moveSelect;
	private boolean run = true;
	
	public Missile(int y, int x, int direction, int moveSelect) {
		super();
		this.y = y;
		this.x = x;
		this.direction = direction;
		this.moveSelect = moveSelect;
	}
	
	public Missile(int y, int x) {
		super();
		this.y = y;
		this.x = x;
		this.direction = 0;
	}
	
	@Override
	public void run() {
		
		while(run) {
			moveSelect();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void moveSelect() {
		
		if (this.moveSelect == 0) {
			move();
		} else if (this.moveSelect ==1) {
			moveUp();
		} else if (this.moveSelect == 2) {
			moveDown();
		} else if (this.moveSelect == 3) {
			moveLeft();
		} else if (this.moveSelect == 4) {
			moveRight();
		}
	}
	
	public void moveDown() {
		if (this.direction == 0) {
			y++;
			x++;
		} else if (this.direction == 1) {
			x--;
			y++;
		} else if (this.direction == 2) {
			y++;
		}
	}
	
	public void moveUp() {
		if (this.direction == 0) {
			y--;
			x++;
		} else if (this.direction == 1) {
			y--;
			x--;
		} else if (this.direction == 2) {
			y--;
		}
	}
	
	public void moveLeft() {
		if (this.direction == 0) {
			x--;
			y++;
		} else if (this.direction == 1) {
			y--;
			x--;
		} else if (this.direction == 2) {
			x--;
		}
	}
	
	public void moveRight() {
		if (this.direction == 0) {
			y--;
			x++;
		} else if (this.direction == 1) {
			y++;
			x++;
		} else if (this.direction == 2) {
			x++;
		}
	}
	
	public void move() {
		if (this.direction == RIGHTUP) {
			y--;
			x++;
		} else if (this.direction == RIGHTDOWN) {
			y++;
			x++;
		} else if (this.direction == LEFTDOWN) {
			x--;
			y++;
		} else if (this.direction == LEFTUP) {
			y--;
			x--;
		}
	}
	
	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	
}
