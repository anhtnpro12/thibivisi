package model;

import java.util.ArrayList;
import java.util.Date;

public class News {

    private int newsID;
    private NewsGroup newsGroup = new NewsGroup();
    private String code;
    private String title;
    private String shortDescription;
    private String description;
    private String url;
    private int view;
    private boolean status;
    private Date createdAt;
    private int createdBy;
    private Date modifiedAt;
    private int modifiedBy;
    public ArrayList<NewsImage> images = new ArrayList<>();

    public News() {
    }

    public News(int newsID, String code, String title, String shortDescription, String description, String url, int view, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy) {
        this.newsID = newsID;
        this.code = code;
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.url = url;
        this.view = view;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public News(int newsID, NewsGroup newsGroup, String code, String title, String shortDescription, String description, int view, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy) {
        this.newsID = newsID;
        this.newsGroup = newsGroup;
        this.code = code;
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.view = view;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public News(int newsID, NewsGroup newsGroup, String code, String title, String shortDescription, String description, int view, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy, ArrayList<NewsImage> images) {
        this.newsID = newsID;
        this.newsGroup = newsGroup;
        this.code = code;
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.view = view;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.images = images;
    }

    public News(int newsID, NewsGroup newsGroup, String code, String title, String shortDescription, String description, String url, int view, boolean status, Date createdAt, int createdBy, Date modifiedAt, int modifiedBy, ArrayList<NewsImage> images) {
        this.newsID = newsID;
        this.newsGroup = newsGroup;
        this.code = code;
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.url = url;
        this.view = view;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public NewsGroup getNewsGroup() {
        return newsGroup;
    }

    public void setNewsGroup(NewsGroup newsGroup) {
        this.newsGroup = newsGroup;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
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

    public ArrayList<NewsImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<NewsImage> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "News{" + "newsID=" + newsID + ", newsGroup=" + newsGroup + ", code=" + code + ", title=" + title + ", shortDescription=" + shortDescription + ", description=" + description + ", url=" + url + ", view=" + view + ", status=" + status + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + ", images=" + images + '}';
    }

}
