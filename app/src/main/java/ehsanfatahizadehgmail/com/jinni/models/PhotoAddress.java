package ehsanfatahizadehgmail.com.jinni.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoAddress {


    @SerializedName("address")
    @Expose
    public String address;






    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
