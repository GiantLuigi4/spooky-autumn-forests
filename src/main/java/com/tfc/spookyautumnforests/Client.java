package com.tfc.spookyautumnforests;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class Client {
	public static void clientSetup(FMLClientSetupEvent event) {
		for (String name : Blocks.transparentBlocks) {
			RenderTypeLookup.setRenderLayer(SpookyAutumnForests.RegistryEvents.blocks.get(name), RenderType.getCutout());
		}
	}
}
