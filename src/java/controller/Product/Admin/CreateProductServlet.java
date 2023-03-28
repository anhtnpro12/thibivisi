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
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import model.Brand;
import model.Category;
import model.ProductImage;
import model.ProductTag;
import model.Tag;
import model.User;

@WebServlet(name = "CreateProductServlet", value = "/admin-create-product")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class CreateProductServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public static String nameToUrl(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").replaceAll("[^a-zA-z0-9\\s]", "")
                .trim().replaceAll("\\s", "-").toLowerCase();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        
        String cateID = request.getParameter("cateID");
        String brandID = request.getParameter("brandID");
        String code = sdf.format(new Date()) + "PD" + (ProductDAO.getQuantityProductFromDate(sdf2.format(new Date())) + 1);
        String name = request.getParameter("productName");
        String url = nameToUrl(name) + "-" + code;
        String shortdescript = request.getParameter("shortdescript");
        String description = request.getParameter("description");
        String ratingStar = "0.0";
        String price = request.getParameter("price").replace(",", "");
        String outOfStock = "true";    
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int createdBy = user.getUser_id();        
        int modifiedBy = user.getUser_id();
        
        Category c = new Category(); c.setCateID(Integer.parseInt(cateID));
        Brand b = new Brand(); b.setBrandID(Integer.parseInt(brandID));
        Product p = new Product(c, b, code, name, url, shortdescript, description
                                , Double.parseDouble(ratingStar), Double.parseDouble(price), Boolean.parseBoolean(outOfStock)
                                , true, new Date(),createdBy
                                , new Date(), modifiedBy);
        ProductDAO.insertProduct(p);
                        
        // upload file to server
        String extension;   
        int temp = 0, level = 1;
        int myID = ProductDAO.getProductIDMax(); 
        System.out.println(myID);
        for (Part part : request.getParts()) {            
            if (part.getName().contains("file-")) {                
                String fileName = extractFileName(part);                 
                if (fileName.isEmpty()) {
                    continue;
                }                         
                fileName = new File(fileName).getName();                
                System.out.println(fileName);
                
                ProductImage pimg = new ProductImage();                               
                pimg.setProductID(myID);
                pimg.setCode(p.getCode() + (++temp));
                pimg.setImage(fileName);
                pimg.setLevel(level); level = 2;              
                pimg.setStatus(true);
                pimg.setCreatedAt(new Date());
                pimg.setCreatedBy(user.getUser_id()); 
                pimg.setModifiedAt(new Date());
                pimg.setModifiedBy(user.getUser_id());
                ProductImageDAO.insertProductImage(pimg);
                
//                System.out.println(this.getFolderUpload(myID+"").getAbsolutePath() + File.separator + fileName);
//                System.out.println(this.getFolderUpload2(myID+"").getAbsolutePath() + File.separator + fileName);                
                part.write(this.getFolderUpload2(myID+"").getAbsolutePath() + File.separator + fileName);                
                part.write(this.getFolderUpload(myID+"").getAbsolutePath() + File.separator + fileName);                                                 
            }
        }
        
        // insert tag
        String[] tags = request.getParameterValues("tags");
        int idMax = TagDAO.getTagIDMax();
        for (int i = 0; i < tags.length; i++) {            
            if (tags[i].contains("tagID=")) {
                tags[i] = tags[i].substring(6);
            } else {                
                TagDAO.createTag(new Tag(
                        0, 
                        sdf.format(new Date()) + "TA" + (TagDAO.getQuantityProductFromDate(sdf2.format(new Date())) + 1), 
                        tags[i], 
                        true, 
                        new Date(),
                        createdBy, 
                        new Date(), 
                        modifiedBy)
                );
                tags[i] = (++idMax) + "";
            }
        }
        int productID = ProductDAO.getProductIDMax();
        for (String tag : tags) {
            ProductTagDAO.insertProductTag(new ProductTag(0, productID, Integer.parseInt(tag)));
        }
        
        request.setAttribute("mess", "Add Product Successful");
        doGet(request, response);
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
        ArrayList<Category> cateList = CategoryDAO.getHierarchicalCategoryList();
        
        if (cateList.get(0).getChildren().isEmpty()) {          
            request.setAttribute("fcate", cateList.get(0));
        } else {
            request.setAttribute("fcate", cateList.get(0).getChildren().get(0));
        }
        request.setAttribute("tags", TagDAO.getListTag());
        request.setAttribute("categories",cateList);
        request.setAttribute("brands",BrandDAO.getListBrand());        
        request.getRequestDispatcher("AdminPage/JSP/product-add.jsp").forward(request, response);
    }
}