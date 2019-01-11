package com.example.android.myfitnessapp.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


@Entity(tableName = "exercise")
public class ExerciseEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "reps")
    private int reps;

    @ColumnInfo(name = "minutes")
    private int minutes;

    @ColumnInfo(name = "intWeight")
    private int intWeight;

    @ColumnInfo(name = "muscle")
    private String muscle;

    @ColumnInfo(name = "intCalories")
    private int intCalories;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "userId")
    private String userId;

    public ExerciseEntity() {
            this.name = name;
            this.reps = reps;
            this.minutes = minutes;
            this.intWeight = intWeight;
            this.muscle = muscle;
            this.intCalories = intCalories;
            this.date = date;
            this.userId = userId;
        }

    public ExerciseEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        reps = in.readInt();
        minutes = in.readInt();
        intWeight = in.readInt();
        muscle = in.readString();
        intCalories = in.readInt();
        date = in.readString();
        userId = in.readString();
    }

    public static final Creator<ExerciseEntity> CREATOR = new Creator<ExerciseEntity>() {
        @Override
        public ExerciseEntity createFromParcel(Parcel in) {
            return new ExerciseEntity(in);
        }

        @Override
        public ExerciseEntity[] newArray(int size) {
            return new ExerciseEntity[size];
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

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getIntWeight() {
        return intWeight;
    }

    public void setIntWeight(int intWeight) {
        this.intWeight = intWeight;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public int getIntCalories() {
        return intCalories;
    }

    public void setIntCalories(int intCalories) {
        this.intCalories = intCalories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId;    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(reps);
        dest.writeInt(minutes);
        dest.writeInt(intWeight);
        dest.writeString(muscle);
        dest.writeInt(intCalories);
        dest.writeString(date);
        dest.writeString(userId);
    }
}
