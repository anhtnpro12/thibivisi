package dal;

import model.User;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author M.S.I
 */
public class UserDAO {

    static Connection cnn; // kết nối
    static PreparedStatement stm; // thực hiên các cáu lệnh sql
    static ResultSet rs; // lưu trữ và xử lí dữ liệu

    public static void createNewUser(String code, String fullname, String dob, String gender, String address, String email, String pass, String id_card, String avatar, int role, int status, String created_at, String created_by, String modified_at, String modified_by) {
        try {
            String sql = "insert [User] ([code],[fullname],[dob],[gender],[address],[email],[password],"
                    + "[id_card],[avatar],[role],[status],[created_at],[created_by],[modified_at],[modified_by])"
                    + "  values (N'" + code + "',"
                    + "N'" + fullname + "',"
                    + "CAST(N'" + dob + "' AS Date),"
                    + "N'" + (gender.equals("Male") ? 1 : 0) + "',"
                    + "N'" + address + "',"
                    + "N'" + email + "',"
                    + "N'" + pass + "'"
                    + "N'" + id_card + "'"
                    + "N'" + avatar + "'"
                    + "N'" + role + "'"
                    + "N'" + status + "'"
                    + "N'" + created_at + "'"
                    + "N'" + created_by + "'"
                    + "N'" + modified_at + "'"
                    + "N'" + modified_by + "')";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.execute();
        } catch (Exception e) {
            System.out.println("createNewUser error" + e.getMessage());
        }
    }

    public static void createUser(User u) {
        try {
            String sql = "INSERT INTO [dbo].[Users]([code],[fullname],[dob],[gender],[address],[email],[password],[id_card]\n"
                    + ",[avatar],[role],[status],[created_at],[created_by],[modified_at],[modified_by])\n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, u.getCode());
            pstm.setString(2, u.getFullname());
            pstm.setDate(3, java.sql.Date.valueOf(u.getDob()));
            pstm.setInt(4, u.getGender().equalsIgnoreCase("male") ? 1 : 0);
            pstm.setString(5, u.getAddress());
            pstm.setString(6, u.getEmail());
            pstm.setString(7, u.getPassword());
            pstm.setString(8, u.getId_card());
            pstm.setString(9, u.getAvatar());
            pstm.setInt(10, u.getRole());
            pstm.setInt(11, u.getStatus() == true ? 1 : 0);
            pstm.setDate(12, new java.sql.Date(System.currentTimeMillis()));
            pstm.setInt(13, u.getCreated_by());
            pstm.setDate(14, new java.sql.Date(System.currentTimeMillis()));
            pstm.setInt(15, u.getModified_by());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("createNewUser error" + e.getMessage());
        }
    }

    public static void updateUser(User u) {
        try {
            String sql = "UPDATE [dbo].[Users]\n"
                    + "   SET [code] = ?\n"
                    + "      ,[fullname] = ?\n"
                    + "      ,[dob] = ?\n"
                    + "      ,[gender] = ?\n"
                    + "      ,[address] = ?\n"
                    + "      ,[email] = ?\n"
                    + "      ,[password] = ?\n"
                    + "      ,[id_card] = ?\n"
                    + "      ,[avatar] = ?\n"
                    + "      ,[role] = ?\n"
                    + "      ,[status] = ?\n"
                    + "      ,[created_at] = ?\n"
                    + "      ,[created_by] = ?\n"
                    + "      ,[modified_at] = ?\n"
                    + "      ,[modified_by] = ?\n"
                    + " WHERE user_id = ?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, u.getCode());
            pstm.setString(2, u.getFullname());
            pstm.setDate(3, java.sql.Date.valueOf(u.getDob()));
            pstm.setInt(4, u.getGender().equalsIgnoreCase("male") ? 1 : 0);
            pstm.setString(5, u.getAddress());
            pstm.setString(6, u.getEmail());
            pstm.setString(7, u.getPassword());
            pstm.setString(8, u.getId_card());
            pstm.setString(9, u.getAvatar());
            pstm.setInt(10, u.getRole());
            pstm.setInt(11, u.getStatus() == true ? 1 : 0);
            pstm.setDate(12, new java.sql.Date(System.currentTimeMillis()));
            pstm.setInt(13, u.getCreated_by());
            pstm.setDate(14, new java.sql.Date(System.currentTimeMillis()));
            pstm.setInt(15, u.getModified_by());
            pstm.setInt(16, u.getUser_id());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateNewUser error" + e.getMessage());
        }
    }

