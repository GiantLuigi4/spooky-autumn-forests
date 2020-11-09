package com.tfc.spookyautumnforests.imc.mystical_pumpkins;

import blueduck.mysticalpumpkins.block.MysticalPumpkinItem;
import com.tfc.spookyautumnforests.API.Nightmare;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SEntityMetadataPacket;
import net.minecraft.network.play.server.SEntityTeleportPacket;
import net.minecraft.world.World;

public class DreamPumpkinItem extends MysticalPumpkinItem {
	public DreamPumpkinItem(Block blockIn, Properties p_i48534_3_) {
		super(blockIn, p_i48534_3_);
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (player instanceof ServerPlayerEntity) {
			Nightmare.getNightmaresForPlayer(player).forEach((entity -> {
				entity.setPosition(0, -10000, 0);
				entity.remove();
				
				//Setup packets
				SEntityTeleportPacket packet = new SEntityTeleportPacket(entity);
				SEntityMetadataPacket packet1 = new SEntityMetadataPacket(entity.getEntityId(), entity.getDataManager(), true);
				
				//Send the packets
				((ServerPlayerEntity) player).connection.sendPacket(packet);
				((ServerPlayerEntity) player).connection.sendPacket(packet1);
			}));
			
			Nightmare.getNightmaresForPlayer(player).clear();
		}
	}
}
