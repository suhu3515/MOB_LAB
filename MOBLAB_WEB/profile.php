<!doctype html>
<html class="fixed">
<head>

    <!-- Basic -->
    <meta charset="UTF-8">

    <title>Admin Profile | MOBLAB</title>
    <meta name="keywords" content="HTML5 Admin Template" />
    <meta name="description" content="Porto Admin - Responsive HTML5 Template">
    <meta name="author" content="okler.net">

    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

    <!-- Web Fonts  -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800|Shadows+Into+Light" rel="stylesheet" type="text/css">

    <!-- Vendor CSS -->
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.css" />

    <link rel="stylesheet" href="assets/vendor/font-awesome/css/font-awesome.css" />
    <link rel="stylesheet" href="assets/vendor/magnific-popup/magnific-popup.css" />
    <link rel="stylesheet" href="assets/vendor/bootstrap-datepicker/css/bootstrap-datepicker3.css" />

    <!-- Specific Page Vendor CSS -->
    <link rel="stylesheet" href="assets/vendor/jquery-ui/jquery-ui.css" />
    <link rel="stylesheet" href="assets/vendor/jquery-ui/jquery-ui.theme.css" />
    <link rel="stylesheet" href="assets/vendor/bootstrap-multiselect/bootstrap-multiselect.css" />
    <link rel="stylesheet" href="assets/vendor/morris.js/morris.css" />

    <!-- Theme CSS -->
    <link rel="stylesheet" href="assets/stylesheets/theme.css" />

    <!-- Skin CSS -->
    <link rel="stylesheet" href="assets/stylesheets/skins/default.css" />

    <!-- Theme Custom CSS -->
    <link rel="stylesheet" href="assets/stylesheets/theme-custom.css">

    <!-- Head Libs -->
    <script src="assets/vendor/modernizr/modernizr.js"></script>

</head>
<body>
<section class="body">

    <!-- start: header -->
    <header class="header">
        <div class="logo-container">
            <a href="homepage.php" class="logo">
                <img src="assets/images/new_logo.png" alt="Porto Admin" />
            </a>
            <div class="visible-xs toggle-sidebar-left" data-toggle-class="sidebar-left-opened" data-target="html" data-fire-event="sidebar-left-opened">
                <i class="fa fa-bars" aria-label="Toggle sidebar"></i>
            </div>
        </div>

        <!-- start: search & user box -->
        <!-- end: search & user box -->
    </header>
    <!-- end: header -->

    <div class="inner-wrapper">
        <!-- start: sidebar -->
        <aside id="sidebar-left" class="sidebar-left">

            <div class="sidebar-header">
                <div class="sidebar-title">
                    Navigation
                </div>
                <div class="sidebar-toggle hidden-xs" data-toggle-class="sidebar-left-collapsed" data-target="html" data-fire-event="sidebar-left-toggle">
                    <i class="fa fa-bars" aria-label="Toggle sidebar"></i>
                </div>
            </div>

            <div class="nano">
                <div class="nano-content">
                    <nav id="menu" class="nav-main" role="navigation">

                        <ul class="nav nav-main ">
                            <li>
                                <a href="homepage.php">
                                    <i class="fa fa-home" aria-hidden="true"></i>
                                    <span>Dashboard</span>
                                </a>
                            </li>
                            <li class="nav-parent ">
                                <a href="#">
                                    <i class="fa fa-columns" aria-hidden="true"></i>
                                    <span>Requests</span>
                                </a>
                                <ul class="nav nav-children ">
                                    <li>
                                        <a href="test_requests.php">
                                            Test Requests
                                        </a>
                                    </li>
                                    <li>
                                        <a href="all_requests.php">
                                            Pending Requests
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-parent">
                                <a href="#">
                                    <i class="fa fa-copy" aria-hidden="true"></i>
                                    <span>Tests</span>
                                </a>
                                <ul class="nav nav-children">
                                    <li>
                                        <a href="tests_list.php">
                                            Tests
                                        </a>
                                    </li>
                                    <li>
                                        <a href="subtests_list.php">
                                            SubTests
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-parent">
                                <a href="#">
                                    <i class="fa fa-tasks" aria-hidden="true"></i>
                                    <span>Tester</span>
                                </a>
                                <ul class="nav nav-children">
                                    <li>
                                        <a href="tester_add.php">
                                            Add Tester
                                        </a>
                                    </li>
                                    <li>
                                        <a href="tester_list.php">
                                            Tester List
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-parent">
                                <a href="#">
                                    <i class="fa fa-list-alt" aria-hidden="true"></i>
                                    <span>Reports</span>
                                </a>
                                <ul class="nav nav-children">
                                    <li>
                                        <a href="report_list.php">
                                            View Reports
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="profile.php">
                                    <i class="fa fa-user" aria-hidden="true"></i>
                                    <span>Profile</span>
                                </a>
                            </li>
                            <li>
                                <a href="pages-signin.html">
                                    <i class="fa fa-external-link" aria-hidden="true"></i>
                                    <span>Logout</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>

                <script>
                    // Maintain Scroll Position
                    if (typeof localStorage !== 'undefined') {
                        if (localStorage.getItem('sidebar-left-position') !== null) {
                            var initialPosition = localStorage.getItem('sidebar-left-position'),
                                sidebarLeft = document.querySelector('#sidebar-left .nano-content');

                            sidebarLeft.scrollTop = initialPosition;
                        }
                    }
                </script>


            </div>

        </aside>
        <!-- end: sidebar -->

        <section role="main" class="content-body">
            <header class="page-header">
                <h2>Admin Profile</h2>

            </header>

            <!-- start: page -->

            <section class="panel panel-featured-left panel-featured-tertiary">
                <div class="panel-body">
                    <div class="widget-summary">
                        <div class="widget-summary-col">
                            <div class="summary">
                                <h4 class="title">Welcome</h4>
                                <div class="info">
                                    <strong class="amount">ADMINISTRATOR</strong>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br><br>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 control-label" for="subname">Mobile Number</label>
                            <div class="col-md-6">
                                <label class="col-md-3 control-label" for="subname">9876543210</label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 control-label" for="refrange">Password</label>
                            <a href="change_password.php"><button class="col-md-3 btn-primary btn">Change password</button></a>
                        </div>
                    </div>
                </div>
            </section>


            <!-- end: page -->
        </section>
    </div>


