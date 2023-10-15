package net.kaupenjoe.mccourse.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

// Done with the help:
// https://github.com/TeamGalacticraft/Galacticraft/tree/main (MIT License)
public class GemEmpoweringCategory implements DisplayCategory<BasicDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(MCCourseMod.MOD_ID, "textures/gui/gem_empowering_station_gui.png");
    public static final CategoryIdentifier<GemEmpoweringDisplay> GEM_EMPOWERING =
            CategoryIdentifier.of(MCCourseMod.MOD_ID, "gem_empowering");

    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return GEM_EMPOWERING;
    }

    @Override
    public Text getTitle() {
        return Text.literal("Gem Empowering Station");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.GEM_EMPOWERING_STATION.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE,
                new Rectangle(startPoint.x, startPoint.y, 175, 82)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 11))
                .entries(display.getInputEntries().get(0)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 59))
                .markOutput().entries(display.getOutputEntries().get(0)));

        // ENERGY
        widgets.add(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
            Rect2i area = new Rect2i(startPoint.x + 156, startPoint.y + 11, 8, 64);
            final int height = area.getHeight();
            int stored = (int)Math.ceil(height * (((GemEmpoweringDisplay) display).getTotalEnergyAmount() / 64000f));

            graphics.fillGradient(area.getX(), area.getY() + (height - stored),
                    area.getX() + area.getWidth(), area.getY() + area.getHeight(),
                    0xffb51500, 0xff600b00);
        }));
        widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 156, startPoint.y + 11, 8, 64), Text.literal("Needs " + ((GemEmpoweringDisplay) display).getTotalEnergyAmount() + " E")));

        // FLUIDS
        widgets.add(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
            int color = FluidVariantRendering.getColor(FluidVariant.of(Fluids.WATER));
            RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, 1F);

            graphics.drawSprite(startPoint.x + 26, startPoint.y + 49, 1, 16, 1, FluidVariantRendering.getSprite(FluidVariant.of(Fluids.WATER)));
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        }));
        widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 26, startPoint.y + 11, 16, 39), Text.literal("Needs 500mB Water")));


        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }
}
