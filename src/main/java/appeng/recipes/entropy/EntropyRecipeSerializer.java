/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2021, TeamAppliedEnergistics, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.recipes.entropy;

import com.mojang.serialization.Codec;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class EntropyRecipeSerializer implements RecipeSerializer<EntropyRecipe> {

    public static final EntropyRecipeSerializer INSTANCE = new EntropyRecipeSerializer();

    private EntropyRecipeSerializer() {
    }

    @Override
    public Codec<EntropyRecipe> codec() {
        return EntropyRecipe.CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, EntropyRecipe> streamCodec() {
        return EntropyRecipe.STREAM_CODEC;
    }
}
