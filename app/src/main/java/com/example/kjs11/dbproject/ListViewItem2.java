package com.example.kjs11.dbproject;

/**
 * Created by kjs11 on 2017-11-23.
 */

import android.graphics.drawable.Drawable;

public class ListViewItem2 {
    private Drawable iconDrawable ;
    private String name ;
    private String addr ;
    private String lati;
    private String longti;
    private String key;

    public void setIcon(Drawable icon) { iconDrawable = icon ;    }
    public void setName(String name) {
        this.name = name ;
    }
    public void setAddr(String addr) { this.addr = addr ;   }
    public void setLati(String lati) { this.lati = lati ;   }
    public void setLongti(String longti) { this.longti = longti ;   }
    public void setKey(String key) {this.key = key;}



    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getName() {
        return this.name ;
    }
    public String getAddr() {
        return this.addr ;
    }
    public String getLati() { return this.lati ; }
    public String getLongti() { return this.longti ; }
    public String getKey() {return this.key; }


}


