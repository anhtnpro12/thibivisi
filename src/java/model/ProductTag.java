/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author TNA
 */
public class ProductTag {
    private int productTagID; 
    private int productID;    
    private int tagID;

    public ProductTag() {
    }

    public ProductTag(int productTagID, int productID, int tagID) {
        this.productTagID = productTagID;
        this.productID = productID;
        this.tagID = tagID;
    }

    public int getProductTagID() {
        return productTagID;
    }

    public void setProductTagID(int productTagID) {
        this.productTagID = productTagID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }
    
    
}
