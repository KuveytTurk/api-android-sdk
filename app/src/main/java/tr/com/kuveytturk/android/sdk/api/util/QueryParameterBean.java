/*
 *  KUVEYT TÜRK PARTICIPATION BANK INC.
 *
 *   Developed under MIT License
 *   Copyright (c) 2018
 *
 *   Author : Fikri Aydemir
 *   Last Modified at : 27.09.2018 11:29
 */

package tr.com.kuveytturk.android.sdk.api.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by faydemir on 27/09/2018.
 */

public class QueryParameterBean implements Parcelable {
    private String paramName;
    private String paramValue;

    public QueryParameterBean() {
    }

    public QueryParameterBean(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public QueryParameterBean(Parcel in){
        String[] data = new String[2];
        in.readStringArray(data);
        this.paramName = data[0];
        this.paramValue = data[1];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.paramName, this.paramValue});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public QueryParameterBean createFromParcel(Parcel in) {
            return new QueryParameterBean(in);
        }

        public QueryParameterBean[] newArray(int size) {
            return new QueryParameterBean[size];
        }
    };
}
