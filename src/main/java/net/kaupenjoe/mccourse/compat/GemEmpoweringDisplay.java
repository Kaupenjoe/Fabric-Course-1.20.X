package net.kaupenjoe.mccourse.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.kaupenjoe.mccourse.recipe.GemEmpoweringRecipe;
import net.minecraft.recipe.RecipeEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GemEmpoweringDisplay extends BasicDisplay {
    private RecipeEntry<GemEmpoweringRecipe> recipe;

    public GemEmpoweringDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public GemEmpoweringDisplay(RecipeEntry<GemEmpoweringRecipe> recipe) {
        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryStacks.of(recipe.value().getResult(null)))));
        this.recipe = recipe;
    }

    private static List<EntryIngredient> getInputList(GemEmpoweringRecipe recipe) {
        if(recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
        return list;
    }

    public int getCraftTime() {
        return recipe.value().getCraftTime();
    }

    public int getEnergyAmount() {
        return recipe.value().getEnergyAmount();
    }

    public int getTotalEnergyAmount() {
        return getCraftTime() * getEnergyAmount();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return GemEmpoweringCategory.GEM_EMPOWERING;
    }
}
