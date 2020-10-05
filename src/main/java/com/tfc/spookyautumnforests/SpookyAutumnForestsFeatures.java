package com.tfc.spookyautumnforests;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

public class SpookyAutumnForestsFeatures {
	public static final ConfiguredFeature<?, ?> TREE = newConfiguredFeature("spooky_forests_tree", Feature.SIMPLE_BLOCK.withConfiguration(
			new BlockWithContextConfig(
					SpookyAutumnForests.RegistryEvents.blocks.get("spooky_forests_tree").getDefaultState(),
					ImmutableList.of(Blocks.GRASS_BLOCK.getDefaultState()),
					ImmutableList.of(Blocks.AIR.getDefaultState()),
					ImmutableList.of(Blocks.AIR.getDefaultState())
			)
	).withPlacement(net.minecraft.world.gen.feature.Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(16, 1, 8))));
	
	private static <FC extends IFeatureConfig, F extends Feature<FC>> ConfiguredFeature<FC, F> newConfiguredFeature(String registryName, ConfiguredFeature<FC, F> configuredFeature) {
		Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("spooky_autumn_forests", registryName), configuredFeature);
		return configuredFeature;
	}
}
