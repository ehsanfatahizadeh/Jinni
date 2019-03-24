package ehsanfatahizadehgmail.com.jinni.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesList {


    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("mobile")
    @Expose
    public String mobile;

    @SerializedName("parent_category")
    @Expose
    public String parent_category;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("num_of_subs")
    @Expose
    public String num_of_subs;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getParent_category() {
        return parent_category;
    }

    public void setParent_category(String parent_category) {
        this.parent_category = parent_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum_of_subs() {
        return num_of_subs;
    }

    public void setNum_of_subs(String num_of_subs) {
        this.num_of_subs = num_of_subs;
    }


}
