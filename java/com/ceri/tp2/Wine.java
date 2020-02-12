package com.ceri.tp2;

import android.os.Parcel;
import android.os.Parcelable;

public class Wine implements Parcelable {

    public static final String TAG = Wine.class.getSimpleName();

    private long id;
    private String title;
    private String region;
    private String localization;
    private String climate;
    private String plantedArea; // in hectares

    public Wine(long id, String title, String region, String localization, String climate, String plantedArea) {
        this.id = id;
        this.title = title;
        this.region = region;
        this.localization = localization;
        this.climate = climate;
        this.plantedArea = plantedArea;
    }

    public Wine(String title, String region, String localization, String climate, String plantedArea) {
        this.title = title;
        this.region = region;
        this.localization = localization;
        this.climate = climate;
        this.plantedArea = plantedArea;
    }

    public Wine() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getClimate() { return climate;}

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getPlantedArea() {
        return plantedArea;
    }

    public void setPlantedArea(String plantedArea) {
        this.plantedArea = plantedArea;
    }

    @Override
    public String toString() {
        return this.title+"("+this.region +")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(region);
        dest.writeString(localization);
        dest.writeString(climate);
        dest.writeString(plantedArea);
    }

    public static final Parcelable.Creator<Wine> CREATOR = new Parcelable.Creator<Wine>()
    {
        @Override
        public Wine createFromParcel(Parcel source)
        {
            return new Wine(source);
        }

        @Override
        public Wine[] newArray(int size)
        {
            return new Wine[size];
        }
    };

    public Wine(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.region = in.readString();
        this.localization = in.readString();
        this.climate = in.readString();
        this.plantedArea = in.readString();
    }
}
