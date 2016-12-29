package root.settings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class SettingsManager {
	private static Settings sSettings;
	private static final String PATH = "settings.conf";
	
	private synchronized static void loadSettings(){
		Scanner scanner = null;
		for(int i = 0; i < 2 && scanner == null; ++i, createDefaultSettings(i)){
			try{
				scanner = new Scanner(new File(PATH));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(scanner != null){
			sSettings = new Settings();
			//TODO: init settings
		}else{
			new SettingsNotFoundExeption("Settings not found, please contact the system am=dministrator, if he can't help contact the game makers!");
		}
	}
	
	private synchronized static void createDefaultSettings(int i) {
		if(i == 1){
			Path file = Paths.get(PATH);
			try {
				Files.write(file, Settings.getDefault());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static synchronized Settings getCopyOfSettings(){
		if(sSettings == null){
			loadSettings();
		}
		return sSettings.clone();
	}
	
	public static synchronized void modyfiSettings(Settings s){
		sSettings = s.clone();
		Path file = Paths.get(PATH);
		try {
			Files.write(file, Settings.getDefault());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
