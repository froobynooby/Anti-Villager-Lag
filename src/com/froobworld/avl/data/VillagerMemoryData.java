package com.froobworld.avl.data;

import com.froobworld.avl.utils.LocationUtils;
import com.google.gson.JsonObject;
import org.bukkit.Location;

public class VillagerMemoryData {
    private Location homeLocation, workLocation, meetingLocation;

    public VillagerMemoryData(Location homeLocation, Location workLocation, Location meetingLocation) {
        this.homeLocation = homeLocation;
        this.workLocation = workLocation;
        this.meetingLocation = meetingLocation;
    }

    public Location getHomeLocation() {
        return homeLocation;
    }

    public Location getWorkLocation() {
        return workLocation;
    }

    public Location getMeetingLocation() {
        return meetingLocation;
    }

    public static VillagerMemoryData fromJson(JsonObject jsonObject) {
        Location homeLocation = !jsonObject.get("homeLocation").isJsonNull() ? LocationUtils.deserialiseLocation(jsonObject.get("homeLocation").getAsString()) : null;
        Location workLocation = !jsonObject.get("workLocation").isJsonNull() ? LocationUtils.deserialiseLocation(jsonObject.get("workLocation").getAsString()) : null;
        Location meetingLocation = !jsonObject.get("meetingLocation").isJsonNull() ? LocationUtils.deserialiseLocation(jsonObject.get("meetingLocation").getAsString()) : null;

        return new VillagerMemoryData(homeLocation, workLocation, meetingLocation);
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("homeLocation", LocationUtils.serialiseLocation(homeLocation));
        jsonObject.addProperty("workLocation", LocationUtils.serialiseLocation(workLocation));
        jsonObject.addProperty("meetingLocation", LocationUtils.serialiseLocation(meetingLocation));

        return jsonObject;
    }
}
