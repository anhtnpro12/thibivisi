<%-- 
    Document   : contact
    Created on : Feb 8, 2023, 10:35:41 PM
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
                <a href="#" class="search-switch"><img src="/img/icon/search.png" alt=""></a>
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

        <!-- Map Begin -->
        <div class="map">
            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d952323.3415503246!2d105.61008906855265!3d21.19164042507325!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31345b465a4e65fb%3A0xaae6040cfabe8fe!2zVHLGsOG7nW5nIMSQ4bqhaSBI4buNYyBGUFQ!5e0!3m2!1svi!2s!4v1677682284019!5m2!1svi!2s" height="500" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
        </div>
        <!-- Map End -->

        <!-- Contact Section Begin -->
        <section class="contact spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-6 col-md-6">
                        <div class="contact__text">
                            <div class="section-title">
                                <span>Information</span>
                                <h2>Contact Us</h2>
                                <p>As you might expect of a company that began as a high-end interiors contractor, we pay
                                    strict attention.</p>
                            </div>
                            <ul>
                                <li>
                                    <h4>FBT University</h4>
                                    <p>Education Zone, Hoa Lac Hi-tech Park, Km29, Thang Long Boulevard, Thach Hoa, Thach That, Ha Noi, Vietnam <br />0366780698</p>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6">
                        <div class="contact__form">
                            <form action="contact" method="post" id="form" onsubmit="return validateForm()">

                                <div class="row">
                                    <div class="col-lg-6">
                                        <input type="text" placeholder="Name" name="name" required>
                                    </div>
                                    <div class="col-lg-6">
                                        <input type="email" placeholder="Email" name="email" required>
                                    </div>
                                    <div class="col-lg-12">
                                        <textarea placeholder="Message" name="message" required></textarea>
                                        <button type="submit" class="site-btn">Send Message</button>
                                    </div>
                                </div>
                            </form>
                            <script>
                                function validateForm() {
                                    var name = document.forms["form"]["name"].value;
                                    var email = document.forms["form"]["email"].value;
                                    var message = document.forms["form"]["message"].value;

                                    if (name == "" || email == "" || message == "") {
                                        alert("Please fill out all fields.");
                                        return false;
                                    } else {
                                        return confirm("Are you sure you want to send this message?");
                                    }
                                }
                            </script>

                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Contact Section End -->

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
