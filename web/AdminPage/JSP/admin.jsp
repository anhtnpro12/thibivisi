<%-- Document : admin Created on : Feb 9, 2023, 12:48:02 AM Author : ASUS --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html lang="en" class="light-style layout-menu-fixed" dir="ltr" data-theme="theme-default"
      data-assets-path="AdminPage/assets/" data-template="vertical-menu-template-free">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

        <title>ADMIN</title>

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
        <%--<c:if test="${sessionScope.user==null}">--%>
        <%--<c:redirect url = "login"/>--%>
        <%--</c:if>--%>
        <%--<c:if test="${sessionScope.user!=null}">--%>  
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
                            <div class="row">                                    
                                <div class="col-lg-3 col-md-12 col-6 mb-4">
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="card-title d-flex align-items-start justify-content-between">
                                                <div class="avatar flex-shrink-0">
                                                    <img src="AdminPage/assets/img/icons/unicons/chart-success.png" alt="chart success"
                                                         class="rounded" />
                                                </div>                                                
                                            </div>
                                            <span class="fw-semibold d-block mb-1">Orders</span>
                                            <h3 class="card-title mb-2">${nowOrder}</h3>
                                            <c:if test="${orderDifference >= 0}">
                                                <small class="text-success fw-semibold"><i class="bx bx-up-arrow-alt"></i> ${orderDifference} Orders</small>                                                
                                            </c:if>
                                            <c:if test="${orderDifference < 0}">                                                
                                                <small class="text-danger fw-semibold"><i class="bx bx-down-arrow-alt"></i> ${orderDifference} Orders</small>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-12 col-6 mb-4">
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="card-title d-flex align-items-start justify-content-between">
                                                <div class="avatar flex-shrink-0">
                                                    <img src="AdminPage/assets/img/icons/unicons/wallet-info.png" alt="Credit Card"
                                                         class="rounded" />
                                                </div>                                                
                                            </div>
                                            <span>Sales</span>
                                            <h3 class="card-title text-nowrap mb-1">$${nowTotal}</h3>
                                            <c:if test="${orderDifference >= 0}">
                                                <small class="text-success fw-semibold"><i class="bx bx-up-arrow-alt"></i> $${totalDifference}</small>                                                
                                            </c:if>
                                            <c:if test="${orderDifference < 0}">                                                
                                                <small class="text-danger fw-semibold"><i class="bx bx-down-arrow-alt"></i> $${totalDifference}</small>
                                            </c:if>                                           
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-12 col-6 mb-4">
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="card-title d-flex align-items-start justify-content-between">
                                                <div class="avatar flex-shrink-0">
                                                    <img src="AdminPage/assets/img/icons/unicons/paypal.png" alt="Credit Card" class="rounded" />
                                                </div>                                                
                                            </div>
                                            <span class="d-block mb-1">Payments</span>
                                            <h3 class="card-title text-nowrap mb-2">$${nowPayment}</h3>
                                            <c:if test="${orderDifference >= 0}">
                                                <small class="text-success fw-semibold"><i class="bx bx-up-arrow-alt"></i> $${paymentDifference}</small>                                                
                                            </c:if>
                                            <c:if test="${orderDifference < 0}">                                                
                                                <small class="text-danger fw-semibold"><i class="bx bx-down-arrow-alt"></i> $${paymentDifference}</small>
                                            </c:if>                                             
                                        </div>
                                    </div>
                                </div>
                                <!--                                <div class="col-3 mb-4">
                                                                    <div class="card">
                                                                        <div class="card-body">
                                                                            <div class="card-title d-flex align-items-start justify-content-between">
                                                                                <div class="avatar flex-shrink-0">
                                                                                    <img src="AdminPage/assets/img/icons/unicons/cc-primary.png" alt="Credit Card"
                                                                                         class="rounded" />
                                                                                </div>                                                
                                                                            </div>
                                                                            <span class="fw-semibold d-block mb-1">Transactions</span>
                                                                            <h3 class="card-title mb-2">$14,857</h3>
                                                                            <small class="text-success fw-semibold"><i class="bx bx-up-arrow-alt"></i> +28.14%</small>
                                                                        </div>
                                                                    </div>
                                                                </div>                                                                        -->
                            </div> 
                            <div class="row">
                                <div class="col-12 mb-4">
                                    <div class="card">
                                        <div class="card-body">
                                            <h3 class="card-title mb-2">Statistical</h3>
                                            <canvas id="myChart" style="width:100%;"></canvas>
                                        </div>
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
        <%--</c:if>--%>
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

        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
        <script>
            const xValues = ['January', 'February', 'March', 'April', 'May', 'June'
                , 'July', 'August', 'September', 'October', 'November', 'December'];

            new Chart("myChart", {
                type: "line",
                data: {
                    labels: xValues,
                    datasets: [{
                            label: "Payments",
                            data: [${payments}],
                            backgroundColor: "rgba(255,0,0,1)",
                            borderColor: "rgba(255,0,0,0.3)",
                            fill: false
                        }, {
                            label: "Sales",
                            data: [${totals}],
                            backgroundColor: "rgba(0,0,255,1)",
                            borderColor: "rgba(0,0,255,0.3)",
                            fill: false
                        }]
                },
                options: {
                    legend: {display: true}
                }
            });
        </script>
    </body>

</html>