
public class Rank {

	private int score;
	private String name;
	private String memo;
	
	
	
	public Rank(int score) {
		super();
		this.score = score;
	}
	public Rank(int score, String name, String memo) {
		super();
		this.score = score;
		this.name = name;
		this.memo = memo;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
