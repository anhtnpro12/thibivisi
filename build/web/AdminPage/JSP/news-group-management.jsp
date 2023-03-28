<%-- Document : admin Created on : Feb 9, 2023, 12:48:02 AM Author : ASUS --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en" class="light-style layout-menu-fixed" dir="ltr" data-theme="theme-default" data-assets-path="AdminPage/assets/"
      data-template="vertical-menu-template-free">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

        <title>Dashboard - Analytics | Sneat - Bootstrap 5 HTML Admin Template - Pro</title>

        <meta name="description" content="" />

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

        <!-- Favicon -->
        <link rel="icon" type="image/x-icon" href="AdminPage/assets/img/favicon/favicon.ico" />

        <!-- Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet" />

        <!-- Icons. Uncomment required icon fonts -->
        <link rel="stylesheet" href="AdminPage/assets/vendor/fonts/boxicons.css" />

        <!-- Core CSS -->
        <link rel="stylesheet" href="AdminPage/assets/vendor/css/core.css" class="template-customizer-core-css" />
        <link rel="stylesheet" href="AdminPage/assets/vendor/css/theme-default.css" class="template-customizer-theme-css" />
        <link rel="stylesheet" href="AdminPage/assets/css/demo.css" />

        <!-- Vendors CSS -->
        <link rel="stylesheet" href="AdminPage/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />

        <link rel="stylesheet" href="AdminPage/assets/vendor/libs/apex-charts/apex-charts.css" />

        <!-- Page CSS -->

        <!-- Helpers -->
        <script src="AdminPage/assets/vendor/js/helpers.js"></script>
        <!--toastr-->        
        <script src="AdminPage/libs/toastr/toastr.min.js"></script>
        <link rel="stylesheet" href="AdminPage/libs/toastr/toastr.min.css"/>

        <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
        <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
        <script src="AdminPage/assets/js/config.js"></script>
        <style>
            #formAccountSettings{
                display: none
            }
        </style>
    </head>

    <body>
        <!-- Layout wrapper -->
        <div class="layout-wrapper layout-content-navbar">
            <div class="layout-container">
                <!-- Menu -->
                <%@ include file="/AdminPage/Template/menu.jsp" %>
                <!-- / Menu -->

                <!-- Layout container -->
                <div class="layout-page">
                    <!-- Navbar -->

                    <%@ include file="/AdminPage/Template/navbar.jsp" %>

                    <!-- / Navbar -->

                    <!-- Content wrapper -->
                    <div class="content-wrapper">
                        <!-- Content -->

                        <!-- Hoverable Table rows -->
                        <div class="card" style="margin: 2.5%">
                            <h5 class="card-header">News Group Manager</h5>
                            <div class="d-flex justify-content-between">                               
                                <a class="card-body d-flex justify-content-end" onclick="openForms()" ><button type="button" class="btn rounded-pill btn-danger">Add News Group</button></a>
                            </div>
                            <div class="table-responsive text-nowrap">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Status</th>
                                            <th>Edit</th>
                                        </tr>
                                    </thead>
                                    <tbody class="table-border-bottom-0">
                                        <c:forEach items="${newsgroup}" var="news">
                                            <tr>
                                                <td>${news.newsgroup_name}</td>
                                                <td>
                                                    <c:if test="${news.status}">
                                                        <span class="badge bg-label-success">Active</span>
                                                    </c:if>
                                                    <c:if test="${!news.status}">
                                                        <span class="badge bg-label-danger">deactivate</span>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <a title="Edit" class="btn btn-sm btn-outline-primary" style="color: blue" onclick="openForm('${news.newsgroup_name}', '${news.newsgroup_id}')">
                                                        <i class="bx bx-edit-alt me-1"></i>
                                                    </a>
                                                    <c:if test="${news.status}">
                                                        <a title="Deactivate" class="btn btn-sm btn-outline-danger" 
                                                           href="admin-change-news-group-status?newsgroup=${news.newsgroup_id}&status=${news.status}&page=${page}">
                                                            <i class='bx bx-hide' ></i>
                                                        </a>                                                         
                                                    </c:if>
                                                    <c:if test="${!news.status}">
                                                        <a title="Active" class="btn btn-sm btn-outline-success" 
                                                           href="admin-change-news-group-status?newsgroup=${news.newsgroup_id}&status=${news.status}&page=${page}">
                                                            <i class='bx bx-show'></i>
                                                        </a>                                                         
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>                                    
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="card" style="margin: 2.5%">
                            <form action="admin-news-group-management" id="formAccountSettings" method="POST">
                                <h5 class="card-header" id="groupsname"></h5>
                                <div class="card-body">
                                    <div class="mb-3 col-md-6">
                                        <label for="productCode" class="form-label">Name</label>
                                        <input class="form-control" type="text" id="name"
                                               name="name" value="" autofocus required/>
                                    </div>
                                    <input id="newsgroupID" name="newsgroupID" type="text" style="display: none" value=""/>
                                    <div class="mt-2">
                                        <button type="submit" class="btn btn-primary me-2">Save</button>
                                        <button type="button" onclick="closeForm()" class="btn btn-outline-secondary">
                                            Back
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!--/ Hoverable Table rows -->
                        <!-- / Content -->

                        <!-- Footer -->
                        <%@ include file="/AdminPage/Template/footer.jsp" %>
                        <!-- / Footer -->

                        <div class="content-backdrop fade"></div>
                    </div>
                    <!-- Content wrapper -->
                </div>
                <!-- / Layout page -->
            </div>
            <script>
                function openForm(name, id) {
                    document.getElementById("formAccountSettings").style.display = "block";
                    document.getElementById("name").value = name;
                    document.getElementById("groupsname").innerHTML = "Editing " + name;
                    document.getElementById("newsgroupID").value = id;
                }

                function closeForm() {
                    document.getElementById("formAccountSettings").style.display = "none";
                }
                function openForms() {
                    document.getElementById("formAccountSettings").style.display = "block";
                    document.getElementById("groupsname").innerHTML = "Add news group";
                    document.getElementById("name").value = "";
                    document.getElementById("newsgroupID").value = "";
                }
            </script>
            <style>
                .avatar-lg:hover img {
                    transform: scale(1.5);
                    transition: 0.5s;
                }
            </style>

            <!-- Overlay -->
            <div class="layout-overlay layout-menu-toggle"></div>
        </div>
        <!-- / Layout wrapper -->

        <!-- Core JS -->
        <!-- build:js assets/vendor/js/core.js -->
        <!--        <script src="AdminPage/assets/vendor/libs/jquery/jquery.js"></script>
                <script src="AdminPage/assets/vendor/libs/popper/popper.js"></script>
                <script src="AdminPage/assets/vendor/js/bootstrap.js"></script>-->
        <script src="AdminPage/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>

        <script src="AdminPage/assets/vendor/js/menu.js"></script>
        <!-- endbuild -->

        <!-- Vendors JS -->
        <script src="AdminPage/assets/vendor/libs/apex-charts/apexcharts.js"></script>

        <!-- Main JS -->
        <script src="AdminPage/assets/js/main.js"></script>

        <!-- Page JS -->
        <script src="AdminPage/assets/js/dashboards-analytics.js"></script>

        <!-- Place this tag in your head or just before your close body tag. -->
        <script async defer src="https://buttons.github.io/buttons.js"></script>
        <script>
                function showSuccessToast(mess) {
                    toastr.options.progressBar = true;
                    toastr.options.positionClass = 'toast-bottom-right';
                    toastr.success(mess, 'Congratulations', {timeOut: 3000});
                }
            <c:if test="${mess != null}">showSuccessToast('${mess}')</c:if>
        </script>
    </body>

</html>