package com.chenkong.morecopperblock.recipe;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import com.chenkong.morecopperblock.item.ModItems;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopperCollection;

public class AlloyCopperRecipe extends CustomRecipe {

    public AlloyCopperRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.width() != 3 || input.height() != 3) return false;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                ItemStack stack = input.getItem(col, row);
                if (stack.isEmpty()) return false;
                if (!isCopperIngot(stack)) return false;
            }
        }
        return true;
    }

    /** Vanilla copper block for each ingot type. */
    private static Block getVanillaCopperBlock(int typeId) {
        WeatheringCopperCollection<Block> c = Blocks.COPPER_BLOCK;
        return switch (typeId) {
            case 0 -> c.weathering().unaffected();
            case 1 -> c.weathering().exposed();
            case 2 -> c.weathering().weathered();
            case 3 -> c.weathering().oxidized();
            case 4 -> c.waxed().unaffected();
            case 5 -> c.waxed().exposed();
            case 6 -> c.waxed().weathered();
            case 7 -> c.waxed().oxidized();
            default -> c.weathering().unaffected();
        };
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        int[] types = new int[9];
        String[] units = new String[9];
        int firstType = -1;
        boolean allSame = true;
        boolean hasNested = false;

        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            ItemStack ingot = input.getItem(col, row);
            types[i] = getIngotTypeId(ingot);
            units[i] = getIngotUnitString(ingot);
            if (units[i].startsWith("[")) hasNested = true;

            if (i == 0) {
                firstType = types[i];
            } else if (types[i] != firstType) {
                allSame = false;
            }
        }

        // All 9 ingots are the same simple type and none is nested
        // → craft the corresponding vanilla copper block
        if (allSame && !hasNested) {
            return new ItemStack(getVanillaCopperBlock(firstType));
        }

        // Mixed types or nested ingots → calculate average oxidation (mod 4 to unwax)
        // and output the matching alloy copper block variant.
        double sum = 0;
        for (int t : types) {
            sum += t % 4; // 0=copper, 1=exposed, 2=weathered, 3=oxidized
        }
        double avg = sum / 9.0;

        Block output;
        if (avg < 0.5) {
            output = MoreCopperBlock.ALLOY_COPPER_BLOCK.get();
        } else if (avg < 1.5) {
            output = MoreCopperBlock.EXPOSED_ALLOY_COPPER_BLOCK.get();
        } else if (avg < 2.5) {
            output = MoreCopperBlock.WEATHERED_ALLOY_COPPER_BLOCK.get();
        } else {
            output = MoreCopperBlock.OXIDIZED_ALLOY_COPPER_BLOCK.get();
        }

        ItemStack result = new ItemStack(output);
        List<Integer> composition = new ArrayList<>(9);
        for (int t : types) composition.add(t);
        result.set(AlloyCopperBlockItem.COMPOSITION.get(), composition);

        // Preserve nested ingot units for naming inheritance
        if (hasNested) {
            List<String> unitList = new ArrayList<>(9);
            for (String u : units) unitList.add(u);
            result.set(AlloyCopperBlockItem.INGOT_COMPOSITION.get(), unitList);
        }
        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.ALLOY_COPPER_RECIPE.get();
    }

    private boolean isCopperIngot(ItemStack stack) {
        return stack.is(Items.COPPER_INGOT)
                || stack.is(ModItems.EXPOSED_COPPER_INGOT.get())
                || stack.is(ModItems.WEATHERED_COPPER_INGOT.get())
                || stack.is(ModItems.OXIDIZED_COPPER_INGOT.get())
                || stack.is(ModItems.WAXED_COPPER_INGOT.get())
                || stack.is(ModItems.WAXED_EXPOSED_COPPER_INGOT.get())
                || stack.is(ModItems.WAXED_WEATHERED_COPPER_INGOT.get())
                || stack.is(ModItems.WAXED_OXIDIZED_COPPER_INGOT.get());
    }

    /**
     * Gets the composition unit string for an ingot slot.
     * Simple types return "0"-"7"; named ingots (carrying a nested
     * composition) return their composition's bracket notation so the
     * resulting block name can inherit it.
     */
    private String getIngotUnitString(ItemStack ingot) {
        List<String> nested = ingot.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
        if (nested != null && !AlloyCopperBlockItem.isDefaultIngotComposition(nested)) {
            return nested.toString();
        }
        return String.valueOf(getIngotTypeId(ingot));
    }

    private int getIngotTypeId(ItemStack stack) {
        if (stack.is(Items.COPPER_INGOT)) return 0;
        if (stack.is(ModItems.EXPOSED_COPPER_INGOT.get())) return 1;
        if (stack.is(ModItems.WEATHERED_COPPER_INGOT.get())) return 2;
        if (stack.is(ModItems.OXIDIZED_COPPER_INGOT.get())) return 3;
        if (stack.is(ModItems.WAXED_COPPER_INGOT.get())) return 4;
        if (stack.is(ModItems.WAXED_EXPOSED_COPPER_INGOT.get())) return 5;
        if (stack.is(ModItems.WAXED_WEATHERED_COPPER_INGOT.get())) return 6;
        if (stack.is(ModItems.WAXED_OXIDIZED_COPPER_INGOT.get())) return 7;
        return 0;
    }
}
