package model;


import java.util.ArrayList;
import java.util.Date;

public class Product {
    private int productID;
    private Category cate;
    private Brand brand;
    private String code;
    private String name;
    private String url;
    private String shortdescript;
    private String description;
    private double ratingStar;
    private double price;
    private boolean outOfStock;
    private boolean status;
    private Date createdAt;
    private int createdBy;
    private Date modifiedAt;
    private int modifiedBy;  
    private ArrayList<ProductImage> images = new ArrayList<>();    

    public Product() {
    }

    public Product(Category cate, Brand brand, String code, String name, String shortdescript, String description, double ratingStar, double price, boolean outOfStock, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy) {
        this.cate = cate;
        this.brand = brand;
        this.code = code;
        this.name = name;
        this.shortdescript = shortdescript;
        this.description = description;
        this.ratingStar = ratingStar;
        this.price = price;
        this.outOfStock = outOfStock;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public Product(Category cate, Brand brand, String code, String name, String url, String shortdescript, String description, double ratingStar, double price, boolean outOfStock, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy) {
        this.cate = cate;
        this.brand = brand;
        this.code = code;
        this.name = name;
        this.url = url;
        this.shortdescript = shortdescript;
        this.description = description;
        this.ratingStar = ratingStar;
        this.price = price;
        this.outOfStock = outOfStock;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }
    
        
    public Product(Category cate, Brand brand, String code, String name, String shortdescript, String description, double ratingStar, double price, boolean outOfStock, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy, ArrayList<ProductImage> images) {
        this.cate = cate;
        this.brand = brand;
        this.code = code;
        this.name = name;
        this.shortdescript = shortdescript;
        this.description = description;
        this.ratingStar = ratingStar;
        this.price = price;
        this.outOfStock = outOfStock;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.images = images;
    }

    public Product(int productID, Category cate, Brand brand, String code, String name, String shortdescript, String description, double ratingStar, double price, boolean outOfStock, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy) {
        this.productID = productID;
        this.cate = cate;
        this.brand = brand;
        this.code = code;
        this.name = name;
        this.shortdescript = shortdescript;
        this.description = description;
        this.ratingStar = ratingStar;
        this.price = price;
        this.outOfStock = outOfStock;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }
    
    
    
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public Category getCate() {
        return cate;
    }

    public void setCate(Category cate) {
        this.cate = cate;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortdescript() {
        return shortdescript;
    }

    public void setShortdescript(String shortdescript) {
        this.shortdescript = shortdescript;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(double ratingStar) {
        this.ratingStar = ratingStar;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ArrayList<ProductImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<ProductImage> images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }        

    @Override
    public String toString() {
        return "Product{" + "productID=" + productID + ", cate=" + cate + ", brand=" + brand + ", code=" + code + ", name=" + name + ", shortdescript=" + shortdescript + ", description=" + description + ", ratingStar=" + ratingStar + ", price=" + price + ", outOfStock=" + outOfStock + ", status=" + status + '}';
    }   
}
