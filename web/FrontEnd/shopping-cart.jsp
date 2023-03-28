<%-- Document : shopping-cart Created on : Feb 8, 2023, 10:38:10 PM Author : ASUS --%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    </head>

    <body>
        <!-- Page Preloder -->
        <!--        <div id="preloder">
                    <div class="loader"></div>
                </div>-->

        <!-- Header Section Begin -->
        <%@ include file="/FrontEnd/Template/header.jsp" %>
        <!-- Header Section End -->

        <!-- Breadcrumb Section Begin -->
        <section class="breadcrumb-option">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="breadcrumb__text">
                            <h4>Shopping Cart</h4>
                            <div class="breadcrumb__links">
                                <a href="./home">Home</a>
                                <a href="./shop">Shop</a>
                                <span>Shopping Cart</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Breadcrumb Section End -->

        <!-- Shopping Cart Section Begin -->
        <section class="shopping-cart spad">
            <div class="container">
                <div class="row" id="content">
                    <div class="col-lg-8">
                        <div class="shopping__cart__table">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Quantity</th>
                                        <th>Total</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody id="body">
                                    <c:forEach items="${requestScope.cart.items}" var="i">                                   
                                        <tr>
                                            <td class="product__cart__item">
                                                <div class="product__cart__item__pic">
                                                    <img style="object-fit: contain; height: 100px" class="img-fluid img-thumbnail" src="<c:if test="${i.product.images.size() == 0}">img/General/no-image.png</c:if><c:if test="${i.product.images.size() > 0}">./img/UploadImgs/ProductImgs/${i.product.productID}/${i.product.images.get(0).image}</c:if>" alt="">
                                                </div>
                                                <div class="product__cart__item__text">
                                                    <h6>${i.product.name}</h6>
                                                    <h5><fmt:formatNumber pattern="#,###.##" value="${i.price}"></fmt:formatNumber></h5>
                                                    </div>
                                                </td>
                                                <td class="quantity__item">
                                                    <div class="quantity">
                                                        <button onclick="process('${i.product.productID}', '1')" class="btn btn-outline-dark"><i class="fa fa-plus-circle"></i></button>
                                                    <span>${i.quantity}</span>
                                                    <button onclick="process('${i.product.productID}', '-1')" class="btn btn-outline-dark"><i class="fa fa-minus-circle"></i></button>
                                                </div>
                                            </td>
                                            <td class="cart__price"><fmt:formatNumber pattern="#,###.##" value="${i.price*i.quantity}"></fmt:formatNumber></td>
                                            <td class="cart__close"> <button onclick="process('${i.product.productID}', '0')" class="btn btn-outline-light"><i class="fa fa-close"></i></button></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-6">
                                <div class="continue__btn">
                                    <a href="shop">Continue Shopping</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="cart__total">
                            <h6>Cart total</h6>
                            <ul>
                                <li>Total <span id="total"><fmt:formatNumber pattern="#,###.##" value="${requestScope.cart.totalMoney}"></fmt:formatNumber></span></li>
                                </ul>
                                <a href="checkout" class="primary-btn" id="checkout">Proceed to checkout</a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Shopping Cart Section End -->

            <!-- Footer Section Begin -->
        <%@ include file="/FrontEnd/Template/footer.jsp" %>
        <!-- Footer Section End -->
        <!-- Search Begin -->
        <div class="search-model">
            <div class="h-100 d-flex align-items-center justify-content-center">
                <div class="search-close-switch">+</div>
                <form class="search-model-form">
                    <input type="text" id="search-input" placeholder="Search here.....">
                </form>
            </div>
        </div>
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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script>
                                                function process(id, amount) {
                                                    $.ajax({
                                                        url: "/SWP/process",
                                                        type: "get", //send it through get method
                                                        data: {
                                                            productID: id,
                                                            amount: amount
                                                        },
                                                        success: function (data) {
                                                            $("#content").html(data);
                                                            if (parseFloat($("#total").html()) === 0) {
                                                                $("#checkout").css("background", "#E21818");
                                                                $("#checkout").click(function (event) {
                                                                    event.preventDefault();
                                                                    showSuccessToast("NOTHING IN YOUR SHOPPING CART");
                                                                });
                                                            }
                                                        },
                                                        error: function (xhr) {
                                                            //Do Something to handle error
                                                        }
                                                    });
                                                }
        </script>
        <script>
            $(document).ready(function () {
                $("#icon2").addClass("d-none");
                $("#icon1").addClass("d-none");
                $("#price1").addClass("d-none");
                $("#price2").addClass("d-none");
            });
            if (parseFloat($("#total").html()) === 0) {
                $("#checkout").css("background", "#E21818");
                $("#checkout").click(function (event) {
                    event.preventDefault();
                    showSuccessToast("NOTHING IN YOUR SHOPPING CART");
                });
            }
        </script>
        <script>
            function showSuccessToast(mess) {
                toastr.options.progressBar = true;
                toastr.options.positionClass = 'toast-top-center';
                toastr.error(mess, 'ERROR', {timeOut: 2000});
            }
        </script>
    </body>

</html>