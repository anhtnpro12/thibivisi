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

        <title>Dashboard - Analytics | ThiBiViSi.com</title>

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

        <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
        <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
        <script src="AdminPage/assets/js/config.js"></script>
        
        <!--filepond-->
        <link href="https://unpkg.com/filepond/dist/filepond.css" rel="stylesheet">        
        <link href="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css" rel="stylesheet">
        <link href="https://unpkg.com/filepond-plugin-image-edit/dist/filepond-plugin-image-edit.css" rel="stylesheet"/>
        <style>
            /*
            * FilePond Custom Styles
            */

            .filepond--drop-label {
                color: #4c4e53;
            }

            .filepond--label-action {
                text-decoration-color: #babdc0;
            }

            .filepond--panel-root {
                background-color: #edf0f4;
            }

            .filepond--root.avatar {
                width: 150px;
                margin: 0 auto;
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
                        <div class="container-xxl flex-grow-1 container-p-y">
                            <h4 class="fw-bold py-3 mb-4"><a href="admin-view-user"><span class="text-muted fw-light">USER LIST /</span></a>
                                USER</h4>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card mb-4">
                                        <h5 class="card-header">User Details</h5>
                                        <!-- Product -->
                                        <form action="admin-create-user" id="formUserCreate" method="POST" enctype="multipart/form-data">
                                            
                                            <hr class="my-0" />
                                            <div class="card-body">
                                                <div class="row">
                                                    <div class="mb-3 col-md-12">
                                                        <label for="file-ava" class="form-label">Avatar</label>
                                                        <input class="avatar" type="file" name="file-ava" id="file-ava">            
                                                    </div>                                                    
                                                    <div class="mb-3 col-md-6">
                                                        <label for="productName" class="form-label">Full Name</label>
                                                        <input class="form-control" type="text" name="userName"
                                                               id="userName" />
                                                    </div>
                                                    <div class="mb-3 col-md-6">
                                                        <label for="userIDCard" class="form-label">ID Card</label>
                                                        <input class="form-control" type="number" name="userIDCard"
                                                               id="userIDCard" value=""/>
                                                    </div>
                                                    <div class="mb-3 col-md-6">
                                                        <label for="userGender" class="form-label">Gender</label>
                                                        <select name="userGender" id="userGender" class="select2 form-select">
                                                            <option value="Male" selected>Male</option>
                                                            <option value="Female">Female</option>
                                                        </select>
                                                    </div>
                                                    <div class="mb-3 col-md-6">
                                                        <label for="userDob" class="form-label">DOB</label>
                                                        <input class="form-control" type="date" name="userDob"
                                                               id="userDob"/>
                                                    </div>
                                                    <div class="mb-3 col-md-6">
                                                        <label for="userAddress" class="form-label">Address</label>
                                                        <input class="form-control" type="text" name="userAddress"
                                                               id="userAddress"/>
                                                    </div>

                                                    <div class="mb-3 col-md-6">
                                                        <label for="userEmail" class="form-label">Email</label>
                                                        <input class="form-control" type="email" name="userEmail"
                                                               id="userEmail"/>
                                                    </div>
                                                    <div class="mb-3 col-md-6 form-password-toggle">
                                                        <label class="form-label" for="basic-default-password32">Password</label>
                                                        <div class="input-group input-group-merge">
                                                            <input
                                                                type="password"
                                                                name="userPassword"
                                                                class="form-control"
                                                                id="basic-default-password32"
                                                                placeholder="&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;"
                                                                aria-describedby="basic-default-password"
                                                                />
                                                            <span class="input-group-text cursor-pointer" id="basic-default-password"
                                                                  ><i class="bx bx-hide"></i
                                                                ></span>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="mt-2">
                                                    <button type="submit" id="submit" class="btn btn-primary me-2">Add User</button>
                                                    <button type="reset" class="btn btn-outline-secondary">Reset</button>
                                                </div>
                                            </div>
                                        </form>
                                        <!-- /Account -->
                                    </div>
                                </div>
                            </div>
                        </div>
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

        <!-- file validate -->
<!--        <script>
            $(document).ready(function () {
                var maxFileSize = 2 * 1024 * 1024; /*2MB*/
                $('#upload').on('change', function () {
                    console.log(this.files[0].size > 2 * 1024 * 1024);
                    if (this.files[0].size > maxFileSize) {
                        $('#formUserCreate').on('submit', function (event) {
                            event.preventDefault();
                            $('#text-test').text('Cannot upload a file has size more than 2MB!');
                            $('#text-test').css('color', 'red');
                        });
                    }
//                    
//                    if(this.value.match(new RegExp('[^.]+$'))){
//                        
//                    }

                });

//                $('#formUserCreate').on('submit', function () {
//                    if ($('#upload').files[0].size > 2 * 1024 * 1024) {
//                        this.preventDefault();
//                        $('#text-test').text('Cannot upload a file has size more than 2MB!');
//                        $('#text-test').css('color', 'red');
//                    }
//                })

            });
        </script>-->

        <!-- Page JS -->
        <script src="AdminPage/assets/js/dashboards-analytics.js"></script>

        <!-- Place this tag in your head or just before your close body tag. -->
        <script async defer src="https://buttons.github.io/buttons.js"></script>
        
        <!--filepond-->
        <script src="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.js"></script>
        <script src="https://unpkg.com/filepond-plugin-image-resize/dist/filepond-plugin-image-resize.js"></script>
        <script src="https://unpkg.com/filepond-plugin-image-transform/dist/filepond-plugin-image-transform.js"></script>

        <!--add the Image plugin script--> 
        <script src="https://unpkg.com/filepond-plugin-image-crop/dist/filepond-plugin-image-crop.js"></script>
        <script src="https://unpkg.com/filepond-plugin-file-validate-size/dist/filepond-plugin-file-validate-size.js"></script>        
        <script src="https://unpkg.com/filepond-plugin-file-validate-type/dist/filepond-plugin-file-validate-type.js"></script>
        <script src="https://unpkg.com/filepond-plugin-image-exif-orientation/dist/filepond-plugin-image-exif-orientation.js"></script>        

        <script src="https://unpkg.com/filepond/dist/filepond.js"></script> 
        
        <script>
            FilePond.registerPlugin(
                    // validates files based on input type
                    FilePondPluginFileValidateType,
                    // corrects mobile image orientation
                    FilePondPluginImageExifOrientation,
                    // previews the image
                    FilePondPluginImagePreview,
                    // crops the image to a certain aspect ratio
                    FilePondPluginImageCrop,
                    // resizes the image to fit a certain size
                    FilePondPluginImageResize,
                    // applies crop and resize information on the client
                    FilePondPluginImageTransform,
                    FilePondPluginFileValidateSize
                    );

            // Select the file input and use create() to turn it into a pond
            // in this example we pass properties along with the create method
            // we could have also put these on the file input element itself
            FilePond.create(
                    document.querySelector('input[name="file-ava"]'),
                    {
                        storeAsFile: true,
                        labelIdle: `Drag & Drop your picture or <span class="filepond--label-action">Browse</span>`,
                        imagePreviewHeight: 150,  
                        imageCropAspectRatio: '1:1',
                        imageResizeTargetWidth: 200,
                        imageResizeTargetHeight: 200,    
                        stylePanelLayout: 'compact circle',
                        styleLoadIndicatorPosition: 'center bottom',
                        styleButtonRemoveItemPosition: 'center bottom',
                        acceptedFileTypes: ['image/png', 'image/jpeg', 'image/gif'],
                        maxFileSize: 2000000
                    }
            );
        </script>
    </body>

</html>