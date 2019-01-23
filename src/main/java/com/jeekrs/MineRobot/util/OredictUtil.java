package com.jeekrs.MineRobot.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
public class OredictUtil {
    public static String[] getOreNames(final Block block) {
        final ItemStack dropped = new ItemStack(block);

        if (ItemUtil.isEmpty(dropped))
            return new String[]{};

        try {
            final int[] oreIds = OreDictionary.getOreIDs(dropped);
            return Arrays.stream(oreIds).mapToObj(OreDictionary::getOreName).toArray(String[]::new);
        } catch (IllegalArgumentException ex) {
            // JAVA IS GOMI
            return new String[]{};
        }
    }

    public static boolean isOre(final Block block) {
        return Arrays.stream(getOreNames(block)).anyMatch(s -> s.toLowerCase().startsWith("ore"));
    }
}
