package com.chenkong.morecopperblock.recipe;

import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import com.chenkong.morecopperblock.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CopperNuggetComposeRecipe extends CustomRecipe {

    public CopperNuggetComposeRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        // 只接受3x3的输入
        if (input.width() != 3 || input.height() != 3) return false;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                ItemStack stack = input.getItem(col, row);
                if (stack.isEmpty()) return false;
                if (!isCopperNugget(stack)) return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        // 收集所有9个铜粒的composition信息（使用String支持嵌套）
        List<String> composition = new ArrayList<>(9);
        boolean allSame = true;
        String firstValue = null;

        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            ItemStack nuggetStack = input.getItem(col, row);
            String value = getNuggetCompositionValue(nuggetStack);
            composition.add(value);

            if (i == 0) {
                firstValue = value;
            } else if (!AlloyCopperBlockItem.compositionEquals(value, firstValue)) {
                allSame = false;
            }
        }

        // 所有9个铜粒相同类型且没有composition数据 -> 输出普通铜锭
        if (allSame && firstValue != null && !firstValue.startsWith("[")) {
            int typeId = Integer.parseInt(firstValue);
            if (hasNoCompositionData(input)) {
                return new ItemStack(getCorrespondingIngot(typeId));
            }
        }

        // 计算平均氧化程度（用于决定输出铜锭类型），递归处理嵌套composition
        double sum = 0;
        for (String value : composition) {
            sum += AlloyCopperBlockItem.averageOxidation(value); // 0=铜, 1=斑驳, 2=锈蚀, 3=氧化
        }
        double avg = sum / 9.0;

        // 决定输出的铜锭类型
        int outputTypeId;
        if (avg < 0.5) {
            outputTypeId = 0; // 铜
        } else if (avg < 1.5) {
            outputTypeId = 1; // 斑驳
        } else if (avg < 2.5) {
            outputTypeId = 2; // 锈蚀
        } else {
            outputTypeId = 3; // 氧化
        }

        ItemStack result = new ItemStack(getCorrespondingIngot(outputTypeId));

        // 检查是否有任何铜粒带有composition数据
        boolean hasAnyComposition = false;
        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            ItemStack nuggetStack = input.getItem(col, row);
            List<String> nuggetComp = nuggetStack.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
            if (nuggetComp != null && !AlloyCopperBlockItem.isDefaultIngotComposition(nuggetComp)) {
                hasAnyComposition = true;
                break;
            }
        }

        // 如果有composition数据或混合类型，设置composition
        if (hasAnyComposition || !allSame) {
            result.set(AlloyCopperBlockItem.INGOT_COMPOSITION.get(), composition);
        }

        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.COPPER_NUGGET_COMPOSE_RECIPE.get();
    }

    /**
     * Gets the composition value for a copper nugget.
     * Returns a String that is either:
     * - A simple type ID ("0"-"7") for regular nuggets
     * - A nested composition string like "[3,5,5,3,5,6,6,6]" for nuggets with composition
     */
    private String getNuggetCompositionValue(ItemStack nuggetStack) {
        // Check if this nugget has its own composition data
        List<String> existingComp = nuggetStack.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
        if (existingComp != null && !AlloyCopperBlockItem.isDefaultIngotComposition(existingComp)) {
            // This nugget has a composition - store it as a nested string
            return existingComp.toString();
        }

        // Regular nugget - return simple type ID
        return String.valueOf(getNuggetTypeId(nuggetStack));
    }

    private boolean hasNoCompositionData(CraftingInput input) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                ItemStack stack = input.getItem(col, row);
                List<String> comp = stack.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
                if (comp != null && !AlloyCopperBlockItem.isDefaultIngotComposition(comp)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isCopperNugget(ItemStack stack) {
        return stack.is(Items.COPPER_NUGGET)
                || stack.is(ModItems.EXPOSED_COPPER_NUGGET.get())
                || stack.is(ModItems.WEATHERED_COPPER_NUGGET.get())
                || stack.is(ModItems.OXIDIZED_COPPER_NUGGET.get())
                || stack.is(ModItems.WAXED_COPPER_NUGGET.get())
                || stack.is(ModItems.WAXED_EXPOSED_COPPER_NUGGET.get())
                || stack.is(ModItems.WAXED_WEATHERED_COPPER_NUGGET.get())
                || stack.is(ModItems.WAXED_OXIDIZED_COPPER_NUGGET.get());
    }

    private int getNuggetTypeId(ItemStack stack) {
        if (stack.is(Items.COPPER_NUGGET)) return 0;
        if (stack.is(ModItems.EXPOSED_COPPER_NUGGET.get())) return 1;
        if (stack.is(ModItems.WEATHERED_COPPER_NUGGET.get())) return 2;
        if (stack.is(ModItems.OXIDIZED_COPPER_NUGGET.get())) return 3;
        if (stack.is(ModItems.WAXED_COPPER_NUGGET.get())) return 4;
        if (stack.is(ModItems.WAXED_EXPOSED_COPPER_NUGGET.get())) return 5;
        if (stack.is(ModItems.WAXED_WEATHERED_COPPER_NUGGET.get())) return 6;
        if (stack.is(ModItems.WAXED_OXIDIZED_COPPER_NUGGET.get())) return 7;
        return 0;
    }

    private Item getCorrespondingIngot(int typeId) {
        return switch (typeId) {
            case 0 -> Items.COPPER_INGOT;
            case 1 -> ModItems.EXPOSED_COPPER_INGOT.get();
            case 2 -> ModItems.WEATHERED_COPPER_INGOT.get();
            case 3 -> ModItems.OXIDIZED_COPPER_INGOT.get();
            case 4 -> ModItems.WAXED_COPPER_INGOT.get();
            case 5 -> ModItems.WAXED_EXPOSED_COPPER_INGOT.get();
            case 6 -> ModItems.WAXED_WEATHERED_COPPER_INGOT.get();
            case 7 -> ModItems.WAXED_OXIDIZED_COPPER_INGOT.get();
            default -> Items.COPPER_INGOT;
        };
    }
}
