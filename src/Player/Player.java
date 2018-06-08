package Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Player {
	
	private int highscore, meterRun, timePlayed, avatar, xp, hp, life, speed, star, death, coin;
	private float effect, level; 							//Effect: Ganze Zahl Art des Effekts, Komma Stärke | level: Level&Checkpoint 
	private String name;
	private static final String RES_LOC = "res/models/";
	
	public Player() {
		highscore = 0;
		meterRun = 0;
		timePlayed = 0;
		xp = 0;
		hp = 5;
		life = 1;
		speed = 1;
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
	
	public void saveData(String profil) {
		FileReader isr = null;
		//File objFile = new File(RES_LOC + profil + ".txt");
	}
	
	public void getData(Player player, String profil) {
		FileReader isr = null;
		File Data = new File(RES_LOC + profil + ".txt");
		try {
			isr = new FileReader(Data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(isr);
		String line;
		try {
			while(true) {
				line = reader.readLine();
				if(line.startsWith("hp")) {
					String[] currentLine = line.split(" ");
					int a = Integer.parseInt(currentLine[1]);
					player.setHp(a);
				}
				else if(line .startsWith("xp")) {
					String[] currentLine = line.split(" ");
					int a = Integer.parseInt(currentLine[1]);
					player.setXp(a);
				}
				else if(line .startsWith("avatar")) {
					String[] currentLine = line.split(" ");
					int a = Integer.parseInt(currentLine[1]);
					player.setAvatar(a);
				}
				else if(line .startsWith("highscore")) {
					String[] currentLine = line.split(" ");
					int a = Integer.parseInt(currentLine[1]);
					player.setHighscore(a);
				}
				else if(line .startsWith("meterRun")) {
					String[] currentLine = line.split(" ");
					int a = Integer.parseInt(currentLine[1]);
					player.setMeterRun(a);
				}
				else if(line .startsWith("timePlayed")) {
					String[] currentLine = line.split(" ");
					int a = Integer.parseInt(currentLine[1]);
					player.setTimePlayed(a);
				}
				else if(line .startsWith("stars")) {
					String[] currentLine = line.split(" ");
					int a = Integer.parseInt(currentLine[1]);
					player.setStar(a);
				}
				else if(line .startsWith("death")) {
					String[] currentLine = line.split(" ");
					int a = Integer.parseInt(currentLine[1]);
					player.setDeath(a);
				}
				else if(line .startsWith("coin")) {
					String[] currentLine = line.split(" ");
					int a = Integer.parseInt(currentLine[1]);
					player.setCoin(a);
				}
				else if(line .startsWith("level")) {
					String[] currentLine = line.split(" ");
					float a = Float.parseFloat(currentLine[1]);
					player.setLevel(a);
				}
				else if(line .startsWith("xp")) {
					String[] currentLine = line.split(" ");
					player.setName(currentLine[1]);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getHighscore() {
		return highscore;
	}
	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}
	public int getMeterRun() {
		return meterRun;
	}
	public void setMeterRun(int meterRun) {
		this.meterRun = meterRun;
	}
	public int getTimePlayed() {
		return timePlayed;
	}
	public void setTimePlayed(int timePlayed) {
		this.timePlayed = timePlayed;
	}
	public int getAvatar() {
		return avatar;
	}
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public int getDeath() {
		return death;
	}
	public void setDeath(int death) {
		this.death = death;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public float getEffect() {
		return effect;
	}
	public void setEffect(float effect) {
		this.effect = effect;
	}
	public float getLevel() {
		return level;
	}
	public void setLevel(float level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public static void main(String[] args) {
		
	}

}