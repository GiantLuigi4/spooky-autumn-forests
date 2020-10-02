package com.tfc.spookyautumnforests;

import java.io.File;
import java.io.FileWriter;

public class Datagen {
	private static final String file = System.getProperty("user.dir");
	
	public static void main(String[] args) {
		for (String[] sa : Blocks.regularBlocks) {
			genBlock(sa[0]);
		}
		for (String[] sa : Blocks.logs) {
			genLogBlock(sa[0]);
		}
	}
	
	private static void genLogBlock(String name) {
		try {
			File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\models\\block\\" + name + ".json");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileWriter writer = new FileWriter(f);
			writer.write(logMDL.replace("%name%", name));
			writer.close();
		} catch (Throwable ignored) {
		}
		try {
			File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\models\\item\\" + name + ".json");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileWriter writer = new FileWriter(f);
			writer.write(blockItem.replace("%name%", name));
			writer.close();
		} catch (Throwable ignored) {
		}
		try {
			File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\blockstates\\" + name + ".json");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileWriter writer = new FileWriter(f);
			writer.write(logState.replace("%name%", name));
			writer.close();
		} catch (Throwable ignored) {
		}
	}
	
	private static void genBlock(String name) {
		try {
			File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\models\\block\\" + name + ".json");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileWriter writer = new FileWriter(f);
			writer.write(blockMDL.replace("%name%", name));
			writer.close();
		} catch (Throwable ignored) {
		}
		try {
			File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\models\\item\\" + name + ".json");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileWriter writer = new FileWriter(f);
			writer.write(blockItem.replace("%name%", name));
			writer.close();
		} catch (Throwable ignored) {
		}
		try {
			File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\blockstates\\" + name + ".json");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileWriter writer = new FileWriter(f);
			writer.write(blockState.replace("%name%", name));
			writer.close();
		} catch (Throwable ignored) {
		}
	}
	
	private static final String logMDL = "{" +
			"\"parent\":\"block/cube_column\"," +
			"\"textures\":{" +
			"\"end\":\"spooky_autumn_forests:block/%name%_top\"," +
			"\"side\":\"spooky_autumn_forests:block/%name%\"}}";
	
	private static final String logState = "{" +
			"\"variants\":{" +
			"\"axis=y\":{\"model\":\"spooky_autumn_forests:block/%name%\"}," +
			"\"axis=z\":{\"model\":\"spooky_autumn_forests:block/%name%\",\"x\":90}," +
			"\"axis=x\":{\"model\":\"spooky_autumn_forests:block/%name%\",\"x\":90,\"y\":90}}}";
	
	private static final String blockMDL = "{" +
			"\"parent\":\"block/cube_all\"," +
			"\"textures\":{" +
			"\"all\":\"spooky_autumn_forests:block/%name%\"}}";
	
	private static final String blockState = "{" +
			"\"variants\":{" +
			"\"\":{\"model\":\"spooky_autumn_forests:block/%name%\"}}}";
	
	private static final String blockItem = "{\"parent\":\"spooky_autumn_forests:block/%name%\"}";
}
