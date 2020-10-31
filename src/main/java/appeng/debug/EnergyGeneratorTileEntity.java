/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2015, AlgorithmX2, All rights reserved.
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

package appeng.debug;

import javax.annotation.Nullable;

import com.google.common.math.IntMath;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import appeng.tile.AEBaseTileEntity;

public class EnergyGeneratorTileEntity extends AEBaseTileEntity implements ITickableTileEntity, IEnergyStorage {
    /**
     * The base energy injected each tick. Adjacent TileEnergyGenerators will increase it to pow(base, #generators).
     */
    private static final int BASE_ENERGY = 8;

    public EnergyGeneratorTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        World world = this.getWorld();
        if (world == null || world.isRemote) {
            return;
        }

        int tier = 1;
        for (Direction facing : Direction.values()) {
            final TileEntity te = world.getTileEntity(this.getPos().offset(facing));

            if (te instanceof EnergyGeneratorTileEntity) {
                tier++;
            }
        }

        final int energyToInsert = IntMath.pow(BASE_ENERGY, tier);

        for (Direction facing : Direction.values()) {
            final TileEntity te = this.getWorld().getTileEntity(this.getPos().offset(facing));
            if (te == null) {
                continue;
            }
            final LazyOptional<IEnergyStorage> cap = te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());

            cap.ifPresent(consumer -> {
                if (consumer.canReceive()) {
                    consumer.receiveEnergy(energyToInsert, false);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (LazyOptional<T>) LazyOptional.of(() -> this);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return maxExtract;
    }

    @Override
    public int getEnergyStored() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxEnergyStored() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
