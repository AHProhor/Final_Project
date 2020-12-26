package com.example.final_project;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultResponse implements Parcelable {
    private String productionDate;
    private String expireDate;
    private String details;
    private double price;

    public ResultResponse() {
    }

    public ResultResponse(String productionDate, String expireDate, String details, double price) {
        this.productionDate = productionDate;
        this.expireDate = expireDate;
        this.details = details;
        this.price = price;
    }

    protected ResultResponse(Parcel in) {
        productionDate = in.readString();
        expireDate = in.readString();
        details = in.readString();
        price = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productionDate);
        dest.writeString(expireDate);
        dest.writeString(details);
        dest.writeDouble(price);
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

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ResultResponse{" +
                "productionDate='" + productionDate + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", details='" + details + '\'' +
                ", price=" + price +
                '}';
    }
}
