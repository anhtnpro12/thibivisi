<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Offcanvas Menu Begin -->
<div class="offcanvas-menu-overlay"></div>
<div class="offcanvas-menu-wrapper">
    <div class="offcanvas__nav__option">
        <a href="#" class="search-switch"><img src="FrontEnd/img/icon/search.png" alt=""></a>
        <a href="shopping-cart" id="icon1"><img src="FrontEnd/img/icon/cart.png" alt=""> </a>
        <div class="price" id="price1"><fmt:formatNumber pattern="#,###.##" value="${cookie.total.value==null?0:cookie.total.value}"></fmt:formatNumber> VND
            </div>
        </div>
    <c:if test="${sessionScope.customer!=null}">
        <div class="header__top__links">
            <a href="customerprofile" style="color: black">Hi, ${customer.name}</a><!-- comment -->
        </div>
    </c:if>
    <div id="mobile-menu-wrap"></div>
    <div class="offcanvas__text">
        <!--        <p>Free shipping, 30-day return or refund guarantee.</p>-->
    </div>
    <c:if test="${sessionScope.customer!=null}">
        <div class="header__top__links">
            <a href="changepassword" style="color: black">Change Password</a>
            <a href="customerlogout" style="color: black">LOGOUT</a>
        </div>
    </c:if>
    <c:if test="${sessionScope.customer==null}">
        <a href="customerlogin">LOGIN</a>
    </c:if>
</div>
<!-- Offcanvas Menu End -->

<!-- Header Section Begin -->
<header class="header">
    <div class="header__top">
        <div class="container">
            <div class="row">
                <div class="col-lg-5 col-md-7">
                    <div class="header__top__left">
                        <div id="error-message" style="color: white">
                            <c:if test="${error!=null}">
                                ${error}
                            </c:if>
                        </div>
                    </div>
                </div>
                <script>
                    setTimeout(function () {
                        document.getElementById('error-message').style.display = 'none';
                    }, 5000); // 5 giây

                </script>

                <div class="col-lg-12 col-md-6">
                    <div class="header__top__right">
                        <div class="header__top__links">
                            <c:if test="${sessionScope.customer!=null}">
                                <a href="customerprofile">Hi, ${customer.name}</a>
                                <a href="orderhistory">Order History</a>
                                <a href="changepassword">Change Password</a>
                                <a href="customerlogout">LOGOUT</a>
                            </c:if>
                            <c:if test="${sessionScope.customer==null}">
                                <a href="customerlogin">LOGIN</a>
                            </c:if>
                            <!--                            <a style="color: white"></a>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-3">
                <div class="header__logo">
                    <a href="home" style="width: 55px;height: 55px;"><img src="FrontEnd/img/logo1.png" alt=""></a>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <nav class="header__menu mobile-menu" style="padding-bottom: 0px">
                    <ul>
                        <li><a href="./home">Home</a></li>
                        <li><a href="./shop">Shop</a></li>
                        <li><a href="javascript:void(0);" onclick="toggleCategory()">Category</a></li>
                        <li><a href="./blog">Blog</a></li>
                        <li><a href="./contact">Contacts</a></li>
                    </ul>
                </nav>
                <nav id="category-list" class="header__menu mobile-menu" style="padding: 15px; display: none;">
                    <ul>
                        <c:forEach  items="${listnull}" var="listnull">

                            <li><a href="${listnull.url}" style="font-size: 15px">${listnull.cate}</a>
                                <c:if test="${listnull.isparent()}">
                                    <ul class="dropdown">
                                        <c:forEach items="${listnotnull}" var="listnotnull">
                                            <c:if test="${listnull.cateID==listnotnull.parentID}">
                                                <li><a href="${listnotnull.url}">${listnotnull.cate}</a></li>
                                                </c:if>
                                            </c:forEach>
                                    </ul>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
            <div class="col-lg-3 col-md-3">
                <div class="header__nav__option">
                    <a href="#" class="search-switch"><img src="FrontEnd/img/icon/search.png" alt=""></a>
                    <a href="shopping-cart" id="icon2"><img src="FrontEnd/img/icon/cart.png" alt=""> </a>
                    <div class="price" id="price2"><fmt:formatNumber pattern="#,###.##" value="${cookie.total.value}"></fmt:formatNumber>${cookie.total.value != null?" VND":"0 VND"}
                    </div>
                </div>
            </div>
        </div>
        <div class="canvas__open"><i class="fa fa-bars"></i></div>
    </div>
    <div class="search-model">
        <div class="h-100 d-flex align-items-center justify-content-center">
            <div class="search-close-switch">+</div>
            <form action="search" method="get" class="search-model-form">
                <input type="text" name="search" id="search-input" value="" placeholder="Search product....">
            </form>
        </div>
    </div>
</header>
<!-- Header Section End -->
<script>
    function toggleCategory() {
        var x = document.getElementById("category-list");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    }
</script>