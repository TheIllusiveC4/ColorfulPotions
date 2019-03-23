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

import c4.colorfulpotions.common.RecipePotionDyes;
import c4.colorfulpotions.proxy.IProxy;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

@Mod(   modid = ColorfulPotions.MODID,
        name = ColorfulPotions.NAME,
        version = "@VERSION@",
        dependencies = "required-after:forge@[14.23.5.2768,)",
        acceptedMinecraftVersions = "[1.12, 1.13)",
        certificateFingerprint = "@FINGERPRINT@")
public class ColorfulPotions {

    public static final String MODID = "colorfulpotions";
    public static final String NAME = "Colorful Potions";

    @SidedProxy(clientSide = "c4.colorfulpotions.proxy.ClientProxy", serverSide = "c4.colorfulpotions.proxy.ServerProxy")
    public static IProxy proxy;

    @EventHandler
    public void init(FMLInitializationEvent evt) {
        proxy.init(evt);
    }

    @EventHandler
    public void onFingerPrintViolation(FMLFingerprintViolationEvent evt) {
        FMLLog.log.log(Level.ERROR, "Invalid fingerprint detected! The file " + evt.getSource().getName() + " may have been tampered with. This version will NOT be supported by the author!");
    }

    @Mod.EventBusSubscriber(modid = MODID)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
            evt.getRegistry().register(new RecipePotionDyes());
        }
    }
}
