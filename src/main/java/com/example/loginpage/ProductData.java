package com.example.loginpage;

import java.sql.Date;

public class ProductData {

    private int id;
    private String product_id;
    private String Product_Name;
    private String stock;
    private String price;
    private String status;
    private String image;
    private java.sql.Date Date;
    private int quantity;
private String type;
    public ProductData(int id, String product_id,String product_Name,
                       String stock, String price, String status,String type,
                       String image, java.sql.Date date) {
        this.id = id;
        Product_Name = product_Name;
        this.product_id = product_id;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.image = image;
        Date = date;
        this.type=type;
    }

    public ProductData(int id, String product_id, String product_Name,
                       String type, int quantity, String price, String image, Date date){
        this.id = id;
        this.product_id = product_id;
        this.Product_Name = product_Name;
        this.type = type;
        this.price = price;
        this.image = image;
        Date = date;
        this.quantity = quantity;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }
}
