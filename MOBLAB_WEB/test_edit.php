<!doctype html>
<html class="fixed">
<head>

    <!-- Basic -->
    <meta charset="UTF-8">

    <title>Edit Test | MOBLAB</title>
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
                <img src="assets/images/new_logo.png" alt="ABC LAB" />
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
                                            Today's Requests
                                        </a>
                                    </li>
                                    <li>
                                        <a href="all_requests.php">
                                            All Requests
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
                                    <li class="nav nav-active">
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
                                <a href="feedback.php">
                                    <i class="fa fa-bookmark" aria-hidden="true"></i>
                                    <span>Feedback</span>
                                </a>
                            </li>
                            <li>
                                <a href="index.html#signin">
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
                <h2>Edit Test</h2>

            </header>

            <!-- start: page -->
            <?php

            $tid = $_GET['tid'];

            $server_name = "localhost";
            $user_name = "root";
            $password = "";
            $database = "moblab";

            $conn = new mysqli($server_name, $user_name, $password, $database);

            $test_sel = "select test_name, specimen, rate, est_time,ref_range,instr from test where test_id='$tid'";
            $res = $conn->query($test_sel);
            $row = $res->fetch_row();

            $tname = $row[0];
            $tspec = $row[1];
            $trate = $row[2];
            $ttime = $row[3];
            $trange = $row[4];
            $tinstr = $row[5];


            ?>
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <div class="panel-actions">
                                <a href="#" class="panel-action panel-action-toggle" data-panel-toggle></a>
                                <a href="#" class="panel-action panel-action-dismiss" data-panel-dismiss></a>
                            </div>

                            <h2 class="panel-title">Edit Test</h2>
                        </header>
                        <form class="form-horizontal form-bordered" method="post">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-md-3 control-label" for="testname">Test Name <span class="required">*</span></label>
                                    <div class="col-md-6">
                                        <?php
                                        echo "<input type='text' class='form-control' id='testname' name='testname' value='$tname' required>";
                                        ?>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-3 control-label" for="specimen">Specimen <span class="required">*</span></label>
                                    <div class="col-md-6">
                                        <?php
                                        echo "<input type='text' class='form-control' id='specimen' name='specimen' value='$tspec' required>";
                                        ?>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-3 control-label" for="price">Price <span class="required">*</span></label>
                                    <div class="col-md-6">
                                        <?php
                                        echo "<input type='number' class='form-control' id='price' name='price' value='$trate' required>";
                                        ?>
                                    </div>
                                </div>



                                <div class="form-group">
                                    <label class="col-md-3 control-label" for="esttime">Est. Time <span class="required">*</span></label>
                                    <div class="col-md-6">
                                        <?php
                                        echo "<input type='text'  class='form-control' id='esttime' name='esttime' value='$ttime' required>";
                                        ?>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-3 control-label" for="refrange">Ref. Range</label>
                                    <div class="col-md-6">
                                        <?php
                                        echo "<input type='text'  class='form-control' id='refrange' name='refrange' value='$trange'>";
                                        ?>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-3 control-label">Instructions</label>
                                    <?php
                                    echo "<div class='col-md-6'>";
                                    echo "<textarea class='form-control' rows='6' id='instructions' name='instructions' data-plugin-textarea-autosize>$tinstr</textarea>";
                                    echo "</div>";
                                    ?>
                                </div>

                            </div>
                            <footer class="panel-footer">
                                <div class="row">
                                    <div class="col-sm-9 col-sm-offset-3">
                                        <input class="btn btn-primary" type="submit" name="edit_test">
                                        <button type="reset" class="btn btn-default">Reset</button>
                                    </div>
                                </div>
                            </footer>

                            <br><br>

                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="alert alert-default">
                                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                        <strong>Est. Time</strong> denotes the time taken for result generation after sample collection
                                    </div>
                                </div>
                            </div>

                        </form>
                    </section>
                </div>
            </div>

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

if (isset($_POST['edit_test']))
{
    $test_name = $_POST['testname'];
    $test_specimen = $_POST['specimen'];
    $test_price = $_POST['price'];
    $test_time = $_POST['esttime'];
    $test_range = $_POST['refrange'];
    $test_instr = $_POST['instructions'];


    $new_test = "UPDATE test SET test_name='$test_name',specimen='$test_specimen',rate='$test_price',est_time='$test_time',ref_range='$test_range',instr='$test_instr' WHERE test_id='$tid'";
    $upd = mysqli_query($conn, $new_test);

    if($upd)
    {
        echo "<script>alert('Test Updated Successfully...')</script>";
        echo "<script>window.location='tests_list.php'</script>";
    }
    else
    {
        echo "<script>alert('Error in Updating test')</script>";
    }

}
