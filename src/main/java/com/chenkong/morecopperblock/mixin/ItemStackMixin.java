package com.chenkong.morecopperblock.mixin;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import com.chenkong.morecopperblock.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "getHoverName", at = @At("HEAD"), cancellable = true)
    private void onGetHoverName(CallbackInfoReturnable<Component> cir) {
        ItemStack self = (ItemStack) (Object) this;

        // Copper ingots with composition (9-value composition = 9 nuggets)
        if (isCopperIngot(self)) {
            List<String> comp = self.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
            if (comp != null && comp.size() == 9 && !AlloyCopperBlockItem.isDefaultIngotComposition(comp)) {
                cir.setReturnValue(AlloyCopperBlockItem.generateIngotLocalizedName(comp));
            }
            return;
        }

        // Copper nuggets with composition (9-value composition = 9 nuggets)
        if (isCopperNugget(self)) {
            List<String> comp = self.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
            if (comp != null && comp.size() == 9 && !AlloyCopperBlockItem.isDefaultIngotComposition(comp)) {
                cir.setReturnValue(AlloyCopperBlockItem.generateNuggetLocalizedName(comp));
            }
            return;
        }

        // Cut alloy stairs (216-value composition = 6 cut blocks × 36)
        if (self.is(MoreCopperBlock.CUT_ALLOY_COPPER_STAIRS_ITEM.get())
                || self.is(MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_STAIRS_ITEM.get())
                || self.is(MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_STAIRS_ITEM.get())
                || self.is(MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_STAIRS_ITEM.get())) {
            List<String> nested = AlloyCopperBlockItem.getNestedUnits(self, 216);
            if (nested != null) {
                cir.setReturnValue(AlloyCopperBlockItem.generateCutStairsNameNested(nested));
                return;
            }
            List<Integer> comp = self.get(AlloyCopperBlockItem.STAIRS_COMPOSITION.get());
            if (comp != null && comp.size() == 216) {
                cir.setReturnValue(AlloyCopperBlockItem.generateCutStairsName(comp));
            }
            return;
        }

        // Chiseled alloy copper blocks (216-value composition = 2 slabs × 108)
        if (self.is(MoreCopperBlock.CHISELED_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.EXPOSED_CHISELED_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.WEATHERED_CHISELED_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.OXIDIZED_CHISELED_ALLOY_COPPER_BLOCK_ITEM.get())) {
            List<String> nested = AlloyCopperBlockItem.getNestedUnits(self, 216);
            if (nested != null) {
                cir.setReturnValue(AlloyCopperBlockItem.generateChiseledNameNested(nested));
                return;
            }
            List<Integer> comp = self.get(AlloyCopperBlockItem.CHISELED_COMPOSITION.get());
            if (comp != null && comp.size() == 216) {
                cir.setReturnValue(AlloyCopperBlockItem.generateChiseledName(comp));
            }
            return;
        }

        // Cut alloy slabs (108-value composition = 3 cut blocks × 36)
        if (self.is(MoreCopperBlock.CUT_ALLOY_COPPER_SLAB_ITEM.get())
                || self.is(MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_SLAB_ITEM.get())
                || self.is(MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_SLAB_ITEM.get())
                || self.is(MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_SLAB_ITEM.get())) {
            List<String> nested = AlloyCopperBlockItem.getNestedUnits(self, 108);
            if (nested != null) {
                cir.setReturnValue(AlloyCopperBlockItem.generateCutSlabNameNested(nested));
                return;
            }
            List<Integer> comp = self.get(AlloyCopperBlockItem.SLAB_COMPOSITION.get());
            if (comp != null && comp.size() == 108) {
                cir.setReturnValue(AlloyCopperBlockItem.generateCutSlabName(comp));
            }
            return;
        }

        // Cut alloy copper blocks (36-value composition)
        if (self.is(MoreCopperBlock.CUT_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_BLOCK_ITEM.get())) {
            List<String> nested = AlloyCopperBlockItem.getNestedUnits(self, 36);
            if (nested != null) {
                cir.setReturnValue(AlloyCopperBlockItem.generateCutLocalizedNameNested(nested));
                return;
            }
            List<Integer> comp = self.get(AlloyCopperBlockItem.CUT_COMPOSITION.get());
            if (comp != null && comp.size() == 36) {
                cir.setReturnValue(AlloyCopperBlockItem.generateCutLocalizedName(comp));
            }
            return;
        }

        // Regular alloy copper blocks (9-value composition)
        if (self.is(MoreCopperBlock.ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.EXPOSED_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.WEATHERED_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.OXIDIZED_ALLOY_COPPER_BLOCK_ITEM.get())) {
            // Nested naming takes priority (block built from named ingots)
            List<String> strComp = self.get(AlloyCopperBlockItem.INGOT_COMPOSITION.get());
            if (strComp != null && strComp.size() == 9
                    && !AlloyCopperBlockItem.isDefaultIngotComposition(strComp)) {
                cir.setReturnValue(AlloyCopperBlockItem.generateBlockLocalizedName(strComp));
                return;
            }
            List<Integer> comp = self.get(AlloyCopperBlockItem.COMPOSITION.get());
            if (comp != null && !comp.equals(AlloyCopperBlockItem.defaultComposition())) {
                cir.setReturnValue(AlloyCopperBlockItem.generateLocalizedName(comp));
            }
        }
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
}
