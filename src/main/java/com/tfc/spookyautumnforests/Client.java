package com.tfc.spookyautumnforests;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.util.Random;

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
	
	private static final Direction[] dirs = new Direction[]{
			Direction.UP,
			Direction.DOWN,
			Direction.NORTH,
			Direction.EAST,
			Direction.SOUTH,
			Direction.WEST,
			null
	};
	private static final RenderState.FogState BLACKFOG = new RenderState.FogState("black_fog", () -> {
		RenderSystem.fog(2918, 0.0F, 0.0F, 0.0F, 1.0F);
		RenderSystem.enableFog();
	}, () -> {
		FogRenderer.applyFog();
		RenderSystem.disableFog();
	});
	private static final RenderState.TransparencyState TRANSPARENCY_STATE = new RenderState.TransparencyState("translucent_transparency", () -> {
		RenderSystem.enableBlend();
//		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_ALPHA);
	}, () -> {
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
	});
	
	public static void debugRender(RenderWorldLastEvent event) {
		PlayerEntity player = Minecraft.getInstance().player;
		World world = Minecraft.getInstance().world;
		int scl = 16;
		event.getMatrixStack().push();
		
		event.getMatrixStack().translate(
				-Minecraft.getInstance().getRenderManager().info.getProjectedView().x + player.getPosX(),
				-Minecraft.getInstance().getRenderManager().info.getProjectedView().y + player.getPosY(),
				-Minecraft.getInstance().getRenderManager().info.getProjectedView().z + player.getPosZ()
		);
		
		event.getMatrixStack().translate(
				player.getLookVec().x * 2,
				player.getLookVec().y * 2,
				player.getLookVec().z * 2
		);
		
		event.getMatrixStack().translate(0, 1, 0);
		
		event.getMatrixStack().scale(1f / scl, 1f / scl, 1f / scl);
		
		for (int x = -scl; x <= scl; x++) {
			for (int y = -scl; y <= scl; y++) {
				for (int z = -scl; z <= scl; z++) {
					event.getMatrixStack().push();
					
					event.getMatrixStack().translate(x, y, z);
					
					BlockPos pos = player.getPosition().add(x, y, z);
					Random rng = new Random(pos.toLong());
					
					event.getMatrixStack().translate(
							rng.nextInt(3) - 2,
							rng.nextInt(3) - 2,
							rng.nextInt(3) - 2
					);
					
					BlockState state = world.getBlockState(pos);
					
					int light = 0;
					for (Direction dir : dirs) {
						light = Math.max(
								light,
								LightTexture.packLight(
										Minecraft.getInstance().world.getLightFor(
												LightType.BLOCK,
												pos.add(0, 1, 0)
										),
										Minecraft.getInstance().world.getLightFor(
												LightType.SKY,
												pos.add(0, 1, 0)
										)
								)
						);
					}
					
					IBakedModel model = Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(state);
					
					for (Direction dir : dirs) {
						for (BakedQuad qd : model.getQuads(state, dir, new Random(pos.toLong()))) {
							int col = Minecraft.getInstance().getBlockColors().getColor(state, Minecraft.getInstance().world, pos, qd.getTintIndex());
							Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().getBuffer(RenderType.getCutout()).addQuad(
									event.getMatrixStack().getLast(), qd,
									((col >> 16) & 0xFF) / 255f,
									((col >> 8) & 0xFF) / 255f,
									((col) & 0xFF) / 255f,
									light, OverlayTexture.NO_OVERLAY
							);
						}
					}
					
					event.getMatrixStack().pop();
				}
			}
		}
		
		Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().finish(RenderType.getCutout());
		event.getMatrixStack().translate(0, -10000, 0);
		
		Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(
				Blocks.DIRT.getDefaultState(),
				event.getMatrixStack(), Minecraft.getInstance().getRenderTypeBuffers().getBufferSource(),
				LightTexture.packLight(15, 15),
				0
		);
		
		event.getMatrixStack().pop();
	}
	
	public static void renderEntity(RenderLivingEvent event) {
		if (event instanceof RenderLivingEvent.Pre) {
			if (event.getEntity().getTags().contains("nightmare_mob") || event.getEntity().getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(SpookyAutumnForests.nightmare_fuel)) {
				event.getMatrixStack().push();
				event.getMatrixStack().rotate(new Quaternion(0, -event.getEntity().rotationYaw / 1f, 0, true));
				event.getMatrixStack().rotate(new Quaternion(180, 0, 0, true));
				event.getMatrixStack().translate(0, -1.5, 0);
				if (event.getRenderer().getEntityModel() instanceof BipedModel)
					event.getRenderer().getEntityModel().isChild = false;
				event.getRenderer().getEntityModel().setLivingAnimations(event.getEntity(), event.getEntity().limbSwing, event.getEntity().limbSwingAmount, 0);
				event.getRenderer().getEntityModel().setRotationAngles(
						event.getEntity(),
						event.getEntity().limbSwing,
						event.getEntity().limbSwingAmount,
						event.getEntity().ticksExisted,
						0,
						event.getEntity().rotationPitch
				);
				event.getRenderer().getEntityModel().render(
						event.getMatrixStack(), event.getBuffers().getBuffer(RenderType.getEntityCutout(event.getRenderer().getEntityTexture(event.getEntity()))),
						event.getLight(), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1
				);
				event.getRenderer().getEntityModel().render(
						event.getMatrixStack(), event.getBuffers().getBuffer(getNightmareEntity()),
						event.getLight(), OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.2f
				);
//				event.getRenderer().getEntityModel().render(
//						event.getMatrixStack(), event.getBuffers().getBuffer(RenderType.getEntityTranslucentCull(new ResourceLocation("spooky_autumn_forests:textures/overlay/nightmare_entity.png"))),
//						event.getLight(), OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.1f
//				);
				event.getMatrixStack().pop();
				event.setCanceled(true);
			}
		}
	}

//	protected static final RenderState.TransparencyState ADDITIVE_TRANSPARENCY = new RenderState.TransparencyState("additive_transparency", () -> {
//		RenderSystem.enableBlend();
//		RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
//	}, () -> {
//		RenderSystem.disableBlend();
//		RenderSystem.defaultBlendFunc();
//	});
	
	public static RenderType getNightmareEntity() {
		RenderState.TransparencyState renderstate$transparencystate;
		RenderState.TextureState renderstate$texturestate;
		
		renderstate$transparencystate = TRANSPARENCY_STATE;
		
		renderstate$texturestate = new RenderState.TextureState(new ResourceLocation("spooky_autumn_forests:textures/overlay/nightmare_entity.png"), false, false);
		
		return RenderType.makeType(
				"overlay",
				DefaultVertexFormats.POSITION_COLOR,
				7,
				256,
				false,
				true,
				RenderType.State.getBuilder()
						.transparency(renderstate$transparencystate)
						.texture(renderstate$texturestate)
						.texturing(new RenderState.PortalTexturingState(
								64
						))
						.fog(BLACKFOG)
						.build(false)
		);
	}
}
