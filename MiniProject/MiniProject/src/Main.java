import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	static String SAVERANKFILE = "rank.txt";
	static int HEIGHT = 20;
	static int WIDTH = 50;
	static int RIGHTUP = 0;
	static int RIGHTDOWN = 1;
	static int LEFTDOWN = 2;
	static int LEFTUP = 3;
	static int LEFT = 4;
	static int RIGHT = 6;
	static int DOWN = 2;
	static int UP = 8;
	static int RANKLENGTH = 3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		int[][] map = new int[HEIGHT][WIDTH];
		Rank[] rank = new Rank[RANKLENGTH+1];
		File file = new File(SAVERANKFILE);
		rank[0] = new Rank(0);
		rank[1] = new Rank(0);
		rank[2] = new Rank(0);
		rank[3] = new Rank(0);
		int time = 0;
		
		if (file.exists()) {
			String str = FileManager.readFile(SAVERANKFILE);
			String[] tmpArr = str.split("&");
			String[] tmpData1 = tmpArr[0].split("#");
			String[] tmpData2 = tmpArr[1].split("#");
			String[] tmpData3 = tmpArr[2].split("#");
			
			rank[0] = new Rank(Integer.parseInt(tmpData1[2]), tmpData1[0], tmpData1[1]);
			rank[1] = new Rank(Integer.parseInt(tmpData2[2]), tmpData2[0], tmpData2[1]);
			rank[2] = new Rank(Integer.parseInt(tmpData3[2]), tmpData3[0], tmpData3[1]);
		}
		
		while(true) {
			
			printMenu(map,time,rank);
			System.out.println();
			int menu = scanner.nextInt();
			if (menu == 1) {
				
				Missile[] missile = new Missile[50];
				Pilot pilot = new Pilot(10, 23, "♠");
				ArrayList<Missile> TsMissile = new ArrayList<>();
				
				for (int i = 0; i < missile.length; i++ ) {
					missile[i] = new Missile((int)(Math.random()*map.length), 
							(int)(Math.random()*map[0].length), (int)(Math.random()*4), 0);
				}
				
				for (int i = 0; i < missile.length; i++ ) {
					missile[i].start();
				}
				
				pilot.start();
				pilot.setKey(new Input() {
					
					@Override
					public void inputKey(int key) {
						if (key == LEFT) {
							pilot.moveLeft();
						} else if (key == RIGHT) {
							pilot.moveRight();
						} else if (key == DOWN) {
							pilot.moveDown();
						} else if (key == UP) {
							pilot.moveUp();
						}
					}
				});
				
				time = 0;
				// 게임실행부분 ******************************
				while(isCrash(missile, pilot,TsMissile)) {
					
					printMap(map,missile,pilot,TsMissile);
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					missileMove(missile, map);
					time++;
					
					if (time%10 == 0) {
						makeTsMissile(TsMissile);
					}
					
				}
				
				for (int i = 0; i < missile.length; i++ ) {
					missile[i].setRun(false);
				}
				for (Missile obj : TsMissile) {
					obj.setRun(false);
				}
				TsMissile = null;
				pilot.setRun(false);
				
			} else if (menu == 2) {
				
				if ((time/2 < rank[2].getScore())) {
					refuseRecord(map);
					scanner.nextLine();
					scanner.nextLine();
					continue;
				}
				
				scanner.nextLine();
				System.out.print("닉네임 : ");
				String tmpName = scanner.nextLine();
				System.out.print("남길말 : ");
				String tmpMemo = scanner.nextLine();
				rank[2].setName(tmpName);
				rank[2].setMemo(tmpMemo);
				rank[2].setScore(time/2);
				
				bubble(rank);
				savaRank(rank);
				
			} else if (menu == 3) {
				
				printRank(rank,map);
				scanner.nextLine();
				scanner.nextLine();
				
			} else if (menu == 4) {
				
				file.delete();
				rank[0] = new Rank(0);
				rank[1] = new Rank(0);
				rank[2] = new Rank(0);
				
			}
		}
	}
	
	public static void refuseRecord(int[][]map) {
		
		for (int i = 0 ; i < map[0].length+2 ; i++) {
			System.out.print("-");
		}System.out.println();
		
		for (int i = 0; i < map.length/2; i++ ) {
			System.out.print("|");
			for (int j =0; j < map[i].length; j++ ) {
				if (i == 2 && j == 24) {
					System.out.print("8");
				}else if (i == 3 && j == 24) {
					System.out.print("▲");
				} else if (i == 4 && j == 20) {
					System.out.print("4");
				} else if (i == 4 && j == 22) {
					System.out.print("◀  ");
				} else if (i == 4 && j == 27) {
					System.out.print("▶ ");
				} else if (i == 4 && j == 29) {
					System.out.print("6 ");
				} else if (i == 5 && j == 24) {
					System.out.print("▼");
				} else if (i == 6 && j == 24) {
					System.out.print("2");
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("|");
			System.out.println();
		}
		
			System.out.println("\t      기록이 너무 낮습니다. 다시 도전하세요.");
			System.out.println("\t      <돌아가려면 Enter를 누르세요>");
		
		for (int i = 0; i < map.length/2-1; i++ ) {
			System.out.print("|");
			for (int j =0; j < map[i].length; j++ ) {
				System.out.print(" ");
			}
			System.out.print("|");
			System.out.println();
		}
		for (int i = 0 ; i < map[0].length+2 ; i++) {
			System.out.print("-");
		}
	}
		
	public static void savaRank(Rank[] rank) {
		String data = "";
		
		for (int i = 0 ; i < RANKLENGTH ; i++) {
			data += rank[i].getName() + "#" + rank[i].getMemo() + "#" + rank[i].getScore() + "&";
		}
		
		FileManager.writeFile(SAVERANKFILE, data);
	}
	
	public static void bubble(Rank[] rank) {
		
		for (int i = 0; i < RANKLENGTH; i++) {
            for (int j = i + 1; j < RANKLENGTH; j++) {
                if (rank[i].getScore() < rank[j].getScore()) {
                	
                    rank[3] = rank[i];
                    rank[i] = rank[j];
                    rank[j] = rank[3];
                }
            }
        }
	}
	
	public static void makeTsMissile(ArrayList<Missile> TsMissile) {
		int i = 0;
		int j = 0;
		for (i = 0; i < WIDTH; i++ ) {
			Missile tmpMi = new Missile(-(i), i, (int)(Math.random()*3), 2); //-(i)
			TsMissile.add(tmpMi);
			tmpMi.start();
		}
		for (j = 0; j < HEIGHT; j++ ) {
			Missile tmpMi = new Missile(j, WIDTH+j, (int)(Math.random()*3), 3); // WIDTH+j
			TsMissile.add(tmpMi);
			tmpMi.start();
		}
		for(i = WIDTH; i > 0; i-- ) {
			Missile tmpMi = new Missile(HEIGHT+(WIDTH-i), i, (int)(Math.random()*3), 1); //HEIGHT+(WIDTH-i)
			TsMissile.add(tmpMi);
			tmpMi.start();
		}
		for(j = HEIGHT; j > 0; j-- ) {
			Missile tmpMi = new Missile(j, 0-j, (int)(Math.random()*3), 4); //0-j
			TsMissile.add(tmpMi);
			tmpMi.start();
		}
	}
	
	public static boolean isCrash(Missile[] missile, Pilot pilot, ArrayList<Missile> TsMissile) {
		for (int i = 0; i < missile.length; i++ ) {
			if (missile[i].getY() == pilot.getY() && missile[i].getX() == pilot.getX()) {
				return false;
			}
		}
		for (Missile obj : TsMissile ) {
			if (obj.getY() == pilot.getY() && obj.getX() == pilot.getX()) {
				return false;
			}
		}
		return true;
	}
	
	public static Missile printTsMissile(ArrayList<Missile> TsMissile,int y, int x) {
		
		for (Missile obj : TsMissile) {
			if (y == obj.getY() && x == obj.getX()) {
				return obj;
			}
		}return null;
	}
	
	public static Missile printMissile(Missile[] missile, int y, int x) {
		
		for (int i = 0; i < missile.length; i++ ) {
			if (y == missile[i].getY() && x == missile[i].getX()) {
				return missile[i];
			}
		}
		return null;
	}
	
	public static void printMap(int[][] map,Missile[] missile, Pilot pilot, ArrayList<Missile> TsMissile) {
		for (int i = 0 ; i < map[0].length+2 ; i++) {
			System.out.print("-");
		}
		System.out.println();
		for (int i = 0 ; i < map.length ; i++) {
			System.out.print("|");
			for (int j = 0 ; j < map[i].length ; j++) {
				Missile tmpMissile = printMissile(missile, i, j);
				Missile tmpTs = printTsMissile(TsMissile, i, j);
				if (tmpMissile != null) {
					System.out.print("*");
				} else if (tmpTs != null) {
					System.out.print("*");
				} else if (i == pilot.getY() && j == pilot.getX()) {
					System.out.print(pilot.getIcon());
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("|");
			System.out.println();
		}
		for (int i = 0 ; i < map[0].length+2 ; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	public static void printRank(Rank[] rank, int map[][]) {
		
		for (int i = 0 ; i < map[0].length+2 ; i++) {
			System.out.print("-");
		}System.out.println();
		
		for (int i = 0; i < map.length/2-1; i++ ) {
			System.out.print("|");
			for (int j =0; j < map[i].length; j++ ) {
				System.out.print(" ");
			}
			System.out.print("|");
			System.out.println();
		}
		
		for (int i = 0; i < RANKLENGTH; i++ ) {
			if (rank[i] != null) {
				System.out.println("\t   " + (i+1) + "등 : " + rank[i].getName()
						+ " | 점수 : " + rank[i].getScore() + " | 한마디 : " + rank[i].getMemo());
			}
		} System.out.println("\t      <돌아가려면 Enter를 누르세요>");
		
		for (int i = 0; i < map.length/2-1; i++ ) {
			System.out.print("|");
			for (int j =0; j < map[i].length; j++ ) {
				System.out.print(" ");
			}
			System.out.print("|");
			System.out.println();
		}
		for (int i = 0 ; i < map[0].length+2 ; i++) {
			System.out.print("-");
		}
	}
	
	public static void printMenu(int[][] map, int time,Rank[] rank) {
		
		for (int i = 0 ; i < map[0].length+2 ; i++) {
			System.out.print("-");
		}System.out.println();
		
		for (int i = 0; i < map.length/2; i++ ) {
			System.out.print("|");
			for (int j =0; j < map[i].length; j++ ) {
				if (i == 2 && j == 24) {
					System.out.print("8");
				}else if (i == 3 && j == 24) {
					System.out.print("▲");
				} else if (i == 4 && j == 20) {
					System.out.print("4");
				} else if (i == 4 && j == 22) {
					System.out.print("◀  ");
				} else if (i == 4 && j == 27) {
					System.out.print("▶ ");
				} else if (i == 4 && j == 29) {
					System.out.print("6 ");
				} else if (i == 5 && j == 24) {
					System.out.print("▼");
				} else if (i == 6 && j == 24) {
					System.out.print("2");
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("|");
			System.out.println();
		}
		
		if (time == 0) {
			System.out.println("\t1.게임시작  2.점수기록  3.랭킹보기 4. 기록삭제");
		} else if ((time/2 > rank[2].getScore())) {
			System.out.println("\t             신기록 갱신! 점수를 기록하세요.");
			System.out.println("\t\t        비행시간 : " + time/2 + "초");
			System.out.println("\t1. 다시시작 2. 점수기록 3. 랭킹보기 4. 기록삭제");
		} else {
		System.out.println("\t\t        비행시간 : " + time/2 + "초");
		System.out.println("\t1. 다시시작 2. 점수기록 3. 랭킹보기 4. 기록삭제");
		} 
		
		for (int i = 0; i < map.length/2-1; i++ ) {
			System.out.print("|");
			for (int j =0; j < map[i].length; j++ ) {
				System.out.print(" ");
			}
			System.out.print("|");
			System.out.println();
		}
		for (int i = 0 ; i < map[0].length+2 ; i++) {
			System.out.print("-");
		}
	}
	
	public static void missileMove(Missile[] missile, int[][] map) {
		
		for (int i = 0; i < missile.length; i++ ) {
			
			if (missile[i].getDirection() == RIGHTUP) {
				if (missile[i].getX() == map[0].length-1 && missile[i].getY() == 0) {
					missile[i].setDirection(LEFTDOWN);
				} else if (missile[i].getY() == 0) {
					missile[i].setDirection(RIGHTDOWN);
				} else if (missile[i].getX() == map[0].length-1) {
					missile[i].setDirection(LEFTUP);
				}
			} else if (missile[i].getDirection() == RIGHTDOWN) {
				if (missile[i].getX() == map[0].length-1 && missile[i].getY() == map.length - 1) {
					missile[i].setDirection(LEFTUP);
				} else if (missile[i].getX() == map[0].length-1) {
					missile[i].setDirection(LEFTDOWN);
				} else if (missile[i].getY() == map.length-1) {
					missile[i].setDirection(RIGHTUP);
				}
			} else if (missile[i].getDirection() == LEFTDOWN) {
				if (missile[i].getX() == 0 && missile[i].getY() == map.length - 1) {
					missile[i].setDirection(RIGHTUP);
				} else if (missile[i].getY() == map.length - 1) {
					missile[i].setDirection(LEFTUP);
				} else if (missile[i].getX() == 0) {
					missile[i].setDirection(RIGHTDOWN);
				}
			} else if (missile[i].getDirection() == LEFTUP) {
				if (missile[i].getX() == 0 && missile[i].getY() == 0) {
					missile[i].setDirection(RIGHTDOWN);
				} else if (missile[i].getX() == 0) {
					missile[i].setDirection(RIGHTUP);
				} else if (missile[i].getY() == 0) {
					missile[i].setDirection(LEFTDOWN);
				}  
			}
		}
	}

}
