package com.froobworld.avl.data;

import com.google.gson.JsonParser;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class VillagerMemoryDataType implements PersistentDataType<String, VillagerMemoryData> {

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public Class<VillagerMemoryData> getComplexType() {
        return VillagerMemoryData.class;
    }

    @Override
    public String toPrimitive(VillagerMemoryData villagerMemoryData, PersistentDataAdapterContext persistentDataAdapterContext) {
        return villagerMemoryData.toJsonObject().toString();
    }

    @Override
    public VillagerMemoryData fromPrimitive(String string, PersistentDataAdapterContext persistentDataAdapterContext) {
        return VillagerMemoryData.fromJson(new JsonParser().parse(string).getAsJsonObject());
    }
}
