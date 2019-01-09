import java.util.Scanner;

public class Pilot extends Thread {
	Scanner scanner = new Scanner(System.in);
	private int y;
	private int x;
	private String icon;
	private boolean run = true;
	private Input key = null;
	
	public Pilot(int y, int x, String icon) {
		super();
		this.y = y;
		this.x = x;
		this.icon = icon;
	}
	
	@Override
	public void run() {
		while(run) {
			int menu = scanner.nextInt();
			
			key.inputKey(menu);
		}
	}
	
	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public Input getKey() {
		return key;
	}

	public void setKey(Input key) {
		this.key = key;
	}

	public int moveLeft() {
		return this.x--;
	}
	public int moveUp() {
		return this.y--;
	}
	public int moveRight() {
		return this.x++;
	}
	public int moveDown() {
		return this.y++;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
}