    public static boolean IfEmailExist(String email) {

        try {
            String sql = "select email from Users where email=?";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("IfEmailExist error:" + e.getMessage());
        }
        return false;
    }

    public void updateUser(String user_id, String fullname, String dob, String gender, String address, String email, String pass, String id_card, String avatar) {
        try {
            String sql = "UPDATE [User]\n"
                    + "   SET [fullname] = N'" + fullname + "',[dob] = " + dob + ",[gender] = '" + gender + "',[email] = '" + email + "',[avatar] = N'" + avatar + "',[address] = N'" + address + "',[id_card] = N'" + id_card + "',[pass] = N'" + pass + "'\n"
                    + " WHERE [user_id]=" + user_id + "";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.execute();
        } catch (Exception e) {
            System.out.println("UpdateUser failed:" + e.getMessage());
        }
    }

    public boolean checkDupCode(String code) {
        try {
            String sql = "select [email] from [Users] where [code]='" + code + "'";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("checkDupCode error:" + e.getMessage());
        }
        return true;
    }

    public boolean checkDupCEmail(String email) {
        try {
            String sql = "select [code] from [Users] where [email]='" + email + "'";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("checkDupEmail error:" + e.getMessage());
        }
        return true;
    }

    public static void sendMail(String to, String sub, String msg, String username, String pass) {
        Properties pr = new Properties();
        pr.setProperty("mail.smtp.host", "smtp.gmail.com");
        pr.setProperty("mail.smtp.port", "587"); //TLS
        pr.setProperty("mail.smtp.auth", "true");
        pr.setProperty("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(pr, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, pass);
            }

        });
        try {
            Message mess = new MimeMessage(session);
            mess.setFrom(new InternetAddress(username));
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            mess.setSubject(sub);
            mess.setContent(msg, "text/html; charset=utf-8");
            Transport.send(mess);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String email, String pass) {
        try {
            String sql = "Select [user_id],[code],[fullname],[dob],[gender],[address],[email],[password],\n"
                    + "[id_card],[avatar],[role],[status],[created_at],[created_by],[modified_at],[modified_by] from [Users] \n"
                    + "where email='" + email + "' AND password='" + pass + "'";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.user_id = rs.getInt(1);
                u.code = rs.getString(2);
                u.fullname = rs.getString(3);
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                u.dob = f.format(rs.getDate(4));
                u.gender = (rs.getBoolean(5) ? "Male" : "Female");
                u.address = rs.getString(6);
                u.email = rs.getString(7);
                u.password = rs.getString(8);
                u.id_card = rs.getString(9);
                u.avatar = rs.getString(10);
                u.role = rs.getInt(11);
                u.status = rs.getBoolean(12);
                u.created_at = rs.getDate(13);
                u.created_by = rs.getInt(14);
                u.modified_at = rs.getDate(15);
                u.modified_by = rs.getInt(16);
                return u;
            }
        } catch (Exception e) {
            System.out.println("getUser Error: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<User> getAllUser(String target) {
        ArrayList<User> list = new ArrayList<>();
        try {
            String sql = "Select [user_id],[code],[fullname],[dob],[gender],[address],[email],[password],\n"
                    + "[id_card],[avatar],[role],[status],[created_at],[created_by],[modified_at],[modified_by] from [Users] \n"
                    + "order by " + target + " desc, user_id desc";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.user_id = rs.getInt(1);
                u.code = rs.getString(2);
                u.fullname = rs.getString(3);
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                u.dob = f.format(rs.getDate(4));
                u.gender = (rs.getBoolean(5) ? "Male" : "Female");
                u.address = rs.getString(6);
                u.email = rs.getString(7);
                u.password = rs.getString(8);
                u.id_card = rs.getString(9);
                u.avatar = rs.getString(10);
                u.role = rs.getInt(11);
                u.status = rs.getBoolean(12);
                u.created_at = rs.getDate(13);
                u.created_by = rs.getInt(14);
                u.modified_at = rs.getDate(15);
                u.modified_by = rs.getInt(16);
                list.add(u);
            }
        } catch (Exception e) {
            System.out.println("getUser Error: " + e.getMessage());
        }
        return list;
    }

    public static ArrayList<User> getListByPage(ArrayList<User> list, int begin, int end) {
        ArrayList<User> myList = new ArrayList<>();
        int myEnd = Math.min(end, list.size());
        for (int i = begin; i < myEnd; i++) {
            myList.add(list.get(i));
        }
        return myList;
    }

    public static void updateNewPass(String user_id, String newpass) {
        try {
            String sqlUpdate = "Update [Users] set password='" + encryptPassword(newpass) + "' "
                    + "where user_id=" + user_id + "";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sqlUpdate);
            stm.execute();
        } catch (Exception e) {
            System.out.println("UpdateNewPass fail:" + e.getMessage());
        }
    }

    public static boolean checkPassByID(String user_id, String oldpass) {
        String a = "";
        try {
            String sql = "select [password] from Users where user_id = ?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, user_id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                a = rs.getString("password");
            }
        } catch (Exception e) {
            System.out.println(a);
            System.out.println("CheckPass fail:" + e.getMessage());
        }
        return a.equals(encryptPassword(oldpass));
    }

    public static String getPassbyEmail(String email) {
        try {
            String sql = "select [password] from [Users] where [email]='" + email + "'";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getPassError" + e.getMessage());
        }
        return null;
    }

    public static User getUserByID(int id) {
        User u = new User();
        try {
            String str = "Select [user_id],[code],[fullname],[dob],[gender],[address],[email],[password]\n"
                    + ",[id_card],[avatar],[role],[status],[created_at],[created_by],[modified_at],[modified_by] \n"
                    + "from [Users]\n"
                    + "where [user_id]=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                u.user_id = rs.getInt(1);
                u.code = rs.getString(2);
                u.fullname = rs.getString(3);
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                u.dob = f.format(rs.getDate(4));
                u.gender = (rs.getBoolean(5) ? "Male" : "Female");
                u.address = rs.getString(6);
                u.email = rs.getString(7);
                u.password = rs.getString(8);
                u.id_card = rs.getString(9);
                u.avatar = rs.getString(10);
                u.role = rs.getInt(11);
                u.status = rs.getBoolean(12);
                u.created_at = rs.getDate(13);
                u.created_by = rs.getInt(14);
                u.modified_at = rs.getDate(15);
                u.modified_by = rs.getInt(16);
            }
        } catch (SQLException e) {
            System.out.println("get user by id error: " + e);
        }
        return u;
    }

    public static int getUserIDMax() {
        int res = -1;
        try {
            String str = "select max(user_id) as 'max'\n"
                    + "from Users";
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

    public static String encryptPassword(String pass) {
        return DigestUtils.md5Hex(pass).toUpperCase();
    }

    public static boolean checkPassword(String passInput, String passDB) {
        return passDB.equals(DigestUtils.md5Hex(passInput).toUpperCase());
    }

    public static void updateUserStatus(int userID, boolean status) {
        try {
            String str = "update Users\n"
                    + "set status=?\n"
                    + "where user_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setBoolean(1, status);
            pstm.setInt(2, userID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update status error: " + e);
        }
    }

    public static ArrayList<User> getListUserContains(String type_1, String value_1, String type_2, String value_2, String target) {
        ArrayList<User> list = new ArrayList<>();
        try {
            String sql = "Select [user_id],[code],[fullname],[dob],[gender],[address],[email],[password],\n"
                    + "[id_card],[avatar],[role],[status],[created_at],[created_by],[modified_at],[modified_by] from [Users] \n"
                    + "where " + type_1 + " like N'%" + value_1 + "%'\n"
                    + "and " + type_2 + " like '%" + value_2 + "%'\n"
                    + "order by " + target + " desc, user_id desc";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                User u = new User();
                u.setUser_id(rs.getInt(1));
                u.setCode(rs.getString(2));
                u.setFullname(rs.getString(3));
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                u.setDob(f.format(rs.getDate(4)));
                u.setGender(rs.getBoolean(5) ? "Male" : "Female");
                u.setAddress(rs.getString(6));
                u.setEmail(rs.getString(7));
                u.setPassword(rs.getString(8));
                u.setId_card(rs.getString(9));
                u.setAvatar(rs.getString(10));
                u.setRole(rs.getInt(11));
                u.setStatus(rs.getBoolean(12));
                u.setCreated_at(rs.getDate(13));
                u.setCreated_by(rs.getInt(14));
                u.setModified_at(rs.getDate(15));
                u.setModified_by(rs.getInt(16));
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Get list product contains error: " + e);
        }
        return list;
    }   

    public static void updatePassbyEmail(String email, String newpass) {
        try {
            String sql = "update Users set password = '" + encryptPassword(newpass) + "'\n"
                    + "where email='" + email + "'";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updatePassbyEmail error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println(getUserByID(10).getAvatar());
    }
}
