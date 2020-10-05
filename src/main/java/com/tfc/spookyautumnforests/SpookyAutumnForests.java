package com.tfc.spookyautumnforests;

import net.minecraft.block.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraft.state.Property;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static net.minecraft.world.gen.feature.Features.*;

@Mod("spooky_autumn_forests")
public class SpookyAutumnForests {
	
	public SpookyAutumnForests() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.register(RegistryEvents.class);
		bus.addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.addListener(Client::onTick);
	}
	
	public void clientSetup(FMLClientSetupEvent event) {
		Client.clientSetup(event);
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		public static final HashMap<String, Block> blocks = new HashMap<>();
		
		public static Biome spooky_forest_2;
		
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
			for (String[] sa : Registries.regularBlocks) {
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
			for (String[] sa : Registries.logs) {
				try {
					Field get = net.minecraft.block.Blocks.class.getDeclaredField(sa[1]);
					get.setAccessible(true);
					Block block = new RotatedPillarBlock(AbstractBlock.Properties.from((AbstractBlock) get.get(null))) {
						@Override
						public @NotNull ActionResultType onBlockActivated(@NotNull BlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand handIn, @NotNull BlockRayTraceResult hit) {
							if (blocks.containsKey("stripped_"+sa[0])) {
								if (player.getHeldItem(handIn).getToolTypes().contains(ToolType.AXE)) {
									AtomicReference<BlockState> state1 = new AtomicReference<>(blocks.get("stripped_"+sa[0]).getDefaultState());
									state.getProperties().forEach(property -> {
										state1.set(applyProperty(state, state1.get(), property));
									});
									worldIn.setBlockState(pos, state1.get());
									worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1, 1, false);
									if (!player.isCreative()) player.getHeldItem(handIn).damageItem(1, player, (p_220040_1_) -> p_220040_1_.sendBreakAnimation(handIn));
									player.swingArm(handIn);
									return ActionResultType.PASS;
								}
							}
							return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
						}
						
						public <A extends Comparable<A>> @NotNull BlockState applyProperty(@NotNull BlockState sourceState, @NotNull BlockState newState, Property<A> property) {
							return newState.with(property, sourceState.get(property));
						}
					};
					block.setRegistryName("spooky_autumn_forests", sa[0]);
					blockRegistryEvent.getRegistry().register(block);
					blocks.put(sa[0], block);
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
			for (String[] sa : Registries.doors) {
				try {
					Field get = net.minecraft.block.Blocks.class.getDeclaredField(sa[1]);
					get.setAccessible(true);
					Block block = new DoorBlock(AbstractBlock.Properties.from((AbstractBlock) get.get(null)));
					block.setRegistryName("spooky_autumn_forests", sa[0]);
					blockRegistryEvent.getRegistry().register(block);
					blocks.put(sa[0], block);
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
			for (String[] sa : Registries.trapdoors) {
				try {
					Field get = net.minecraft.block.Blocks.class.getDeclaredField(sa[1]);
					get.setAccessible(true);
					Block block = new TrapDoorBlock(AbstractBlock.Properties.from((AbstractBlock) get.get(null)));
					block.setRegistryName("spooky_autumn_forests", sa[0]);
					blockRegistryEvent.getRegistry().register(block);
					blocks.put(sa[0], block);
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
			for (String[] sa : Registries.leaves) {
				try {
					Field get = net.minecraft.block.Blocks.class.getDeclaredField(sa[1]);
					get.setAccessible(true);
					Block block = new LeavesBlock(AbstractBlock.Properties.from((AbstractBlock) get.get(null)));
					block.setRegistryName("spooky_autumn_forests", sa[0]);
					blockRegistryEvent.getRegistry().register(block);
					blocks.put(sa[0], block);
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
			{
				Block block = new SaplingBlock(AbstractBlock.Properties.from(Blocks.OAK_SAPLING), true).setRegistryName("spooky_autumn_forests", "spooky_wood_copper_sapling");
				blockRegistryEvent.getRegistry().register(block);
				blocks.put("spooky_wood_copper_sapling", block);
			}
			{
				Block block = new SaplingBlock(AbstractBlock.Properties.from(Blocks.OAK_SAPLING), false).setRegistryName("spooky_autumn_forests", "spooky_wood_sapling");
				blockRegistryEvent.getRegistry().register(block);
				blocks.put("spooky_wood_sapling", block);
			}
			{
				Block block = new SaplingBlock(AbstractBlock.Properties.from(Blocks.OAK_SAPLING), false, true).setRegistryName("spooky_autumn_forests", "spooky_forests_tree");
				blockRegistryEvent.getRegistry().register(block);
				blocks.put("spooky_forests_tree", block);
			}
		}
		
		@SubscribeEvent
		public static void onBiomeRegistry(final RegistryEvent.Register<Biome> biomeRegistryEvent) {
			int r = 128;
			int color = ((0xFF) << 24) |
					((r & 0xFF) << 16) |
					(((r / 2) & 0xFF) << 8) |
					((0 & 0xFF));
			int color2 = ((0xFF) << 24) |
					((r & 0xFF) << 16) |
					(((r / 2) & 0xFF) << 8) |
					((r & 0xFF));
			spooky_forest_2 = new Biome.Builder()
					.scale(0.1f)
					.temperature(8)
					.category(Biome.Category.FOREST)
					.depth(0.2f)
					.precipitation(Biome.RainType.RAIN)
					.withMobSpawnSettings(
							new MobSpawnInfoBuilder(MobSpawnInfo.EMPTY)
									.withCreatureSpawnProbability(0.9f)
									.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE, 50, 10, 20))
									.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 50, 10, 20))
									.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SPIDER, 50, 10, 20))
									.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.CREEPER, 50, 10, 20))
									.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 50, 10, 20))
									.copy()
					).setEffects(
							new BiomeAmbience.Builder()
									.setFogColor(12638463)
									.withFoliageColor(color)
									.setWaterColor(color2)
									.setWaterFogColor(color2)
									.withSkyColor(0)
									.setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
									.build()
					)
					.downfall(0.25f)
					.withGenerationSettings(
							new BiomeGenerationSettingsBuilder(BiomeGenerationSettings.DEFAULT_SETTINGS)
									.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_GOLD)
									.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_IRON)
									.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_COAL)
									.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_DIAMOND)
									.withFeature(GenerationStage.Decoration.LAKES, LAKE_WATER)
									.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, SpookyAutumnForestsFeatures.TREE)
									.withCarver(GenerationStage.Carving.AIR, WorldCarver.CAVE.func_242761_a(new ProbabilityConfig(0.5f)))
									.withCarver(GenerationStage.Carving.AIR, WorldCarver.CANYON.func_242761_a(new ProbabilityConfig(0.5f)))
									.withSurfaceBuilder(DefaultSurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG))
									.build()
					)
					.build()
					.setRegistryName("spooky_autumn_forests", "spooky_wood_forest_2");
			biomeRegistryEvent.getRegistry().register(spooky_forest_2);
			BiomeManager.addBiome(
					BiomeManager.BiomeType.WARM,
					new BiomeManager.BiomeEntry(
							RegistryKey.getOrCreateKey(
									Registry.BIOME_KEY,
									new ResourceLocation(
											"spooky_autumn_forests", "spooky_wood_forest_2"
									)), 23812038));
		}
		
		@SubscribeEvent
		public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
			for (String[] sa : Registries.regularBlocks) {
				Item item = new BlockItem(blocks.get(sa[0]), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
				item.setRegistryName("spooky_autumn_forests", sa[0]);
				itemRegistryEvent.getRegistry().register(item);
			}
			for (String[] sa : Registries.logs) {
				Item item = new BlockItem(blocks.get(sa[0]), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
				item.setRegistryName("spooky_autumn_forests", sa[0]);
				itemRegistryEvent.getRegistry().register(item);
			}
			for (String[] sa : Registries.trapdoors) {
				Item item = new BlockItem(blocks.get(sa[0]), new Item.Properties().group(ItemGroup.REDSTONE));
				item.setRegistryName("spooky_autumn_forests", sa[0]);
				itemRegistryEvent.getRegistry().register(item);
			}
			for (String[] sa : Registries.doors) {
				Item item = new TallBlockItem(blocks.get(sa[0]), new Item.Properties().group(ItemGroup.REDSTONE));
				item.setRegistryName("spooky_autumn_forests", sa[0]);
				itemRegistryEvent.getRegistry().register(item);
			}
			for (String[] sa : Registries.leaves) {
				Item item = new BlockItem(blocks.get(sa[0]), new Item.Properties().group(ItemGroup.REDSTONE));
				item.setRegistryName("spooky_autumn_forests", sa[0]);
				itemRegistryEvent.getRegistry().register(item);
			}
			itemRegistryEvent.getRegistry().register(new BlockItem(blocks.get("spooky_wood_copper_sapling"), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName("spooky_autumn_forests", "spooky_wood_copper_sapling"));
			itemRegistryEvent.getRegistry().register(new BlockItem(blocks.get("spooky_wood_sapling"), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName("spooky_autumn_forests", "spooky_wood_sapling"));
		}
	}
}
