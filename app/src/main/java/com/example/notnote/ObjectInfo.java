package com.example.notnote;

import java.util.ArrayList;
/*  NIM : 10119288
    Nama : Muhamad Dimas
    Kelas : IF-7
    Tanggal : Rabu, 11 Agustus 2022
    */
public class ObjectInfo {
    int ivObject;
    String txTips;

    public ObjectInfo(int ivObject, String txTips) {
        this.ivObject = ivObject;
        this.txTips = txTips;
    }

    public static ArrayList<ObjectInfo> createData() {
        ArrayList<ObjectInfo> justObject = new ArrayList<>();
        justObject.add(new ObjectInfo(R.drawable.write,"Aplikasi Ini merupakan sebuah tempat untuk menyimpan catatan dengan mudah"));
        return justObject;
    }
}

