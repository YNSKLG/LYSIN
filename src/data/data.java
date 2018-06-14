package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import entities.Player;

public class data {

	private static final String RES_LOC = "res/models/";
	
	public void getData(Player player, String objFileName) {
		FileReader isr = null;
		File objFile = new File(RES_LOC + objFileName + ".obj");
		
		try {
			isr = new FileReader(objFile);
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
				}else if (line.startsWith("meterRun")) {
					String[] currentLine = line.split("");
					player.setMeterRun(Integer.parseInt(currentLine[1]));
				}else if (line.startsWith("timePlayed")) {
					String[] currentLine = line.split("");
					player.setTimePlayed(Integer.parseInt(currentLine[1]));
				}else if (line.startsWith("avatar")) {
					String[] currentLine = line.split("");
					player.setAvatar(Integer.parseInt(currentLine[1]));
				}else if (line.startsWith("xp")) {
					String[] currentLine = line.split("");
					player.setXp(Integer.parseInt(currentLine[1]));
				}
				
			}
			
		}catch (IOException e) {
			System.err.println("Error reading the file");
		}
	}
}

