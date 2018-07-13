package com.example.acer.datastorageapp.data;

public class ProductObject {
    private String product_name;
    private int price;
    private int quantity;
    private int supplier_id;
    private String supplier_number;
    private String supplier;

    public ProductObject(String product_name, int price, int quantity, int supplier_id, String supplier_number){
        this.product_name = product_name;
        this.price = price;
        this.quantity = quantity;
        this.supplier_id = supplier_id;
        this.supplier_number = supplier_number;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSupplier() {
        getSupplierFromId(supplier_id);
        return supplier;
}

    public String getSupplier_number() {
        return supplier_number;
    }

    public void getSupplierFromId(int supplier_id){
        switch (supplier_id){
            case 1:
                supplier = "Google";
                break;
            case 2:
                supplier = "Udacity";
                break;
            case 3:
                supplier = "Facebook";
                break;
            case 4:
                supplier = "Youtube";
                break;
            default:
                supplier = "unknown";
                break;
        }
    }
}
