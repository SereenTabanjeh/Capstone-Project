package com.example.android.myfitnessapp.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "workout")
public class workoutEntity  implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "exeIds")
    private String exeIds;

    @ColumnInfo(name = "userId")
    private String userId;


    public workoutEntity() {
        this.name = name;
        this.exeIds=exeIds;
        this.id = id;
        this.userId = userId;
    }

    protected workoutEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        exeIds = in.readString();
        userId = in.readString();
    }

    public static final Creator<workoutEntity> CREATOR = new Creator<workoutEntity>() {
        @Override
        public workoutEntity createFromParcel(Parcel in) {
            return new workoutEntity(in);
        }

        @Override
        public workoutEntity[] newArray(int size) {
            return new workoutEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExeIds() {
        return exeIds;
    }

    public void setExeIds(String exeIds) {
        this.exeIds = exeIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(exeIds);
        dest.writeString(userId);
    }
}
