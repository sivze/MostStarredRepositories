package me.sivze.githubmoststarred.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Siva on 6/2/2016.
 */
public class ContributorsModel implements Parcelable{

    @SerializedName("login")
    public String name;

    @SerializedName("avatar_url")
    public String avatarUrl;

    @SerializedName("url")
    public String profileUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.profileUrl);
    }

    protected ContributorsModel(Parcel in) {
        this.name = in.readString();
        this.avatarUrl = in.readString();
        this.profileUrl = in.readString();
    }

    public static final Parcelable.Creator<ContributorsModel> CREATOR = new Parcelable.Creator<ContributorsModel>() {
        public ContributorsModel createFromParcel(Parcel source) {
            return new ContributorsModel(source);
        }

        public ContributorsModel[] newArray(int size) {
            return new ContributorsModel[size];
        }
    };
}
