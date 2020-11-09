package com.tfc.spookyautumnforests.imc.mystical_pumpkins;

import blueduck.mysticalpumpkins.registry.InfusionTableRecipeRegistry;
import com.tfc.spookyautumnforests.SpookyAutumnForests;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class RecipeRegistry {
	public static void init() {
		InfusionTableRecipeRegistry.addInfuserRecipe(
				new ItemStack(Blocks.CARVED_PUMPKIN),
				20,
				new ItemStack(SpookyAutumnForests.nightmare_fuel, 3),
				new ItemStack(SpookyAutumnForests.nightmare_pumpkin)
		);
		InfusionTableRecipeRegistry.addInfuserRecipe(
				new ItemStack(SpookyAutumnForests.nightmare_pumpkin),
				10,
				new ItemStack(Items.ROSE_BUSH),
				new ItemStack(SpookyAutumnForests.dream_pumpkin)
		);
		InfusionTableRecipeRegistry.addInfuserRecipe(
				new ItemStack(SpookyAutumnForests.nightmare_pumpkin),
				20,
				new ItemStack(Items.POPPY),
				new ItemStack(SpookyAutumnForests.dream_pumpkin)
		);
	}
}
