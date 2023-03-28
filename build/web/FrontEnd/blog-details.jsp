<%-- 
    Document   : blog-details
    Created on : Feb 8, 2023, 10:23:27 PM
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
        <!-- Offcanvas Menu End -->

        <!-- Header Section Begin -->
        <%@ include file="/FrontEnd/Template/header.jsp" %>
        <!-- Header Section End -->

        <!-- Blog Details Hero Begin -->
        <section class="blog-hero spad">
            <div class="container">
                <div class="row d-flex justify-content-center">
                    <div class="col-lg-9 text-center">
                        <div class="blog__hero__text">
                            <h2>${newss.title}</h2>
                            <ul>
                                <li>By ${UserDAO.getUserByID(newss.createdBy).fullname} </li>
                                <li>${newss.createdAt}</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Blog Details Hero End -->

        <!-- Blog Details Section Begin -->
        <section class="blog-details spad">
            <div class="container">
                <div class="row d-flex justify-content-center">
                    <c:if test="${newss.images.size() > 0}">
                        <div class="col-lg-12">
                            <div class="blog__details__pic">
                                <img src="img/UploadImgs/NewsImgs/${newss.code}/${newss.images.get(0).image}" alt="">
                            </div>
                        </div>
                    </c:if>
                    <div class="col-lg-8">
                        <div class="blog__details__content">
                            <div class="blog__details__share">
                                <span>share</span>
                                <ul>
                                    <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                                    <li><a href="#" class="twitter"><i class="fa fa-twitter"></i></a></li>
                                    <li><a href="#" class="youtube"><i class="fa fa-youtube-play"></i></a></li>
                                    <li><a href="#" class="linkedin"><i class="fa fa-linkedin"></i></a></li>
                                </ul>
                            </div>
                            <div class="blog__details__quote mt-5   ">
                                <i class="fa fa-quote-left"></i>
                                <p>${newss.shortDescription}</p>
                            </div>
                            <div class="blog__details__text">
                                <p>${newss.description}</p>
                                <p></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Blog Details Section End -->

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
