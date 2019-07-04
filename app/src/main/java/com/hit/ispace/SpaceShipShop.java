package com.hit.ispace;

public class SpaceShipShop {
    private int id;
    private String name_ship;
    private String src_path;
    private int locked;
    private int price;

    public SpaceShipShop(){}

    public SpaceShipShop(int id, String name_ship, String src_path, int locked,int price)
    {
        this.id = id;
        this.name_ship = name_ship;
        this.src_path = src_path;
        this.locked = locked;
        this.price = price;
    }

    public int getId() {return  id;}

    public String getName_ship() {
        return name_ship;
    }

    public String getSrc_path() {
        return src_path;
    }

    public int getLocked() {return  locked;}

    public int getPrice() {return  price;}

    public void setId(int id) {
        this.id = id;
    }

    public void setName_ship(String name_ship) {
        this.name_ship = name_ship;
    }

    public void setSrc_path(String src_path) {
        this.src_path = src_path;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