</section>

<!-- Vendor -->
<script src="assets/vendor/jquery/jquery.js"></script>
<script src="assets/vendor/jquery-browser-mobile/jquery.browser.mobile.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.js"></script>
<script src="assets/vendor/nanoscroller/nanoscroller.js"></script>
<script src="assets/vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="assets/vendor/magnific-popup/jquery.magnific-popup.js"></script>
<script src="assets/vendor/jquery-placeholder/jquery-placeholder.js"></script>

<!-- Specific Page Vendor -->
<script src="assets/vendor/jquery-ui/jquery-ui.js"></script>
<script src="assets/vendor/jqueryui-touch-punch/jqueryui-touch-punch.js"></script>
<script src="assets/vendor/jquery-appear/jquery-appear.js"></script>
<script src="assets/vendor/bootstrap-multiselect/bootstrap-multiselect.js"></script>
<script src="assets/vendor/jquery.easy-pie-chart/jquery.easy-pie-chart.js"></script>
<script src="assets/vendor/flot/jquery.flot.js"></script>
<script src="assets/vendor/flot.tooltip/flot.tooltip.js"></script>
<script src="assets/vendor/flot/jquery.flot.pie.js"></script>
<script src="assets/vendor/flot/jquery.flot.categories.js"></script>
<script src="assets/vendor/flot/jquery.flot.resize.js"></script>
<script src="assets/vendor/jquery-sparkline/jquery-sparkline.js"></script>
<script src="assets/vendor/raphael/raphael.js"></script>
<script src="assets/vendor/morris.js/morris.js"></script>
<script src="assets/vendor/gauge/gauge.js"></script>
<script src="assets/vendor/snap.svg/snap.svg.js"></script>
<script src="assets/vendor/liquid-meter/liquid.meter.js"></script>
<script src="assets/vendor/jqvmap/jquery.vmap.js"></script>
<script src="assets/vendor/jqvmap/data/jquery.vmap.sampledata.js"></script>
<script src="assets/vendor/jqvmap/maps/jquery.vmap.world.js"></script>
<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.africa.js"></script>
<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.asia.js"></script>
<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.australia.js"></script>
<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.europe.js"></script>
<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.north-america.js"></script>
<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.south-america.js"></script>

<!-- Theme Base, Components and Settings -->
<script src="assets/javascripts/theme.js"></script>

<!-- Theme Custom -->
<script src="assets/javascripts/theme.custom.js"></script>

<!-- Theme Initialization Files -->
<script src="assets/javascripts/theme.init.js"></script>

<!-- Examples -->
<script src="assets/javascripts/dashboard/examples.dashboard.js"></script>

</body>
</html>

<?php
