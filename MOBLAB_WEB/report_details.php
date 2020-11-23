<!doctype html>
<html class="fixed">
<head>

    <!-- Basic -->
    <meta charset="UTF-8">

    <title>Report List | MOBLAB</title>
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
                                    <li class="nav nav-active">
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
                <h2>Test Reports</h2>

            </header>

            <!-- start: page -->
            <?php

            $server_name = "localhost";
            $user_name = "root";
            $password = "";
            $database = "moblab";

            $conn = new mysqli($server_name, $user_name, $password, $database);

            $test_req_id = $_GET['tr_id'];
            $tr_details = "select user_id, tester_id ,tr_date,doc_name from test_request where tr_id = '$test_req_id'";
            $tr_res  = $conn->query($tr_details);
            while ($tr_row = $tr_res->fetch_array())
            {
                $user_id = $tr_row[0];
                $tester_id = $tr_row[1];
                $test_date = $tr_row[2];
                $doc_name = $tr_row[3];

                $user_sel = "select user_name, h_name, place, mobile from users where user_id='$user_id'";
                $user_res = $conn->query($user_sel);
                while ($user_row = $user_res->fetch_array())
                {
                    $users_name = $user_row[0];
                    $users_hname = $user_row[1];
                    $users_place = $user_row[2];
                    $users_mob = $user_row[3];
                }

                $tester_sel = "select user_name from users where user_id = '$tester_id'";
                $tester_res = $conn->query($tester_sel);
                while ($tester_row = $tester_res->fetch_array())
                {
                    $tester_name = $tester_row[0];
                }
            }

            ?>
            <section class="panel">
                <div class="panel-body">
                    <div class="invoice">
                        <header class="clearfix">
                            <div class="row">
                                <div class="col-sm-6 mt-md">
                                    <h2 class="h2 mt-none mb-sm text-dark text-weight-bold">TEST REPORT</h2>
                                </div>
                                <div class="col-sm-6 text-right mt-md mb-md">
                                    <address class="ib mr-xlg">
                                        ABC Laboratory
                                        <br/>
                                        Mavoor Road, Calicut , Kerala
                                        <br/>
                                        Phone: +91 96330 58949
                                        <br/>
                                        abclab@gmail.com
                                    </address>
                                    <div class="ib">
                                        <img src="assets/images/new_logo.png" alt="OKLER Themes" />
                                    </div>
                                </div>
                            </div>
                        </header>
                        <div class="bill-info">
                            <div class="row">
                                <div class="col-md- 6">
                                    <div class="bill-to">
                                        <p class="h5 mb-xs text-dark text-weight-semibold">To:</p>
                                        <?php
                                        echo "<address>";
                                        echo $users_name;
                                        echo "<br/>";
                                        echo "$users_hname , $users_place";
                                        echo "<br/>";
                                        echo "Phone: +91 $users_mob";
                                        echo "</address>";
                                        ?>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="bill-data text-left">
                                        <p class="mb-none">
                                            <span class="text-dark">Test Date:</span>
                                            <?php
                                            echo "<span class='value'>$test_date</span>";
                                            ?>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="table-responsive">
                            <table class="table invoice-items">
                                <thead>
                                <tr class="h4 text-dark">
                                    <th id="cell-item"   class="text-weight-semibold">Test Name</th>
                                    <th id="cell-desc"   class="text-weight-semibold">Observed Value</th>
                                    <th id="cell-price"  class="text-center text-weight-semibold">Reference</th>
                                    <th id="cell-qty"    class="text-center text-weight-semibold">Specimen</th>
                                </tr>
                                </thead>
                                <tbody>
                                <?php

                                $asgn_sel = "select ass_id, test_id from assigned_test where testreq_id='$test_req_id'";
                                $asgn_res = $conn->query($asgn_sel);
                                while ($asgn_row = $asgn_res->fetch_array())
                                {
                                    $as_id = $asgn_row[0];
                                    $test_id = $asgn_row[1];
                                    $test_check = "select * from subtests where test_id='$test_id'";
                                    $test_check_res = $conn->query($test_check);
                                    if ($test_check_res->num_rows>0)
                                    {
                                        $st_obs_sel = "select * from results where asgn_test_id='$as_id'";
                                        $st_obs_res = $conn->query($st_obs_sel);
                                        while ($st_obs_row = $st_obs_res->fetch_array())
                                        {
                                            for ($i=2;$i<12;$i++)
                                            {
                                                if ((!is_null($st_obs_row[$i])) && $i<12)
                                                {
                                                    $st_obs_id = $st_obs_row[$i];
                                                    $st_observ_sel= "select subtest_id, observ_value from observation where obs_id='$st_obs_id'";
                                                    $st_observ_res = $conn->query($st_observ_sel);
                                                    while ($st_observ_row = $st_observ_res->fetch_array())
                                                    {
                                                        $st_subtest_sel = "select sub_name, ref_range from subtests where sub_id='$st_observ_row[0]'";
                                                        $st_subtest_res = $conn->query($st_subtest_sel);
                                                        while ($st_subtest_row = $st_subtest_res->fetch_array())
                                                        {

                                                            //$subtest_name = $st_subtest_row[0];
                                                            echo "<tr>";
                                                            echo "<td class='text-weight-semibold text-dark'>$st_subtest_row[0]</td>";
                                                            $subtest_ref = $st_subtest_row[1];
                                                        }

                                                        $subtest_observ_value = $st_observ_row[1];

                                                        $st_test_sel = "select specimen from test where test_id=(select test_id from subtests where sub_id='$st_observ_row[0]')";
                                                        $st_test_res = $conn->query($st_test_sel);
                                                        while ($st_test_row = $st_test_res->fetch_array())
                                                        {
                                                            $subtest_specimen = $st_test_row[0];
                                                        }
                                                        echo "<td>$subtest_observ_value</td>";
                                                        echo "<td class='text-center'>$subtest_ref</td>";
                                                        echo "<td class='text-center'>$subtest_specimen</td>";

                                                    }
                                                }
                                                else
                                                {

                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        $obs_sel = "select observ_1 from results where asgn_test_id='$as_id'";
                                        $obs_res = $conn->query($obs_sel);
                                        while ($obs_row = $obs_res->fetch_array())
                                        {
                                            $observ_sel = "select observ_value from observation where obs_id='$obs_row[0]'";
                                            $observ_res = $conn->query($observ_sel);
                                            while ($observ_row = $observ_res->fetch_array())
                                            {
                                                $observ_value = $observ_row[0];
                                            }

                                            $test_sel = "select test_name, specimen, ref_range from test where test_id='$test_id'";
                                            $test_res = $conn->query($test_sel);
                                            while ($test_row = $test_res->fetch_array())
                                            {
                                                $test_name = $test_row[0];
                                                $test_spec = $test_row[1];
                                                $test_ref_range = $test_row[2];
                                            }

                                            echo "<tr>";
                                            echo "<td class='text-weight-semibold text-dark'>$test_name</td>";
                                            echo "<td>$observ_value</td>";
                                            echo "<td class='text-center'>$test_ref_range</td>";
                                            echo "<td class='text-center'>$test_spec</td>";
                                        }
                                    }
                                }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="invoice-summary">
                        <div class="row">
                            <div class="col-sm-4 col-sm-offset-8">
                                <table class="table h5 text-dark">
                                    <tbody>
                                    <tr class="b-top-none">
                                        <td>Referred by</td>
                                        <?php
                                        echo "<td class='text-left'>$doc_name</td>";
                                        ?>
                                    </tr>
                                    <tr class="b-top-none">
                                        <td>Tested by</td>
                                        <?php
                                        echo "<td class='text-left'>$tester_name</td>";
                                        ?>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>

                    <div class="text-right mr-lg">
                        <a href="report_list.php" class="btn btn-primary">Go Back</a>
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
