package com.tfc.spookyautumnforests;

import com.tfc.spookyautumnforests.API.Nightmare;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.client.CUseEntityPacket;

public class AttackPacketCode {
	public static void doStuff(ServerPlayerEntity player, CUseEntityPacket packetIn) {
		Nightmare.damage(player, packetIn.getEntityFromWorld(player.world).getEntityId());
	}
}
