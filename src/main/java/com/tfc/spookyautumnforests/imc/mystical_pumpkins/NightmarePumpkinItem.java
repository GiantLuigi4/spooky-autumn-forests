package com.tfc.spookyautumnforests.imc.mystical_pumpkins;

import blueduck.mysticalpumpkins.block.MysticalPumpkinItem;
import blueduck.mysticalpumpkins.registry.RegisterHandler;
import com.tfc.spookyautumnforests.API.Nightmare;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NightmarePumpkinItem extends MysticalPumpkinItem {
	public NightmarePumpkinItem(Block blockIn, Properties p_i48534_3_) {
		super(blockIn, p_i48534_3_);
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (player instanceof ServerPlayerEntity) {
			if (((ServerPlayerEntity) player).getServerWorld().getDimensionKey().equals(World.THE_END)) {
				Nightmare.handleSpawns(
						player,
						new EntityType[]{
								EntityType.SHULKER,
								EntityType.ENDERMITE, EntityType.ENDERMITE, EntityType.ENDERMITE,
								EntityType.ENDERMAN, EntityType.ENDERMAN, EntityType.ENDERMAN,
						}
				);
			} else {
				Nightmare.handleSpawns(
						player,
						new EntityType[]{
								EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE,
								EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON,
								EntityType.SPIDER, EntityType.SPIDER, EntityType.SPIDER, EntityType.SPIDER,
								RegisterHandler.DRAGOURD.get(),
								EntityType.CREEPER, EntityType.CREEPER, EntityType.CREEPER, EntityType.CREEPER,
						}
				);
			}
		}
	}
}
