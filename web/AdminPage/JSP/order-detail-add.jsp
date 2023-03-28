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

        <title>ADMIN - Add Product</title>

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
        
        <!--Bootstrap select-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
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
                        <div class="container-xxl flex-grow-1 container-p-y">
                            <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">
                                <a href="admin-view-order" style="color:inherit">ORDER LIST</a> /
                                <a href="admin-edit-order?orderID=${orderID}" style="color:inherit">ORDER: 
                                    <c:forEach items="${orderList}" var="order">                                                                        
                                        <c:if test="${order.orderID==orderID}">${order.code}</c:if>
                                    </c:forEach>
                                </a> /     
                                </span>
                                ADD PRODUCT</h4>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card mb-4">
                                        <div class="d-flex justify-content-between">
                                            <div>
                                                <h5 class="card-header">Add Product</h5>                                                                                          
                                            </div>                                            
                                        </div>
                                        <form action="admin-create-order-detail" id="formAccountSettings" method="POST">                                           
                                            <hr class="my-0" />
                                            <div class="card-body">
                                                <div class="row">                                                                                                                                                            
                                                    <div class="mb-3 col-md-6">
                                                        <label class="form-label" for="productID">Product</label>
                                                        <select onchange="changeUnitPrice();" name="productID" id="productID" class="selectpicker"
                                                                data-live-search="true" data-width="100%" 
                                                                data-style="border" data-size="5">
                                                            <c:forEach items="${productList}" var="p">
                                                                <option value="${p.productID}" <c:if test="${p.status==false}">disabled style="color:#ccc"</c:if>
                                                                        data-content='<div class="d-flex">
                                                                            <div class="avatar avatar-lg me-2">
                                                                                <img src="<c:if test="${p.images.size() == 0}">img/General/no-image.png</c:if><c:if test="${p.images.size() > 0}">./img/UploadImgs/ProductImgs/${p.productID}/${p.images.get(0).image}</c:if>" 
                                                                                     alt="avatar" class="img-thumbnail" style="object-fit: contain"/>
                                                                            </div>
                                                                            <div class="d-flex flex-column">
                                                                                <div>${p.name}</div>                                                                                
                                                                                <small><div><span class="badge bg-label-${p.status?"success":"danger"} me-1">${p.status?"ACTIVE":"DEACTIVE"}</span></div></small>     
                                                                                <small>
                                                                                    <div class="d-flex">
                                                                                        <small class="me-2">${p.cate.cate}</small>
                                                                                        <small>${p.brand.name}</small>
                                                                                    </div>
                                                                                </small>
                                                                                <small><span>VND</span> ${df.format(p.price)}</small>
                                                                            </div>
                                                                        </div>'>${p.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="mb-3 col-md-6">
                                                        <label for="unitPrice" class="form-label">Unit Price</label>
                                                        <input onchange="this.value=formatTextToNumber(this.value);" class="form-control" type="text" name="unitPrice"
                                                               id="unitPrice" value="${df.format(fprice)}" required/>
                                                    </div>                                                                                                                                                                                                                                                                                                                                
                                                    <div class="mb-3 col-md-6">
                                                        <label for="quantity" class="form-label">Quantity</label>
                                                        <input class="form-control" type="number" min="1" name="quantity"
                                                               id="quantity" value="" required=""/>
                                                    </div>                                                                                                                                                                                                                                                                                                                                
                                                </div>
                                                <input type="hidden" name="orderID" value="${orderID}" />
                                                <div class="mt-2">
                                                    <button type="submit" class="btn btn-primary me-2">Add Product</button>
                                                    <button type="button" onclick="window.location.href='admin-edit-order?orderID=${orderID}'" class="btn btn-outline-secondary">Back</button>
                                                </div>                                            
                                            </div>
                                        </form>
                                        <!-- /Account -->
                                    </div>
                                </div>
                            </div>
                        </div>
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
        <!--<script src="AdminPage/assets/vendor/libs/jquery/jquery.js"></script>-->
        <!--<script src="AdminPage/assets/vendor/libs/popper/popper.js"></script>-->
        <!--<script src="AdminPage/assets/vendor/js/bootstrap.js"></script>-->
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
        <script>                        
            function formatTextToNumber(value) {
                value = value.replaceAll(/[^0-9.]/g, "");                
                value = (+value).toLocaleString("en-US");
                return value;
            }
            
            var unitPrice = document.getElementById("unitPrice");
            var plist = [
                <c:forEach items="${productList}" var="p">
                {pid:${p.productID},price:${p.price}},
                </c:forEach>
                {pid:-1, price:0}
            ];
            console.log(plist);
            function changeUnitPrice() {
                var pidElement = document.getElementById("productID");
                plist.forEach((p)=> {
                    if (p.pid === +pidElement.value) {
                        unitPrice.value = formatTextToNumber(p.price+"");
                    }
                });
            }
        </script>
    </body>

</html>