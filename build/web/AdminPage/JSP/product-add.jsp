<%-- Document : admin Created on : Feb 9, 2023, 12:48:02 AM Author : ASUS --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html lang="en" class="light-style layout-menu-fixed" dir="ltr" data-theme="theme-default" data-assets-path="AdminPage/assets/"
      data-template="vertical-menu-template-free">

    <head>        

        <!-- include summernote css/js -->
        <meta charset="utf-8" />
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

        <title>ADMIN - Create Product</title>

        <!--        <meta name="description" content="" />-->

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

        <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">

        <!-- include summernote css/js -->
        <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<!--        <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>-->
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

            .filepond--root.thumb {
                width: 300px;
                margin: 0 auto;
            }

            .filepond--list-scroller {
                display: block;
            }

            .filepond--item {
                width: calc(50% - 0.5em);
            }

            @media (min-width: 30em) {
                .filepond--item {
                    width: calc(50% - 0.5em);
                }
            }

            @media (min-width: 50em) {
                .filepond--item {
                    width: calc(33.33% - 0.5em);
                }
            }
        </style>

        <!--toastr-->        
        <script src="AdminPage/libs/toastr/toastr.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css"/>                

        <!--Bootstrap select-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>   

        <!--select2-->
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

        <!--dropdown category style-->
        <style>
            .dropdown-menu li {
                position: relative;
            }
            .dropdown-menu .dropdown-submenu {
                display: none;
                position: absolute;
                left: 100%;
                top: -7px;
            }
            .dropdown-menu .dropdown-submenu-left {
                right: 100%;
                left: auto;
            }
            .dropdown-menu > li:hover > .dropdown-submenu {
                display: block;
            }

            .dropdown-hover:hover>.dropdown-menu {
                display: inline-block;
            }

            .dropdown-hover>.dropdown-toggle:active {
                /*Without this, clicking will make it sticky*/
                pointer-events: none;
            }
            .is-flex {
                display: flex;
                justify-content: space-between;
                align-items: center;
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
                            <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">
                                    <a href="admin-view-product" style="color:inherit">PRODUCT LIST</a> /</span>
                                PRODUCT</h4>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card mb-4">
                                        <h5 class="card-header">Product Details</h5>
                                        <!-- Product -->
                                        <form action="admin-create-product" id="formAccountSettings" method="POST" enctype="multipart/form-data">

                                            <hr class="my-0" />
                                            <div class="card-body">
                                                <div class="row"> 
                                                    <div class="mb-3 col-md-12">
                                                        <label for="file-desc" class="form-label">Images</label>
                                                        <input type="file" name="file-desc" id="file-desc" multiple
                                                               data-style-item-panel-aspect-ratio="0.5625">            
                                                    </div>
                                                    <div class="mb-3 col-md-6">
                                                        <label for="productName" class="form-label">Product Name</label>
                                                        <input class="form-control" type="text" name="productName"
                                                               id="productName" value="" required=""/>
                                                    </div>
                                                    <!--                                                    <div class="mb-3 col-md-6">
                                                                                                            <label class="d-block form-label" for="category">Category</label>
                                                                                                            <select name="cateID"id="category" class="selectpicker"
                                                                                                                    data-live-search="true" data-width="100%" 
                                                                                                                    data-style="border" data-show-subtext="true"
                                                                                                                    data-size="5">
                                                    <c:forEach  items="${categories}" var="category">
                                                        <option value="${category.cateID}">${category.cate}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>                                                    -->
                                                    <div class="mb-3 col-md-6">
                                                        <label class="d-block form-label" for="brand">Brand</label>
                                                        <select name="brandID" id="brand" class="selectpicker"
                                                                data-live-search="true" data-width="100%" 
                                                                data-style="border" data-show-subtext="true"
                                                                data-size="5">
                                                            <c:forEach  items="${brands}" var="brand">
                                                                <option value="${brand.brandID}">${brand.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="mb-3 col-md-6">
                                                        <label for="" class="form-label">Category</label>                                                        
                                                        <div class="dropdown">
                                                            <input id="category" type="hidden" name="cateID" value="${fcate.cateID}" required />
                                                            <button class="btn border w-100 dropdown-toggle is-flex" type="button" id="dropdownMenuButton"
                                                                    data-toggle="dropdown" aria-expanded="false" >   
                                                                ${fcate.cate}
                                                            </button>                                                            
                                                            <ul class="dropdown-menu w-50" aria-labelledby="dropdownMenuButton">                                                                                                                                
                                                                <c:forEach  items="${categories}" var="category">                                                                
                                                                    <li>  
                                                                        <c:if test="${category.children.size() == 0}">
                                                                            <input onclick="changeCategory(${category.cateID}, '${category.cate}')" 
                                                                                   class="dropdown-item" type="button" value="${category.cate}" />
                                                                        </c:if>

                                                                        <c:if test="${category.children.size() != 0}">
                                                                            <input class="dropdown-item" type="button" value="${category.cate} &raquo;" />
                                                                            <ul class="dropdown-menu dropdown-submenu">                                                                            
                                                                                <c:forEach  items="${category.children}" var="chil">
                                                                                    <li>
                                                                                        <input onclick="changeCategory(${chil.cateID}, '${chil.cate}')" 
                                                                                               class="dropdown-item" type="button" value="${chil.cate}" />
                                                                                    </li>
                                                                                </c:forEach>
                                                                            </ul>
                                                                        </c:if>
                                                                    </li>
                                                                </c:forEach>
                                                            </ul>
                                                        </div>

                                                    </div> 

                                                    <div class="mb-3 col-md-6">
                                                        <label for="price" class="form-label">Price (VND)</label>
                                                        <input type="text" class="form-control" id="price" name="price" required=""/>
                                                    </div>    
                                                    <div class="mb-3 col-md-6">
                                                        <label for="select2Multiple" class="form-label">Tags</label>
                                                        <div class="select2-primary">                                                                                                                    
                                                            <select id="select2Multiple" name="tags" class="select2 form-select" multiple="multiple">
                                                                <c:forEach  items="${tags}" var="tag">
                                                                    <option value="tagID=${tag.tag_id}">${tag.tag}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>                                                                                                        
                                                    <div class="mb-3 col-md-12">
                                                        <label for="shortDescription" class="form-label">Short description</label>
                                                        <textarea class="form-control" name="shortdescript" id="shortDescription" cols="10" rows="3" required=""></textarea>
                                                    </div>
                                                    <div class="mb-3 col-md-12">
                                                        <label for="description" class="form-label">Description</label>
                                                        <textarea class="form-control" name="description" id="description" cols="10" rows="6" required=""></textarea>
                                                    </div>

                                                </div>                                               
                                                <div class="mt-2">
                                                    <button type="submit" class="btn btn-primary me-2">Add product</button>
                                                    <button type="button" onclick="location.href = 'admin-view-product'" class="btn btn-outline-secondary">
                                                        Back
                                                    </button>
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
        <!--<script src="AdminPage/assets/vendor/libs/jquery/jquery.js"></script>-->
        <!--<script src="AdminPage/assets/vendor/libs/popper/popper.js"></script>-->
        <!--<script src="AdminPage/assets/vendor/js/bootstrap.js"></script>-->
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
        <script src="SummerNote/summernote-bs4.js"></script>
        <link rel="stylesheet" href="SummerNote/summernote-bs4.css">
        <script>
                                                        $(document).ready(function () {
                                                            $('#description').summernote({
                                                                tabsize: 2,
                                                                height: 120
                                                            });
                                                        });
        </script>        

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

        <!--format price script-->
        <script>
                                                        var price = document.getElementById("price");
                                                        // format input number
                                                        price.addEventListener("change", inputEvent => {
                                                            price.value = formatTextToNumber(price.value);
                                                        });

                                                        function formatTextToNumber(value) {
                                                            value = value.replaceAll(/[^0-9.]/g, "");
                                                            value = (+value).toLocaleString("en-US");
                                                            return value;
                                                        }
        </script>

        <!--thumbnail picture-->
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
                    document.querySelector('input[name="file-thumb"]'),
                    {
                        storeAsFile: true,
                        labelIdle: `Drag & Drop your picture or <span class="filepond--label-action">Browse</span>`,
//                        imagePreviewHeight: 300,                        
//                        imageResizeTargetWidth: 200,
//                        imageResizeTargetHeight: 200,                                 
//                        styleLoadIndicatorPosition: 'center bottom',
//                        styleButtonRemoveItemPosition: 'center bottom',
                        acceptedFileTypes: ['image/png', 'image/jpeg'],
                        maxFileSize: 2000000
                    }
            );
        </script>
        <!--detail picture-->
        <script>
            FilePond.registerPlugin(
                    // register the Image Crop plugin with FilePond
                    FilePondPluginImageCrop,
                    FilePondPluginImagePreview,
                    FilePondPluginImageResize,
                    FilePondPluginImageTransform,
                    FilePondPluginFileValidateSize,
                    FilePondPluginFileValidateType
                    );

            const inputElement = document.querySelector('input[name="file-desc"]');
            const pond = FilePond.create(inputElement, {
                maxFiles: 10,
                storeAsFile: true,
                allowReorder: true,
                maxFileSize: 2000000,
                labelIdle: 'Drag & Drop your pictures or <span class="filepond--label-action"> Browse </span>',
                acceptedFileTypes: ['image/png', 'image/jpeg', 'image/gif']
            });
        </script>


        <!--toast message-->
        <script>
            function showSuccessToast(mess) {
                toastr.options.progressBar = true;
                toastr.options.positionClass = 'toast-bottom-right';
                toastr.success(mess, 'Congratulations', {timeOut: 3000});
            }
            <c:if test="${mess != null}">showSuccessToast('${mess}')</c:if>
        </script>

        <!--select2-->
        <script>
            $(".select2").select2({
                tags: true
            });
        </script>

        <!--change category-->
        <script>
            function changeCategory(id, name) {
                $(".is-flex")[0].innerHTML = name;
                $(".is-flex").dropdown('toggle');
                $("#category")[0].value = id;
            }
        </script>
    </body>

</html>