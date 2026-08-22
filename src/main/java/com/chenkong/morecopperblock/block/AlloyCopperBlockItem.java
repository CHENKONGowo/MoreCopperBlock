package com.chenkong.morecopperblock.block;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class AlloyCopperBlockItem {
    private static final String[] TYPE_TRANSLATION_KEYS = {
            "alloy.morecopperblock.type.copper",
            "alloy.morecopperblock.type.exposed",
            "alloy.morecopperblock.type.weathered",
            "alloy.morecopperblock.type.oxidized",
            "alloy.morecopperblock.type.waxed",
            "alloy.morecopperblock.type.waxed_exposed",
            "alloy.morecopperblock.type.waxed_weathered",
            "alloy.morecopperblock.type.waxed_oxidized"
    };

    private static final String[] COUNT_TRANSLATION_KEYS = {
            null,
            "alloy.morecopperblock.count.2",
            "alloy.morecopperblock.count.3",
            "alloy.morecopperblock.count.4",
            "alloy.morecopperblock.count.5",
            "alloy.morecopperblock.count.6",
            "alloy.morecopperblock.count.7",
            "alloy.morecopperblock.count.8",
            "alloy.morecopperblock.count.9"
    };

    static final String CUT_SUFFIX_TRANSLATION_KEY = "alloy.morecopperblock.cut_suffix";
    private static final String CUT_STAIRS_SUFFIX_KEY = "alloy.morecopperblock.cut_stairs_suffix";
    private static final String CUT_SLAB_SUFFIX_KEY = "alloy.morecopperblock.cut_slab_suffix";
    private static final String CHISELED_SUFFIX_KEY = "alloy.morecopperblock.chiseled_suffix";
    private static final String SUFFIX_TRANSLATION_KEY = "alloy.morecopperblock.suffix";
    private static final String INGOT_SUFFIX_TRANSLATION_KEY = "alloy.morecopperblock.ingot_suffix";
    private static final String NUGGET_SUFFIX_TRANSLATION_KEY = "alloy.morecopperblock.nugget_suffix";

    public static List<Integer> defaultComposition() {
        return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public static final Codec<List<Integer>> COMPOSITION_CODEC = Codec.INT.listOf(0, 9);

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
            for (int i = 0; i < 9; i++) buf.writeByte(list.get(i));
        }
        @Override public List<Integer> decode(RegistryFriendlyByteBuf buf) {
            List<Integer> list = new ArrayList<>(9);
            for (int i = 0; i < 9; i++) list.add((int) buf.readByte());
            return list;
        }
    };

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MoreCopperBlock.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> COMPOSITION =
            DATA_COMPONENTS.register("alloy_composition",
                    () -> DataComponentType.<List<Integer>>builder()
                            .persistent(COMPOSITION_CODEC)
                            .networkSynchronized(COMPOSITION_STREAM_CODEC)
                            .build());

    // ---- Cut alloy (36 values = 4 input blocks × 9) ----

    public static final Codec<List<Integer>> CUT_COMPOSITION_CODEC = Codec.INT.listOf(0, 36);

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> CUT_COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
            for (int i = 0; i < 36; i++) buf.writeByte(list.get(i));
        }
        @Override public List<Integer> decode(RegistryFriendlyByteBuf buf) {
            List<Integer> list = new ArrayList<>(36);
            for (int i = 0; i < 36; i++) list.add((int) buf.readByte());
            return list;
        }
    };

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> CUT_COMPOSITION =
            DATA_COMPONENTS.register("cut_alloy_composition",
                    () -> DataComponentType.<List<Integer>>builder()
                            .persistent(CUT_COMPOSITION_CODEC)
                            .networkSynchronized(CUT_COMPOSITION_STREAM_CODEC)
                            .build());

    // ---- Cut stairs (216 values = 6 cut blocks × 36) ----

    public static final Codec<List<Integer>> STAIRS_COMPOSITION_CODEC = Codec.INT.listOf(0, 216);

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> STAIRS_COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
            for (int i = 0; i < 216; i++) buf.writeByte(list.get(i));
        }
        @Override public List<Integer> decode(RegistryFriendlyByteBuf buf) {
            List<Integer> list = new ArrayList<>(216);
            for (int i = 0; i < 216; i++) list.add((int) buf.readByte());
            return list;
        }
    };

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> STAIRS_COMPOSITION =
            DATA_COMPONENTS.register("stairs_alloy_composition",
                    () -> DataComponentType.<List<Integer>>builder()
                            .persistent(STAIRS_COMPOSITION_CODEC)
                            .networkSynchronized(STAIRS_COMPOSITION_STREAM_CODEC)
                            .build());

    // ---- Cut slabs (108 values = 3 cut blocks × 36) ----

    public static final Codec<List<Integer>> SLAB_COMPOSITION_CODEC = Codec.INT.listOf(0, 108);

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> SLAB_COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
            for (int i = 0; i < 108; i++) buf.writeByte(list.get(i));
        }
        @Override public List<Integer> decode(RegistryFriendlyByteBuf buf) {
            List<Integer> list = new ArrayList<>(108);
            for (int i = 0; i < 108; i++) list.add((int) buf.readByte());
            return list;
        }
    };

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> SLAB_COMPOSITION =
            DATA_COMPONENTS.register("slab_alloy_composition",
                    () -> DataComponentType.<List<Integer>>builder()
                            .persistent(SLAB_COMPOSITION_CODEC)
                            .networkSynchronized(SLAB_COMPOSITION_STREAM_CODEC)
                            .build());

    // ---- Chiseled (216 values = 2 slabs × 108) ----

    public static final Codec<List<Integer>> CHISELED_COMPOSITION_CODEC = Codec.INT.listOf(0, 216);

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> CHISELED_COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
            for (int i = 0; i < 216; i++) buf.writeByte(list.get(i));
        }
        @Override public List<Integer> decode(RegistryFriendlyByteBuf buf) {
            List<Integer> list = new ArrayList<>(216);
            for (int i = 0; i < 216; i++) list.add((int) buf.readByte());
            return list;
        }
    };

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> CHISELED_COMPOSITION =
            DATA_COMPONENTS.register("chiseled_alloy_composition",
                    () -> DataComponentType.<List<Integer>>builder()
                            .persistent(CHISELED_COMPOSITION_CODEC)
                            .networkSynchronized(CHISELED_COMPOSITION_STREAM_CODEC)
                            .build());

    // ---- Ingot composition (9 values = 9 nuggets, supports nesting) ----
    // Each element is a String:
    // - Simple type: "0", "1", ..., "7" (type ID)
    // - Nested: "[3,5,5,3,5,6,6,6]" (JSON array of nested composition)

    public static final Codec<List<String>> INGOT_COMPOSITION_CODEC = Codec.STRING.listOf();

    public static final StreamCodec<RegistryFriendlyByteBuf, List<String>> INGOT_COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<String> list) {
            buf.writeVarInt(list.size());
            for (String s : list) buf.writeUtf(s);
        }
        @Override public List<String> decode(RegistryFriendlyByteBuf buf) {
            int size = buf.readVarInt();
            List<String> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) list.add(buf.readUtf());
            return list;
        }
    };

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<String>>> INGOT_COMPOSITION =
            DATA_COMPONENTS.register("ingot_alloy_composition",
                    () -> DataComponentType.<List<String>>builder()
                            .persistent(INGOT_COMPOSITION_CODEC)
                            .networkSynchronized(INGOT_COMPOSITION_STREAM_CODEC)
                            .build());

    public static Component generateLocalizedName(List<Integer> composition) {
        return buildInnerName(composition).append(Component.translatable(SUFFIX_TRANSLATION_KEY));
    }

    /**
     * Generates the localized name for an alloy copper ingot from its 9-element
     * composition (9 nuggets).
     * <p>
     * Format: positions of identical nugget types, count prefix (if &gt;1),
     * nugget type name, then "铜锭" suffix.
     * Supports arbitrarily nested composition for copper nuggets.
     * Example: {@code 1,2,3,4,5-五(1,2,3,5,6-五涂蜡的锈蚀-4-涂蜡的斑驳-7,8,9-三氧化)-6,7,8,9-四氧化铜锭}
     */
    public static Component generateIngotLocalizedName(List<String> composition) {
        List<Object> units = new ArrayList<>(composition.size());
        for (String s : composition) {
            units.add(parseUnit(s));
        }
        return buildNestedUnitName(units).append(Component.translatable(INGOT_SUFFIX_TRANSLATION_KEY));
    }

    /**
     * Generates the localized name for a copper nugget carrying a full
     * 9-element composition (preserved from a decomposed named ingot).
     * Same inner naming as the ingot, but with a "铜粒" suffix.
     */
    public static Component generateNuggetLocalizedName(List<String> composition) {
        List<Object> units = new ArrayList<>(composition.size());
        for (String s : composition) {
            units.add(parseUnit(s));
        }
        return buildNestedUnitName(units).append(Component.translatable(NUGGET_SUFFIX_TRANSLATION_KEY));
    }

    /**
     * Generates the localized name for an alloy copper block from a 9-element
     * composition whose units may be nested (named ingots used as input).
     * Each unit is either a simple type ID or a nested bracket notation.
     */
    public static Component generateBlockLocalizedName(List<String> composition) {
        List<Object> units = new ArrayList<>(composition.size());
        for (String s : composition) {
            units.add(parseUnit(s));
        }
        return buildNestedUnitName(units).append(Component.translatable(SUFFIX_TRANSLATION_KEY));
    }

    // ===== Nested (List<String>) naming for cut variants =====

    /**
     * Cut alloy copper block name from 36 nested units (4 blocks × 9 ingot-units).
     */
    public static Component generateCutLocalizedNameNested(List<String> nested) {
        return groupNestedName(nested, 9, "(", ")")
                .append(Component.translatable(CUT_SUFFIX_TRANSLATION_KEY));
    }

    /**
     * Cut alloy stairs name from 216 nested units (6 cut blocks × 36).
     */
    public static Component generateCutStairsNameNested(List<String> nested) {
        return groupNestedName(nested, 36, "[", "]")
                .append(Component.translatable(CUT_STAIRS_SUFFIX_KEY));
    }

    /**
     * Cut alloy slab name from 108 nested units (3 cut blocks × 36).
     */
    public static Component generateCutSlabNameNested(List<String> nested) {
        return groupNestedName(nested, 36, "[", "]")
                .append(Component.translatable(CUT_SLAB_SUFFIX_KEY));
    }

    /**
     * Chiseled alloy copper block name from 216 nested units (2 slabs × 108).
     */
    public static Component generateChiseledNameNested(List<String> nested) {
        return groupNestedName(nested, 108, "{", "}")
                .append(Component.translatable(CHISELED_SUFFIX_KEY));
    }

    /**
     * Groups units into blocks of {@code groupSize}, skips all-zero groups, and
     * renders each group as {@code pos[-count]open inner close}, joined by "-".
     * The inner name is generated recursively down to the 9-unit leaf level,
     * where {@link #buildNestedUnitName} is used.
     */
    private static MutableComponent groupNestedName(List<String> units, int groupSize, String open, String close) {
        int blockCount = units.size() / groupSize;
        List<List<String>> groups = new ArrayList<>(blockCount);
        for (int b = 0; b < blockCount; b++) {
            groups.add(new ArrayList<>(units.subList(b * groupSize, (b + 1) * groupSize)));
        }

        Map<String, List<Integer>> grouped = new LinkedHashMap<>();
        for (int b = 0; b < blockCount; b++) {
            if (isAllZeroComposition(groups.get(b))) continue;
            grouped.computeIfAbsent(groups.get(b).toString(), k -> new ArrayList<>()).add(b + 1);
        }

        List<Map.Entry<String, List<Integer>>> sorted = new ArrayList<>(grouped.entrySet());
        sorted.sort(Comparator.comparingInt(e -> e.getValue().get(0)));

        MutableComponent result = Component.empty();
        for (Map.Entry<String, List<Integer>> entry : sorted) {
            List<Integer> positions = entry.getValue();
            int count = positions.size();

            if (!result.getString().isEmpty()) {
                result.append(Component.literal("-"));
            }

            String posStr = positions.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            result.append(Component.literal(posStr));
            result.append(Component.literal("-"));

            if (count >= 2) {
                result.append(Component.translatable(COUNT_TRANSLATION_KEYS[count - 1]));
            }

            result.append(Component.literal(open));
            result.append(nestedInnerName(groups.get(positions.get(0) - 1), groupSize));
            result.append(Component.literal(close));
        }
        return result;
    }

    /**
     * Renders the inner name of a group of {@code groupSize} units:
     * 9 → leaf unit name; 36 → grouped 4×9; 108 → grouped 3×36.
     */
    private static MutableComponent nestedInnerName(List<String> units, int groupSize) {
        if (groupSize <= 9) {
            List<Object> parsed = new ArrayList<>(units.size());
            for (String s : units) {
                parsed.add(parseUnit(s));
            }
            return buildNestedUnitName(parsed);
        }
        if (groupSize == 36) {
            return groupNestedName(units, 9, "(", ")");
        }
        return groupNestedName(units, 36, "[", "]");
    }

    /**
     * Returns the nested composition units stored on an alloy item stack,
     * or null when absent / all-zero / wrong size.
     */
    public static List<String> getNestedUnits(ItemStack stack, int expectedSize) {
        List<String> nested = stack.get(INGOT_COMPOSITION.get());
        if (nested != null && nested.size() == expectedSize && !isAllZeroComposition(nested)) {
            return nested;
        }
        return null;
    }

    /**
     * True if every element is "0" (works for any list size).
     */
    public static boolean isAllZeroComposition(List<String> composition) {
        if (composition == null) return true;
        for (String s : composition) {
            if (!"0".equals(s)) return false;
        }
        return true;
    }

    /**
     * Parses a single composition unit: a simple type ID, or a nested bracket
     * notation like "[3,5,5,3,5,6,6,6]".
     *
     * @return an Integer for simple types, or a List&lt;Object&gt; for nested units
     *         (elements are again Integer or List&lt;Object&gt;)
     */
    public static Object parseUnit(String s) {
        s = s.trim();
        if (s.startsWith("[")) return parseNestedList(s);
        return Integer.parseInt(s);
    }

    /**
     * Recursively parses a bracket notation string into a nested structure.
     * Elements are Integer (type IDs) or nested List&lt;Object&gt;.
     * Example: {@code "[1, [2, 3], 4]"} -&gt; {@code [1, [2, 3], 4]}
     */
    public static List<Object> parseNestedList(String s) {
        s = s.trim();
        if (s.startsWith("[")) s = s.substring(1, s.length() - 1);
        List<Object> result = new ArrayList<>();
        int depth = 0;
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[') depth++;
            else if (c == ']') depth--;
            if (c == ',' && depth == 0) {
                String token = current.toString().trim();
                if (!token.isEmpty()) result.add(parseUnit(token));
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        String token = current.toString().trim();
        if (!token.isEmpty()) result.add(parseUnit(token));
        return result;
    }

    /**
     * Recursively builds the inner name (type/count/positions) for a list of
     * composition units. Each unit is an Integer type ID or a nested List.
     * Plain copper (type 0) positions are skipped, matching block naming.
     */
    @SuppressWarnings("unchecked")
    private static MutableComponent buildNestedUnitName(List<Object> units) {
        // Group positions by their unit value (Integer or nested List)
        Map<List<Object>, List<Integer>> groups = new LinkedHashMap<>();
        for (int pos = 0; pos < units.size(); pos++) {
            Object unit = units.get(pos);
            List<Object> key = new ArrayList<>();
            key.add(unit);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(pos);
        }

        // Sort by first position
        List<Map.Entry<List<Object>, List<Integer>>> sorted = new ArrayList<>(groups.entrySet());
        sorted.sort(Comparator.comparingInt(e -> e.getValue().get(0)));

        MutableComponent result = Component.empty();
        for (Map.Entry<List<Object>, List<Integer>> entry : sorted) {
            Object unit = entry.getKey().get(0);
            List<Integer> positions = entry.getValue();
            int count = positions.size();

            // Skip plain copper (type 0) units entirely
            if (unit instanceof Integer typeId && typeId == 0) continue;

            if (!result.getString().isEmpty()) {
                result.append(Component.literal("-"));
            }

            String posStr = positions.stream()
                    .map(p -> String.valueOf(p + 1))
                    .collect(Collectors.joining(","));
            result.append(Component.literal(posStr));
            result.append(Component.literal("-"));

            if (count >= 2) {
                result.append(Component.translatable(COUNT_TRANSLATION_KEYS[count - 1]));
            }

            if (unit instanceof List<?> nested) {
                result.append(Component.literal("("));
                result.append(buildNestedUnitName((List<Object>) nested));
                result.append(Component.literal(")"));
            } else {
                int typeId = (Integer) unit;
                result.append(Component.translatable(TYPE_TRANSLATION_KEYS[typeId]));
            }
        }
        return result;
    }

    /**
     * Checks if two composition strings are equivalent.
     * Handles both simple type IDs and arbitrarily nested compositions.
     */
    public static boolean compositionEquals(String a, String b) {
        if (a.equals(b)) return true;
        if (a.startsWith("[") && b.startsWith("[")) {
            return parseNestedList(a).equals(parseNestedList(b));
        }
        return false;
    }

    /**
     * Recursively computes the average oxidation level (0-3) of a composition
     * unit, used to decide the output ingot type for mixed crafting.
     */
    public static double averageOxidation(String value) {
        return averageOxidation(parseUnit(value));
    }

    @SuppressWarnings("unchecked")
    private static double averageOxidation(Object unit) {
        if (unit instanceof List<?> nested) {
            double sum = 0;
            for (Object o : (List<Object>) nested) sum += averageOxidation(o);
            return nested.isEmpty() ? 0 : sum / nested.size();
        }
        return ((Integer) unit) % 4;
    }

    /**
     * Checks if a String composition is the default (all zeros).
     */
    public static boolean isDefaultIngotComposition(List<String> composition) {
        if (composition == null || composition.size() != 9) return true;
        for (String s : composition) {
            if (!"0".equals(s)) return false;
        }
        return true;
    }

    /** Builds the inner portion (type/count/positions) without the "铜块" suffix. */
    private static MutableComponent buildInnerName(List<Integer> composition) {
        Map<Integer, List<Integer>> groups = new LinkedHashMap<>();
        for (int pos = 0; pos < 9; pos++) {
            int type = composition.get(pos);
            groups.computeIfAbsent(type, k -> new ArrayList<>()).add(pos);
        }
        List<Map.Entry<Integer, List<Integer>>> sorted = new ArrayList<>(groups.entrySet());
        sorted.sort(Comparator.comparingInt(e -> e.getValue().get(0)));

        MutableComponent result = Component.empty();
        for (Map.Entry<Integer, List<Integer>> entry : sorted) {
            int type = entry.getKey();
            if (type == 0) continue;
            List<Integer> positions = entry.getValue();
            int count = positions.size();

            if (!result.getString().isEmpty()) {
                result.append(Component.literal("-"));
            }

            String posStr = positions.stream()
                    .map(p -> String.valueOf(p + 1))
                    .collect(Collectors.joining(","));
            result.append(Component.literal(posStr));
            result.append(Component.literal("-"));

            if (count >= 2) {
                result.append(Component.translatable(COUNT_TRANSLATION_KEYS[count - 1]));
            }

            result.append(Component.translatable(TYPE_TRANSLATION_KEYS[type]));
        }
        return result;
    }

    /**
     * Generates the localized name for a cut alloy copper block from its 36-element
     * composition (4 input blocks × 9 ingots each).
     * <p>
     * Format: positions of identical source blocks, count prefix (if &gt;1),
     * source composition in parentheses, then "切制铜块" suffix.
     * Example: {@code 1,3-二(1-涂蜡的斑驳-…)切制铜块}
     */
    public static Component generateCutLocalizedName(List<Integer> cutComposition) {
        return buildCutInnerName(cutComposition)
                .append(Component.translatable(CUT_SUFFIX_TRANSLATION_KEY));
    }

    /**
     * Generates the localized name for cut alloy stairs from its 216-element
     * composition (6 cut blocks × 36 values each).
     * <p>
     * Outer level groups cut blocks by their 36-value composition, then
     * each group shows the cut block's inner alloy composition.
     * Example: {@code 1,3-二[1-二(1-斑驳-2,4-二涂蜡…)]-2-[1-(1-锈蚀…)]切制铜楼梯}
     */
    public static Component generateCutStairsName(List<Integer> stairsComposition) {
        return buildCutVariantName(stairsComposition, 6)
                .append(Component.translatable(CUT_STAIRS_SUFFIX_KEY));
    }

    /**
     * Generates the localized name for cut alloy slabs from its 108-element
     * composition (3 cut blocks × 36 values each).
     */
    public static Component generateCutSlabName(List<Integer> slabComposition) {
        return buildCutVariantName(slabComposition, 3)
                .append(Component.translatable(CUT_SLAB_SUFFIX_KEY));
    }

    /**
     * Generates the localized name for chiseled alloy copper blocks from its
     * 216-element composition (2 slabs × 108 values each).
     * <p>
     * Each slab's inner name is wrapped in curly braces, prefixed by its
     * position and count. Example:
     * {@code 1-{1,3-[…]-2-[…]}雕纹铜块}
     */
    public static Component generateChiseledName(List<Integer> chiseledComposition) {
        // Split into 2 slabs of 108 values each
        List<List<Integer>> slabComps = new ArrayList<>(2);
        for (int s = 0; s < 2; s++) {
            List<Integer> comp = new ArrayList<>(108);
            for (int i = 0; i < 108; i++) {
                comp.add(chiseledComposition.get(s * 108 + i));
            }
            slabComps.add(comp);
        }

        // Group identical slabs by their 108-value composition
        Map<String, List<Integer>> groups = new LinkedHashMap<>();
        for (int s = 0; s < 2; s++) {
            String key = slabComps.get(s).toString();
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s + 1);
        }

        MutableComponent result = Component.empty();

        for (Map.Entry<String, List<Integer>> entry : groups.entrySet()) {
            List<Integer> positions = entry.getValue();
            int count = positions.size();

            if (!result.getString().isEmpty()) {
                result.append(Component.literal("-"));
            }

            String posStr = positions.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            result.append(Component.literal(posStr));
            result.append(Component.literal("-"));

            if (count >= 2) {
                result.append(Component.translatable(COUNT_TRANSLATION_KEYS[count - 1]));
            }

            int firstPos = positions.get(0) - 1;
            result.append(Component.literal("{"));
            result.append(buildCutVariantName(slabComps.get(firstPos), 3));
            result.append(Component.literal("}"));
        }

        return result.append(Component.translatable(CHISELED_SUFFIX_KEY));
    }

    /**
     * Builds the name for a stairs/slab variant by grouping cut blocks by their
     * 36-value composition. Each group shows positions, count prefix, and the
     * cut block's inner composition in square brackets.
     *
     * @param fullComposition the full composition (216 for stairs, 108 for slabs)
     * @param blockCount      number of cut blocks (6 for stairs, 3 for slabs)
     */
    private static MutableComponent buildCutVariantName(List<Integer> fullComposition, int blockCount) {
        // Split into cut-block-sized groups (36 values each)
        List<List<Integer>> cutBlockComps = new ArrayList<>(blockCount);
        for (int b = 0; b < blockCount; b++) {
            List<Integer> comp = new ArrayList<>(36);
            for (int i = 0; i < 36; i++) {
                comp.add(fullComposition.get(b * 36 + i));
            }
            cutBlockComps.add(comp);
        }

        // Group identical cut block compositions by their inner name
        Map<String, List<Integer>> groups = new LinkedHashMap<>();
        for (int b = 0; b < blockCount; b++) {
            if (isAllZero(cutBlockComps.get(b))) continue; // skip plain copper cut blocks
            String key = cutBlockComps.get(b).toString();
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(b + 1);
        }

        MutableComponent result = Component.empty();

        for (Map.Entry<String, List<Integer>> entry : groups.entrySet()) {
            List<Integer> positions = entry.getValue();
            int count = positions.size();

            if (!result.getString().isEmpty()) {
                result.append(Component.literal("-"));
            }

            String posStr = positions.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            result.append(Component.literal(posStr));
            result.append(Component.literal("-"));

            if (count >= 2) {
                result.append(Component.translatable(COUNT_TRANSLATION_KEYS[count - 1]));
            }

            // Enclose the cut block's inner composition in square brackets
            int firstPos = positions.get(0) - 1;
            result.append(Component.literal("["));
            result.append(buildCutInnerName(cutBlockComps.get(firstPos)));
            result.append(Component.literal("]"));
        }

        return result;
    }

    /** Builds the inner portion for a cut block name, shared by block/stairs/slab. */
    private static MutableComponent buildCutInnerName(List<Integer> cutComposition) {
        List<List<Integer>> blockComps = new ArrayList<>(4);
        for (int b = 0; b < 4; b++) {
            List<Integer> comp = new ArrayList<>(9);
            for (int i = 0; i < 9; i++) {
                comp.add(cutComposition.get(b * 9 + i));
            }
            blockComps.add(comp);
        }

        // Group blocks by their 9-value composition (skip plain copper blocks)
        Map<String, List<Integer>> groups = new LinkedHashMap<>();
        for (int b = 0; b < 4; b++) {
            if (isAllZero(blockComps.get(b))) continue; // skip plain copper blocks
            String key = blockComps.get(b).toString();
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(b + 1);
        }

        MutableComponent result = Component.empty();

        for (Map.Entry<String, List<Integer>> entry : groups.entrySet()) {
            List<Integer> positions = entry.getValue();
            int count = positions.size();

            if (!result.getString().isEmpty()) {
                result.append(Component.literal("-"));
            }

            String posStr = positions.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            result.append(Component.literal(posStr));
            result.append(Component.literal("-"));

            if (count >= 2) {
                result.append(Component.translatable(COUNT_TRANSLATION_KEYS[count - 1]));
            }

            int firstPos = positions.get(0) - 1;
            List<Integer> comp = blockComps.get(firstPos);
            result.append(Component.literal("("));
            if (isUniform(comp)) {
                result.append(buildUniformBlockName(comp.get(0)));
            } else {
                result.append(buildInnerName(comp));
            }
            result.append(Component.literal(")"));
        }
        return result;
    }

    private static boolean isUniform(List<Integer> comp) {
        int first = comp.get(0);
        for (int i = 1; i < comp.size(); i++) {
            if (comp.get(i) != first) return false;
        }
        return true;
    }

    private static boolean isAllZero(List<Integer> comp) {
        for (int v : comp) {
            if (v != 0) return false;
        }
        return true;
    }

    /** Builds a block-level name for a uniform 9-value composition (e.g. "斑驳"). */
    private static MutableComponent buildUniformBlockName(int type) {
        return Component.translatable(TYPE_TRANSLATION_KEYS[type]);
    }
}
