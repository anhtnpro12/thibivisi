package dal;

import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;
import static dal.BrandDAO.cnn;
import static dal.BrandDAO.stm;
import model.News;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Category;
import model.NewsGroup;
import model.NewsImage;
import model.OrderDetail;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class NewsDAO {

    public static ArrayList<News> getAllAboutUs() {
        ArrayList<News> news = new ArrayList<>();
        try {
            String str = "select n.news_id, n.code, n.title, n.short_description, n.[description], n.[view], n.[status], n.created_at, n.created_by, n.modified_at, n.modified_by,\n"
                    + "ng.newsgroup_id, ng.parent_id, ng.code, ng.newsgroup_name, ng.[status], ng.created_at, ng.created_by, ng.modified_at, ng.modified_by,\n"
                    + "ni.image_id, ni.[code], ni.[image], ni.[level], ni.[status],ni.created_at, ni.created_by, ni.modified_at, ni.modified_by, n.url\n"
                    + "from News n\n"
                    + "left join NewsGroups ng on ng.newsgroup_id = n.newsgroup_id\n"
                    + "left join NewsImages ni on ni.news_id = n.news_id\n"
                    + "where n.newsgroup_id = 1";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                NewsImage img = new NewsImage();
                img.setImageID(rs.getInt(21));
                img.setCode(rs.getString(22));
                img.setImage(rs.getString(23));
                img.setLevel(rs.getInt(24));
                img.setStatus(rs.getBoolean(25));
                img.setCreated_at(rs.getDate(26));
                img.setCreated_by(rs.getInt(27));
                img.setModified_at(rs.getDate(28));
                img.setModified_by(rs.getInt(29));
                if (preID != rs.getInt(1)) {
                    News anew = new News();
                    anew.setNewsID(rs.getInt(1));
                    anew.setCode(rs.getString(2));
                    anew.setTitle(rs.getString(3));
                    anew.setShortDescription(rs.getString(4));
                    anew.setDescription(rs.getString(5));
                    anew.setView(rs.getInt(6));
                    anew.setStatus(rs.getBoolean(7));
                    anew.setCreatedAt(rs.getDate(8));
                    anew.setCreatedBy(rs.getInt(9));
                    anew.setModifiedAt(rs.getDate(10));
                    anew.setModifiedBy(rs.getInt(11));
                    anew.setUrl(rs.getString(30));
                    anew.getNewsGroup().setNewsgroup_id(rs.getInt(12));
                    anew.getNewsGroup().setParent_id(rs.getInt(13));
                    anew.getNewsGroup().setCode(rs.getString(14));
                    anew.getNewsGroup().setNewsgroup_name(rs.getString(15));
                    anew.getNewsGroup().setStatus(rs.getBoolean(16));
                    anew.getNewsGroup().setCreated_at(rs.getDate(17));
                    anew.getNewsGroup().setCreated_by(rs.getInt(18));
                    anew.getNewsGroup().setModified_at(rs.getDate(19));
                    anew.getNewsGroup().setModified_by(rs.getInt(20));
                    if (img.getImageID() != 0) {
                        anew.getImages().add(img);
                    }
                    preID = anew.getNewsID();
                    news.add(anew);
                } else {
                    if (img.getImageID() != 0) {
                        news.get(news.size() - 1).getImages().add(img);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("get about us new: " + e);
        }
        return news;
    }

    public static ArrayList<News> getAllNotAboutUs() {
        ArrayList<News> news = new ArrayList<>();
        try {
            String str = "select n.news_id, n.code, n.title, n.short_description, n.[description], n.[view], n.[status], n.created_at, n.created_by, n.modified_at, n.modified_by,\n"
                    + "ng.newsgroup_id, ng.parent_id, ng.code, ng.newsgroup_name, ng.[status], ng.created_at, ng.created_by, ng.modified_at, ng.modified_by,\n"
                    + "ni.image_id, ni.[code], ni.[image], ni.[level], ni.[status],ni.created_at, ni.created_by, ni.modified_at, ni.modified_by, n.url\n"
                    + "from News n\n"
                    + "left join NewsGroups ng on ng.newsgroup_id = n.newsgroup_id\n"
                    + "left join NewsImages ni on ni.news_id = n.news_id\n"
                    + "where n.newsgroup_id != 1";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                NewsImage img = new NewsImage();
                img.setImageID(rs.getInt(21));
                img.setCode(rs.getString(22));
                img.setImage(rs.getString(23));
                img.setLevel(rs.getInt(24));
                img.setStatus(rs.getBoolean(25));
                img.setCreated_at(rs.getDate(26));
                img.setCreated_by(rs.getInt(27));
                img.setModified_at(rs.getDate(28));
                img.setModified_by(rs.getInt(29));
                if (preID != rs.getInt(1)) {
                    News anew = new News();
                    anew.setNewsID(rs.getInt(1));
                    anew.setCode(rs.getString(2));
                    anew.setTitle(rs.getString(3));
                    anew.setShortDescription(rs.getString(4));
                    anew.setDescription(rs.getString(5));
                    anew.setView(rs.getInt(6));
                    anew.setStatus(rs.getBoolean(7));
                    anew.setCreatedAt(rs.getDate(8));
                    anew.setCreatedBy(rs.getInt(9));
                    anew.setModifiedAt(rs.getDate(10));
                    anew.setModifiedBy(rs.getInt(11));
                    anew.setUrl(rs.getString(30));
                    anew.getNewsGroup().setNewsgroup_id(rs.getInt(12));
                    anew.getNewsGroup().setParent_id(rs.getInt(13));
                    anew.getNewsGroup().setCode(rs.getString(14));
                    anew.getNewsGroup().setNewsgroup_name(rs.getString(15));
                    anew.getNewsGroup().setStatus(rs.getBoolean(16));
                    anew.getNewsGroup().setCreated_at(rs.getDate(17));
                    anew.getNewsGroup().setCreated_by(rs.getInt(18));
                    anew.getNewsGroup().setModified_at(rs.getDate(19));
                    anew.getNewsGroup().setModified_by(rs.getInt(20));
                    if (img.getImageID() != 0) {
                        anew.getImages().add(img);
                    }
                    preID = anew.getNewsID();
                    news.add(anew);
                } else {
                    if (img.getImageID() != 0) {
                        news.get(news.size() - 1).getImages().add(img);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("getAllNotAboutUs error : " + e);
        }
        return news;
    }


    public static ArrayList<News> getListByPage(ArrayList<News> list, int begin, int end) {
        ArrayList<News> myList = new ArrayList<>();
        int myEnd = Math.min(end, list.size());
        for (int i = begin; i < myEnd; i++) {
            myList.add(list.get(i));
        }
        return myList;
    }

    public static void changeNewsStatus(String newsID, String status) {
        try {
            status = status.equalsIgnoreCase("true") ? "false" : "true";
            String sql = "Update [News] Set [status] = '" + status + "' "
                    + "Where [news_id]=" + newsID + "";

            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.execute();
        } catch (Exception e) {
            System.out.println("changeNewsStatus Error:" + e.getMessage());
        }
    }

    public static News getNewsByNewsID(int parseInt) {
        News anew = new News();
        try {
            String str = "select n.news_id, n.code, n.title, n.short_description, n.[description], n.[view], n.[status], n.created_at, n.created_by, n.modified_at, n.modified_by,\n"
                    + "ng.newsgroup_id, ng.parent_id, ng.code, ng.newsgroup_name, ng.[status], ng.created_at, ng.created_by, ng.modified_at, ng.modified_by,\n"
                    + "ni.image_id, ni.[code], ni.[image], ni.[level], ni.[status],ni.created_at, ni.created_by, ni.modified_at, ni.modified_by\n"
                    + "from News n\n"
                    + "left join NewsGroups ng on ng.newsgroup_id = n.newsgroup_id\n"
                    + "left join NewsImages ni on ni.news_id = n.news_id\n"
                    + "where n.news_id = " + parseInt + "";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                if (preID != rs.getInt(1)) {
                    anew.setNewsID(rs.getInt(1));
                    anew.setCode(rs.getString(2));
                    anew.setTitle(rs.getString(3));
                    anew.setShortDescription(rs.getString(4));
                    anew.setDescription(rs.getString(5));
                    anew.setView(rs.getInt(6));
                    anew.setStatus(rs.getBoolean(7));
                    anew.setCreatedAt(rs.getDate(8));
                    anew.setCreatedBy(rs.getInt(9));
                    anew.setModifiedAt(rs.getDate(10));
                    anew.setModifiedBy(rs.getInt(11));
                    anew.getNewsGroup().setNewsgroup_id(rs.getInt(12));
                    anew.getNewsGroup().setParent_id(rs.getInt(13));
                    anew.getNewsGroup().setCode(rs.getString(14));
                    anew.getNewsGroup().setNewsgroup_name(rs.getString(15));
                    anew.getNewsGroup().setStatus(rs.getBoolean(16));
                    anew.getNewsGroup().setCreated_at(rs.getDate(17));
                    anew.getNewsGroup().setCreated_by(rs.getInt(18));
                    anew.getNewsGroup().setModified_at(rs.getDate(19));
                    anew.getNewsGroup().setModified_by(rs.getInt(20));
                    preID = anew.getNewsID();
                }
                if (rs.getInt(21) != 0) {
                    NewsImage img = new NewsImage();
                    img.setImageID(rs.getInt(21));
                    img.setCode(rs.getString(22));
                    img.setImage(rs.getString(23));
                    img.setLevel(rs.getInt(24));
                    img.setStatus(rs.getBoolean(25));
                    img.setCreated_at(rs.getDate(26));
                    img.setCreated_by(rs.getInt(27));
                    img.setModified_at(rs.getDate(28));
                    img.setModified_by(rs.getInt(29));
                    anew.getImages().add(img);
                }

            }
        } catch (SQLException e) {
            System.out.println("getNewsByNewsID error : " + e);
        }
        return anew;
    }

    public static void updateNews(News n) {
        try {
            String str = "update News\n"
                    + "set code=?, [title]=?, short_description=?\n"
                    + ", [description]=?, [status]=?\n"
                    + ", created_at=?, created_by=?, modified_at=?\n"
                    + ", modified_by=?\n"
                    + "where news_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, n.getCode());
            pstm.setString(2, n.getTitle());
            pstm.setString(3, n.getShortDescription());
            pstm.setString(4, n.getDescription());
            pstm.setBoolean(5, n.isStatus());
            pstm.setObject(6, n.getCreatedAt());
            pstm.setInt(7, n.getCreatedBy());
            pstm.setObject(8, n.getModifiedAt());
            pstm.setInt(9, n.getModifiedBy());
            pstm.setInt(10, n.getNewsID());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateNews error: " + e);
        }
    }

    public static void deleteImageByProductID(int newsID) {
        try {
            String str = "delete from NewsImages\n"
                    + "where news_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, newsID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteImageByProductID error: " + e);
        }
    }

    public static void insertNewsImage(NewsImage pimg) {
        try {
            String str = "insert into NewsImages([news_id]\n"
                    + "           ,[code]\n"
                    + "           ,[image]\n"
                    + "           ,[level]\n"
                    + "           ,[status]\n"
                    + "           ,[created_at]\n"
                    + "           ,[created_by]\n"
                    + "           ,[modified_at]\n"
                    + "           ,[modified_by])\n"
                    + "values (?,?,?,?,?,?,?,?,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, pimg.getNews_id());
            pstm.setString(2, pimg.getCode());
            pstm.setString(3, pimg.getImage());
            pstm.setInt(4, pimg.getLevel());
            pstm.setBoolean(5, pimg.isStatus());
            pstm.setObject(6, pimg.getCreated_at());
            pstm.setInt(7, pimg.getCreated_by());
            pstm.setObject(8, pimg.getModified_at());
            pstm.setInt(9, pimg.getModified_by());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertNewsImage error: " + e);
        }
    }
    
    public static News getNewsByCode(String code) {
        News anew = new News();
        try {
            String str = "select n.news_id, n.code, n.title, n.short_description, n.[description], n.[view], n.[status], n.created_at, n.created_by, n.modified_at, n.modified_by,\n"
                    + "ng.newsgroup_id, ng.parent_id, ng.code, ng.newsgroup_name, ng.[status], ng.created_at, ng.created_by, ng.modified_at, ng.modified_by,\n"
                    + "ni.image_id, ni.[code], ni.[image], ni.[level], ni.[status],ni.created_at, ni.created_by, ni.modified_at, ni.modified_by\n"
                    + "from News n\n"
                    + "left join NewsGroups ng on ng.newsgroup_id = n.newsgroup_id\n"
                    + "left join NewsImages ni on ni.news_id = n.news_id\n"
                    + "where n.code = '" + code + "'";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                if (preID != rs.getInt(1)) {
                    anew.setNewsID(rs.getInt(1));
                    anew.setCode(rs.getString(2));
                    anew.setTitle(rs.getString(3));
                    anew.setShortDescription(rs.getString(4));
                    anew.setDescription(rs.getString(5));
                    anew.setView(rs.getInt(6));
                    anew.setStatus(rs.getBoolean(7));
                    anew.setCreatedAt(rs.getDate(8));
                    anew.setCreatedBy(rs.getInt(9));
                    anew.setModifiedAt(rs.getDate(10));
                    anew.setModifiedBy(rs.getInt(11));
                    anew.getNewsGroup().setNewsgroup_id(rs.getInt(12));
                    anew.getNewsGroup().setParent_id(rs.getInt(13));
                    anew.getNewsGroup().setCode(rs.getString(14));
                    anew.getNewsGroup().setNewsgroup_name(rs.getString(15));
                    anew.getNewsGroup().setStatus(rs.getBoolean(16));
                    anew.getNewsGroup().setCreated_at(rs.getDate(17));
                    anew.getNewsGroup().setCreated_by(rs.getInt(18));
                    anew.getNewsGroup().setModified_at(rs.getDate(19));
                    anew.getNewsGroup().setModified_by(rs.getInt(20));
                    preID = anew.getNewsID();
                }
                if (rs.getInt(21) != 0) {
                    NewsImage img = new NewsImage();
                    img.setImageID(rs.getInt(21));
                    img.setCode(rs.getString(22));
                    img.setImage(rs.getString(23));
                    img.setLevel(rs.getInt(24));
                    img.setStatus(rs.getBoolean(25));
                    img.setCreated_at(rs.getDate(26));
                    img.setCreated_by(rs.getInt(27));
                    img.setModified_at(rs.getDate(28));
                    img.setModified_by(rs.getInt(29));
                    anew.getImages().add(img);
                }

            }
        } catch (SQLException e) {
            System.out.println("getNewsByCode error : " + e);
        }
        return anew;
    }

    public static String getURL(String title, String code) {
        String url = Normalizer.normalize(title, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").trim().replaceAll("\\s+", "-") + "-" + code;
        return url;
    }

    public static void insertNews(News n) {
        try {
            String sql = "INSERT INTO [News]\n"
                    + "           ([newsgroup_id]\n"
                    + "           ,[code]\n"
                    + "           ,[title]\n"
                    + "           ,[short_description]\n"
                    + "           ,[description]\n"
                    + "           ,[view]\n"
                    + "           ,[url]\n"
                    + "           ,[status]\n"
                    + "           ,[created_at]\n"
                    + "           ,[created_by]\n"
                    + "           ,[modified_at]\n"
                    + "           ,[modified_by])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, n.getNewsGroup().getNewsgroup_id());
            pstm.setString(2, genCode());
            pstm.setString(3, n.getTitle());
            pstm.setString(4, n.getShortDescription());
            pstm.setString(5, n.getDescription());
            pstm.setInt(6, 0);
            pstm.setString(7, getURL(n.getTitle(), genCode()));
            pstm.setBoolean(8, n.isStatus());
            pstm.setObject(9, n.getCreatedAt());
            pstm.setInt(10, n.getCreatedBy());
            pstm.setObject(11, n.getModifiedAt());
            pstm.setInt(12, n.getModifiedBy());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertNews error: " + e);
        }
    }

    public static String genCode() {
        ArrayList<Date> AD = new ArrayList();
        try {
            String sql = "Select created_at from News where CAST(created_at AS DATE) =?";
            Connection connection = DBConnect.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            LocalDate currentDate = LocalDate.now();

            // Lấy giá trị ngày, tháng và năm của ngày hôm nay
            int day = currentDate.getDayOfMonth();
            String days, months;
            if (Integer.toString(day).length() == 1) {
                days = "0" + Integer.toString(day);
            } else {
                days = Integer.toString(day);
            }
            int month = currentDate.getMonthValue();
            if (Integer.toString(month).length() == 1) {
                months = "0" + Integer.toString(month);
            } else {
                months = Integer.toString(month);
            }
            int year = currentDate.getYear();
            String years = Integer.toString(year).substring(2, 4);

            pstm.setObject(1, dateFormat.parse(year + "/" + months + "/" + days));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                AD.add(rs.getDate(1));
            }
            int count = AD.size() + 1;
            return days + months + years + "N" + count;
        } catch (Exception e) {
            System.out.println("Gencode error: " + e.getMessage());
        }
        return null;
    }

    public static int getNewsIDMax() {
        int res = -1;
        try {
            String str = "select max(news_id) as 'max'\n"
                    + "from News";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return res;
    }

    public static ArrayList<News> getAllNewsbyID(int id) {
        ArrayList<News> news = new ArrayList<>();
        try {
            String str = "select n.news_id, n.code, n.title, n.short_description, n.[description], n.[view], n.[status], n.created_at, n.created_by, n.modified_at, n.modified_by,\n"
                    + "ng.newsgroup_id, ng.parent_id, ng.code, ng.newsgroup_name, ng.[status], ng.created_at, ng.created_by, ng.modified_at, ng.modified_by,\n"
                    + "ni.image_id, ni.[code], ni.[image], ni.[level], ni.[status],ni.created_at, ni.created_by, ni.modified_at, ni.modified_by, n.url\n"
                    + "from News n\n"
                    + "left join NewsGroups ng on ng.newsgroup_id = n.newsgroup_id\n"
                    + "left join NewsImages ni on ni.news_id = n.news_id\n"
                    + "where n.newsgroup_id = " + id + "";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                NewsImage img = new NewsImage();
                img.setImageID(rs.getInt(21));
                img.setCode(rs.getString(22));
                img.setImage(rs.getString(23));
                img.setLevel(rs.getInt(24));
                img.setStatus(rs.getBoolean(25));
                img.setCreated_at(rs.getDate(26));
                img.setCreated_by(rs.getInt(27));
                img.setModified_at(rs.getDate(28));
                img.setModified_by(rs.getInt(29));
                if (preID != rs.getInt(1)) {
                    News anew = new News();
                    anew.setNewsID(rs.getInt(1));
                    anew.setCode(rs.getString(2));
                    anew.setTitle(rs.getString(3));
                    anew.setShortDescription(rs.getString(4));
                    anew.setDescription(rs.getString(5));
                    anew.setView(rs.getInt(6));
                    anew.setStatus(rs.getBoolean(7));
                    anew.setCreatedAt(rs.getDate(8));
                    anew.setCreatedBy(rs.getInt(9));
                    anew.setModifiedAt(rs.getDate(10));
                    anew.setModifiedBy(rs.getInt(11));
                    anew.setUrl(rs.getString(30));
                    anew.getNewsGroup().setNewsgroup_id(rs.getInt(12));
                    anew.getNewsGroup().setParent_id(rs.getInt(13));
                    anew.getNewsGroup().setCode(rs.getString(14));
                    anew.getNewsGroup().setNewsgroup_name(rs.getString(15));
                    anew.getNewsGroup().setStatus(rs.getBoolean(16));
                    anew.getNewsGroup().setCreated_at(rs.getDate(17));
                    anew.getNewsGroup().setCreated_by(rs.getInt(18));
                    anew.getNewsGroup().setModified_at(rs.getDate(19));
                    anew.getNewsGroup().setModified_by(rs.getInt(20));
                    if (img.getImageID() != 0) {
                        anew.getImages().add(img);
                    }
                    preID = anew.getNewsID();
                    news.add(anew);
                } else {
                    if (img.getImageID() != 0) {
                        news.get(news.size() - 1).getImages().add(img);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("getAllNotAboutUs error : " + e);
        }
        return news;
    }
    public static void main(String[] args) {
        System.out.println(getTop3ViewNews().get(2).getNewsID());
    }
    public static ArrayList<News> getTop2News() {
        ArrayList<News> news = new ArrayList<>();
        try {
            String str = "select top 2 n.news_id, n.code, n.title, n.short_description, n.[description], n.[view], n.[status], n.created_at, n.created_by, n.modified_at, n.modified_by,\n"
                    + "ng.newsgroup_id, ng.parent_id, ng.code, ng.newsgroup_name, ng.[status], ng.created_at, ng.created_by, ng.modified_at, ng.modified_by,\n"
                    + "ni.image_id, ni.[code], ni.[image], ni.[level], ni.[status],ni.created_at, ni.created_by, ni.modified_at, ni.modified_by, n.url\n"
                    + "from News n\n"
                    + "left join NewsGroups ng on ng.newsgroup_id = n.newsgroup_id\n"
                    + "left join NewsImages ni on ni.news_id = n.news_id\n"
                    + "order by n.created_at desc";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                NewsImage img = new NewsImage();
                img.setImageID(rs.getInt(21));
                img.setCode(rs.getString(22));
                img.setImage(rs.getString(23));
                img.setLevel(rs.getInt(24));
                img.setStatus(rs.getBoolean(25));
                img.setCreated_at(rs.getDate(26));
                img.setCreated_by(rs.getInt(27));
                img.setModified_at(rs.getDate(28));
                img.setModified_by(rs.getInt(29));
                if (preID != rs.getInt(1)) {
                    News anew = new News();
                    anew.setNewsID(rs.getInt(1));
                    anew.setCode(rs.getString(2));
                    anew.setTitle(rs.getString(3));
                    anew.setShortDescription(rs.getString(4));
                    anew.setDescription(rs.getString(5));
                    anew.setView(rs.getInt(6));
                    anew.setStatus(rs.getBoolean(7));
                    anew.setCreatedAt(rs.getDate(8));
                    anew.setCreatedBy(rs.getInt(9));
                    anew.setModifiedAt(rs.getDate(10));
                    anew.setModifiedBy(rs.getInt(11));
                    anew.setUrl(rs.getString(30));
                    anew.getNewsGroup().setNewsgroup_id(rs.getInt(12));
                    anew.getNewsGroup().setParent_id(rs.getInt(13));
                    anew.getNewsGroup().setCode(rs.getString(14));
                    anew.getNewsGroup().setNewsgroup_name(rs.getString(15));
                    anew.getNewsGroup().setStatus(rs.getBoolean(16));
                    anew.getNewsGroup().setCreated_at(rs.getDate(17));
                    anew.getNewsGroup().setCreated_by(rs.getInt(18));
                    anew.getNewsGroup().setModified_at(rs.getDate(19));
                    anew.getNewsGroup().setModified_by(rs.getInt(20));
                    if (img.getImageID() != 0) {
                        anew.getImages().add(img);
                    }
                    preID = anew.getNewsID();
                    news.add(anew);
                } else {
                    if (img.getImageID() != 0) {
                        news.get(news.size() - 1).getImages().add(img);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("getTop2News error : " + e);
        }
        return news;
    }

    public static void onemoreview(News news) {
        try {
            String str = "update News\n"
                    + "set [view]=?\n"
                    + "where news_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, news.getView() + 1);
            pstm.setInt(2, news.getNewsID());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("onemoreview error: " + e);
        }
    }

    public static ArrayList<News> getTop3ViewNews() {
        ArrayList<News> news = new ArrayList<>();
        try {
            String str = "select top 3  n.news_id, n.code, n.title, n.short_description, n.[description], n.[view], n.[status], n.created_at, n.created_by, n.modified_at, n.modified_by,\n"
                    + "                    ng.newsgroup_id, ng.parent_id, ng.code, ng.newsgroup_name, ng.[status], ng.created_at, ng.created_by, ng.modified_at, ng.modified_by,\n"
                    + "                    ni.image_id, ni.[code], ni.[image], ni.[level], ni.[status],ni.created_at, ni.created_by, ni.modified_at, ni.modified_by, n.url, n.newsgroup_id\n"
                    + "                    from News n\n"
                    + "                    left join NewsGroups ng on ng.newsgroup_id = n.newsgroup_id\n"
                    + "                    left join NewsImages ni on ni.news_id = n.news_id\n"
                    + "                     where n.newsgroup_id != 1\n"
                    + "                    order by n.[view] desc";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                NewsImage img = new NewsImage();
                img.setImageID(rs.getInt(21));
                img.setCode(rs.getString(22));
                img.setImage(rs.getString(23));
                img.setLevel(rs.getInt(24));
                img.setStatus(rs.getBoolean(25));
                img.setCreated_at(rs.getDate(26));
                img.setCreated_by(rs.getInt(27));
                img.setModified_at(rs.getDate(28));
                img.setModified_by(rs.getInt(29));
                if (preID != rs.getInt(1)) {
                    News anew = new News();
                    anew.setNewsID(rs.getInt(1));
                    anew.setCode(rs.getString(2));
                    anew.setTitle(rs.getString(3));
                    anew.setShortDescription(rs.getString(4));
                    anew.setDescription(rs.getString(5));
                    anew.setView(rs.getInt(6));
                    anew.setStatus(rs.getBoolean(7));
                    anew.setCreatedAt(rs.getDate(8));
                    anew.setCreatedBy(rs.getInt(9));
                    anew.setModifiedAt(rs.getDate(10));
                    anew.setModifiedBy(rs.getInt(11));
                    anew.setUrl(rs.getString(30));
                    anew.getNewsGroup().setNewsgroup_id(rs.getInt(12));
                    anew.getNewsGroup().setParent_id(rs.getInt(13));
                    anew.getNewsGroup().setCode(rs.getString(14));
                    anew.getNewsGroup().setNewsgroup_name(rs.getString(15));
                    anew.getNewsGroup().setStatus(rs.getBoolean(16));
                    anew.getNewsGroup().setCreated_at(rs.getDate(17));
                    anew.getNewsGroup().setCreated_by(rs.getInt(18));
                    anew.getNewsGroup().setModified_at(rs.getDate(19));
                    anew.getNewsGroup().setModified_by(rs.getInt(20));
                    news.add(anew);
                    if (img.getImageID() != 0) {
                        anew.getImages().add(img);
                    }
                    preID = anew.getNewsID();
                    news.add(anew);
                } else {
                    if (img.getImageID() != 0) {
                        news.get(news.size() - 1).getImages().add(img);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("getTop3ViewNews error : " + e);
        }
        return news;
    }

}
