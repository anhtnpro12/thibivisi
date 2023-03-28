<%-- 
    Document   : vnpay_return
    Created on : Mar 16, 2023, 4:36:26 AM
    Author     : TNA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- include libraries(jQuery, bootstrap) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <style>
            a:hover {
                text-decoration: none;
            }
        </style> 
    </head>
    <body>
        <div class="container">
            <div class="header clearfix">
                <h3 class="text-muted">ThiBiViSi Shop</h3>
            </div>
            <div class="table-responsive">
                <p>${mess}</p>
                <p>Visit <a href="home">Shop</a></p>
            </div>
            <p>
                &nbsp;
            </p>
            <footer class="footer">
                <p>&copy; TEAM 6</p>
            </footer>
        </div>
    </body>
</html>
