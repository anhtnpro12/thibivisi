<%-- 
    Document   : blog
    Created on : Feb 8, 2023, 10:24:39 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zxx">

    <head>
        <meta charset="UTF-8">
        <meta name="description" content="Male_Fashion Template">
        <meta name="keywords" content="Male_Fashion, unica, creative, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>ThiBiViSi.com</title>

        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
              rel="stylesheet">

        <!-- Css Styles -->
        <link rel="stylesheet" href="FrontEnd/css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="FrontEnd/css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="FrontEnd/css/elegant-icons.css" type="text/css">
        <link rel="stylesheet" href="FrontEnd/css/magnific-popup.css" type="text/css">
        <link rel="stylesheet" href="FrontEnd/css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="FrontEnd/css/owl.carousel.min.css" type="text/css">
        <link rel="stylesheet" href="FrontEnd/css/slicknav.min.css" type="text/css">
        <link rel="stylesheet" href="FrontEnd/css/style.css" type="text/css">
    </head>

    <body>
        <!-- Page Preloder -->
        <div id="preloder">
            <div class="loader"></div>
        </div>

        <!-- Offcanvas Menu Begin -->
        <div class="offcanvas-menu-overlay"></div>
        <div class="offcanvas-menu-wrapper">
            <div class="offcanvas__option">
                <div class="offcanvas__links">
                    <a href="#">Sign in</a>
                    <a href="#">FAQs</a>
                </div>
            </div>
            <div class="offcanvas__nav__option">
                <a href="#" class="search-switch"><img src="img/icon/search.png" alt=""></a>
                <a href="#"><img src="img/icon/cart.png" alt=""> <span>0</span></a>
                <div class="price">$0.00</div>
            </div>
            <div id="mobile-menu-wrap"></div>
            <div class="offcanvas__text">
                <p>Free shipping, 30-day return or refund guarantee.</p>
            </div>
        </div>
        <!-- Offcanvas Menu End -->

        <!-- Header Section Begin -->
        <%@ include file="/FrontEnd/Template/header.jsp" %>
        <!-- Header Section End -->

        <!-- Breadcrumb Section Begin -->
        <section class="breadcrumb-option">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="breadcrumb__text">
                            <h4>Order history</h4>
                            <div class="breadcrumb__links">
                                <a href="home">Home</a>
                                <span>Order history</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Breadcrumb Section End -->

        <!-- Blog Section Begin -->
        <section class="blog spad">
            <div class="container">
                <div class="row">
                    <div class="table-responsive text-nowrap">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Order Code</th>
                                    <th>Address</th>
                                    <th>Total</th>
                                    <th>State</th>
                                    <th>Payment</th>
                                </tr>
                            </thead>
                            <tbody class="table-border-bottom-0">
                                <c:forEach items="${order}" var="order">
                                <tr>
                                    <td>${order.code}</td>
                                    <td>${order.address}</td>
                                    <td>${df.format(order.total)}</td>
                                    <td>${order.state==1?"Unconfirm":""}${order.state==2?"Confirm":""}${order.state==3?"Shipping":""}${order.state==4?"Shipped":""}${order.state==5?"Eeject":""}</td>
                                    <td>${order.payment?"Paid":"Not Paid"}</td>
                                    <td><a href="orderdetailhistory?orderID=${order.orderID}">View Details-></a></td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>
        <!-- Blog Section End -->

        <!-- Footer Section Begin -->
        <%@ include file="/FrontEnd/Template/footer.jsp" %>
        <!-- Footer Section End -->

        <!-- Search Begin -->
        <!-- Search End -->

        <!-- Js Plugins -->
        <script src="FrontEnd/js/jquery-3.3.1.min.js"></script>
        <script src="FrontEnd/js/bootstrap.min.js"></script>
        <script src="FrontEnd/js/jquery.nice-select.min.js"></script>
        <script src="FrontEnd/js/jquery.nicescroll.min.js"></script>
        <script src="FrontEnd/js/jquery.magnific-popup.min.js"></script>
        <script src="FrontEnd/js/jquery.countdown.min.js"></script>
        <script src="FrontEnd/js/jquery.slicknav.js"></script>
        <script src="FrontEnd/js/mixitup.min.js"></script>
        <script src="FrontEnd/js/owl.carousel.min.js"></script>
        <script src="FrontEnd/js/main.js"></script>
    </body>

</html>
