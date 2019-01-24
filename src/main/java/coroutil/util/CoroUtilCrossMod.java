package CoroUtil.util;

import CoroUtil.config.ConfigCoroUtilAdvanced;
import CoroUtil.forge.CULog;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO: respect infernal mobs config of specifically disabled modifiers, and maybe some other things?
 * - see InfernalMobsCore.mobMods, contains list of allowed mods, does not contain config removed ones
 *
 * his settings:
 * - elite: 2 + 3 rand
 * - ultra: + 3 + 2 rand
 * - infernal: + 3 + 2 rand
 *
 * Created by Corosus on 1/7/2017.
 */
public class CoroUtilCrossMod {

    private static boolean checkHasInfernalMobs = true;
    private static boolean hasInfernalMobs = false;

    public static List<String> listModifiers = new ArrayList<>();

    static {
        listModifiers.add("1UP");
        listModifiers.add("Alchemist");
        listModifiers.add("Berserk");
        listModifiers.add("Blastoff");
        listModifiers.add("Bulwark");
        listModifiers.add("Choke");
        listModifiers.add("Cloaking");
        listModifiers.add("Darkness");
        listModifiers.add("Ender");
        listModifiers.add("Exhaust");
        listModifiers.add("Fiery");
        listModifiers.add("Ghastly");
        listModifiers.add("Gravity");
        listModifiers.add("LifeSteal");
        listModifiers.add("Ninja");
        listModifiers.add("Poisonous");
        listModifiers.add("Quicksand");
        listModifiers.add("Regen");
        listModifiers.add("Rust");
        listModifiers.add("Sapper");
        listModifiers.add("Sprint");
        listModifiers.add("Sticky");
        listModifiers.add("Storm");
        listModifiers.add("Vengeance");
        listModifiers.add("Weakness");
        listModifiers.add("Webber");
        listModifiers.add("Wither");

    }

    /**
     * Check if infernal mobs mod is loaded, cache result
     *
     * @return
     */
    public static boolean hasInfernalMobs() {
        if (!checkHasInfernalMobs) {
            return hasInfernalMobs;
        } else {
            checkHasInfernalMobs = false;
            try {
                //this throws exception when its not installed
                Class clazz = Class.forName("atomicstryker.infernalmobs.common.InfernalMobsCore");
                if (clazz != null) {
                    hasInfernalMobs = true;
                }
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
            CULog.log("CoroUtil detected Infernal Mobs " + (hasInfernalMobs ? "Installed" : "Not Installed") + " for use");
            return hasInfernalMobs;
        }
    }

    public static boolean infernalMobs_AddRandomModifiers(EntityLivingBase ent, List<String> listModifiersToUse, int modifierCount) {
        String listMods = "";

        CULog.dbg("infernalMobs_AddRandomModifiers perform:");

        if (modifierCount >= listModifiersToUse.size()) {
            modifierCount = listModifiersToUse.size() - 1;
        }

        CULog.dbg("modifierCount: " + modifierCount);
        CULog.dbg("listModifiersToUse size: " + listModifiersToUse.size());

        List<Integer> listInts = new ArrayList<>();
        for (int i = 0; i < listModifiersToUse.size(); i++) { listInts.add(i); }
        Collections.shuffle(listInts);

        for (int i = 0; i < modifierCount; i++) {
            listMods += listModifiersToUse.get(listInts.get(i)) + " ";
        }

        CULog.dbg("listMods final: " + listMods);

        if (!listMods.equals("")) {
            return infernalMobs_AddModifiers(ent, listMods);
        } else {
            //didnt fail, just nothing to apply because difficulty too low
            return true;
        }
    }

    public static boolean infernalMobs_AddModifiers(EntityLivingBase ent, String modifiers) {

        if (!hasInfernalMobs()) return false;

        /**
         *
         * need to do this:
         * InfernalMobsCore.proxy.getRareMobs().remove(mob);
         * - we arent doing this, maybe it was unneeded?
         * InfernalMobsCore.instance().addEntityModifiersByString(mob, modifier);
         *
         */

        try {
            Class clazz = Class.forName("atomicstryker.infernalmobs.common.InfernalMobsCore");
            if (clazz != null) {
                Method method = clazz.getDeclaredMethod("instance");
                Object obj = method.invoke(null);
                Method methodMods = obj.getClass().getDeclaredMethod("addEntityModifiersByString", EntityLivingBase.class, String.class);
                methodMods.invoke(obj, ent, modifiers);
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return false;
    }

    public static boolean infernalMobs_RemoveAllModifiers(EntityLivingBase ent) {
        if (!hasInfernalMobs()) return false;

        try {
            Class clazz = Class.forName("atomicstryker.infernalmobs.common.InfernalMobsCore");
            if (clazz != null) {

                Method method = clazz.getDeclaredMethod("removeEntFromElites", EntityLivingBase.class);
                method.invoke(null, ent);

                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Called from lowest priority event so ours always runs after infernals, keeps track of what mod added the modifications and removes if it wasnt us
     *
     * @param event
     */
    public static void processSpawnOverride(EntityJoinWorldEvent event) {

        if (!hasInfernalMobs()) return;

        if (!ConfigCoroUtilAdvanced.difficulty_OverrideInfernalMobs) return;

        //disable unless HW monsters is installed which performs the difficulty based replacements
        if (!CoroUtilCompatibility.isHWMonstersInstalled()) return;

        //updated based off of InfernalMobsCore.getNBTTag();
        String infernalNBTString = "InfernalMobsMod";

        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase)event.getEntity();
            /**
             * Aggressively remove infernal modifiers and nbt data for it, unless we added them ourselves
             */
            if (ent.getEntityData().hasKey(infernalNBTString)) {

            }
        }
    }
}
