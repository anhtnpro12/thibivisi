<%-- Document : home.jsp Created on : Feb 8, 2023, 2:17:48 AM Author : ASUS --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta name="description" content="Male_Fashion Template">
        <meta name="keywords" content="Male_Fashion, unica, creative, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Shop</title>
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
        <div id="preloder">
            <div class="loader"></div>
        </div>

        <!-- Header  -->
        <%@ include file="/FrontEnd/Template/header.jsp" %>
        <!-- Header  -->

        <!-- Hero Section Begin -->
        <section class="hero">
            <div class="hero__slider owl-carousel">
                <div class="hero__items set-bg" data-setbg="<c:if test="${news1.images.size() == 0}">img/General/no-image.png</c:if><c:if test="${news1.images.size() > 0}">img/UploadImgs/NewsImgs/${news1.code}/${news1.images.get(0).image}</c:if>">
                    <div class="container">
                        <div class="row">
                            <div class="col-xl-5 col-lg-7 col-md-8">
                                <div class="hero__text">
                                    <h2>${news1.title}</h2>
                                    <h2></h2>
                                    <p style="color: red;"><b>${news1.shortDescription}</b></p>
                                    <a href="${news1.url}" class="primary-btn">Read more <span class="icon_book_alt"></span></a>
                                    <div class="hero__social">
                                        <a href="#"><i class="fa fa-facebook"></i></a>
                                        <a href="#"><i class="fa fa-twitter"></i></a>
                                        <a href="#"><i class="fa fa-pinterest"></i></a>
                                        <a href="#"><i class="fa fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="hero__items set-bg" data-setbg="<c:if test="${news2.images.size() == 0}">img/General/no-image.png</c:if><c:if test="${news2.images.size() > 0}">img/UploadImgs/NewsImgs/${news2.code}/${news2.images.get(0).image}</c:if>">
                    <div class="container">
                        <div class="row">
                            <div class="col-xl-5 col-lg-7 col-md-8">
                                <div class="hero__text">
                                    <h2>${news2.title}</h2>
                                    <h2></h2>
                                    <p style="color: red;"><b>${news2.shortDescription}</b></p>
                                    <a href="${news2.url}" class="primary-btn">Read more <span class="icon_book_alt"></span></a>
                                    <div class="hero__social">
                                        <a href="#"><i class="fa fa-facebook"></i></a>
                                        <a href="#"><i class="fa fa-twitter"></i></a>
                                        <a href="#"><i class="fa fa-pinterest"></i></a>
                                        <a href="#"><i class="fa fa-instagram"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Hero Section End -->

        <!-- Banner Section Begin -->
        <section class="banner spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-7 offset-lg-6">
                        <div class="banner__item">
                            <div class="banner__item__pic" style="padding-left: 100px">
                                <img src="<c:if test="${news3.images.size() == 0}">img/General/no-image.png</c:if><c:if test="${news3.images.size() > 0}">img/UploadImgs/NewsImgs/${news3.code}/${news3.images.get(0).image}</c:if>" style="opacity: 0.5" alt="">
                            </div>
                            <div class="banner__item__text">
                                <h2 style="color: red">${news3.title}</h2>
                                <a href="${news3.url}">Read more</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5">
                        <div class="banner__item banner__item--middle">
                            <div class="banner__item__pic">
                                <img src="<c:if test="${news4.images.size() == 0}">img/General/no-image.png</c:if><c:if test="${news4.images.size() > 0}">img/UploadImgs/NewsImgs/${news4.code}/${news4.images.get(0).image}</c:if>" alt="">
                            </div>
                            <div class="banner__item__text">
                                <h2 style="color: red">${news4.title}</h2>
                                <a href="${news4.url}">Read more</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-7">
                        <div class="banner__item banner__item--last">
                            <div class="banner__item__pic" style="padding-left: 120px"> 
                                <img src="<c:if test="${news5.images.size() == 0}">img/General/no-image.png</c:if><c:if test="${news5.images.size() > 0}">img/UploadImgs/NewsImgs/${news5.code}/${news5.images.get(0).image}</c:if>" style="opacity: 0.5" alt="">
                            </div>
                            <div class="banner__item__text">
                                <h2 style="color: red">${news5.title}</h2>
                                <a href="${news5.url}">Read more</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Banner Section End -->

        <!-- Product Section Begin -->
        <section class="product spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-title">
                            <span>Featured products</span>
                            <h2>Product List</h2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <ul class="filter__controls">
                            <li onclick="showProductList()" data-filter=".top-price">Top Price</li>
                            <li onclick="showProductList()" data-filter=".new-arrivals">New Arrivals</li>
                        </ul>
                    </div>
                </div>
                <div class="row product__filter" id="productListFilter" style="display: none;">
                    <c:forEach items="${productListTopPrice}" var="product">                            
                        <div class="col-lg-3 col-md-6 col-sm-6 col-md-6 col-sm-6 mix top-price">
                            <div class="product__item">
                                <a href="${product.url}">
                                    <img class="product__item__pic" src="img/UploadImgs/ProductImgs/${product.productID}/${product.images.get(0).image}" alt="alt"/>
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
                                    <h5>${df.format(product.price)}</h5>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <c:forEach items="${productListNew}" var="product">                            
                        <div class="col-lg-3 col-md-6 col-sm-6 col-md-6 col-sm-6 mix new-arrivals">
                            <div class="product__item">
                                <a href="${product.url}">
                                    <img class="product__item__pic" src="img/UploadImgs/ProductImgs/${product.productID}/${product.images.get(0).image}" alt="alt"/>
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
                                    <h5>${df.format(product.price)}</h5>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>
        <!-- Product Section End -->

        <!-- Categories Section Begin -->
        <!--        <section class="categories spad">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-3">
                                <div class="categories__text">
                                    <h2>Clothings Hot <br /> <span>Shoe Collection</span> <br /> Accessories</h2>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="categories__hot__deal">
                                    <img src="FrontEnd/img/product-sale.png" alt="">
                                    <div class="hot__deal__sticker">
                                        <span>Sale Of</span>
                                        <h5>$29.99</h5>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4 offset-lg-1">
                                <div class="categories__deal__countdown">
                                    <span>Deal Of The Week</span>
                                    <h2>Multi-pocket Chest Bag Black</h2>
                                    <div class="categories__deal__countdown__timer" id="countdown">
                                        <div class="cd-item">
                                            <span>3</span>
                                            <p>Days</p>
                                        </div>
                                        <div class="cd-item">
                                            <span>1</span>
                                            <p>Hours</p>
                                        </div>
                                        <div class="cd-item">
                                            <span>50</span>
                                            <p>Minutes</p>
                                        </div>
                                        <div class="cd-item">
                                            <span>18</span>
                                            <p>Seconds</p>
                                        </div>
                                    </div>
                                    <a href="#" class="primary-btn">Shop now</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>-->
        <!-- Categories Section End -->

        <!-- Latest Blog Section Begin -->
        <section class="latest spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-title">
                            <!--                            <span>Latest News</span>-->
                            <h2>About us</h2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${news}" var="news">
                        <div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="blog__item">
                                <div class="blog__item__pic set-bg" data-setbg="img/UploadImgs/NewsImgs/${news.code}/${news.images.get(0).image}"></div>
                                <div class="blog__item__text">                        
                                    <span><img src="FrontEnd/img/icon/calendar.png" alt=""> ${news.createdAt}</span>
                                    <h5>${news.title}</h5>
                                    <a href="${news.url}">Read More</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>
        <!-- Latest Blog Section End -->

        <!-- Footer Section Begin -->
        <%@ include file="/FrontEnd/Template/footer.jsp" %>
        <!-- Footer Section End -->

        <!-- Search Begin -->
        <!-- Search End -->

        <!-- Js Plugins -->
        <script>
            function showProductList() {
                var x = document.getElementById("productListFilter");
                x.style.display = "flex";
            }
        </script>
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
