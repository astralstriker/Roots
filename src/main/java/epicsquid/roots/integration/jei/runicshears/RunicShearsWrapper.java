package epicsquid.roots.integration.jei.runicshears;

import epicsquid.roots.recipe.RunicShearRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class RunicShearsWrapper implements IRecipeWrapper {

  public final RunicShearRecipe recipe;

  public RunicShearsWrapper(RunicShearRecipe recipe) {
    this.recipe = recipe;
  }

  @Override
  public void getIngredients(IIngredients ingredients) {
    ingredients.setInput(VanillaTypes.ITEM, this.recipe.getOptionalDisplayItem());
    ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(this.recipe.getDrop()));
  }
}
