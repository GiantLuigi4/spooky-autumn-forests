package com.tfc.spookyautumnforests;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

public class Client {
	public static long time = 0;
	
	public static void a(RenderGameOverlayEvent a) {
		a.setCanceled(a.isCancelable());
	}
	
	public static boolean useNormal = false;
	
	public static void clientSetup(FMLClientSetupEvent event) {
		for (String name : Registries.transparentBlocks) {
			RenderTypeLookup.setRenderLayer(SpookyAutumnForests.RegistryEvents.blocks.get(name), RenderType.getCutout());
		}
		LeavesBlockColors colors = new LeavesBlockColors();
		for (String[] sa : Registries.leaves) {
			Minecraft.getInstance().getBlockColors().register(colors, SpookyAutumnForests.RegistryEvents.blocks.get(sa[0]));
			Minecraft.getInstance().getItemColors().register(colors, SpookyAutumnForests.RegistryEvents.blocks.get(sa[0]));
		}
	}
	
	public static void onTick(LivingEvent event) {
		if (
				event instanceof LivingEvent.LivingUpdateEvent &&
						event.getEntity().getEntityWorld().isRemote &&
						event.getEntity() instanceof PlayerEntity &&
						event.getEntity().getPosition().equals(Minecraft.getInstance().player.getPosition())
		) {
			Biome b = Minecraft.getInstance().player.worldClient.getBiome(Minecraft.getInstance().player.getPosition());
			if (event.getEntity().getEntityWorld().getWorldInfo() instanceof ClientWorld.ClientWorldInfo) {
				ClientWorld.ClientWorldInfo info = ((ClientWorld.ClientWorldInfo) event.getEntity().getEntityWorld().getWorldInfo());
				if (
						b.getSkyColor() == 0
				) {
					info.setDayTime(18000);
//					useNormal = false;
//					long gtime = info.getDayTime();
//					long off = (gtime/(18000*2));
//					gtime = off*(18000*2)+(18000);
//					time = gtime;
				} else {
//					if (time != info.getDayTime()) {
//						time = (int) MathHelper.lerp(0.1f, time, info.getDayTime());
//						if (Math.abs(info.getGameTime() - time) < 5) {
//							useNormal = true;
//						}
//					}
//					if (!useNormal) {
//						info.setDayTime(time);
//					}
				}
			}
		}
	}
	
	public static class LeavesBlockColors implements IBlockColor, IItemColor {
		@Override
		public int getColor(BlockState state, @Nullable IBlockDisplayReader reader, @Nullable BlockPos pos, int color) {
			int col = reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault();
			int r = (col >> 16) & 0xFF;
			int g = (col >> 8) & 0xFF;
			int b = (col) & 0xFF;
			r = (int) MathHelper.lerp(0.75f, r, 255);
			g = (int) MathHelper.lerp(0.75f, g, 128);
			b = (int) MathHelper.lerp(0.75f, b, 0);
			return ((0xFF) << 24) |
					((r & 0xFF) << 16) |
					((g & 0xFF) << 8) |
					((b & 0xFF));
		}
		
		@Override
		public int getColor(ItemStack p_getColor_1_, int p_getColor_2_) {
			return ((0xFF) << 24) |
					((0xFF) << 16) |
					((128 & 0xFF) << 8) |
					((0));
		}
	}
}
