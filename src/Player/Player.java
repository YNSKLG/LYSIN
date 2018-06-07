package Player;

public class Player {
	
	private int highscore, meterRun, timePlayed, avatar, xp, hp, life, speed, size, star, death, coin;
	private float effect, level; 							//Effect: Ganze Zahl Art des Effekts, Komma Stärke | level: Level&Checkpoint 
	private String name;
	
	public Player() {
		highscore = 0;
		meterRun = 0;
		timePlayed = 0;
		xp = 0;
		hp = 5;
		life = 1;
		speed = 1;
		size = 1;
		star = 0;
		effect = 0;
		level = 0;
		death = 0;
		avatar = 0;
		coin = 0;
		name = "Standard" ;
	}
	
	public Player(String name, int avatar) {
		highscore = 0;
		meterRun = 0;
		timePlayed = 0;
		xp = 0;
		hp = 5;
		life = 1;
		speed = 1;
		size = 1;
		star = 0;
		effect = 0;
		level = 0;
		death = 0;
		coin = 0;
		this.avatar = avatar;
		this.name = name;
	}
	
	public int changeHp(int hp, int change) {
		hp =+ change;
		return hp;
	}
	public int changeXp(int xp, int change) {
		xp =+ change;
		return xp;
	}
	public int changeLife(int life, int change) {
		life =+ change;
		return life;
	}
	public int changeDeath(int death, int change) {
		death =+ change;
		return death;
	}
	public int changeStar(int star, int change) {
		star =+ change;
		return star;
	}
	public int changeMeterRun(int meter, int change) {
		meter =+ change;
		return meter;
	}
	public int changeTimePlayed(int time, int change) {
		time =+ change;
		return time;
	}
	public int changeCoin(int coin, int change) {
		coin =+ change;
		return coin;
	}
	
	public static void main(String[] args) {
		
	}

}