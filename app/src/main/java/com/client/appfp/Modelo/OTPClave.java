package com.client.appfp.Modelo;

public class OTPClave {
    String clave;
    String telefono;
    public OTPClave(String clave, String telefono){
        this.telefono=telefono;
        this.clave=clave;
    }
    public OTPClave(){}

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

}
