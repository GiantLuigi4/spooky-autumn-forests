package com.tfc.spookyautumnforests;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class SpookyAutumnForestsFeatures {
//	public static final ConfiguredFeature<?, ?> TREE = newConfiguredFeature("spooky_forests_tree", Feature.SIMPLE_BLOCK.withConfiguration(
//			new BlockWithContextConfig(
//					SpookyAutumnForests.RegistryEvents.blocks.get("spooky_forests_tree").getDefaultState(),
//					ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState()),
//					ImmutableList.of(Blocks.AIR.getDefaultState()),
//					ImmutableList.of(Blocks.AIR.getDefaultState())
//			)
//	).withPlacement(net.minecraft.world.gen.feature.Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(16, 1, 8))));
	
	public static final ConfiguredFeature<?, ?> TREE = newConfiguredFeature("spooky_forests_tree", newFeature("spooky_forests_tree", new Feature<NoFeatureConfig>(NoFeatureConfig.field_236558_a_) {
				@Override
				public boolean func_241855_a(ISeedReader world, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {
					BlockState state = world.getBlockState(p_241855_4_.down());
					if (state.isIn(Blocks.GRASS_BLOCK) || state.isIn(Blocks.DIRT) || state.isIn(Blocks.COARSE_DIRT) || state.isIn(Blocks.PODZOL) || state.isIn(Blocks.FARMLAND))
						SaplingBlock.gen(world, p_241855_4_, world.getRandom(), false);
					return false;
				}
			}
			).withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG)
					.withPlacement(net.minecraft.world.gen.feature.Features.Placements.HEIGHTMAP_PLACEMENT)
					.withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(16, 1, 8)))
	);
	
	private static <FC extends IFeatureConfig, F extends Feature<FC>> ConfiguredFeature<FC, F> newConfiguredFeature(String registryName, ConfiguredFeature<FC, F> configuredFeature) {
		Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("spooky_autumn_forests", registryName), configuredFeature);
		return configuredFeature;
	}
	
	private static <FC extends IFeatureConfig> Feature<FC> newFeature(String registryName, Feature<FC> configuredFeature) {
		//thank you noeppi_noeppi
		configuredFeature.setRegistryName(new ResourceLocation("spooky_autumn_forests", registryName));
		ForgeRegistries.FEATURES.register(configuredFeature);
		return configuredFeature;
	}
}
