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

import c4.colorfulpotions.ColorfulPotions;
import c4.colorfulpotions.common.ColorUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RecipePotionDyes extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public RecipePotionDyes() {
        this.setRegistryName(ColorfulPotions.MODID, "potion_dye");
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        ItemStack itemstack = ItemStack.EMPTY;
        List<ItemStack> list = new ArrayList<>();

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack1 = inv.getStackInSlot(i);

            if (!itemstack1.isEmpty()) {

                if (itemstack1.getItem() instanceof ItemPotion) {

                    if (!itemstack.isEmpty()) {
                        return false;
                    }
                    itemstack = itemstack1;
                } else {

                    if (!DyeUtils.isDye(itemstack1)) {
                        return false;
                    }
                    list.add(itemstack1);
                }
            }
        }
        return !itemstack.isEmpty() && !list.isEmpty();
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack itemstack = ItemStack.EMPTY;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemPotion itempotion = null;

        for (int k = 0; k < inv.getSizeInventory(); ++k) {
            ItemStack itemstack1 = inv.getStackInSlot(k);

            if (!itemstack1.isEmpty()) {

                if (itemstack1.getItem() instanceof ItemPotion) {
                    itempotion = (ItemPotion) itemstack1.getItem();
                    itemstack = itemstack1.copy();
                    itemstack.setCount(1);

                    if (ColorUtils.isDyed(itemstack1)) {
                        int l = PotionUtils.getColor(itemstack);
                        float f = (float)(l >> 16 & 255) / 255.0F;
                        float f1 = (float)(l >> 8 & 255) / 255.0F;
                        float f2 = (float)(l & 255) / 255.0F;
                        i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                        aint[0] = (int)((float)aint[0] + f * 255.0F);
                        aint[1] = (int)((float)aint[1] + f1 * 255.0F);
                        aint[2] = (int)((float)aint[2] + f2 * 255.0F);
                        ++j;
                    }
                } else {

                    if (!DyeUtils.isDye(itemstack1)) {
                        return ItemStack.EMPTY;
                    }
                    float[] afloat = DyeUtils.colorFromStack(itemstack1).map(EnumDyeColor::getColorComponentValues).orElse(new float[]{});
                    int l1 = (int)(afloat[0] * 255.0F);
                    int i2 = (int)(afloat[1] * 255.0F);
                    int j2 = (int)(afloat[2] * 255.0F);
                    i += Math.max(l1, Math.max(i2, j2));
                    aint[0] += l1;
                    aint[1] += i2;
                    aint[2] += j2;
                    ++j;
                }
            }
        }

        if (itempotion == null) {
            return ItemStack.EMPTY;
        } else {
            int i1 = aint[0] / j;
            int j1 = aint[1] / j;
            int k1 = aint[2] / j;
            float f3 = (float)i / (float)j;
            float f4 = (float)Math.max(i1, Math.max(j1, k1));
            i1 = (int)((float)i1 * f3 / f4);
            j1 = (int)((float)j1 * f3 / f4);
            k1 = (int)((float)k1 * f3 / f4);
            int k2 = (i1 << 8) + j1;
            k2 = (k2 << 8) + k1;
            ColorUtils.setColor(itemstack, k2);

            if (!ColorUtils.isDyed(itemstack)) {
                ColorUtils.setDyed(itemstack);
            }
            return itemstack;
        }
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput()
    {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            nonnulllist.set(i, ForgeHooks.getContainerItem(itemstack));
        }
        return nonnulllist;
    }

    @Override
    public boolean isDynamic()
    {
        return true;
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return width * height >= 2;
    }
}
