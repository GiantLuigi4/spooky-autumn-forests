package com.tfc.spookyautumnforests.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SUpdateTimePacket.class)
public class WorldTimePacketMixin {
	@Inject(at = @At("RETURN"), method = "getWorldTime()J")
	public void alterTime(CallbackInfoReturnable<Long> cir) {
		if (Minecraft.getInstance().player != null) {
			Biome b = Minecraft.getInstance()
					.player.worldClient
					.getBiome(Minecraft.getInstance()
							.player.getPosition());
			if (b.getSkyColor() == -128)
				cir.setReturnValue(18000L);
		}
	}
}
