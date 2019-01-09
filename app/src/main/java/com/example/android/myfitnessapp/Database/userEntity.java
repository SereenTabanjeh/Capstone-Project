package com.example.android.myfitnessapp.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "user")
public class userEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    public userEntity() {
        //this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }



    public userEntity(Parcel in) {
        uid = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<userEntity> CREATOR = new Creator<userEntity>() {
        @Override
        public userEntity createFromParcel(Parcel in) {
            return new userEntity(in);
        }

        @Override
        public userEntity[] newArray(int size) {
            return new userEntity[size];
        }
    };


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(password);
    }
}


