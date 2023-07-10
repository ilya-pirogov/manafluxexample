package dev.pirogov.manafluxexample.api;

import team.reborn.energy.api.base.SimpleEnergyStorage;

@SuppressWarnings({"unused"})
public class SimpleManaFluxStorage extends SimpleEnergyStorage implements ManaFluxStorage {

    public SimpleManaFluxStorage(long capacity, long maxInsert, long maxExtract) {
        super(capacity, maxInsert, maxExtract);
    }
}
