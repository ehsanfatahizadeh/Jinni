package ehsanfatahizadehgmail.com.jinni.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("mahiat_titr")
    @Expose
    public String mahiat_titr;

    @SerializedName("jensiat_titr")
    @Expose
    public String jensiat_titr;

    @SerializedName("brand_titr")
    @Expose
    public String brand_titr;

    @SerializedName("model_titr")
    @Expose
    public String model_titr;

    @SerializedName("vizhegi_titr")
    @Expose
    public String vizhegi_titr;

    @SerializedName("size_titr")
    @Expose
    public String size_titr;

    @SerializedName("tozihat")
    @Expose
    public String tozihat;

    @SerializedName("haraji")
    @Expose
    public String haraji;

    @SerializedName("special")
    @Expose
    public String special;

    @SerializedName("name_vahed")
    @Expose
    public String name_vahed;

    @SerializedName("gheymate_vahed")
    @Expose
    public String gheymate_vahed;

    @SerializedName("vahede_zaribe_sefaresh")
    @Expose
    public String vahede_zaribe_sefaresh;

    @SerializedName("zaribe_sefaresh")
    @Expose
    public String zaribe_sefaresh;

    @SerializedName("mobile")
    @Expose
    public String mobile;

    @SerializedName("category")
    @Expose
    public String category;

    @SerializedName("colors")
    @Expose
    public List<String> colors;

    @SerializedName("photos")
    @Expose
    public List<String> photos;

    @SerializedName("property_description")
    @Expose
    public List<String> property_description;

    @SerializedName("property_name")
    @Expose
    public List<String> property_name;

    @SerializedName("sizes")
    @Expose
    public List<String> sizes;

    @SerializedName("tedad_az")
    @Expose
    public List<String> tedad_az;

    @SerializedName("tedad_ta")
    @Expose
    public List<String> tedad_ta;

    @SerializedName("tedad_gheymat")
    @Expose
    public List<String> tedad_gheymat;

    @SerializedName("category_name")
    @Expose
    public String category_name;




    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }



    public List<String> getTedad_gheymat() {
        return tedad_gheymat;
    }

    public void setTedad_gheymat(List<String> tedad_gheymat) {
        this.tedad_gheymat = tedad_gheymat;
    }

    public List<String> getTedad_az() {
        return tedad_az;
    }

    public void setTedad_az(List<String> tedad_az) {
        this.tedad_az = tedad_az;
    }

    public List<String> getTedad_ta() {
        return tedad_ta;
    }

    public void setTedad_ta(List<String> tedad_ta) {
        this.tedad_ta = tedad_ta;
    }


    public List<String> getProperty_description() {
        return property_description;
    }

    public void setProperty_description(List<String> property_description) {
        this.property_description = property_description;
    }

    public List<String> getProperty_name() {
        return property_name;
    }

    public void setProperty_name(List<String> property_name) {
        this.property_name = property_name;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public List<String> getPhotos() {

        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMahiat_titr() {
        return mahiat_titr;
    }

    public void setMahiat_titr(String mahiat_titr) {
        this.mahiat_titr = mahiat_titr;
    }

    public String getJensiat_titr() {
        return jensiat_titr;
    }

    public void setJensiat_titr(String jensiat_titr) {
        this.jensiat_titr = jensiat_titr;
    }

    public String getBrand_titr() {
        return brand_titr;
    }

    public void setBrand_titr(String brand_titr) {
        this.brand_titr = brand_titr;
    }

    public String getModel_titr() {
        return model_titr;
    }

    public void setModel_titr(String model_titr) {
        this.model_titr = model_titr;
    }

    public String getVizhegi_titr() {
        return vizhegi_titr;
    }

    public void setVizhegi_titr(String vizhegi_titr) {
        this.vizhegi_titr = vizhegi_titr;
    }

    public String getSize_titr() {
        return size_titr;
    }

    public void setSize_titr(String size_titr) {
        this.size_titr = size_titr;
    }

    public String getTozihat() {
        return tozihat;
    }

    public void setTozihat(String tozihat) {
        this.tozihat = tozihat;
    }

    public String getHaraji() {
        return haraji;
    }

    public void setHaraji(String haraji) {
        this.haraji = haraji;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getName_vahed() {
        return name_vahed;
    }

    public void setName_vahed(String name_vahed) {
        this.name_vahed = name_vahed;
    }

    public String getGheymate_vahed() {
        return gheymate_vahed;
    }

    public void setGheymate_vahed(String gheymate_vahed) {
        this.gheymate_vahed = gheymate_vahed;
    }

    public String getVahede_zaribe_sefaresh() {
        return vahede_zaribe_sefaresh;
    }

    public void setVahede_zaribe_sefaresh(String vahede_zaribe_sefaresh) {
        this.vahede_zaribe_sefaresh = vahede_zaribe_sefaresh;
    }

    public String getZaribe_sefaresh() {
        return zaribe_sefaresh;
    }

    public void setZaribe_sefaresh(String zaribe_sefaresh) {
        this.zaribe_sefaresh = zaribe_sefaresh;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
