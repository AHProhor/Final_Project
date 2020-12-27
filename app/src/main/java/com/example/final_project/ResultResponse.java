package com.example.final_project;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultResponse implements Parcelable {
    private String productionDate;
    private String expireDate;
    private String details;
    private String price;
    private String name;
    private String type;
    private String company;


    public ResultResponse() {
    }

    public ResultResponse(String productionDate, String expireDate, String details, String price, String name, String type, String company) {
        this.productionDate = productionDate;
        this.expireDate = expireDate;
        this.details = details;
        this.price = price;
        this.name = name;
        this.type = type;
        this.company = company;
    }

    protected ResultResponse(Parcel in) {
        productionDate = in.readString();
        expireDate = in.readString();
        details = in.readString();
        price = in.readString();
        name = in.readString();
        type = in.readString();
        company = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productionDate);
        dest.writeString(expireDate);
        dest.writeString(details);
        dest.writeString(price);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(company);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultResponse> CREATOR = new Creator<ResultResponse>() {
        @Override
        public ResultResponse createFromParcel(Parcel in) {
            return new ResultResponse(in);
        }

        @Override
        public ResultResponse[] newArray(int size) {
            return new ResultResponse[size];
        }
    };

    public String getProductionDate() {
        return productionDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getDetails() {
        return details;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return "ResultResponse{" +
                "productionDate='" + productionDate + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", details='" + details + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
