package com.tfc.spookyautumnforests;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("spooky_autumn_forests")
public class SpookyAutumnForests {
	
	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();
	
	public SpookyAutumnForests() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.register(RegistryEvents.class);
		bus.addListener(this::clientSetup);
	}
	
	public void clientSetup(FMLClientSetupEvent event) {
		Client.clientSetup(event);
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		public static final HashMap<String, Block> blocks = new HashMap<>();
		
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
			for (String[] sa : Blocks.regularBlocks) {
				try {
					Field get = net.minecraft.block.Blocks.class.getDeclaredField(sa[1]);
					get.setAccessible(true);
					Block block = new Block(AbstractBlock.Properties.from((AbstractBlock) get.get(null)));
					block.setRegistryName("spooky_autumn_forests", sa[0]);
					blockRegistryEvent.getRegistry().register(block);
					blocks.put(sa[0], block);
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
			for (String[] sa : Blocks.logs) {
				try {
					Field get = net.minecraft.block.Blocks.class.getDeclaredField(sa[1]);
					get.setAccessible(true);
					Block block = new RotatedPillarBlock(AbstractBlock.Properties.from((AbstractBlock) get.get(null)));
					block.setRegistryName("spooky_autumn_forests", sa[0]);
					blockRegistryEvent.getRegistry().register(block);
					blocks.put(sa[0], block);
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
		}
		
		@SubscribeEvent
		public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
			for (String[] sa : Blocks.regularBlocks) {
				Item item = new BlockItem(blocks.get(sa[0]), new Item.Properties());
				item.setRegistryName("spooky_autumn_forests", sa[0]);
				itemRegistryEvent.getRegistry().register(item);
			}
			for (String[] sa : Blocks.logs) {
				Item item = new BlockItem(blocks.get(sa[0]), new Item.Properties());
				item.setRegistryName("spooky_autumn_forests", sa[0]);
				itemRegistryEvent.getRegistry().register(item);
			}
		}
	}
}
