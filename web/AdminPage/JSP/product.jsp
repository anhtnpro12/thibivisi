<%-- Document : admin Created on : Feb 9, 2023, 12:48:02 AM Author : ASUS --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html lang="en" class="light-style layout-menu-fixed" dir="ltr" data-theme="theme-default" data-assets-path="AdminPage/assets/"
      data-template="vertical-menu-template-free">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

        <title>ADMIN - Product</title>

        <meta name="description" content="" />

        <!-- include libraries(jQuery, bootstrap) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <style>
            a:hover {
                text-decoration: none;
            }
        </style>
        
        <!-- Favicon -->
        <link rel="icon" type="image/x-icon" href="AdminPage/assets/img/favicon/favicon.ico" />

        <!-- Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet" />

        <!-- Icons. Uncomment required icon fonts -->
        <link rel="stylesheet" href="AdminPage/assets/vendor/fonts/boxicons.css" />

        <!-- Core CSS -->
        <link rel="stylesheet" href="AdminPage/assets/vendor/css/core.css" class="template-customizer-core-css" />
        <link rel="stylesheet" href="AdminPage/assets/vendor/css/theme-default.css" class="template-customizer-theme-css" />
        <link rel="stylesheet" href="AdminPage/assets/css/demo.css" />

        <!-- Vendors CSS -->
        <link rel="stylesheet" href="AdminPage/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />

        <link rel="stylesheet" href="AdminPage/assets/vendor/libs/apex-charts/apex-charts.css" />

        <!-- Page CSS -->

        <!-- Helpers -->
        <script src="AdminPage/assets/vendor/js/helpers.js"></script>

        <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
        <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
        <script src="AdminPage/assets/js/config.js"></script>
    </head>

    <body>
        <!-- Layout wrapper -->
        <div class="layout-wrapper layout-content-navbar">
            <div class="layout-container">
                <!-- Menu -->
                <%@ include file="/AdminPage/Template/menu.jsp" %>
                <!-- / Menu -->

                <!-- Layout container -->
                <div class="layout-page">
                    <!-- Navbar -->

                    <%@ include file="/AdminPage/Template/navbar.jsp" %>

                    <!-- / Navbar -->

                    <!-- Content wrapper -->
                    <div class="content-wrapper">
                        <!-- Content -->

                        <!-- Hoverable Table rows -->
                        <div class="card" style="margin: 2.5%">
                            <h5 class="card-header">PRODUCT LIST</h5>
                            <div class="d-flex justify-content-between">
                                <ul class="card-body nav nav-pills">
                                    <li class="nav-item">
                                        <a href="admin-view-product?page-type=all"><button class="nav-link ${pageType.equals("all")?"active":""}">All</button></a>                                      
                                    </li>
                                    <li class="nav-item">
                                        <a href="admin-view-product?page-type=false"><button class="nav-link ${pageType.equals("false")?"active":""}">Deactivate</button></a>       
                                    </li>
                                    <li class="nav-item">
                                        <a href="admin-view-product?page-type=true"><button class="nav-link ${pageType.equals("true")?"active":""}">Active</button></a>       
                                    </li>
                                </ul>
                                    <a class="card-body d-flex justify-content-end" href="admin-create-product" style="max-width: fit-content">
                                    <button type="button" class="btn rounded-pill btn-danger">Add Product</button>
                                </a>
                            </div>
                            <div class="row g-0 card-body">
                                <div class="col-6 col-md-4 d-flex"><div class="align-self-center">${productDao.getProductIDMax()} ${productDao.getProductIDMax()>1?"Products":"Product"}</div></div>
                                <div class="col-sm-6 col-md-8">
                                    <form action="admin-view-product" method="get">
                                        <div class="input-group input-group-merge">
                                            <span class="input-group-text" id="basic-addon-search31"><i class="bx bx-search"></i></span>
                                            <input type="text" class="form-control" value="${name}" name="name" placeholder="Search by name" aria-label="Search..." aria-describedby="basic-addon-search31" />
                                            <input type="text" name="page-type" value="${pageType}" style="display: none"/>                                            
                                            <input type="submit" value="" style="display: none"/>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="table-responsive text-nowrap">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>CODE</th>
                                            <th>Product</th>
                                            <th>Out of stock</th>
                                            <th>Author</th>
                                            <th>Rating star</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>                                    
                                    <tbody class="table-border-bottom-0">                                                                                    
                                        <c:forEach items="${list}" var="product">
                                            <tr>
                                                <td>${product.code}</td>
                                                <td>
                                                    <div class="d-flex">
                                                        <div class="avatar avatar-lg me-2">
                                                            <img src="<c:if test="${product.images.size() == 0}">img/General/no-image.png</c:if><c:if test="${product.images.size() > 0}">./img/UploadImgs/ProductImgs/${product.productID}/${product.images.get(0).image}</c:if>" 
                                                                 alt="avatar" class="img-thumbnail" style="object-fit: contain"/>
                                                        </div>
                                                        <div class="d-flex flex-column">
                                                            <div>${product.name}</div>
                                                            <small><div><span class="badge bg-label-${product.status?"success":"danger"} me-1">${product.status?"ACTIVE":"DEACTIVE"}</span></div></small>     
                                                            <small>
                                                                <div class="d-flex">
                                                                    <small class="me-2">${product.cate.cate}</small>
                                                                    <small>${product.brand.name}</small>
                                                                </div>
                                                            </small>
                                                            <small><span>VND</span> ${df.format(product.price)}</small>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>${!product.outOfStock?"OUT OF STOCK":"AVAILABLE"}</td>
                                                <td>${productDao.getUserByProductID(product.productID).code}</td>
                                                <td>${product.ratingStar}</td>
                                                <td>
                                                    <a title="Edit" class="btn btn-sm btn-outline-primary" href="admin-update-product?productID=${product.productID}">
                                                        <i class="bx bx-edit-alt me-1"></i>
                                                    </a>
                                                    <a title="${product.status?"Deactivate":"Active"}" class="btn btn-sm btn-outline-${product.status?"danger":"success"}" href="/SWP/admin-change-product-status?productID=${product.productID}&status=${product.status}">
                                                        <i class="bx bx-${product.status?"hide":"show"} me-1"></i>
                                                    </a>
                                                    <a title="Preview" class="btn btn-sm btn-outline-primary" href="javascript:void(0);">
                                                        <i class="bx bx-chevrons-right me-1"></i>
                                                    </a>
                                                </td>
                                            </tr>                                      
                                        </c:forEach>
                                    <style>
                                        .avatar-lg:hover img {
                                            transform: scale(1.5);
                                            transition: 0.5s;
                                        }
                                    </style>
                                    </tbody>
                                </table>
                                <c:if test="${size == 0}">
                                    <div class="d-flex justify-content-center">
                                        <img src="./img/General/empty.png" alt="alt"/>
                                    </div>
                                </c:if>
                                <nav aria-label="Page navigation">
                                    <ul class="pagination justify-content-end me-3">
                                        <c:if test="${size != 0}">
                                        <li class="page-item prev">
                                            <a class="page-link" href="admin-view-product?page-type=${pageType}&page=${page-1==0?size:page-1}&name=${name}"><i class="tf-icon bx bx-chevrons-left"></i></a>
                                        </li>
                                        </c:if>
                                        <c:forEach begin="1" end="${size}" var="i">
                                            <li class="page-item ${i==page?"active":""}">
                                                <a class="page-link" href="admin-view-product?page-type=${pageType}&page=${i}&name=${name}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <c:if test="${size != 0}">
                                        <li class="page-item next">
                                            <a class="page-link" href="admin-view-product?page-type=${pageType}&page=${page+1>size?1:page+1}&name=${name}"><i class="tf-icon bx bx-chevrons-right"></i></a>
                                        </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                        <!--/ Hoverable Table rows -->
                        <!-- / Content -->

                        <!-- Footer -->
                        <%@ include file="/AdminPage/Template/footer.jsp" %>
                        <!-- / Footer -->

                        <div class="content-backdrop fade"></div>
                    </div>
                    <!-- Content wrapper -->
                </div>
                <!-- / Layout page -->
            </div>

            <!-- Overlay -->
            <div class="layout-overlay layout-menu-toggle"></div>
        </div>
        <!-- / Layout wrapper -->
        <!-- Core JS -->
        <!-- build:js assets/vendor/js/core.js -->
<!--        <script src="AdminPage/assets/vendor/libs/jquery/jquery.js"></script>
        <script src="AdminPage/assets/vendor/libs/popper/popper.js"></script>
        <script src="AdminPage/assets/vendor/js/bootstrap.js"></script>-->
        <script src="AdminPage/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>

        <script src="AdminPage/assets/vendor/js/menu.js"></script>
        <!-- endbuild -->

        <!-- Vendors JS -->
        <script src="AdminPage/assets/vendor/libs/apex-charts/apexcharts.js"></script>

        <!-- Main JS -->
        <script src="AdminPage/assets/js/main.js"></script>

        <!-- Page JS -->
        <script src="AdminPage/assets/js/dashboards-analytics.js"></script>

        <!-- Place this tag in your head or just before your close body tag. -->
        <script async defer src="https://buttons.github.io/buttons.js"></script>
    </body>

</html>