package model;

import dal.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Category {
    private int cateID;
    private int parentID;
    private String code;
    private String cate;
    private String url;
    private boolean status;
    private Date createdAt;
    private int createdBy;
    private Date modifiedAt;
    private int modifiedBy;
    private ArrayList<Category> children = new ArrayList<>();

    public Category() {
    }

    public Category(int parentID, String code, String cate, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy) {
        this.parentID = parentID;
        this.code = code;
        this.cate = cate;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public Category(int cateID, int parentID, String code, String cate, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy) {
        this.cateID = cateID;
        this.parentID = parentID;
        this.code = code;
        this.cate = cate;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public ArrayList<Category> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Category> children) {
        this.children = children;
    }
        
    public int getCateID() {
        return cateID;
    }

    public void setCateID(int cateID) {
        this.cateID = cateID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }        

    @Override
    public String toString() {
        return "Category{" + "cateID=" + cateID + ", parentID=" + parentID + ", code=" + code + ", cate=" + cate + ", status=" + status + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + '}';
    }
    static Connection cnn; // kết nối
    static PreparedStatement stm; // thực hiên các cáu lệnh sql
    static ResultSet rs; // lưu trữ và xử lí dữ liệu
    public boolean isparent(){
        try {
            String sql ="Select cate_id from Categories where parent_id = "+cateID+"";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("isparent error: " + e.getMessage());
        }
        return false;
    }
    
}
