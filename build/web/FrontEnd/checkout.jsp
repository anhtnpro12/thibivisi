<%-- 
    Document   : checkout
    Created on : Feb 8, 2023, 10:25:40 PM
    Author     : ASUS
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <!-- Header Section Begin -->
        <%@ include file="/FrontEnd/Template/header.jsp" %>
        <!-- Header Section End -->

        <!-- Breadcrumb Section Begin -->
        <section class="breadcrumb-option">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="breadcrumb__text">
                            <h4>Check Out</h4>
                            <div class="breadcrumb__links">
                                <a href="./home">Home</a>
                                <a href="./shop">Shop</a>
                                <span>Check Out</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Breadcrumb Section End -->

        <!-- Checkout Section Begin -->
        <section class="checkout spad">
            <div class="container">
                <div class="checkout__form">
                    <form action="checkout" method="POST">
                        <div class="row">
                            <div class="col-lg-8 col-md-6">
                                <h6 class="checkout__title">Billing Details</h6>
                                <c:if test="${sessionScope.customer==null}">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="mb-3 col-md-6">
                                                <label for="name" class="form-label"><b>Name</b></label>
                                                <input class="form-control" type="text" id="name"
                                                       name="name" value="" autofocus required/>
                                            </div>
                                            <div class="col-md">  
                                                <label for="gender" class="form-label d-block"><b>Gender</b></label>                                                            
                                                <div class="form-check form-check-inline mt-3">
                                                    <input class="form-check-input" type="radio" name="gender" id="male" value="true" checked>
                                                    <label class="form-check-label" for="male">Male</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="gender" id="female" value="false">
                                                    <label class="form-check-label" for="female">Female</label>
                                                </div>                                                            
                                            </div>
                                            <div class="mb-3 col-md-6">
                                                <label for="email" class="form-label"><b>Email</b></label>
                                                <input class="form-control" type="email" name="email"
                                                       id="email" value="" required/>
                                            </div>                                                        
                                            <div class="mb-3 col-md-6">
                                                <label for="phoneNumber" class="form-label"><b>Phone Number</b></label>
                                                <input class="form-control" type="tel" name="phoneNumber"
                                                       id="phoneNumber" value="" required/>
                                            </div>                                                        
                                            <div class="mb-6 col-md-12">
                                                <label for="phoneNumber" class="form-label"><b>Address</b></label>
                                                <input class="form-control" type="text" name="address"
                                                       id="address" value="" required/>
                                            </div>                                                        
                                        </div>                                                                                                                                                   
                                    </div> 
                                </c:if>
                                <c:if test="${sessionScope.customer!=null}">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="mb-3 col-md-6">
                                                <label for="name" class="form-label"><b>Name</b></label>
                                                <input class="form-control" type="text" id="name"
                                                       name="name" value="${customer.name}" autofocus required readonly/>
                                            </div>
                                            <div class="col-md">  
                                                <label for="gender" class="form-label d-block"><b>Gender</b></label>                                                            
                                                <div class="form-check form-check-inline mt-3">
                                                    <input class="form-check-input" type="radio" name="gender" id="male" value="true" ${customer.gender?"checked":""} disabled>
                                                    <label class="form-check-label" for="male">Male</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="gender" id="female" value="false" ${customer.gender?"":"checked"} disabled>
                                                    <label class="form-check-label" for="female">Female</label>
                                                </div>                                                            
                                            </div>
                                            <div class="mb-3 col-md-6">
                                                <label for="email" class="form-label"><b>Email</b></label>
                                                <input class="form-control" type="email" name="email"
                                                       id="email" value="${customer.email}" required readonly/>
                                            </div>                                                        
                                            <div class="mb-3 col-md-6">
                                                <label for="phoneNumber" class="form-label"><b>Phone Number</b></label>
                                                <input class="form-control" type="tel" name="phoneNumber"
                                                       id="phoneNumber" value="${customer.phoneNumber}" required readonly/>
                                            </div>                                                        
                                            <div class="mb-6 col-md-12">
                                                <label for="phoneNumber" class="form-label"><b>Address</b></label>
                                                <input class="form-control" type="text" name="address"
                                                       id="address" value="" required />
                                            </div>                                                        
                                        </div>                                                                                                                                                   
                                    </div> 
                                </c:if>
                            </div>
                            <div class="col-lg-4 col-md-6">
                                <div class="checkout__order">
                                    <h4 class="order__title">Your order</h4>
                                    <div class="checkout__order__products">Product <span>Total</span></div>
                                    <ul class="checkout__total__products">
                                        <c:forEach items="${requestScope.cart.items}" var="i">                                   
                                            <li>${i.product.name}<span>${df.format(i.price*i.quantity)}</span></li>
                                            <input type="hidden" name="productID" value="${i.product.productID}">    
                                            <input type="hidden" name="quantity" value="${i.quantity}">    
                                            <input type="hidden" name="unitPrice" value="${df.format(i.price)}">    
                                        </c:forEach>
                                    </ul>
                                    <ul class="checkout__total__all">
                                        <li>Total<span>${df.format(requestScope.cart.totalMoney)}</span></li>
                                    </ul>
                                    <input class="form-control" type="hidden" name="total"
                                           id="total" value="${df.format(requestScope.cart.totalMoney)}" required/>
                                    <button type="submit" class="site-btn">PLACE ORDER</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>
        <!-- Checkout Section End -->

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
