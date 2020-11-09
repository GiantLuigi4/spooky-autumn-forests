package com.tfc.spookyautumnforests.API;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.tfc.spookyautumnforests.SpookyAutumnForests;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SEntityEquipmentPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
	
	public static void handleSpawns(PlayerEntity entity) {
		if (entity instanceof ServerPlayerEntity) {
			World world = entity.getEntityWorld();
			
			if ((world.getWorldInfo().getGameTime() % 100) == 0) {
				if (world.getRandom().nextDouble() > 0.25) {
					BlockPos pos = new BlockPos(entity.getPosition());
					pos = pos.add((world.getRandom().nextInt(32) + 32) * (world.getRandom().nextBoolean() ? -1 : 1), 0, (world.getRandom().nextInt(32) + 32) * (world.getRandom().nextBoolean() ? -1 : 1));
					pos = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
					EntityType<?>[] entities = new EntityType[]{
							EntityType.ZOMBIE,
							EntityType.SKELETON,
							EntityType.SPIDER,
							EntityType.CREEPER,
					};
					
					for (int i = 0; i < world.getRandom().nextInt(3) + 1; i++) {
						EntityType<?> type = entities[world.getRandom().nextInt(entities.length - 1)];
						Entity e = type.create(entity.world);
						
						if (e != null) {
							e.setPosition(pos.getX(), pos.getY(), pos.getZ());
							
							if (e instanceof SkeletonEntity) {
								e.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.SKELETON_SKULL));
								e.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
							} else if (e instanceof ZombieEntity)
								e.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.ZOMBIE_HEAD));
							else if (e instanceof CreeperEntity)
								e.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.CREEPER_HEAD));
							
							e.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(SpookyAutumnForests.nightmare_fuel, new Random().nextInt(3)));
							
							e.addTag("nightmare_mob");
							
							((LivingEntity) e).setHealth(1);
							
							((ServerPlayerEntity) entity).connection.sendPacket(e.createSpawnPacket());
							Nightmare.addNightmareEntity((PlayerEntity) entity, e);
							
							List<Pair<EquipmentSlotType, ItemStack>> p_i241270_2_ = ImmutableList.of(
									Pair.of(EquipmentSlotType.FEET, new ItemStack(SpookyAutumnForests.nightmare_fuel)),
									Pair.of(EquipmentSlotType.MAINHAND, ((LivingEntity) e).getHeldItem(Hand.MAIN_HAND))
							);
							
							((ServerPlayerEntity) entity).connection.sendPacket(new SEntityEquipmentPacket(e.getEntityId(), p_i241270_2_));
						}
					}
				}
			}
		}
	}
	
	public static void handleSpawns(PlayerEntity entity, EntityType<?>[] spawns) {
		if (entity instanceof ServerPlayerEntity) {
			World world = entity.getEntityWorld();
			
			if ((world.getWorldInfo().getGameTime() % 100) == 0) {
				if (world.getRandom().nextDouble() > 0.25) {
					BlockPos pos = new BlockPos(entity.getPosition());
					pos = pos.add((world.getRandom().nextInt(32) + 32) * (world.getRandom().nextBoolean() ? -1 : 1), 0, (world.getRandom().nextInt(32) + 32) * (world.getRandom().nextBoolean() ? -1 : 1));
					pos = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
					
					for (int i = 0; i < world.getRandom().nextInt(3) + 1; i++) {
						EntityType<?> type = spawns[world.getRandom().nextInt(spawns.length - 1)];
						Entity e = type.create(entity.world);
						
						if (e != null) {
							e.setPosition(pos.getX(), pos.getY(), pos.getZ());
							
							if (e instanceof SkeletonEntity) {
								e.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.SKELETON_SKULL));
								e.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
							} else if (e instanceof ZombieEntity)
								e.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.ZOMBIE_HEAD));
							else if (e instanceof CreeperEntity)
								e.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.CREEPER_HEAD));
							
							e.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(SpookyAutumnForests.nightmare_fuel, new Random().nextInt(3)));
							
							e.addTag("nightmare_mob");
							
							((LivingEntity) e).setHealth(1);
							
							((ServerPlayerEntity) entity).connection.sendPacket(e.createSpawnPacket());
							Nightmare.addNightmareEntity((PlayerEntity) entity, e);
							
							List<Pair<EquipmentSlotType, ItemStack>> p_i241270_2_ = ImmutableList.of(
									Pair.of(EquipmentSlotType.FEET, new ItemStack(SpookyAutumnForests.nightmare_fuel)),
									Pair.of(EquipmentSlotType.MAINHAND, ((LivingEntity) e).getHeldItem(Hand.MAIN_HAND))
							);
							
							((ServerPlayerEntity) entity).connection.sendPacket(new SEntityEquipmentPacket(e.getEntityId(), p_i241270_2_));
						}
					}
				}
			}
		}
	}
}
