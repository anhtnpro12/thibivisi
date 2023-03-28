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
public class User {

    public int user_id, role, created_by, modified_by;
    public String code, fullname, dob, address, email, password, id_card, avatar, gender;
    public boolean status;
    public Date created_at, modified_at;
    private String dobConverted;

    public String getDobConverted() {
        return convertDate(dob);
    }
    
    public static String convertDate(String date){
        String[] dmy = date.split("/");
        return dmy[2] + '-' + dmy[1] + '-' + dmy[0];
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getModified_by() {
        return modified_by;
    }

    public void setModified_by(int modified_by) {
        this.modified_by = modified_by;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean getStatus() {
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

    public Date getModified_at() {
        return modified_at;
    }

    public void setModified_at(Date modified_at) {
        this.modified_at = modified_at;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User() {
    }

    public User(int user_id, int role, int created_by, int modified_by, String code, String fullname, String dob, String gender, String address, String email, String password, String id_card, String avatar, boolean status, Date created_at, Date modified_at, boolean bit) {
        this.user_id = user_id;
        this.role = role;
        this.created_by = created_by;
        this.modified_by = modified_by;
        this.code = code;
        this.fullname = fullname;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.password = password;
        this.id_card = id_card;
        this.avatar = avatar;
        this.status = status;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", role=" + role + ", created_by=" + created_by + ", modified_by=" + modified_by + ", code=" + code + ", fullname=" + fullname + ", dob=" + dob + ", address=" + address + ", email=" + email + ", password=" + password + ", id_card=" + id_card + ", avatar=" + avatar + ", gender=" + gender + ", status=" + status + ", created_at=" + created_at + ", modified_at=" + modified_at + '}';
    }
    
    public static void main(String[] args) {
        System.out.println(User.convertDate("14/02/2002"));
    }
    
}
