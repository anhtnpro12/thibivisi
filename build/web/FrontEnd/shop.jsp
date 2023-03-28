<%-- 
    Document   : shop.jsp
    Created on : Feb 8, 2023, 10:02:19 PM
    Author     : ASUS
--%>

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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    </head>

    <body>
        <!-- Page Preloder -->
        <!--        <div id="preloder">
                    <div class="loader"></div>
                </div>-->

        <!-- Header  -->
        <%@ include file="/FrontEnd/Template/header.jsp" %>
        <!-- Header  -->

        <!-- Breadcrumb Section Begin -->
        <section class="breadcrumb-option">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="breadcrumb__text">
                            <h4>Shop</h4>
                            <div class="breadcrumb__links">
                                <a href="home">Home</a>
                                <span>Shop</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Breadcrumb Section End -->

        <!-- Shop Section Begin -->
        <section class="shop spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-3">
                        <div class="shop__sidebar">
                            <div class="shop__sidebar__accordion">
                                <div class="accordion" id="accordionExample">
                                    <div class="card">
                                        <div class="card-heading">
                                            <a data-toggle="collapse" data-target="#collapseTwo">Branding</a>
                                        </div>
                                        <div id="collapseTwo" class="collapse show" data-parent="#accordionExample">
                                            <div class="card-body">
                                                <div class="shop__sidebar__brand">
                                                    <ul>
                                                        <c:forEach items = "${brandList}" var="brand" >
                                                            <li><a href="shopbybrandID?brandID=${brand.brandID}">${brand.name} (${productDAO.getListProductContains("brand_id",brand.brandID,"price").size()})</a></li>
                                                            </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="card-heading">
                                            <a data-toggle="collapse" data-target="#collapseTwo">Filter Price</a>
                                        </div>
                                        <div id="collapseTwo" class="collapse show" data-parent="#accordionExample">
                                            <div class="card-body">
                                                <div class="shop__sidebar__price">
                                                    <ul>
                                                        <li><a href="shopbyprice?filterprice=1">Below 20.000VND</a></li>
                                                        <li><a href="shopbyprice?filterprice=2">20.000VND - 50.000VND</a></li>
                                                        <li><a href="shopbyprice?filterprice=3">50.000VND - 100.000VND</a></li>
                                                        <li><a href="shopbyprice?filterprice=4">100.000VND - 500.000VND</a></li>
                                                        <li><a href="shopbyprice?filterprice=5">500.000VND - 1.000.000VND</a></li>
                                                        <li><a href="shopbyprice?filterprice=6">Above 1.000.000VND</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-9">
                        <div class="shop__product__option">
                            <div class="row">
                                <div class="col-lg-6 col-md-6 col-sm-6">
                                    <div class="shop__product__option__left">
                                        <p>All ${numOfProduct} products</p>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-6">
                                    <div class="shop__product__option__right">
                                        <p>Sort by Price:</p>
                                        <select onchange="location = this.value;">
                                            <option value="shop?sortType=desc"${sortType.equals("desc")?"selected":""}>High To Low</option>
                                            <option value="shop?sortType=asc" ${sortType.equals("asc")?"selected":""}>Low To High</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <c:forEach items="${list}" var="product">
                                <div class="col-lg-4 col-md-6 col-sm-6">
                                    <div class="product__item">
                                        <a href="${product.url}">                                           
                                            <img class="product__item__pic" src="<c:if test="${product.images.size() == 0}">img/General/no-image.png</c:if><c:if test="${product.images.size() > 0}">./img/UploadImgs/ProductImgs/${product.productID}/${product.images.get(0).image}</c:if>" alt="alt"/>
                                        </a>
                                        <div class="product__item__text">
                                            <h6>${product.name}</h6>
                                            <div id="addcart" onclick="addToCart(${product.productID})" style="cursor: pointer"><a class="add-cart">+ Add To Cart</a></div>
                                            <div class="rating" style="display: inline-block; position: relative; unicode-bidi: bidi-override">
                                                <div class="rating-upper" style="width: ${product.ratingStar/5.0*100}%; position: absolute; display: flex; z-index: 1;overflow: hidden; color: gold;">
                                                    <span>★</span>
                                                    <span>★</span>
                                                    <span>★</span>
                                                    <span>★</span>
                                                    <span>★</span>
                                                </div>
                                                <div class="rating-lower" style="display: flex; z-index: 0;">
                                                    <span>★</span>
                                                    <span>★</span>
                                                    <span>★</span>
                                                    <span>★</span>
                                                    <span>★</span>
                                                </div>
                                            </div>
                                            <h5>${df.format(product.price)} VND</h5>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="product__pagination">
                                    <c:forEach begin="1" end="${size}" var="i">
                                        <a class="${i==page?"active":""}" href="shop?page=${i}&sortType=${sortType}">${i}</a>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Shop Section End -->

        <!-- Footer Section Begin -->
        <%@ include file="/FrontEnd/Template/footer.jsp" %>
    </footer>
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <script>
                                                function addToCart(pid) {
                                                    $.ajax({
                                                        url: "/SWP/buy",
                                                        type: "get", //send it through get method
                                                        data: {
                                                            productID: pid,
                                                            amount: 1
                                                        },
                                                        success: function (data) {
                                                            $("#price2").html(data);
                                                            $("#price1").html(data);
                                                            showSuccessToast("Successfully!")
                                                        },
                                                        error: function (xhr) {
                                                            //Do Something to handle error
                                                        }
                                                    });
                                                }
    </script>
    <script>
        function showSuccessToast(mess) {
            toastr.options.progressBar = true;
            toastr.options.positionClass = 'toast-top-center';
            toastr.success(mess, 'ADD TO CART', {timeOut: 3000});
        }
    </script>
</body>

</html>
