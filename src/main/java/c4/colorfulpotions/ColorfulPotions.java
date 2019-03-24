/*
 * Copyright (C) 2019  C4
 *
 * This file is part of Colorful Potions, a mod made for Minecraft.
 *
 * Colorful Potions is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Colorful Potions is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Colorful Potions.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.colorfulpotions;

import c4.colorfulpotions.common.recipe.RecipePotionDyes;
import c4.colorfulpotions.common.recipe.RecipeTippedArrowAlter;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTippedArrow;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;

import java.util.List;

@Mod(   modid = ColorfulPotions.MODID,
        name = ColorfulPotions.NAME,
        version = "@VERSION@",
        dependencies = "required-after:forge@[14.23.5.2768,)",
        acceptedMinecraftVersions = "[1.12, 1.13)",
        certificateFingerprint = "@FINGERPRINT@")
public class ColorfulPotions {

    public static final String MODID = "colorfulpotions";
    public static final String NAME = "Colorful Potions";

    @EventHandler
    public void onFingerPrintViolation(FMLFingerprintViolationEvent evt) {
        FMLLog.log.log(Level.ERROR, "Invalid fingerprint detected! The file " + evt.getSource().getName() + " may have been tampered with. This version will NOT be supported by the author!");
    }

    @Mod.EventBusSubscriber(modid = MODID)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
            evt.getRegistry().registerAll(new RecipePotionDyes(), new RecipeTippedArrowAlter());
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Side.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onPotionTooltip(ItemTooltipEvent evt) {
            ItemStack stack = evt.getItemStack();

            if (stack.getItem() instanceof ItemPotion || stack.getItem() instanceof ItemTippedArrow) {
                NBTTagCompound nbttagcompound = stack.getTagCompound();

                if (nbttagcompound != null && nbttagcompound.hasKey("CustomPotionColor", 99)) {
                    List<String> tooltip = evt.getToolTip();

                    if (evt.getFlags().isAdvanced()) {
                        tooltip.add(I18n.format("item.color", String.format("#%06X", nbttagcompound.getInteger("CustomPotionColor"))));
                    } else {
                        tooltip.add(TextFormatting.ITALIC + I18n.format("item.dyed"));
                    }
                }
            }
        }
    }
}
