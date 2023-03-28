<%-- 
    Document   : Login
    Created on : Dec 5, 2022, 10:06:52 AM
    Author     : M.S.I
--%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CHANGE PASS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap" rel="stylesheet">-->

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

        <link rel="stylesheet" href="FrontEnd/css/styles.css">

    </head>
    <body>
        <section class="ftco-section">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-7 col-lg-5">
                        <div class="wrap">
                            <div class="login-wrap p-4 p-md-5">
                                <div class="d-flex">
                                    <div class="w-100">
                                        <h3 class="mb-4"><b>Change Password</b></h3>
                                    </div>
                                </div>
                                <form action="changepassword" method="post" class="signin-form">

                                    <div class="form-group mt-3">
                                        <input id="password-field" type="password" name="oldpass" class="form-control" value="" required>
                                        <label class="form-control-placeholder" for="oldpass">Enter old password</label>
                                        <span toggle="#password-field" class="fa fa-fw fa-eye field-icon toggle-password"></span>

                                    </div>
                                    <div class="form-group">
                                        <input id="password-field" name="newpass" type="password" class="form-control" required>
                                        <label class="form-control-placeholder" for="newpass">Enter new password</label>
                                        <span toggle="#password-field" class="fa fa-fw fa-eye field-icon toggle-password"></span>
                                    </div>
                                    <div class="form-group">
                                        <input id="password-field" name="renewpass" type="password" class="form-control" required>
                                        <label class="form-control-placeholder" for="renewpass">Confirm new password</label>
                                        <span toggle="#password-field" class="fa fa-fw fa-eye field-icon toggle-password"></span>
                                    </div>
                                    <input type="hidden" value="${customerID}" name="customerID">
                                    <% if (request.getAttribute("error") != null) {%>
                                    <div class="w-100">
                                        <%=request.getAttribute("error")%>
                                    </div>
                                    <%}%>
                                    <div class="form-group">
                                        <button type="submit" class="form-control btn btn-primary rounded submit px-3">Save</button>
                                    </div>
                                </form>

                                <!--                                <p class="text-center">Không phải là thành viên? <a href="signup.jsp">Đăng kí</a></p>-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <script src="FrontEnd/js/jquery.min.js"></script>
        <!--        <script src="js/popper.js"></script>-->
        <script src="FrontEnd/js/bootstrap.min_1.js"></script>
        <script src="FrontEnd/js/main_1.js"></script>

    </body>
</html>
