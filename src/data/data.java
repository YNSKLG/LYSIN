package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import entities.Player;

public class data {

	private static final String RES_LOC = "res/models/";
	
	public void getData(Player player, String FileName) {
		FileReader isr = null;
		File File = new File(RES_LOC + FileName + ".obj");
		
		try {
			isr = new FileReader(File);
		} catch (FileNotFoundException e) {
			System.err.println("File not found in res; don't use any extention");
		}
		BufferedReader reader = new BufferedReader(isr);
		String line;
		try {
			while(true) {
				line = reader.readLine();
				if(line.startsWith("highscore")) {
					String[] currentLine = line.split("");
					player.setHighscore(Integer.parseInt(currentLine[1]));
					
				}else if(line.startsWith("meterRun")) {
					String[] currentLine = line.split("");
					player.setMeterRun(Integer.parseInt(currentLine[1]));
					
				}else if(line.startsWith("timePlayed")) {
					String[] currentLine = line.split("");
					player.setTimePlayed(Integer.parseInt(currentLine[1]));
					
				}else if(line.startsWith("avatar")) {
					String[] currentLine = line.split("");
					player.setAvatar(Integer.parseInt(currentLine[1]));
					
				}else if(line.startsWith("xp")) {
					String[] currentLine = line.split("");
					player.setXp(Integer.parseInt(currentLine[1]));
					
				}else if(line.startsWith("hp")) {
					String[] currentLine = line.split("");
					player.setHp(Integer.parseInt(currentLine[1]));
				
				}else if(line.startsWith("stars")) {
					String[] currentLine = line.split("");
					player.setStar(Integer.parseInt(currentLine[1]));
				
				}else if(line.startsWith("death")) {
					String[] currentLine = line.split("");
					player.setDeath(Integer.parseInt(currentLine[1]));
				
				}else if(line.startsWith("coin")) {
					String[] currentLine = line.split("");
					player.setCoin(Integer.parseInt(currentLine[1]));
				
				}else if(line.startsWith("name")) {
					String[] currentLine = line.split("");
					player.setName(currentLine[1]);
				}
				
			}
			
		}catch (IOException e) {
			System.err.println("Error reading the file");
		}
	}
	
	public void saveData(Player player, String FileName) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(RES_LOC + FileName + ".obj");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		bw = new BufferedWriter(fw);

		try {
			bw.write("highscore " + player.getHighscore() + "\n");
			bw.write("meterRun " + player.getMeterRun() + "\n");
			bw.write("timeplayed " + player.getTimePlayed() + "\n");
			bw.write("avatar " + player.getAvatar() + "\n");
			bw.write("xp " + player.getXp() + "\n");
			bw.write("hp " + player.getHp() + "\n");
			bw.write("star " + player.getStar() + "\n");
			bw.write("death " + player.getDeath() + "\n");
			bw.write("coin " + player.getCoin() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

