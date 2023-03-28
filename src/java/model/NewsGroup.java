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
public class NewsGroup {
    private int newsgroup_id;
    private int parent_id;
    private String code;
    private String newsgroup_name;
    private String url;
    private boolean status;
    private Date created_at;
    private int created_by;
    private Date modified_at;
    private int modified_by;

    public NewsGroup() {
    }

    public NewsGroup(int newsgroup_id, int parent_id, String code, String newsgroup_name, String url, boolean status, Date created_at, int created_by, Date modified_at, int modified_by) {
        this.newsgroup_id = newsgroup_id;
        this.parent_id = parent_id;
        this.code = code;
        this.newsgroup_name = newsgroup_name;
        this.url = url;
        this.status = status;
        this.created_at = created_at;
        this.created_by = created_by;
        this.modified_at = modified_at;
        this.modified_by = modified_by;
    }
    
    public NewsGroup(int newsgroup_id, int parent_id, String code, String newsgroup_name, boolean status, Date created_at, int created_by, Date modified_at, int modified_by) {
        this.newsgroup_id = newsgroup_id;
        this.parent_id = parent_id;
        this.code = code;
        this.newsgroup_name = newsgroup_name;
        this.status = status;
        this.created_at = created_at;
        this.created_by = created_by;
        this.modified_at = modified_at;
        this.modified_by = modified_by;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNewsgroup_id() {
        return newsgroup_id;
    }

    public void setNewsgroup_id(int newsgroup_id) {
        this.newsgroup_id = newsgroup_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewsgroup_name() {
        return newsgroup_name;
    }

    public void setNewsgroup_name(String newsgroup_name) {
        this.newsgroup_name = newsgroup_name;
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
        return "NewsGroup{" + "newsgroup_id=" + newsgroup_id + ", parent_id=" + parent_id + ", code=" + code + ", newsgroup_name=" + newsgroup_name + ", status=" + status + ", created_at=" + created_at + ", created_by=" + created_by + ", modified_at=" + modified_at + ", modified_by=" + modified_by + '}';
    }
    
}
