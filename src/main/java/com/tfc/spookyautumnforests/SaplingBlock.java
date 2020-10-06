package com.tfc.spookyautumnforests;

import net.minecraft.block.*;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class SaplingBlock extends Block implements net.minecraftforge.common.IPlantable, IGrowable {
	private final boolean isCopper;
	private final boolean instant;
	
	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		return this.getDefaultState();
	}
	
	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return PlantType.PLAINS;
	}
	
	public SaplingBlock(Properties properties, boolean isCopper) {
		super(properties);
		this.isCopper = isCopper;
		this.instant = false;
	}
	
	public SaplingBlock(Properties properties, boolean isCopper, boolean instant) {
		super(properties);
		this.isCopper = isCopper;
		this.instant = instant;
	}
	
	@Override
	public boolean ticksRandomly(BlockState state) {
		return true;
	}
	
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (instant && worldIn instanceof ServerWorld)
			randomTick(state, (ServerWorld) worldIn, pos, worldIn.rand);
	}
	
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.down();
		if (state.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
			return worldIn.getBlockState(blockpos).canSustainPlant(worldIn, blockpos, Direction.UP, this);
		return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
	}
	
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return state.isIn(Blocks.GRASS_BLOCK) || state.isIn(Blocks.DIRT) || state.isIn(Blocks.COARSE_DIRT) || state.isIn(Blocks.PODZOL) || state.isIn(Blocks.FARMLAND);
	}
	
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return true;
	}
	
	public static void gen(IServerWorld worldIn, BlockPos pos, Random random, boolean isCopper) {
		int num1 = random.nextInt(4) + 4;
		int y = 0;
		double sclXL = ((random.nextDouble() - 0.5d) * 2);
		double sclZL = ((random.nextDouble() - 0.5d) * 2);
		int leanX = 0;
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
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
							(int) ((x + (sclZ * 2)) * sclZ) + (int) (leanX * sclZL),
							isCopper
					);
				}
				for (int f = 0; f < 3; f++) {
					setLeaves(worldIn, pos,
							(int) ((x + (sclX * 2)) * sclX) + (int) (leanX * sclXL),
							y - (int) ((i * sclY) - f) + (int) (x * sclY),
							(int) (x * sclZ) + (int) (leanX * sclZL),
							isCopper
					);
				}
				for (int f = 0; f < 2; f++) {
					setLeaves(worldIn, pos,
							(int) (x * sclX) + (int) (leanX * sclXL),
							(int) ((i * sclY) - f) + (int) (x * sclY),
							(int) ((x + (sclX * 2)) * sclZ) + (int) (leanX * sclZL),
							isCopper
					);
				}
				setLeaves(worldIn, pos,
						(int) (x * sclX) + (int) (leanX * sclXL),
						y - (int) (i * sclY) + (int) (x * sclY) + 1,
						(int) (x * sclZ) + (int) (leanX * sclZL),
						isCopper
				);
			}
			leanX++;
		}
		y--;
		leanX--;
		for (int f = 0; f < 4; f++)
			setLeaves(worldIn, pos, 1 + (int) (leanX * sclXL), y - f, (int) (leanX * sclZL), isCopper);
		for (int f = 0; f < 3; f++)
			setLeaves(worldIn, pos, -1 + (int) (leanX * sclXL), y - f, (int) (leanX * sclZL), isCopper);
		for (int f = 0; f < 2; f++)
			setLeaves(worldIn, pos, (int) (leanX * sclXL), y - f, -1 + (int) (leanX * sclZL), isCopper);
		for (int f = 0; f < 5; f++)
			setLeaves(worldIn, pos, (int) (leanX * sclXL), y - f, 1 + (int) (leanX * sclZL), isCopper);
		setLeaves(worldIn, pos, (int) (leanX * sclXL), y + 1, (int) (leanX * sclZL), isCopper);
	}
	
	public static void setLeaves(IServerWorld worldIn, BlockPos pos, int offX, int offY, int offZ, boolean isCopper) {
		BlockPos pos1 = new BlockPos(pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ);
		if (worldIn.getBlockState(pos1).canBeReplacedByLeaves(worldIn, pos1)) {
			boolean placeCopper = isCopper || worldIn.getRandom().nextDouble() > 0.8;
			worldIn.setBlockState(pos1,
					SpookyAutumnForests.RegistryEvents.blocks.get(placeCopper ? "spooky_leaves_copper" : "spooky_wood_leaves").getDefaultState().with(LeavesBlock.DISTANCE, 4),
					3
			);
		}
	}
	
	public static void setLogs(IServerWorld worldIn, BlockPos pos, int offX, int offY, int offZ) {
		BlockPos pos1 = new BlockPos(pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ);
		if (worldIn.getBlockState(pos1).canBeReplacedByLogs(worldIn, pos1) || worldIn.getBlockState(pos1).isAir() || worldIn.getBlockState(pos1).getBlock().getRegistryName().toString().contains("spooky_leaves_copper")) {
			worldIn.setBlockState(pos1,
					SpookyAutumnForests.RegistryEvents.blocks.get("spooky_wood_log").getDefaultState(),
					3
			);
		}
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		super.randomTick(state, worldIn, pos, random);
		gen(worldIn, pos, random, isCopper);
	}
	
	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		if (this.instant && world instanceof ServerWorld)
			this.randomTick(state, (ServerWorld) world, pos, ((ServerWorld) world).getRandom());
	}
	
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}
	
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return (double) worldIn.rand.nextFloat() < 0.25D;
	}
	
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		this.randomTick(state, worldIn, pos, rand);
	}
	
	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
		if (worldIn instanceof ServerWorld && instant)
			randomTick(state, (ServerWorld) worldIn, pos, worldIn.getRandom());
		else if (!instant)
			super.onPlayerDestroy(worldIn, pos, state);
	}
}
