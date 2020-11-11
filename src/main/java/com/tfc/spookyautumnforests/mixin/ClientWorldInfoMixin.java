package com.tfc.spookyautumnforests.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientWorld.ClientWorldInfo.class)
public class ClientWorldInfoMixin {
//	@Inject(at = @At("RETURN"), method = "getDayTime()J")
//	public void alterTime(CallbackInfoReturnable<Long> cir) {
//		if (Minecraft.getInstance().player != null) {
//			Biome b = Minecraft.getInstance()
//					.player.worldClient
//					.getBiome(Minecraft.getInstance()
//							.player.getPosition());
//			if (b.getSkyColor() == -128)
//				cir.setReturnValue(18000L);
//		}
//	}
}
