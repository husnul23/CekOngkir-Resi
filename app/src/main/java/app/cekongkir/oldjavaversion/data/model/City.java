package app.cekongkir.oldjavaversion.data.model;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("city_id") private String city_id;
    @SerializedName("province_id") private String province_id;
    @SerializedName("province") private String province;
    @SerializedName("type") private String type;
    @SerializedName("city_name") private String city_name;
    @SerializedName("postal_code") private String postal_code;
}
