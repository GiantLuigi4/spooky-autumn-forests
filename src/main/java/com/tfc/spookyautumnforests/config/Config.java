package com.tfc.spookyautumnforests.config;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;

public class Config {
	private static int nightmareCap = 600;
	private static int nightmareInterval = 100;
	private static double nightmareChance = 0.25;
	private static int nightmareDist = 32;
	private static int nightmareRange = 32;
	private static int nightmareFuelBurnTime = 18000;
	private static int nightmareLifetime = 64000;
	private static boolean respectGamemodes = true;
	
	public static void readAndWrite() {
		File f = new File("config/spooky_autumn_forests.properties");
		try {
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
				StringBuilder config = new StringBuilder();
				for (Field field : Config.class.getDeclaredFields()) {
					field.setAccessible(true);
					config.append(field.getName()).append(":").append(field.get(null)).append("\n");
				}
				FileWriter writer = new FileWriter(f);
				writer.write(config.toString());
				writer.close();
			}
			PropertiesReader reader = new PropertiesReader(f);
			for (String entry : reader.getEntries()) {
				Field field = Config.class.getDeclaredField(entry);
				field.setAccessible(true);
				Object val = reader.getValue(entry);
				if (field.getType().equals(int.class)) val = Integer.valueOf((String) val);
				else if (field.getType().equals(boolean.class)) val = Boolean.valueOf((String) val);
				else if (field.getType().equals(double.class)) val = Double.valueOf((String) val);
				else if (field.getType().equals(float.class)) val = Float.valueOf((String) val);
				else if (field.getType().equals(Long.class)) val = Long.valueOf((String) val);
				else if (field.getType().equals(Byte.class)) val = Byte.valueOf((String) val);
				field.set(null, val);
			}
		} catch (Throwable err) {
			err.printStackTrace();
			throw new RuntimeException(err);
		}
	}
	
	public static int getNightmareCap() {
		return nightmareCap;
	}
	
	public static int getNightmareInterval() {
		return nightmareInterval;
	}
	
	public static double getNightmareChance() {
		return nightmareChance;
	}
	
	public static int getNightmareDist() {
		return nightmareDist;
	}
	
	public static int getNightmareRange() {
		return nightmareRange;
	}
	
	public static int getNightmareFuelBurnTime() {
		return nightmareFuelBurnTime;
	}
	
	public static int getNightmareLifetime() {
		return nightmareLifetime;
	}
	
	public static boolean shouldRespectGamemodes() {
		return respectGamemodes;
	}
}
