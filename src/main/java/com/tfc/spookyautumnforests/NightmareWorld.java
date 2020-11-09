package com.tfc.spookyautumnforests;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.storage.ISpawnWorldInfo;
import net.minecraft.world.storage.MapData;

import javax.annotation.Nullable;
import java.util.List;
import java.util.OptionalLong;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class NightmareWorld extends World {
	private static final DimensionType type = new DimensionType(
			OptionalLong.of(0L), false, false,
			false, false, 0, true,
			false, false, false,
			255,
			new ResourceLocation("minecraft:grass_block"),
			new ResourceLocation("minecraft:stone"),
			0f
	);
	private static final ISpawnWorldInfo spawnWorldInfo = new ISpawnWorldInfo() {
		@Override
		public int getSpawnX() {
			return 0;
		}
		
		@Override
		public void setSpawnX(int x) {
		
		}
		
		@Override
		public int getSpawnY() {
			return 0;
		}
		
		@Override
		public void setSpawnY(int y) {
		
		}
		
		@Override
		public int getSpawnZ() {
			return 0;
		}
		
		@Override
		public void setSpawnZ(int z) {
		
		}
		
		@Override
		public float getSpawnAngle() {
			return 0;
		}
		
		@Override
		public void setSpawnAngle(float angle) {
		
		}
		
		@Override
		public long getGameTime() {
			return 0;
		}
		
		@Override
		public long getDayTime() {
			return 18000L;
		}
		
		@Override
		public boolean isThundering() {
			return false;
		}
		
		@Override
		public boolean isRaining() {
			return false;
		}
		
		@Override
		public void setRaining(boolean isRaining) {
		}
		
		@Override
		public boolean isHardcore() {
			return false;
		}
		
		@Override
		public GameRules getGameRulesInstance() {
			return null;
		}
		
		@Override
		public Difficulty getDifficulty() {
			return Difficulty.NORMAL;
		}
		
		@Override
		public boolean isDifficultyLocked() {
			return false;
		}
	};
	public int targetPlayer = 0;
	public World parent = null;
	public Entity nightmareToTick = null;
	
	public NightmareWorld(RegistryKey<World> dimension, boolean isRemote, boolean isDebug, long seed) {
		super(spawnWorldInfo, dimension, type, () -> null, isRemote, isDebug, seed);
	}
	
	@Override
	public IProfiler getProfiler() {
		return parent.getProfiler();
	}
	
	@Override
	public boolean chunkExists(int chunkX, int chunkZ) {
		return true;
	}
	
	@Override
	public boolean isBlockLoaded(BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean isAreaLoaded(BlockPos center, int range) {
		return true;
	}
	
	@Override
	public boolean isAreaLoaded(BlockPos from, BlockPos to) {
		return true;
	}
	
	@Override
	public boolean isAreaLoaded(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
		return true;
	}
	
	@Override
	public Chunk getChunkAt(BlockPos pos) {
		return new Chunk(this, new ChunkPos(pos), parent.getChunkAt(pos).getBiomes()) {
			@Override
			public BlockState getBlockState(BlockPos pos) {
				int scl = 1;
				BlockState state = Blocks.AIR.getDefaultState();
				for (int x = -scl; x <= scl; x++)
					for (int y = -scl; y <= scl; y++)
						for (int z = -scl; z <= scl; z++) {
							BlockPos pos1 = pos.add(x, y, z);
							if (pos1.equals(pos))
								state = parent.getBlockState(pos1);
						}
				return state;
			}
		};
	}
	
	@Override
	public Chunk getChunk(int chunkX, int chunkZ) {
		return getChunkAt(new BlockPos(chunkX * 16, 0, chunkZ * 16));
	}
	
	@Override
	public WorldLightManager getLightManager() {
		return parent.getLightManager();
	}
	
	@Override
	public List<Entity> getEntitiesInAABBexcluding(@Nullable Entity entityIn, AxisAlignedBB boundingBox, @Nullable Predicate<? super Entity> predicate) {
		List<Entity> entities = parent.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
		for (Entity e : entities) {
			if (e.getEntityId() == targetPlayer) {
				return ImmutableList.of(parent.getEntityByID(targetPlayer));
			}
		}
		return ImmutableList.of();
	}
	
	@Override
	public <T extends Entity> List<T> getLoadedEntitiesWithinAABB(Class<? extends T> p_225316_1_, AxisAlignedBB p_225316_2_, @Nullable Predicate<? super T> p_225316_3_) {
		List<Entity> entities = (List<Entity>) parent.getEntitiesWithinAABB(p_225316_1_, p_225316_2_, p_225316_3_);
		Entity player = parent.getEntityByID(targetPlayer);
		
		if (p_225316_1_.isInstance(player)) {
			for (Entity e : entities) {
				if (e.getEntityId() == targetPlayer)
					return ImmutableList.of((T) player);
			}
		}
		
		if (p_225316_1_.isInstance(nightmareToTick))
			return ImmutableList.of((T) nightmareToTick);
		
		return ImmutableList.of();
	}
	
	@Override
	public IChunk getChunk(int x, int z, ChunkStatus requiredStatus, boolean nonnull) {
		return getChunkAt(new BlockPos(x * 16, 0, z * 16));
//		return super.getChunk(x, z, requiredStatus, nonnull);
	}
	
	@Override
	public Supplier<IProfiler> getWorldProfiler() {
		return () -> parent.getProfiler();
	}
	
	@Override
	public BlockState getBlockState(BlockPos pos) {
		int scl = 1;
		BlockState state = Blocks.AIR.getDefaultState();
		for (int x = -scl; x <= scl; x++)
			for (int y = -scl; y <= scl; y++)
				for (int z = -scl; z <= scl; z++) {
					BlockPos pos1 = pos.add(x, y, z);
					if (pos1.equals(pos))
						state = parent.getBlockState(pos1);
				}
		return state;
	}
	
	@Override
	public void notifyBlockUpdate(BlockPos pos, BlockState oldState, BlockState newState, int flags) {
	}
	
	@Override
	public void playSound(@Nullable PlayerEntity player, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
		parent.playSound(player, x, y, z, soundIn, category, volume, pitch);
	}
	
	@Override
	public void playMovingSound(@Nullable PlayerEntity playerIn, Entity entityIn, SoundEvent eventIn, SoundCategory categoryIn, float volume, float pitch) {
		parent.playMovingSound(playerIn, entityIn, eventIn, categoryIn, volume, pitch);
	}
	
	@Nullable
	@Override
	public Entity getEntityByID(int id) {
		if (id == targetPlayer)
			return parent.getEntityByID(id);
		return null;
	}
	
	@Nullable
	@Override
	public MapData getMapData(String mapName) {
		return null;
	}
	
	@Override
	public void registerMapData(MapData mapDataIn) {
	
	}
	
	@Override
	public int getNextMapId() {
		return 0;
	}
	
	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
	
	}
	
	@Override
	public Scoreboard getScoreboard() {
		return parent.getScoreboard();
	}
	
	@Override
	public RecipeManager getRecipeManager() {
		return null;
	}
	
	@Override
	public ITagCollectionSupplier getTags() {
		return parent.getTags();
	}
	
	@Override
	public ITickList<Block> getPendingBlockTicks() {
		return null;
	}
	
	@Override
	public ITickList<Fluid> getPendingFluidTicks() {
		return null;
	}
	
	@Override
	public AbstractChunkProvider getChunkProvider() {
		NightmareWorld thisWorld = this;
		return new AbstractChunkProvider() {
			@Nullable
			@Override
			public IChunk getChunk(int chunkX, int chunkZ, ChunkStatus requiredStatus, boolean load) {
				return thisWorld.getChunk(chunkX, chunkZ);
			}
			
			@Override
			public String makeString() {
				return thisWorld.toString();
			}
			
			@Override
			public WorldLightManager getLightManager() {
				return thisWorld.getLightManager();
			}
			
			@Override
			public IBlockReader getWorld() {
				return thisWorld;
			}
		};
	}
	
	@Override
	public void playEvent(@Nullable PlayerEntity player, int type, BlockPos pos, int data) {
		parent.playEvent(player, type, pos, data);
	}
	
	@Override
	public DynamicRegistries func_241828_r() {
		return null;
	}
	
	@Override
	public float func_230487_a_(Direction p_230487_1_, boolean p_230487_2_) {
		return 0;
	}
	
	@Override
	public List<? extends PlayerEntity> getPlayers() {
		return ImmutableList.of((PlayerEntity) parent.getEntityByID(targetPlayer));
	}
	
	@Override
	public Biome getNoiseBiomeRaw(int x, int y, int z) {
		return null;
	}
	
	@Override
	public GameRules getGameRules() {
		return parent.getGameRules();
	}
}
