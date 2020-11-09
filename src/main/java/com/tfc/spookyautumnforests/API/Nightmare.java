package com.tfc.spookyautumnforests.API;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is handled server side, not client side.
 * Do not use this on the client
 */
public class Nightmare {
	public static final HashMap<Integer, ArrayList<Entity>> nightmares = new HashMap<>();
	
	/**
	 * Adds a nightmare entity for a player
	 *
	 * @param player    the target
	 * @param nightmare the entity
	 */
	public static void addNightmareEntity(PlayerEntity player, Entity nightmare) {
		ArrayList<Entity> nightmaresForThisPlayer = getNightmaresForPlayer(player);
		nightmaresForThisPlayer.add(nightmare);
	}
	
	/**
	 * Deletes a nightmare entity for a player
	 *
	 * @param player    the target
	 * @param nightmare the entity
	 */
	public static void removeNightmareEntity(PlayerEntity player, int nightmare) {
		ArrayList<Entity> nightmaresForThisPlayer = getNightmaresForPlayer(player);
		for (Entity e : nightmaresForThisPlayer) {
			if (e.getEntityId() == nightmare) {
				nightmaresForThisPlayer.remove(e);
				return;
			}
		}
	}
	
	/**
	 * Damages a nightmare entity
	 *
	 * @param player the player
	 * @param entity the nightmare's id
	 */
	public static void damage(PlayerEntity player, int entity) {
		ArrayList<Entity> nightmaresForThisPlayer = getNightmaresForPlayer(player);
		for (Entity e : nightmaresForThisPlayer) {
			if (e.getEntityId() == entity) {
				e.hitByEntity(player);
			}
		}
	}
	
	/**
	 * Damages a nightmare entity
	 *
	 * @param entity the nightmare
	 * @param amount the amount to deal
	 */
	public static void damage(int entity, int amount) {
		ArrayList<Entity> allNightmares = getAllNightmares();
		for (Entity e : allNightmares) {
			if (e.getEntityId() == entity) {
				((LivingEntity) e).heal(-amount);
			}
		}
	}
	
	/**
	 * gets the list of nightmares for the given player entity
	 *
	 * @param player the given player
	 * @return the list of nightmares
	 */
	public static ArrayList<Entity> getNightmaresForPlayer(PlayerEntity player) {
		ArrayList<Entity> nightmaresForThisPlayer;
		
		if (nightmares.containsKey(player.getEntityId()))
			nightmaresForThisPlayer = nightmares.get(player.getEntityId());
		else {
			nightmaresForThisPlayer = new ArrayList<>();
			nightmares.put(player.getEntityId(), nightmaresForThisPlayer);
		}
		
		return nightmaresForThisPlayer;
	}
	
	/**
	 * gets the list of nightmares for the given player id
	 *
	 * @param player the id of the player
	 * @return the list of nightmares
	 */
	public static ArrayList<Entity> getNightmaresForPlayer(int player) {
		ArrayList<Entity> nightmaresForThisPlayer;
		
		if (nightmares.containsKey(player))
			nightmaresForThisPlayer = nightmares.get(player);
		else {
			nightmaresForThisPlayer = new ArrayList<>();
			nightmares.put(player, nightmaresForThisPlayer);
		}
		
		return nightmaresForThisPlayer;
	}
	
	public static ArrayList<Entity> getAllNightmares() {
		ArrayList<Entity> allNightmares = new ArrayList<>();
		
		for (ArrayList<Entity> nightmares : nightmares.values()) allNightmares.addAll(nightmares);
		
		return allNightmares;
	}
}
