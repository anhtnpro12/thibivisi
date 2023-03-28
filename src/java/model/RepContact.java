/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author M.S.I
 */
public class RepContact {
    private int repcontactID;
    private String code;
    private int contactID;
    private String title;
    private String message;
    private boolean status;
    private Date createdAt;
    private int createdBy;
    private Date modifiedAt;
    private int modifiedBy;

    public RepContact() {
    }

    public RepContact(int repcontactID, String code, int contactID, String title, String message, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy) {
        this.repcontactID = repcontactID;
        this.code = code;
        this.contactID = contactID;
        this.title = title;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    

    public int getRepcontactID() {
        return repcontactID;
    }

    public void setRepcontactID(int repcontactID) {
        this.repcontactID = repcontactID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RepContact{" + "repcontactID=" + repcontactID + ", code=" + code + ", contactID=" + contactID + ", title=" + title + ", message=" + message + ", status=" + status + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + '}';
    }
    
    
    
}
