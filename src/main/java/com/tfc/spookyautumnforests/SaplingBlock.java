package com.tfc.spookyautumnforests;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SaplingBlock extends Block {
	private final boolean isCopper;
	public SaplingBlock(Properties properties, boolean isCopper) {
		super(properties);
		this.isCopper = isCopper;
	}
	
	@Override
	public boolean ticksRandomly(BlockState state) {
		return true;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		super.randomTick(state, worldIn, pos, random);
		int num1 = random.nextInt(4) + 4;
		int y = 0;
		double sclXL = ((random.nextDouble() - 0.5d) * 2);
		double sclZL = ((random.nextDouble() - 0.5d) * 2);
		int leanX = 0;
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		for (int a = 0; a < num1; a++) {
			setLogs(worldIn, pos,
					(int) (leanX * sclXL), y, (int) (leanX * sclZL)
			);
			y++;
			if (random.nextDouble() >= 0.75) {
				int num = random.nextInt(1) + 2;
				int x = 0;
				int i;
				double sclX = ((random.nextDouble() - 0.5d) * 2);
				double sclY = ((random.nextDouble() - 0.5d) * 0.5f);
				double sclZ = ((random.nextDouble() - 0.5d) * 2);
				for (i = 0; i < num; i++) {
					int num2 = random.nextInt(3) + 1;
					for (int b = 0; b < num2; b++) {
						x++;
						setLogs(worldIn, pos,
								(int) (x * sclX) + (int) (leanX * sclXL),
								y - (int) (i * sclY) + (int) (x * sclY),
								(int) (x * sclZ) + (int) (leanX * sclZL)
						);
					}
				}
				x--;
				for (int f = 0; f < 4; f++) {
					setLeaves(worldIn, pos,
							(int) ((x + (sclX * 2)) * sclX) + (int) (leanX * sclXL),
							y - (int) ((i * sclY) - f) + (int) (x * sclY),
							(int) ((x + (sclZ * 2)) * sclZ) + (int) (leanX * sclZL)
					);
				}
				for (int f = 0; f < 3; f++) {
					setLeaves(worldIn, pos,
							(int) ((x + (sclX * 2)) * sclX) + (int) (leanX * sclXL),
							y - (int) ((i * sclY) - f) + (int) (x * sclY),
							(int) (x * sclZ) + (int) (leanX * sclZL)
					);
				}
				for (int f = 0; f < 2; f++) {
					setLeaves(worldIn, pos,
							(int) (x * sclX) + (int) (leanX * sclXL),
							(int) ((i * sclY) - f) + (int) (x * sclY),
							(int) ((x + (sclX * 2)) * sclZ) + (int) (leanX * sclZL)
					);
				}
				setLeaves(worldIn, pos,
						(int) (x * sclX) + (int) (leanX * sclXL),
						y - (int) (i * sclY) + (int) (x * sclY) + 1,
						(int) (x * sclZ) + (int) (leanX * sclZL)
				);
			}
			leanX++;
		}
		y--;
		leanX--;
		for (int f = 0; f < 4; f++) setLeaves(worldIn, pos, 1 + (int) (leanX * sclXL), y - f, (int) (leanX * sclZL));
		for (int f = 0; f < 3; f++) setLeaves(worldIn, pos, -1 + (int) (leanX * sclXL), y - f, (int) (leanX * sclZL));
		for (int f = 0; f < 2; f++) setLeaves(worldIn, pos, (int) (leanX * sclXL), y - f, -1 + (int) (leanX * sclZL));
		for (int f = 0; f < 5; f++) setLeaves(worldIn, pos, (int) (leanX * sclXL), y - f, 1 + (int) (leanX * sclZL));
		setLeaves(worldIn, pos, (int) (leanX * sclXL), y + 1, (int) (leanX * sclZL));
	}
	
	public void setLeaves(World worldIn, BlockPos pos, int offX, int offY, int offZ) {
		BlockPos pos1 = new BlockPos(pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ);
		if (worldIn.getBlockState(pos1).canBeReplacedByLeaves(worldIn, pos1)) {
			worldIn.setBlockState(pos1,
					SpookyAutumnForests.RegistryEvents.blocks.get(isCopper?"spooky_leaves_copper":"spooky_leaves").getDefaultState()
			);
		}
	}
	
	public static void setLogs(World worldIn, BlockPos pos, int offX, int offY, int offZ) {
		BlockPos pos1 = new BlockPos(pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ);
		if (worldIn.getBlockState(pos1).canBeReplacedByLogs(worldIn, pos1) || worldIn.getBlockState(pos1).isAir() || worldIn.getBlockState(pos1).getBlock().getRegistryName().toString().contains("spooky_leaves_copper")) {
			worldIn.setBlockState(pos1,
					SpookyAutumnForests.RegistryEvents.blocks.get("spooky_wood_log").getDefaultState()
			);
		}
	}
}
