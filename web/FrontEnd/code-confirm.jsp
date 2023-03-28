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
        <title>FOTGOT PASSWORD</title>
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
                                        <h3 class="mb-4"><b>Enter code</b></h3>
                                    </div>
                                </div>
                                <div class="mb-4">
                                    We have send to your email: ${email} a code. Please check and enter the code below
                                </div>
                                <form action="codeconfirm" method="post" class="signin-form">
                                    <input type="hidden" name="code" value="${code}">
                                    <input type="hidden" name="email" value="${email}">

                                    <div class="form-group mt-3">
                                        <input type="text" name="codeenter" class="form-control" value="" required>
                                        <label class="form-control-placeholder" for="email">Code</label>
                                    </div>

                                    <% if (request.getAttribute("error") != null) {%>
                                    <div class="w-100">
                                        <%=request.getAttribute("error")%>
                                    </div>
                                    <%}%>
                                    <div class="form-group">
                                        <button type="submit" class="form-control btn btn-primary rounded submit px-3">Continue</button>
                                    </div>
                                </form>
                                <!--                                <p class="text-center">Không phải là thành viên? <a href="signup.jsp">Đăng kí</a></p>-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </body>
</html>
