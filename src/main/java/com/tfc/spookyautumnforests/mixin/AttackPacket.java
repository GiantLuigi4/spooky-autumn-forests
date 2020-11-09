package com.tfc.spookyautumnforests.mixin;

import com.tfc.spookyautumnforests.AttackPacketCode;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CUseEntityPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetHandler.class)
public class AttackPacket {
	@Shadow
	public ServerPlayerEntity player;
	
	@Inject(at = @At("HEAD"), method = "processUseEntity(Lnet/minecraft/network/play/client/CUseEntityPacket;)V")
	public void receive(CUseEntityPacket packetIn, CallbackInfo ci) {
		AttackPacketCode.doStuff(player, packetIn);
	}
}
