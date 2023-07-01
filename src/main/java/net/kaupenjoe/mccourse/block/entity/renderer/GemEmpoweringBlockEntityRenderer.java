package net.kaupenjoe.mccourse.block.entity.renderer;

import net.kaupenjoe.mccourse.block.custom.GemEmpoweringStationBlock;
import net.kaupenjoe.mccourse.block.entity.GemEmpoweringStationBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class GemEmpoweringBlockEntityRenderer implements BlockEntityRenderer<GemEmpoweringStationBlockEntity> {
    public GemEmpoweringBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(GemEmpoweringStationBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack itemStack = entity.getRenderStack();
        matrices.push();
        matrices.translate(0.5f, 0.75f, 0.5f);
        matrices.scale(0.35f, 0.35f, 0.35f);
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getCachedState().get(GemEmpoweringStationBlock.FACING).asRotation()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));

        itemRenderer.renderItem(itemStack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
