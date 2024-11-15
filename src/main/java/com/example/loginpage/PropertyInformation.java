package com.example.loginpage;

public class PropertyInformation implements Comparable{
    private String type1;
    private String roadNo;
    private String houseNo;
    private String bedroomCount;
    private String bathroomCount;
    private String block;
    private String price;
    private String area;

    // Constructor
    public PropertyInformation(String type1, String roadNo, String houseNo, String bedroomCount,
                               String bathroomCount, String block, String price, String area) {
        this.type1 = type1;
        this.roadNo = roadNo;
        this.houseNo = houseNo;
        this.bedroomCount = bedroomCount;
        this.bathroomCount = bathroomCount;
        this.block = block;
        this.price = price;
        this.area = area;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String toString() {
        return "\ntype1 : "+type1+"\nroadno : "+roadNo+"\nHouseNo : "+houseNo+"\nBedroomCount : "+bedroomCount+"\nbathroomcount : "+"\nBlock : "+block+"\nPrice : "+price+"\nArea : "+area;
    }

    // Getters
    public String getType1() {
        return type1;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public String getBedroomCount() {
        return bedroomCount;
    }

    public String getBathroomCount() {
        return bathroomCount;
    }

    public String getBlock() {
        return block;
    }

    public String getPrice() {
        return price;
    }

    public String getArea() {
        return area;
    }
}
