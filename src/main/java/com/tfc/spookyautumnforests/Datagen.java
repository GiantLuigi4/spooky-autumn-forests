package com.tfc.spookyautumnforests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Datagen {
	private static final String file = System.getProperty("user.dir");
	
	public static void main(String[] args) throws IOException {
		StringBuilder lang = new StringBuilder("{");
		for (String[] sa : Blocks.regularBlocks) {
			genBlock(sa[0]);
			addLangEntry(sa[0],lang);
		}
		for (String[] sa : Blocks.logs) {
			genLogBlock(sa[0]);
			addLangEntry(sa[0],lang);
		}
		for (String[] sa : Blocks.doors) {
			genDoorBlock(sa[0]);
			addLangEntry(sa[0],lang);
		}
		for (String[] sa : Blocks.trapdoors) {
			genTrapDoorBlock(sa[0]);
			addLangEntry(sa[0],lang);
		}
		lang.append("§}");
		{
			File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\lang\\en_us.json");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileOutputStream stream = new FileOutputStream(f);
			stream.write(lang.toString().replace(",§","").getBytes());
			stream.close();
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
	
	private static void genDoorBlock(String name) {
		String[] types = new String[]  {
				"top",
				"top_rh",
				"bottom",
				"bottom_rh",
		};
		for (String s : types) {
			try {
				File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\models\\block\\" + name + "_" + s + ".json");
				if (!f.exists()) {
					f.getParentFile().mkdirs();
					f.createNewFile();
				}
				FileWriter writer = new FileWriter(f);
				writer.write(doorMDL.replace("%name%", name).replace("%type%", s));
				writer.close();
			} catch (Throwable ignored) {
			}
		}
		try {
			File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\models\\item\\" + name + ".json");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileWriter writer = new FileWriter(f);
			writer.write(normalItem.replace("%name%", name));
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
			writer.write(doorState.replace("%name%", name));
			writer.close();
		} catch (Throwable ignored) {
		}
	}
	
	private static void genTrapDoorBlock(String name) {
		String[] types = new String[]  {
				"top",
				"open",
				"bottom",
		};
		for (String s : types) {
			try {
				File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\models\\block\\" + name + "_" + s + ".json");
				if (!f.exists()) {
					f.getParentFile().mkdirs();
					f.createNewFile();
				}
				FileWriter writer = new FileWriter(f);
				writer.write(trapdoorMDL.replace("%name%", name).replace("%type%", s));
				writer.close();
			} catch (Throwable ignored) {
			}
		}
		try {
			File f = new File(file + "\\src\\main\\resources\\assets\\spooky_autumn_forests\\models\\item\\" + name + ".json");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileWriter writer = new FileWriter(f);
			writer.write(trapdoorItem.replace("%name%", name));
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
			writer.write(trapdoorState.replace("%name%", name));
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
	
	private static final String logMDL = "{\"parent\":\"block/cube_column\",\"textures\":{" +
			"\"end\":\"spooky_autumn_forests:block/%name%_top\"," +
			"\"side\":\"spooky_autumn_forests:block/%name%\"}}";
	
	private static final String logState = "{\"variants\":{" +
			"\"axis=y\":{\"model\":\"spooky_autumn_forests:block/%name%\"}," +
			"\"axis=z\":{\"model\":\"spooky_autumn_forests:block/%name%\",\"x\":90}," +
			"\"axis=x\":{\"model\":\"spooky_autumn_forests:block/%name%\",\"x\":90,\"y\":90}}}";
	
	private static final String blockMDL = "{\"parent\":\"block/cube_all\",\"textures\":{" +
			"\"all\":\"spooky_autumn_forests:block/%name%\"}}";
	
	private static final String blockState = "{\"variants\":{" +
			"\"\":{\"model\":\"spooky_autumn_forests:block/%name%\"}}}";
	
	private static final String blockItem = "{\"parent\":\"spooky_autumn_forests:block/%name%\"}";
	private static final String trapdoorItem = "{\"parent\":\"spooky_autumn_forests:block/%name%_bottom\"}";
	private static final String normalItem = "{\"parent\":\"item/generated\",\"textures\":{" +
			"\"layer0\":\"spooky_autumn_forests:item/%name%\"}}";
	
	private static final String doorMDL = "{\"parent\":\"block/door_%type%\",\"textures\":{" +
			"\"bottom\":\"spooky_autumn_forests:block/%name%_bottom\"," +
			"\"top\":\"spooky_autumn_forests:block/%name%_top\"}}";
	
	private static String doorState = "{\"variants\":{" +
			"\"facing=east,half=lower,hinge=left,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\"}," +
			"\"facing=south,half=lower,hinge=left,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\",\"y\":90}," +
			"\"facing=west,half=lower,hinge=left,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\",\"y\":180}," +
			"\"facing=north,half=lower,hinge=left,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\",\"y\":270}," +
			"\"facing=east,half=lower,hinge=right,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom_rh\"}," +
			"\"facing=south,half=lower,hinge=right,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom_rh\",\"y\":90}," +
			"\"facing=west,half=lower,hinge=right,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom_rh\",\"y\":180}," +
			"\"facing=north,half=lower,hinge=right,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom_rh\",\"y\":270}," +
			"\"facing=east,half=lower,hinge=left,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom_rh\",\"y\":90}," +
			"\"facing=south,half=lower,hinge=left,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom_rh\",\"y\":180}," +
			"\"facing=west,half=lower,hinge=left,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom_rh\",\"y\":270}," +
			"\"facing=north,half=lower,hinge=left,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom_rh\"}," +
			"\"facing=east,half=lower,hinge=right,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\",\"y\":270}," +
			"\"facing=south,half=lower,hinge=right,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\"}," +
			"\"facing=west,half=lower,hinge=right,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\",\"y\":90}," +
			"\"facing=north,half=lower,hinge=right,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\",\"y\":180}," +
			"\"facing=east,half=upper,hinge=left,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top\"}," +
			"\"facing=south,half=upper,hinge=left,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top\",\"y\":90}," +
			"\"facing=west,half=upper,hinge=left,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top\",\"y\":180}," +
			"\"facing=north,half=upper,hinge=left,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top\",\"y\":270}," +
			"\"facing=east,half=upper,hinge=right,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top_rh\"}," +
			"\"facing=south,half=upper,hinge=right,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top_rh\",\"y\":90}," +
			"\"facing=west,half=upper,hinge=right,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top_rh\",\"y\":180}," +
			"\"facing=north,half=upper,hinge=right,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top_rh\",\"y\":270}," +
			"\"facing=east,half=upper,hinge=left,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_top_rh\",\"y\":90}," +
			"\"facing=south,half=upper,hinge=left,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_top_rh\",\"y\":180}," +
			"\"facing=west,half=upper,hinge=left,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_top_rh\",\"y\":270}," +
			"\"facing=north,half=upper,hinge=left,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_top_rh\"}," +
			"\"facing=east,half=upper,hinge=right,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_top\",\"y\":270}," +
			"\"facing=south,half=upper,hinge=right,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_top\"}," +
			"\"facing=west,half=upper,hinge=right,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_top\",\"y\":90}," +
			"\"facing=north,half=upper,hinge=right,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_top\",\"y\":180}}}"
			;
	
	private static final String trapdoorMDL = "{\"parent\":\"block/template_trapdoor_%type%\"," +
			"\"textures\":{\"texture\":\"spooky_autumn_forests:block/%name%\"}}";
	
	private static final String trapdoorState = "{\"variants\":{" +
			"\"facing=north,half=bottom,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\"}," +
			"\"facing=south,half=bottom,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\"}," +
			"\"facing=east,half=bottom,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\"}," +
			"\"facing=west,half=bottom,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_bottom\"}," +
			"\"facing=north,half=top,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top\"}," +
			"\"facing=south,half=top,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top\"}," +
			"\"facing=east,half=top,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top\"}," +
			"\"facing=west,half=top,open=false\":{\"model\":\"spooky_autumn_forests:block/%name%_top\"}," +
			"\"facing=north,half=bottom,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_open\"}," +
			"\"facing=south,half=bottom,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_open\",\"y\":180}," +
			"\"facing=east,half=bottom,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_open\",\"y\":90}," +
			"\"facing=west,half=bottom,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_open\",\"y\":270}," +
			"\"facing=north,half=top,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_open\"}," +
			"\"facing=south,half=top,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_open\",\"y\":180}," +
			"\"facing=east,half=top,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_open\",\"y\":90}," +
			"\"facing=west,half=top,open=true\":{\"model\":\"spooky_autumn_forests:block/%name%_open\",\"y\":270}}}";
	
	private static void addLangEntry(String name, StringBuilder lang) {
		lang.append("\"block.spooky_autumn_forests.").append(name).append("\":\"");
		int index = 0;
		String[] strings = name.split("_");
		for (String s : strings) {
			lang.append(s.toUpperCase(), 0, 1).append(s.substring(1));
			if (index != strings.length) lang.append(" ");
			index++;
		}
		lang.append("\",");
	}
}
