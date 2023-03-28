package dal;

import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Brand;
import model.Category;
import model.ProductImage;
import model.User;

public class ProductDAO {

    static Connection cnn; // kết nối
    static PreparedStatement stm; // thực hiên các cáu lệnh sql
    static ResultSet rs; // lưu trữ và xử lí dữ liệu

    public static void insertProduct(Product p) {
        try {
            String str = "insert into Products(cate_id,brand_id,code,[name],shortdescript,[description]\n"
                    + ",rating_star,price,out_of_stock,[status],created_at,created_by,modified_at\n"
                    + ",modified_by,[url])\n"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, p.getCate().getCateID());
            pstm.setInt(2, p.getBrand().getBrandID());
            pstm.setString(3, p.getCode());
            pstm.setString(4, p.getName());
            pstm.setString(5, p.getShortdescript());
            pstm.setString(6, p.getDescription());
            pstm.setDouble(7, p.getRatingStar());
            pstm.setDouble(8, p.getPrice());
            pstm.setBoolean(9, p.isOutOfStock());
            pstm.setBoolean(10, p.isStatus());
            pstm.setObject(11, p.getCreatedAt());
            pstm.setInt(12, p.getCreatedBy());
            pstm.setObject(13, p.getModifiedAt());
            pstm.setInt(14, p.getModifiedBy());
            pstm.setString(15, p.getUrl());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert product error: " + e);
        }
    }

    public static void updateProduct(Product p) {
        try {
            String str = "update Products\n"
                    + "set cate_id=?, brand_id=?, code=?, [name]=?, shortdescript=?\n"
                    + ", [description]=?, rating_star=?, price=?, out_of_stock=?, [status]=?\n"
                    + ", created_at=?, created_by=?, modified_at=?\n"
                    + ", modified_by=?\n"
                    + "where product_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, p.getCate().getCateID());
            pstm.setInt(2, p.getBrand().getBrandID());
            pstm.setString(3, p.getCode());
            pstm.setString(4, p.getName());
            pstm.setString(5, p.getShortdescript());
            pstm.setString(6, p.getDescription());
            pstm.setDouble(7, p.getRatingStar());
            pstm.setDouble(8, p.getPrice());
            pstm.setBoolean(9, p.isOutOfStock());
            pstm.setBoolean(10, p.isStatus());
            pstm.setObject(11, p.getCreatedAt());
            pstm.setInt(12, p.getCreatedBy());
            pstm.setObject(13, p.getModifiedAt());
            pstm.setInt(14, p.getModifiedBy());
            pstm.setInt(15, p.getProductID());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update product error: " + e);
        }
    }

    public static void updateProductStatus(int productID, boolean status) {
        try {
            String str = "update Products\n"
                    + "set status=?\n"
                    + "where product_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setBoolean(1, status);
            pstm.setInt(2, productID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update status error: " + e);
        }
    }

    public static ArrayList<Product> getListByPage(ArrayList<Product> list, int begin, int end) {
        ArrayList<Product> myList = new ArrayList<>();
        int myEnd = Math.min(end, list.size());
        for (int i = begin; i < myEnd; i++) {
            myList.add(list.get(i));
        }
        return myList;
    }

    public static ArrayList<Product> getAllProduct(String target) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by, p.[url]\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "order by p." + target + " desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setUrl(rs.getString(41));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    if (pimg.getImageID() != 0) {
                        p.getImages().add(pimg);
                    }
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    if (pimg.getImageID() != 0) {
                        list.get(list.size() - 1).getImages().add(pimg);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProductAsc(String target) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by, p.[url]\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "order by p." + target + " asc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setUrl(rs.getString(41));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    if (pimg.getImageID() != 0) {
                        p.getImages().add(pimg);
                    }
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    if (pimg.getImageID() != 0) {
                        list.get(list.size() - 1).getImages().add(pimg);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }
    public static ArrayList<Product> getAllProductDesc(String target) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by, p.[url]\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "order by p." + target + " desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setUrl(rs.getString(41));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    if (pimg.getImageID() != 0) {
                        p.getImages().add(pimg);
                    }
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    if (pimg.getImageID() != 0) {
                        list.get(list.size() - 1).getImages().add(pimg);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProduct() {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "order by p.product_id desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    if (pimg.getImageID() != 0) {
                        p.getImages().add(pimg);
                    }
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    if (pimg.getImageID() != 0) {
                        list.get(list.size() - 1).getImages().add(pimg);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

        public static ArrayList<Product> getTop8Product(String target) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select top 8 p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by, p.[url]\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where pimg.level = 1\n"
                    + "order by p." + target + " desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setUrl(rs.getString(41));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }
    public static ArrayList<Product> getRelatedProduct(String target, int cateID) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select top 4 p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by, p.[url]\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where pimg.level = 1 AND c.cate_id=" + cateID + "\n"
                    + "order by pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setUrl(rs.getString(41));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static int getProductIDbyCateID(int product_id) {
        int number = 0;
        try {
            String sql = "select cate_id from Products where product_id=" + product_id + "";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                number = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getProductIDbyCateID error: " + e.getMessage());
        }
        return number;
    }

    public static String genCode(int product_id) {
        String code = null;
        int number = 0;
        int cate_id = getProductIDbyCateID(product_id);
        try {
            String sql = "SELECT COUNT(p.cate_id)\n"
                    + "  FROM [Categories] c inner join [Products] p\n"
                    + "  ON c.cate_id=p.cate_id\n"
                    + "  where c.cate_id = " + cate_id + "\n"
                    + "  group by c.code";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                number = rs.getInt(1);
            }
            sql = "select code from Products where product_id=" + product_id + "";
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                code = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("genCode error: " + e.getMessage());
        }
        return code + "-" + number;
    }

    public static ArrayList<Product> getListProductContains(String type, String value, String target) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where p." + type + " like '%" + value + "%'\n"
                    + "order by p." + target + " desc, p.product_id desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get list product contains error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getListProductContains(String type_1, String value_1, String type_2, String value_2, String target) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where p." + type_1 + " like '%" + value_1 + "%'\n"
                    + "and p." + type_2 + " like '%" + value_2 + "%'\n"
                    + "order by p." + target + " desc, p.product_id desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get list product contains error: " + e);
        }
        return list;
    }

    public static Product getProductByID(int id) {
        Product p = new Product();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where p.product_id=?\n"
                    + "order by p.modified_at desc, p.product_id desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                if (preID != rs.getInt(1)) {
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    preID = p.getProductID();
                }
                if (rs.getInt(32) != 0) {
                    ProductImage pimg = new ProductImage();
                    pimg.setImageID(rs.getInt(32));
                    pimg.setCode(rs.getString(33));
                    pimg.setImage(rs.getString(34));
                    pimg.setLevel(rs.getInt(35));
                    pimg.setStatus(rs.getBoolean(36));
                    pimg.setCreatedAt(rs.getDate(37));
                    pimg.setCreatedBy(rs.getInt(38));
                    pimg.setModifiedAt(rs.getDate(39));
                    pimg.setModifiedBy(rs.getInt(40));
                    p.getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get product by id error: " + e);
        }
        return p;
    }
    
    public static Product getProductByCode(String code) {
        Product p = new Product();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where p.code=?\n"
                    + "order by p.modified_at desc, p.product_id desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, code);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                if (preID != rs.getInt(1)) {
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    preID = p.getProductID();
                }
                if (rs.getInt(32) != 0) {
                    ProductImage pimg = new ProductImage();
                    pimg.setImageID(rs.getInt(32));
                    pimg.setCode(rs.getString(33));
                    pimg.setImage(rs.getString(34));
                    pimg.setLevel(rs.getInt(35));
                    pimg.setStatus(rs.getBoolean(36));
                    pimg.setCreatedAt(rs.getDate(37));
                    pimg.setCreatedBy(rs.getInt(38));
                    pimg.setModifiedAt(rs.getDate(39));
                    pimg.setModifiedBy(rs.getInt(40));
                    p.getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get product by id error: " + e);
        }
        return p;
    }

    public static int getProductIDMax() {
        int res = -1;
        try {
            String str = "select max(product_id) as 'max'\n"
                    + "from Products";
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

    public static int getQuantitySold(int productID) {
        try {
            String str = "select SUM(od.quantity)\n"
                    + "from Products p\n"
                    + "inner join OrderDetails od on p.product_id=od.product_id \n"
                    + "where p.product_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, productID);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("get quantity sold error: " + e);

        }
        return 0;
    }

    public static int getQuantityComment(int productID) {
        try {
            String str = "select COUNT(*)\n"
                    + "from Products p\n"
                    + "inner join Comments cmt on p.product_id=cmt.product_id\n"
                    + "where p.product_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, productID);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("get quantity comment error: " + e);
        }
        return 0;
    }

    public static int getCreatedbyByProductid(int product_id) {
        try {
            int i = 0;
            String sql = "select created_by from Products where product_id=?";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.setInt(1, product_id);
            rs = stm.executeQuery();
            while (rs.next()) {
                i = rs.getInt(1);
            }
            return i;
        } catch (Exception e) {
            System.out.println("getCreatedbyByProductid error: " + e.getMessage());
        }
        return 0;

    }

    public static User getUserByProductID(int product_id) {

        User u = new User();
        try {
            String str = "Select [user_id],[code],[fullname],[dob],[gender],[address],[email],[password]\n"
                    + ",[id_card],[avatar],[role],[status],[created_at],[created_by],[modified_at],[modified_by] \n"
                    + "from [Users]\n"
                    + "where [user_id]=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, getCreatedbyByProductid(product_id));
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

    public static int getQuantityProductFromDate(String date) {
        int res = 0;
        try {
            String str = "select COUNT(product_id)\n"
                    + "from Products\n"
                    + "where created_at >= ?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, date);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("get quantity product from date error: " + e);
        }
        return res;
    }

    public static ArrayList<Product> getAllProductByCateID(String target, int cateID) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by, p.[url]\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where c.cate_id=" + cateID + ""
                    + "order by p." + target + " desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setUrl(rs.getString(41));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProductAscByCateID(String target, int cateID) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by, p.[url]\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where c.cate_id=" + cateID + ""
                    + "order by p." + target + " asc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setUrl(rs.getString(41));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProductByCateID(int cateID) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where c.cate_id=" + cateID + ""
                    + "order by p.product_id desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProductBySearch(String target, String search) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where p.[name] like '%" + search + "%'"
                    + "order by p." + target + " desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getListProductByStatus(boolean status, String name) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where p.[status]=? and p.[name] like ?\n"
                    + "order by p.product_id desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setBoolean(1, status);
            pstm.setString(2, "%" + name + "%");
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    if (pimg.getImageID() != 0) {
                        p.getImages().add(pimg);
                    }
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    if (pimg.getImageID() != 0) {
                        list.get(list.size() - 1).getImages().add(pimg);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("getListProductByStatus error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getSearchProduct(String name) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where p.[name] like ?\n"
                    + "order by p.product_id desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, "%" + name + "%");
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    if (pimg.getImageID() != 0) {
                        p.getImages().add(pimg);
                    }
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    if (pimg.getImageID() != 0) {
                        list.get(list.size() - 1).getImages().add(pimg);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("getSearchProduct error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProductAscBySearch(String target, String search) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where p.[name] like '%" + search + "%'"
                    + "order by p." + target + " asc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(ProductDAO.getRelatedProduct("price", 8).size());
    }

    public static ArrayList<Product> getAllProductByBrandID(String target, int brandID) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where b.brand_id=" + brandID + ""
                    + "order by p." + target + " desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProductAscByBrandID(String target, int brandID) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where b.brand_id=" + brandID + ""
                    + "order by p." + target + " asc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProductByPrice(String target, int parseInt) {
        int from, to;
        switch (parseInt) {
            case 1:
                from = 0;
                to = 20000;
                break;
            case 2:
                from = 20000;
                to = 50000;
                break;
            case 3:
                from = 50000;
                to = 100000;
                break;
            case 4:
                from = 100000;
                to = 500000;
                break;
            case 5:
                from = 500000;
                to = 1000000;
                break;
            default:
                from = 1000000;
                to = 2000000000;
                break;
        }
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where p.price between " + from + " and " + to + ""
                    + "order by p." + target + " desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProductAscByPrice(String target, int parseInt) {
        int from, to;
        switch (parseInt) {
            case 1:
                from = 0;
                to = 20000;
                break;
            case 2:
                from = 20000;
                to = 50000;
                break;
            case 3:
                from = 50000;
                to = 100000;
                break;
            case 4:
                from = 100000;
                to = 500000;
                break;
            case 5:
                from = 500000;
                to = 1000000;
                break;
            default:
                from = 1000000;
                to = 2000000000;
                break;
        }
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where p.price between " + from + " and " + to + ""
                    + "order by p." + target + " asc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProductByParentID(String target, int cateID) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by, p.[url]\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where c.parent_id=" + cateID + ""
                    + "order by p." + target + " desc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setUrl(rs.getString(41));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }

    public static ArrayList<Product> getAllProductAscByParentID(String target, int cateID) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String str = "select p.product_id,p.cate_id,p.brand_id,p.code,p.[name],shortdescript\n"
                    + ", [description],rating_star,price,out_of_stock,p.[status]\n"
                    + ",p.created_at,p.created_by,p.modified_at,p.modified_by\n"
                    + ",c.parent_id,c.code,c.cate,c.[status],c.created_at,c.created_by,c.modified_at,c.modified_by\n"
                    + ",b.code,b.[name],b.logo,b.[status],b.created_at,b.created_by,b.modified_at,b.modified_by\n"
                    + ",pimg.image_id,pimg.code,pimg.[image],pimg.[level],pimg.[status],pimg.created_at,pimg.created_by\n"
                    + ",pimg.modified_at,pimg.modified_by, p.[url]\n"
                    + "from Products p\n"
                    + "left join Categories c on c.cate_id=p.cate_id\n"
                    + "left join Brands b on p.brand_id=b.brand_id\n"
                    + "left join ProductImages pimg on p.product_id=pimg.product_id\n"
                    + "where c.parent_id=" + cateID + ""
                    + "order by p." + target + " asc, pimg.[level] asc ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
//            pstm.setString(1, target);
            ResultSet rs = pstm.executeQuery();

            int preID = -1;
            while (rs.next()) {
                ProductImage pimg = new ProductImage();
                pimg.setImageID(rs.getInt(32));
                pimg.setCode(rs.getString(33));
                pimg.setImage(rs.getString(34));
                pimg.setLevel(rs.getInt(35));
                pimg.setStatus(rs.getBoolean(36));
                pimg.setCreatedAt(rs.getDate(37));
                pimg.setCreatedBy(rs.getInt(38));
                pimg.setModifiedAt(rs.getDate(39));
                pimg.setModifiedBy(rs.getInt(40));
                if (preID != rs.getInt(1)) {
                    Product p = new Product();
                    p.setProductID(rs.getInt(1));
                    p.setCate(new Category());
                    p.getCate().setCateID(rs.getInt(2));
                    p.setBrand(new Brand());
                    p.getBrand().setBrandID(rs.getInt(3));
                    p.setCode(rs.getString(4));
                    p.setName(rs.getString(5));
                    p.setUrl(rs.getString(41));
                    p.setShortdescript(rs.getString(6));
                    p.setDescription(rs.getString(7));
                    p.setRatingStar(rs.getDouble(8));
                    p.setPrice(rs.getDouble(9));
                    p.setOutOfStock(rs.getBoolean(10));
                    p.setStatus(rs.getBoolean(11));
                    p.setCreatedAt(rs.getDate(12));
                    p.setCreatedBy(rs.getInt(13));
                    p.setModifiedAt(rs.getDate(14));
                    p.setModifiedBy(rs.getInt(15));
                    p.getCate().setParentID(rs.getInt(16));
                    p.getCate().setCode(rs.getString(17));
                    p.getCate().setCate(rs.getString(18));
                    p.getCate().setStatus(rs.getBoolean(19));
                    p.getCate().setCreatedAt(rs.getDate(20));
                    p.getCate().setCreatedBy(rs.getInt(21));
                    p.getCate().setModifiedAt(rs.getDate(22));
                    p.getCate().setModifiedBy(rs.getInt(23));
                    p.getBrand().setCode(rs.getString(24));
                    p.getBrand().setName(rs.getString(25));
                    p.getBrand().setLogo(rs.getString(26));
                    p.getBrand().setStatus(rs.getBoolean(27));
                    p.getBrand().setCreatedAt(rs.getDate(28));
                    p.getBrand().setCreatedBy(rs.getInt(29));
                    p.getBrand().setModifiedAt(rs.getDate(30));
                    p.getBrand().setModifiedBy(rs.getInt(31));
                    p.getImages().add(pimg);
                    preID = p.getProductID();
                    list.add(p);
                } else {
                    list.get(list.size() - 1).getImages().add(pimg);
                }
            }
        } catch (SQLException e) {
            System.out.println("Get all product error: " + e);
        }
        return list;
    }
    
    
}
