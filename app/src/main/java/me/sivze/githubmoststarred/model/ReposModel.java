package me.sivze.githubmoststarred.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Siva on 6/1/2016.
 */
public class ReposModel implements Parcelable {

    @SerializedName("name")
    public String name;

    @SerializedName("stargazers_count")
    public int stars;

    @SerializedName("forks_count")
    public int forks;

    @SerializedName("contributors_url")
    public String contributorsUrl;

    @SerializedName("created_at")
    public Date createdAt;

    @SerializedName("owner")
    public Owner owner;



    public Date getFormattedDate() {
        if (createdAt!=null) {
            return createdAt;
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.stars);
        dest.writeInt(this.forks);
        dest.writeString(this.contributorsUrl);
        dest.writeLong(this.createdAt.getTime());
        dest.writeParcelable(this.owner, flags);
    }

    public ReposModel(){

    }

    protected ReposModel(Parcel in) {
        this.name = in.readString();
        this.stars = in.readInt();
        this.forks = in.readInt();
        this.contributorsUrl = in.readString();
        this.createdAt = new Date(in.readLong());
        owner = (Owner) in.readParcelable(Owner.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReposModel> CREATOR = new Parcelable.Creator<ReposModel>() {
        public ReposModel createFromParcel(Parcel source) {
            return new ReposModel(source);
        }

        public ReposModel[] newArray(int size) {
            return new ReposModel[size];
        }
    };

//    @Override
//    public String toString() {
//        return "ReposData{" +
//                "name='" + name + '\'' +
//                ", stars=" + stars +
//                ", forks=" + forks +
//                ", contributorsUrl='" + contributorsUrl + '\'' +
//                ", createdAt='" + createdAt + '\'' +
//                ", avatarUrl='" + owner.avatarUrl + '\'' +
//                ", login='" + owner.login + '\'' +
//                '}';
//    }

    public static class Owner implements Parcelable {
        @SerializedName("avatar_url")
        public String avatarUrl;
        public String login;

        public Owner(){

        }

        protected Owner(Parcel in){
            this.avatarUrl = in.readString();
            this.login = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.avatarUrl);
            dest.writeString(this.login);
        }

        public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {
            public Owner createFromParcel(Parcel source) {
                return new Owner(source);
            }

            public Owner[] newArray(int size) {
                return new Owner[size];
            }
        };
    }
}
