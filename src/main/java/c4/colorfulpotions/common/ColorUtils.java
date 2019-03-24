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

package c4.colorfulpotions.common;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;

public class ColorUtils {

    public static boolean isDyed(ItemStack stack) {
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        return nbttagcompound != null && nbttagcompound.hasKey("dyed");
    }

    public static void setDyed(ItemStack stack) {
        stack.setTagInfo("dyed", new NBTTagByte((byte)0));
    }

    public static void setColor(ItemStack stack, int color) {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null) {
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }
        nbttagcompound.setInteger("CustomPotionColor", color);
    }
}
