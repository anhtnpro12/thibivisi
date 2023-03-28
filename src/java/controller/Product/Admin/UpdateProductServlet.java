package controller.Product.Admin;

import dal.BrandDAO;
import dal.CategoryDAO;
import dal.ProductDAO;
import dal.ProductImageDAO;
import dal.ProductTagDAO;
import dal.TagDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.File;
import model.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Brand;
import model.Category;
import model.ProductImage;
import model.ProductTag;
import model.Tag;
import model.User;

@WebServlet(name = "UpdateProductServlet", value = "/admin-update-product")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UpdateProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            
            String productID = request.getParameter("productID");
            String cateID = request.getParameter("cateID");
            String brandID = request.getParameter("brandID");
            String code = request.getParameter("productCode");
            String name = request.getParameter("productName");
            String shortdescript = request.getParameter("shortdescript");
            String description = request.getParameter("description");
            String ratingStar = "0.0";
            String price = request.getParameter("price").replace(",", "");
            String outOfStock = "true";
            String status = "true";
            String createdBy = request.getParameter("createdBy");
            String createdAt = request.getParameter("createdAt");
            
            HttpSession session = request.getSession();
            User u = (User)session.getAttribute("user");                   

            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");

            Category c = new Category();
            c.setCateID(Integer.parseInt(cateID));
            Brand b = new Brand();
            b.setBrandID(Integer.parseInt(brandID));
            Product p = new Product(Integer.parseInt(productID), c, b, code, name, shortdescript, description,
                     Double.parseDouble(ratingStar), Double.parseDouble(price), Boolean.parseBoolean(outOfStock),
                     Boolean.parseBoolean(status), dtf.parse(createdAt), Integer.parseInt(createdBy),
                     new Date(), u.getUser_id());
            ProductDAO.updateProduct(p);
            
            // upload file to server  
            File myFolder = new File(this.getFolderUpload(productID+"").getAbsolutePath());
            String[]entries = myFolder.list();
            ProductImageDAO.deleteByProductID(p.getProductID());
            int temp = 0, level = 1;
            for (Part part : request.getParts()) {            
                if (part.getName().contains("file-")) {                
                    String fileName = extractFileName(part);                 
                    if (fileName.isEmpty()) {
                        continue;
                    }                         
                    fileName = new File(fileName).getName();                    
                    System.out.println(fileName);

                    ProductImage pimg = new ProductImage();                                   
                    pimg.setProductID(Integer.parseInt(productID));
                    pimg.setCode(sdf.format(new Date()) + "PIMG" + (++temp));
                    pimg.setImage(fileName);
                    pimg.setLevel(level); level = 2;              
                    pimg.setStatus(true);
                    pimg.setCreatedAt(new Date());
                    pimg.setCreatedBy(u.getUser_id()); 
                    pimg.setModifiedAt(new Date());
                    pimg.setModifiedBy(u.getUser_id());
                    ProductImageDAO.insertProductImage(pimg);
                    
                    boolean isExist = false;
                    for (String s : entries) {
                        if (s.equals(fileName)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        part.write(this.getFolderUpload2(productID+"").getAbsolutePath() + File.separator + fileName);                
                        part.write(this.getFolderUpload(productID+"").getAbsolutePath() + File.separator + fileName);                                                                         
                    }
                }
            }            
            for(String s: entries){
                boolean isSaved = false;
                for (Part part : request.getParts()) {            
                    if (part.getName().contains("file-")) {                
                        String fileName = extractFileName(part);
                        fileName = new File(fileName).getName();
                        if (s.equals(fileName)) {
                            isSaved = true;
                            break;
                        }
                    }                    
                }
                if (!isSaved) {
//                    System.out.println(s);
                    File currentFile = new File(myFolder.getPath(),s);
                    currentFile.delete();                    
                }
            }
            
            // update product tags
            String[] tags = request.getParameterValues("tags");            
            int idMax = TagDAO.getTagIDMax();
            if (tags != null) {
                
                for (int i = 0; i < tags.length; i++) {            
                    if (tags[i].contains("tagID=")) {
                        tags[i] = tags[i].substring(6);
                    } else {                
                        TagDAO.createTag(new Tag(
                                0, 
                                sdf.format(new Date()) + "TA" + (TagDAO.getQuantityProductFromDate(sdf2.format(new Date())) + 1), 
                                tags[i], 
                                true, 
                                dtf.parse(createdAt),
                                Integer.parseInt(createdBy), 
                                new Date(), 
                                u.getUser_id())
                        );
                        tags[i] = (++idMax) + "";
                    }
                }
                ProductTagDAO.deletebyProductID(p.getProductID());            
                for (String tag : tags) {
                    ProductTagDAO.insertProductTag(new ProductTag(0, p.getProductID(), Integer.parseInt(tag)));
                }                        
            } else {
                ProductTagDAO.deletebyProductID(p.getProductID());
            }
            
            request.setAttribute("mess", "Product Saved");
            doGet(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private File getFolderUpload(String code) {        
        String basePath = getServletContext().getRealPath(File.separator) + "\\..\\..\\web\\img\\UploadImgs\\ProductImgs\\" + code;
//        System.out.println(basePath);
        File folderUpload = new File(basePath);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }
    
    private File getFolderUpload2(String code) {        
        String basePath = getServletContext().getRealPath(File.separator) + "\\img\\UploadImgs\\ProductImgs\\" + code;
//        System.out.println(basePath);
        File folderUpload = new File(basePath);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productID = request.getParameter("productID");
        Product p = ProductDAO.getProductByID(Integer.parseInt(productID));
        
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        request.setAttribute("df", df);
        request.setAttribute("product", p);
        request.setAttribute("categories", CategoryDAO.getHierarchicalCategoryList());        
        request.setAttribute("brands", BrandDAO.getListBrand());
        request.setAttribute("ptags", TagDAO.getListTagByProductID(p.getProductID()));
        request.setAttribute("tags", TagDAO.getListTag());
        request.getRequestDispatcher("AdminPage/JSP/product-update.jsp").forward(request, response);
//        PrintWriter out = response.getWriter();
//        out.println(CategoryDAO.getListCategory().size());
//        out.println(BrandDAO.getListBrand().size());

    }
}
