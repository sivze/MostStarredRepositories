package me.sivze.githubmoststarred.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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
    public String createdAt;

    @SerializedName("owner")
    public Owner owner;

    public String getFormattedDate() {
        return createdAt.substring(0,10);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.stars);
        dest.writeString(this.contributorsUrl);
        dest.writeValue(this.owner);
    }

    protected ReposModel(Parcel in) {

        this.name = in.readString();
        this.stars = in.readInt();
        this.contributorsUrl = in.readString();
        this.owner = (Owner) in.readValue(Owner.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReposModel> CREATOR = new Parcelable.Creator<ReposModel>() {
        public ReposModel createFromParcel(Parcel source) {
            return new ReposModel(source);
        }

        public ReposModel[] newArray(int size) {
            return new ReposModel[size];
        }
    };

    public class Owner {
        public String avatar_url;
        public String login;
    }
}
