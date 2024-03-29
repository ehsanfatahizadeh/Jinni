package ehsanfatahizadehgmail.com.jinni.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckGet {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("mobile")
    @Expose
    public String mobile;

    @SerializedName("tavasote")
    @Expose
    public String tavasote;

    @SerializedName("shomare_check")
    @Expose
    public String shomare_check;

    @SerializedName("gheymat")
    @Expose
    public String gheymat;

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

    public String getTavasote() {
        return tavasote;
    }

    public String getShomare_check() {
        return shomare_check;
    }

    public String getGheymat() {
        return gheymat;
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
