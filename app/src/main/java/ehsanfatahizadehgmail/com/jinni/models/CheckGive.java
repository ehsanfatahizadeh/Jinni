package ehsanfatahizadehgmail.com.jinni.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckGive {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("mobile")
    @Expose
    public String mobile;

    @SerializedName("dar_vajhe")
    @Expose
    public String dar_vajhe;

    @SerializedName("az_hesabe")
    @Expose
    public String az_hesabe;

    @SerializedName("name_bank")
    @Expose
    public String name_bank;

    @SerializedName("shomare_check")
    @Expose
    public String shomare_check;

    @SerializedName("mablagh")
    @Expose
    public String mablagh;

    @SerializedName("tozihat")
    @Expose
    public String tozihat;

    @SerializedName("vaziat")
    @Expose
    public String vaziat;

    @SerializedName("year")
    @Expose
    public String year;

    @SerializedName("month")
    @Expose
    public String month;

    @SerializedName("day")
    @Expose
    public String day;

    @SerializedName("year_shamsi")
    @Expose
    public String year_shamsi;

    @SerializedName("month_shamsi")
    @Expose
    public String month_shamsi;

    @SerializedName("day_shamsi")
    @Expose
    public String day_shamsi;


    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDar_vajhe() {
        return dar_vajhe;
    }

    public String getAz_hesabe() {
        return az_hesabe;
    }

    public String getName_bank() {
        return name_bank;
    }

    public String getShomare_check() {
        return shomare_check;
    }

    public String getMablagh() {
        return mablagh;
    }

    public String getTozihat() {
        return tozihat;
    }

    public String getVaziat() {
        return vaziat;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getYear_shamsi() {
        return year_shamsi;
    }

    public String getMonth_shamsi() {
        return month_shamsi;
    }

    public String getDay_shamsi() {
        return day_shamsi;
    }


}
