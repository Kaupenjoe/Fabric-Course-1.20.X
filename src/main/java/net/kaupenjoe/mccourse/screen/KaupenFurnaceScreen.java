package net.kaupenjoe.mccourse.screen;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class KaupenFurnaceScreen extends AbstractFurnaceScreen<KaupenFurnaceScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(MCCourseMod.MOD_ID, "textures/gui/kaupen_furnace.png");
    private static final Identifier LIT_PROGRESS_TEXTURE = new Identifier("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = new Identifier("container/furnace/burn_progress");

    public KaupenFurnaceScreen(KaupenFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new KaupenFurnaceRecipeBookComponent(), inventory, title, TEXTURE, LIT_PROGRESS_TEXTURE, BURN_PROGRESS_TEXTURE);
    }
}
