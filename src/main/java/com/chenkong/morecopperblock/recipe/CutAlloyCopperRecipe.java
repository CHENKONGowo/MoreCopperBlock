package com.chenkong.morecopperblock.recipe;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopperCollection;

import java.util.ArrayList;
import java.util.List;

public class CutAlloyCopperRecipe extends CustomRecipe {

    public CutAlloyCopperRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.width() != 2 || input.height() != 2) return false;

        boolean hasAlloy = false;
        Block firstVanilla = null;
        boolean allVanillaSame = true;

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                ItemStack stack = input.getItem(col, row);
                if (stack.isEmpty()) return false;
                if (!isCopperBlock(stack)) return false;

                Block block = ((BlockItem) stack.getItem()).getBlock();
                if (isAlloyBlock(block)) {
                    hasAlloy = true;
                } else {
                    if (firstVanilla == null) firstVanilla = block;
                    else if (block != firstVanilla) allVanillaSame = false;
                }
            }
        }

        // If all 4 are the same vanilla block, let the vanilla recipe handle it
        if (!hasAlloy && allVanillaSame) return false;

        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        List<Integer> cutComposition = new ArrayList<>(36);
        List<String> nestedUnits = new ArrayList<>(36);
        boolean hasNested = false;
        double sum = 0;

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                ItemStack stack = input.getItem(col, row);
                List<Integer> comp = getBlockComposition(stack);
                for (int v : comp) {
                    cutComposition.add(v);
                    sum += v % 4;
                }
                // 嵌套单元（来自命名铜锭的命名继承）
                List<String> units = getBlockNestedUnits(stack);
                nestedUnits.addAll(units);
                if (hasAnyNested(units)) hasNested = true;
            }
        }

        double avg = sum / 36.0;

        Block output;
        if (avg < 0.5)       output = MoreCopperBlock.CUT_ALLOY_COPPER_BLOCK.get();
        else if (avg < 1.5)  output = MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_BLOCK.get();
        else if (avg < 2.5)  output = MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_BLOCK.get();
        else                 output = MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_BLOCK.get();

        ItemStack result = new ItemStack(output, 4);
        result.set(AlloyCopperBlockItem.CUT_COMPOSITION.get(), cutComposition);
        if (hasNested) {
            result.set(AlloyCopperBlockItem.INGOT_COMPOSITION.get(), nestedUnits);
        }
        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.CUT_ALLOY_COPPER_RECIPE.get();
    }

    private static boolean isAlloyBlock(Block block) {
        return block == MoreCopperBlock.ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.EXPOSED_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.WEATHERED_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.OXIDIZED_ALLOY_COPPER_BLOCK.get();
    }

    /** True if the stack is a vanilla copper block or an alloy copper block. */
    private static boolean isCopperBlock(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem bi)) return false;
        Block block = bi.getBlock();

        // Alloy copper blocks
        if (block == MoreCopperBlock.ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.EXPOSED_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.WEATHERED_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.OXIDIZED_ALLOY_COPPER_BLOCK.get())
            return true;

        // Vanilla copper blocks
        WeatheringCopperCollection<Block> c = Blocks.COPPER_BLOCK;
        return block == c.weathering().unaffected()
                || block == c.weathering().exposed()
                || block == c.weathering().weathered()
                || block == c.weathering().oxidized()
                || block == c.waxed().unaffected()
                || block == c.waxed().exposed()
                || block == c.waxed().weathered()
                || block == c.waxed().oxidized();
    }

    /** Gets the 9-value composition of a copper block. */
    private static List<Integer> getBlockComposition(ItemStack stack) {
        // Alloy copper block — read stored composition
        List<Integer> comp = stack.get(AlloyCopperBlockItem.COMPOSITION.get());
        if (comp != null && comp.size() == 9) return comp;

        // Vanilla copper block — derive from block type
        Block block = ((BlockItem) stack.getItem()).getBlock();
        WeatheringCopperCollection<Block> c = Blocks.COPPER_BLOCK;
        int type;
        if (block == c.weathering().unaffected() || block == c.waxed().unaffected())      type = 0;
        else if (block == c.weathering().exposed() || block == c.waxed().exposed())        type = 1;
        else if (block == c.weathering().weathered() || block == c.waxed().weathered())    type = 2;
        else                                                                                type = 3;

        List<Integer> defaultComp = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) defaultComp.add(type);
        return defaultComp;
    }

    /**
     * Gets the 9 nested unit strings of an input copper block.
     * Prefers the stored nested composition (from named ingots), then the
     * simple composition, and finally falls back to the block type.
     */
    private static List<String> getBlockNestedUnits(ItemStack stack) {
        // Alloy copper block with nested naming
        List<String> nested = stack.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
        if (nested != null && nested.size() == 9) return nested;

        // Alloy copper block with simple composition
        List<Integer> comp = stack.get(AlloyCopperBlockItem.COMPOSITION.get());
        if (comp != null && comp.size() == 9) {
            List<String> result = new ArrayList<>(9);
            for (int v : comp) result.add(String.valueOf(v));
            return result;
        }

        // Vanilla copper block — derive from block type
        Block block = ((BlockItem) stack.getItem()).getBlock();
        WeatheringCopperCollection<Block> c = Blocks.COPPER_BLOCK;
        int type;
        if (block == c.weathering().unaffected() || block == c.waxed().unaffected())      type = 0;
        else if (block == c.weathering().exposed() || block == c.waxed().exposed())        type = 1;
        else if (block == c.weathering().weathered() || block == c.waxed().weathered())    type = 2;
        else                                                                                type = 3;

        List<String> result = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) result.add(String.valueOf(type));
        return result;
    }

    /** True if any unit is a nested bracket notation (named ingot). */
    private static boolean hasAnyNested(List<String> units) {
        for (String s : units) {
            if (s.startsWith("[")) return true;
        }
        return false;
    }
}
