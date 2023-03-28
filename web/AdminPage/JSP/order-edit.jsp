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

        <title>Admin - Edit Order: ${order.code}</title>

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
        <!--toastr-->        
        <script src="AdminPage/libs/toastr/toastr.min.js"></script>
        <link rel="stylesheet" href="AdminPage/libs/toastr/toastr.min.css"/>
        
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
                                    <a href="admin-view-order" style="color:inherit">ORDER LIST</a> /</span>
                                ORDER: ${order.code}</h4>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card mb-4">
                                        <div class="d-flex justify-content-between">
                                            <div>
                                                <h5 class="card-header">Order: ${order.code}</h5>                                                
                                            </div>  
                                            <div class="d-flex justify-content-end">
                                                <a class="card-body d-flex justify-content-end" href="admin-create-order-detail?preSite=false&orderID=${order.orderID}"><button type="button" class="btn rounded-pill btn-danger">Add Product</button></a>                                                
                                            </div>
                                        </div>
                                        <form action="admin-edit-order" id="formAccountSettings" method="POST">                                           
                                            <hr class="my-0" />
                                            <div class="card-body">
                                                <div class="row">
                                                    <div class="mb-3 col-md-6">
                                                        <label class="form-label" for="customer">Customer</label>
                                                        <select name="customerID" id="customer" class="selectpicker"
                                                                data-live-search="true" data-width="100%" 
                                                                data-style="border" data-size="5">
                                                            <c:forEach items="${cusList}" var="cus">
                                                                <option value="${cus.customerID}" <c:if test="${order.customerID eq cus.customerID}">selected</c:if>
                                                                        <c:if test="${cus.status==false}">disabled style="color:#ccc"</c:if>>${cus.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md">  
                                                        <label for="status" class="form-label d-block">Status</label>                                                            
                                                        <div class="form-check form-check-inline mt-3">
                                                            <input class="form-check-input" type="radio" name="status" id="active" value="true" <c:if test="${order.status==true}">checked</c:if>>
                                                            <label class="form-check-label" for="active">Active</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="status" id="deactivate" value="false" <c:if test="${order.status==false}">checked</c:if>>
                                                            <label class="form-check-label" for="deactivate">Deactivate</label>
                                                        </div>                                                            
                                                    </div>
                                                    <div class="mb-3 col-md-6">
                                                        <label for="address" class="form-label">Address</label>
                                                        <input class="form-control" type="text" name="address"
                                                               id="address" value="${order.address}" required/>
                                                    </div>
                                                    <div class="mb-3 col-md-6">
                                                        <label for="state" class="d-block form-label">state</label>
                                                        <select id="state" name="state" class="selectpicker" 
                                                                data-live-search="true" data-width="100%" 
                                                                data-style="border" data-size="5">
                                                            <option value="1" data-content='<span class="badge badge-secondary">Unconfirmed</span>' <c:if test="${order.state==1}">selected</c:if>>Unconfirmed</option>                                                                
                                                            <option value="2" data-content='<span class="badge badge-primary">Confirmed</span>' <c:if test="${order.state==2}">selected</c:if>>Confirmed</option>                                                                
                                                            <option value="3" data-content='<span class="badge badge-warning">Delivery</span>' <c:if test="${order.state==3}">selected</c:if>>Delivery</option>                                                                
                                                            <option value="4" data-content='<span class="badge badge-success">Complete</span>' <c:if test="${order.state==4}">selected</c:if>>Complete</option>                                                                
                                                        </select>
                                                    </div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
                                                    <div class="mb-3 col-md-6">
                                                        <label for="total" class="form-label">Total</label>
                                                        <input class="form-control" type="text" name="total"
                                                               id="total" value="${df.format(order.total)}" required/>
                                                    </div>
                                                    <div class="col-md">  
                                                        <label for="payment" class="form-label d-block">payment status</label>                                                            
                                                        <div class="form-check form-check-inline mt-3">
                                                            <input class="form-check-input" type="radio" name="payment" id="paid" value="true" <c:if test="${order.payment==true}">checked</c:if>>
                                                            <label class="form-check-label" for="paid">Paid</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="payment" id="unpaid" value="false" <c:if test="${order.payment==false}">checked</c:if>>
                                                            <label class="form-check-label" for="unpaid">Unpaid</label>
                                                        </div>                                                            
                                                    </div> 
                                                </div>
                                            <input name="orderID" type="text" value="${order.orderID}" style="display: none;">                                                
                                            <input name="createdAt" type="text" value="${order.createdAt}" style="display: none;">                                                
                                            <input name="createdBy" type="text" value="${order.createdBy}" style="display: none;">                                                
                                            <input name="code" type="text" value="${order.code}" style="display: none;">                                                
                                                <div class="mt-2">
                                                    <button type="submit" class="btn btn-primary me-2">Save Order</button>
                                                    <button type="button" onclick="location.href = 'admin-view-order'" class="btn btn-outline-secondary">Back</button>
                                                </div>                                            
                                            </div>
                                        </form>
                                        <!-- /Account -->
                                    </div>
                                </div>
                            </div>
                                       
                            <c:set var="count" value="${0}"/>
                            <c:forEach items="${odList}" var="od">
                            <c:set var="count" value="${count+1}"/>                            
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card mb-4">
                                        <div class="d-flex justify-content-between">
                                            <div>
                                                <h5 class="card-header">Product ${count}: </h5>                                                     
                                            </div> 
                                            <div class="d-flex justify-content-end">
                                                <a class="card-body d-flex justify-content-end" href="admin-deactivate-order-detail?orderID=${order.orderID}&orderDetailID=${od.orderDetailID}&price=${od.unitPrice*od.quantity}"><button type="button" class="btn rounded-pill btn-danger">Remove Order Detail</button></a>                                                
                                            </div>
                                        </div>
                                        <form action="admin-update-order-detail" id="formAccountSettings" method="POST">                                           
                                            <hr class="my-0" />
                                            <div class="card-body">
                                                <div class="row">
                                                    <div class="mb-3 col-md-6">
                                                        <label class="form-label" for="productID${count}">Product</label>
                                                        <select onchange="changeUnitPrice('${count}');" name="productID" id="productID${count}" class="selectpicker"
                                                                data-live-search="true" data-width="100%" 
                                                                data-style="border" data-size="5">
                                                            <c:forEach items="${productList}" var="p">
                                                                <option value="${p.productID}" 
                                                                        <c:if test="${p.status==false}">disabled style="color:#ccc"</c:if>
                                                                        <c:if test="${p.productID==od.productID}">selected</c:if>
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
                                                        <label for="unitPrice${count}" class="form-label">Unit Price</label>
                                                        <input onchange="changeTotal(); this.value=formatTextToNumber(this.value);" class="form-control" type="text" name="unitPrice"
                                                               id="unitPrice${count}" value="${df.format(od.unitPrice)}" required/>
                                                    </div>                                                                                                                                                                                                                                                                                                                                
                                                    <div class="mb-3 col-md-6">
                                                        <label for="quantity${count}" class="form-label">Quantity</label>
                                                        <input class="form-control" type="number" min="1" name="quantity"
                                                               id="quantity${count}" value="${od.quantity}" required=""/>
                                                    </div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
                                                </div>  
                                                <input type="text" name="preTotal" value="${od.quantity*od.unitPrice}" style="display: none;"/>
                                                <input type="text" name="orderDetailID" value="${od.orderDetailID}" style="display: none;"/>
                                                <input type="text" name="orderID" value="${od.orderID}" style="display: none;"/>
                                                <div class="mt-2">
                                                    <button type="submit" class="btn btn-primary me-2">Save Product</button>
                                                    <!--<button type="reset" class="btn btn-outline-secondary">Reset</button>-->
                                                </div>                                            
                                            </div>
                                        </form>
                                        <!-- /Account -->
                                    </div>
                                </div>
                            </div>
                            </c:forEach>
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
               
        <!--alert-->
        
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
            var total = document.getElementById("total");            
            // format input number
            total.addEventListener("change", inputEvent => {
                total.value = formatTextToNumber(total.value);
            });
            
            function formatTextToNumber(value) {
                value = value.replaceAll(/[^0-9.]/g, "");                
                value = (+value).toLocaleString("en-US");
                return value;
            }
            
            var plist = [
                <c:forEach items="${productList}" var="p">
                {pid:${p.productID},price:${p.price}},
                </c:forEach>
                {pid:-1, price:0}
            ];
            
            function changeUnitPrice(value) {
                var unitPrice = document.getElementById("unitPrice"+value);
                var pidElement = document.getElementById("productID"+value);
                plist.forEach((p)=> {
                    if (p.pid === +pidElement.value) {
                        unitPrice.value = formatTextToNumber(p.price+"");
                    }
                });
            }
            
            function changeTotal() {
                var priceList = document.getElementsByName("unitPrice");                
                var quanList = document.getElementsByName("quantity");                
                var sum = 0;
                for (var i = 0; i < priceList.length; i++) {                    
                    sum += (+priceList[i].value.replaceAll(/[^0-9.]/g, ""))*(+quanList[i].value.replaceAll(/[^0-9.]/g, ""));
                }
                total.value = formatTextToNumber(sum+"");                
            }
        </script>        
        <script>
            function showSuccessToast(mess) {                
                toastr.options.progressBar = true;
                toastr.options.positionClass = 'toast-bottom-right';
                toastr.success(mess, 'Congratulations', {timeOut: 3000});
            }
            <c:if test="${mess != null}">showSuccessToast('${mess}')</c:if>
        </script>
    </body>

</html>