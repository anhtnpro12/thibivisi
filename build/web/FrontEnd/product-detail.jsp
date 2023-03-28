<%-- 
    Document   : shop-details
    Created on : Feb 8, 2023, 10:37:20 PM
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
        <link rel="stylesheet" href="FrontEnd/css/slide.css" type="text/css">
    </head>

    <body>
        <!-- Page Preloder -->
        <!--        <div id="preloder">
                    <div class="loader"></div>
                </div>-->
        <!-- Header  -->
        <%@ include file="/FrontEnd/Template/header.jsp" %>
        <!-- Header  -->

        <!-- Shop Details Section Begin -->
        <section class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="product__details__breadcrumb">
                        <a href="home">Home</a>
                        <a href="shop">Shop</a>
                        <span>Product Details</span>
                    </div>
                </div>
            </div>            
        </section>
        <section class="container shop-details">
            <div class="row">
                <div class="col-lg-4 col-md-12 product__details__pic" style="height:70vh">
                    <div class="container">                        
                        <div class="row">
                            <!-- Slideshow container -->
                            <div class="slideshow-container">
                                <c:set var="tt" value="0"/>
                                <c:set var="num" value="${numOfImages}"/>
                                <c:forEach items="${images}" var="image">
                                    <c:set var="tt" value="${tt+1}"/>

                                    <!-- Full-width images with number and caption text -->
                                    <div class="mySlides">
                                        <div class="numbertext" style="background-color:grey;
                                             border-radius: 50%">${tt} / ${num}</div>
                                        <img src="img/UploadImgs/ProductImgs/${product.productID}/${image.image}" style="width:100%">
                                    </div>
                                    <!-- Next and previous buttons -->
                                    <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
                                    <a class="next" onclick="plusSlides(1)">&#10095;</a>
                                </c:forEach>
                            </div>
                            <br>
                        </div>
                    </div>
                </div>
                <div class="col-lg-8 col-md-12 product__details__content">
                    <div class="container">
                        <div class="row d-flex justify-content-center">
                            <div class="col-lg-8">
                                <div class="product__details__text">
                                    <h4>${product.name}</h4>
                                    <div class="rating" style="display: inline-block;
                                         position: relative;
                                         unicode-bidi: bidi-override">
                                        <div class="rating-upper" style="width: ${product.ratingStar/5.0*100}%;
                                             position: absolute;
                                             display: flex;
                                             z-index: 1;
                                             overflow: hidden;
                                             color: gold;">
                                            <span>★</span>
                                            <span>★</span>
                                            <span>★</span>
                                            <span>★</span>
                                            <span>★</span>
                                        </div>
                                        <div class="rating-lower" style="display: flex;
                                             z-index: 0;">
                                            <span>★</span>
                                            <span>★</span>
                                            <span>★</span>
                                            <span>★</span>
                                            <span>★</span>
                                        </div>
                                    </div>
                                    <h3>${df.format(product.price)} VND</h3>
                                    <p>${product.shortdescript}</p>
                                    <div class="product__details__cart__option">
                                        <form action="buy" method="GET">
                                            <input type="hidden" name="productID" value="${product.productID}">
                                            <div class="input-group d-flex justify-content-center">
                                                <span class="input-group-btn">
                                                    <button type="button" class="quantity-left-minus btn btn-danger btn-number"  data-type="minus" data-field="-">
                                                        <span class="fa fa-minus"></span>
                                                    </button>
                                                </span>
                                                <div style="width: 100px" >
                                                    <input type="text" id="quantity" name="amount" class="form-control input-number" value="1" min="1">
                                                </div>
                                                <span class="input-group-btn">
                                                    <button type="button" class="quantity-right-plus btn btn-success btn-number" data-type="plus" data-field="+">
                                                        <span class="fa fa-plus"></span>
                                                    </button>
                                                </span>
                                            </div>
                                            <button class="primary-btn" style="margin:16px">add to cart</button>

                                        </form>
                                    </div>
                                    <div class="product__details__last__option">
                                        <ul>
                                            <li><span>Code:</span> ${product.code}</li>
                                            <li><span>Categories:</span> ${product.cate.cate}</li>
                                            <li><span>Brand:</span> ${product.brand.name}</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="product__details__tab">
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" data-toggle="tab" href="#tabs-5"
                                               role="tab">Description</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="tabs-5" role="tabpanel">
                                            <div class="product__details__tab__content" style="overflow: hidden">
                                                <p class="note">${product.shortdescript}</p>
                                                <div class="product__details__tab__content__item" style="overflow-x: scroll;">
                                                    <h5>Products Infomation</h5>
                                                    <p>${product.description}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>                
            </div>
        </section>
        <!-- Shop Details Section End -->

        <!-- Related Section Begin -->
        <section class="related spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <h3 class="related-title">Related Product</h3>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${relatedProduct}" var="product">                            
                        <div class="col-lg-3 col-md-6 col-sm-6 col-md-6 col-sm-6">
                            <div class="product__item">
                                <a href="${product.url}">
                                    <img class="product__item__pic" src="img/UploadImgs/ProductImgs/${product.productID}/${product.images.get(0).image}" alt="alt"/>
                                </a>
                                <div class="product__item__text">
                                    <h6>${product.name}</h6>
                                    <a href="buy?productID=${product.productID}&amount=1" class="add-cart">+ Add To Cart</a>
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
        <!-- Related Section End -->

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
        <script>
                                        $(document).ready(function () {

                                            var quantitiy = 0;
                                            $('.quantity-right-plus').click(function (e) {

                                                // Stop acting like a button
                                                e.preventDefault();
                                                // Get the field name
                                                var quantity = parseInt($('#quantity').val());

                                                // If is not undefined

                                                $('#quantity').val(quantity + 1);


                                                // Increment

                                            });

                                            $('.quantity-left-minus').click(function (e) {
                                                // Stop acting like a button
                                                e.preventDefault();
                                                // Get the field name
                                                var quantity = parseInt($('#quantity').val());

                                                // If is not undefined

                                                // Increment
                                                if (quantity > 0) {
                                                    $('#quantity').val(quantity - 1);
                                                }
                                            });

                                        });
        </script>
        <script>
            let slideIndex = 1;
            showSlides(slideIndex);

            // Next/previous controls
            function plusSlides(n) {
                showSlides(slideIndex += n);
            }

            // Thumbnail image controls
            function currentSlide(n) {
                showSlides(slideIndex = n);
            }

            function showSlides(n) {
                let i;
                let slides = document.getElementsByClassName("mySlides");
                if (n > slides.length) {
                    slideIndex = 1
                }
                if (n < 1) {
                    slideIndex = slides.length
                }
                for (i = 0; i < slides.length; i++) {
                    slides[i].style.display = "none";
                }

                slides[slideIndex - 1].style.display = "block";
            }
        </script>
    </body>

</html>
