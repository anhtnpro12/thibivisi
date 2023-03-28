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

        <title>Dashboard - Analytics | Sneat - Bootstrap 5 HTML Admin Template - Pro</title>

        <meta name="description" content="" />

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
                                        <a href="/SWP/admin-view-product?page-type=all"><button class="nav-link ${pageType.equals("all")?"active":""}">All</button></a>                                      
                                    </li>
                                    <li class="nav-item">
                                        <a href="/SWP/admin-search-product?page-type=deactive"><button class="nav-link ${pageType.equals("deactive")?"active":""}">Deactive</button></a>       
                                    </li>
                                    <li class="nav-item">
                                        <a href="/SWP/admin-search-product?page-type=active"><button class="nav-link ${pageType.equals("active")?"active":""}">Active</button></a>       
                                    </li>
                                </ul>
                                <a class="card-body d-flex justify-content-end" href="create-product"><button type="button" class="btn rounded-pill btn-danger">Add Product</button></a>
                            </div>
                            <div class="row g-0 card-body">
                                <div class="col-6 col-md-4 d-flex"><div class="align-self-center">${productDao.getProductIDMax()} ${productDao.getProductIDMax()>1?"Products":"Product"}</div></div>
                                <div class="col-sm-6 col-md-8">
                                    <form action="admin-search-product" method="get">
                                        <div class="input-group input-group-merge">
                                            <span class="input-group-text" id="basic-addon-search31"><i class="bx bx-search"></i></span>
                                            <input type="text" class="form-control" name="name" placeholder="Search by name" aria-label="Search..." aria-describedby="basic-addon-search31" />
                                        </div>
                                        <div>
                                            <input class="d-none" value="${pageType}" name="page-type" type="text">
                                        </div>
                                        <input type="submit" class="d-none"></input>
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
                                                            <img src="avatar/1.png" alt="avatar" class="img-thumbnail"/>
                                                        </div>
                                                        <div class="d-flex flex-column">
                                                            <div>${product.name}</div>
                                                            <small><div><span class="badge bg-label-${product.status?"primary":"danger"} me-1">${product.status?"ACTIVE":"DEACTIVE"}</span></div></small>     
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
                                                    <div class="dropdown">
                                                        <button type="button" class="btn p-0 dropdown-toggle hide-arrow"
                                                                data-bs-toggle="dropdown">
                                                            <i class="bx bx-dots-vertical-rounded"></i>
                                                        </button>
                                                        <div class="dropdown-menu">
                                                            <a class="dropdown-item" href="update-product?productID=${product.productID}"><i
                                                                    class="bx bx-edit-alt me-1"></i> Edit</a>
                                                            <a class="dropdown-item" href="/SWP/admin-change-product-status?productID=${product.productID}&status=${product.status}"><i
                                                                    class="bx bx-${product.status?"hide":"show"} me-1"></i> ${product.status?"Hide":"Show"}</a>
                                                            <a class="dropdown-item" href="javascript:void(0);"><i
                                                                    class="bx bx-chevrons-right me-1"></i>Preview</a>
                                                        </div>
                                                    </div>
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
                                <nav aria-label="Page navigation" class="${list.size()==0?"d-none":""}">
                                    <ul class="pagination justify-content-end me-3">
                                        <li class="page-item prev">
                                            <a class="page-link" href="/SWP/admin-search-product?page-type=${pageType}&page=${page-1==0?size:page-1}"><i class="tf-icon bx bx-chevrons-left"></i></a>
                                        </li>
                                        <c:forEach begin="1" end="${size}" var="i">
                                            <li class="page-item ${i==page?"active":""}">
                                                <a class="page-link" href="/SWP/admin-search-product?page-type=${pageType}&page=${i}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item next">
                                            <a class="page-link" href="/SWP/admin-search-product?page-type=${pageType}&page=${page+1>size?1:page+1}"><i class="tf-icon bx bx-chevrons-right"></i></a>
                                        </li>
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
        <script src="AdminPage/assets/vendor/libs/jquery/jquery.js"></script>
        <script src="AdminPage/assets/vendor/libs/popper/popper.js"></script>
        <script src="AdminPage/assets/vendor/js/bootstrap.js"></script>
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