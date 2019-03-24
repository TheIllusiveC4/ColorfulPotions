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

package c4.colorfulpotions.common.recipe;

import c4.colorfulpotions.common.ColorUtils;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeTippedArrow;
import net.minecraft.potion.PotionUtils;

import javax.annotation.Nonnull;

public class RecipeTippedArrowAlter extends RecipeTippedArrow {

    public RecipeTippedArrowAlter() {
        this.setRegistryName("minecraft:tippedarrow");
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv)
    {
        ItemStack itemstack = inv.getStackInRowAndColumn(1, 1);

        if (itemstack.getItem() != Items.LINGERING_POTION)
        {
            return ItemStack.EMPTY;
        }
        else
        {
            ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);
            PotionUtils.addPotionToItemStack(itemstack1, PotionUtils.getPotionFromItem(itemstack));
            PotionUtils.appendEffects(itemstack1, PotionUtils.getFullEffectsFromItem(itemstack));

            if (ColorUtils.isDyed(itemstack)) {
                ColorUtils.setColor(itemstack1, PotionUtils.getColor(itemstack));
            }
            return itemstack1;
        }
    }
}
