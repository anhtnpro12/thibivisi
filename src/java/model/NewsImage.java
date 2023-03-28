/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class NewsImage {
    private int imageID;
    private int news_id;
    private String code;
    private String image;
    private int level;
    private boolean status;
    private Date created_at;
    private int created_by;
    private Date modified_at;
    private int modified_by;

    public NewsImage() {
    }

    public NewsImage(int imageID, int news_id, String code, String image, int level, boolean status, Date created_at, int created_by, Date modified_at, int modified_by) {
        this.imageID = imageID;
        this.news_id = news_id;
        this.code = code;
        this.image = image;
        this.level = level;
        this.status = status;
        this.created_at = created_at;
        this.created_by = created_by;
        this.modified_at = modified_at;
        this.modified_by = modified_by;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Date getModified_at() {
        return modified_at;
    }

    public void setModified_at(Date modified_at) {
        this.modified_at = modified_at;
    }

    public int getModified_by() {
        return modified_by;
    }

    public void setModified_by(int modified_by) {
        this.modified_by = modified_by;
    }

    @Override
    public String toString() {
        return "NewsImage{" + "newsimage=" + imageID + ", news_id=" + news_id + ", code=" + code + ", image=" + image + ", level=" + level + ", status=" + status + ", created_at=" + created_at + ", created_by=" + created_by + ", modified_at=" + modified_at + ", modified_by=" + modified_by + '}';
    }
    
    
}
