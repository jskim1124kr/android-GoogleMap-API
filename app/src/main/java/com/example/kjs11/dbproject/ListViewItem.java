package com.example.kjs11.dbproject;

/**
 * Created by kjs11 on 2017-11-23.
 */

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable ;
    private String place ;
    private String addr ;
    private String tel;
    private String lati;
    private String longti;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setPlace(String place) {
        this.place = place ;
    }
    public void setAddr(String addr) { this.addr = addr ;   }
    public void setTel(String tel) { this.tel = tel ;   }
    public void setLati(String lati) { this.lati = lati ;   }
    public void setLongti(String longti) { this.longti = longti ;   }




    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getPlace() {
        return this.place ;
    }
    public String getAddr() {
        return this.addr ;
    }
    public String getTel() { return this.tel ; }
    public String getLati() { return this.lati ; }
    public String getLongti() { return this.longti ; }


}


