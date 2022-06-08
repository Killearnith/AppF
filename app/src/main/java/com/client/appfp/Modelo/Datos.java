package com.client.appfp.Modelo;

import android.os.Parcel;
import android.os.Parcelable;


public class Datos implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Datos createFromParcel(Parcel in) {
            return new Datos(in);
        }

        public Datos[] newArray(int size) {
            return new Datos[size];
        }
    };
    private String clave;
    private String telefono;
    private String auth;
    private String tokenconex;
    private String urlDB;
    /*
    public Datos(String clave, String telefono){
        this.telefono=telefono;
        this.clave=clave;
    }
    */
    //Constructor completo
    public Datos(String clave, String telefono, String auth, String tokenconex, String urlDB){
        this.clave = clave;
        this.telefono = telefono;
        this.auth =  auth;
        this.tokenconex = tokenconex;
        this.urlDB = urlDB;

    }

    //Constructor vacio
    public Datos(){}

    public String getClave() {
        return clave;
    }

    public String getTelefono() {
        return telefono;
    }


    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getTokenconex() {
        return tokenconex;
    }

    public void setTokenconex(String tokenconex) {
        this.tokenconex = tokenconex;
    }

    public String getUrlDB() {
        return urlDB;
    }

    public void setUrlDB(String urlDB) {
        this.urlDB = urlDB;
    }

    // Parcelling part
    public Datos(Parcel in){
        this.clave = in.readString();
        this.telefono = in.readString();
        this.auth =  in.readString();
        this.tokenconex = in.readString();
        this.urlDB = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.clave);
        dest.writeString(this.telefono);
        dest.writeString(this.auth);
        dest.writeString(this.tokenconex);
        dest.writeString(this.urlDB);
    }
}
