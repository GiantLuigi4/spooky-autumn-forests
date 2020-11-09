package com.tfc.spookyautumnforests.imc.mystical_pumpkins;

import blueduck.mysticalpumpkins.block.MysticalPumpkinItem;
import com.tfc.spookyautumnforests.API.Nightmare;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DreamPumpkinItem extends MysticalPumpkinItem {
	public DreamPumpkinItem(Block blockIn, Properties p_i48534_3_) {
		super(blockIn, p_i48534_3_);
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (player instanceof ServerPlayerEntity) {
			Nightmare.getNightmaresForPlayer(player).clear();
		}
		;
	}
}
