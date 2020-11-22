<?php

define('DB_HOST',"localhost");
define('DB_USER',"root");
define('DB_PASS',"");
define('DB_NAME',"moblab");

$conn = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);

$result_list = array();

if (mysqli_connect_errno())
{
    echo "Failed to connect to MySQL: ". mysqli_connect_error();
    die();
}
if (isTheseParametersAvailable(array('user_id','tester_id')))
{
    $user_id = $_POST['user_id'];
    $tester_id = $_POST['tester_id'];
    $temp = array();
    $results = array();
    $asgn_sel = "select ass_id, test_id from assigned_test where testreq_id=(select tr_id from test_request where tester_id ='$tester_id'and user_id='$user_id' and  status=5)";
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
                                $temp['name'] = $st_subtest_row[0];
                                $subtest_ref = $st_subtest_row[1];
                            }

                            $subtest_observ_value = $st_observ_row[1];

                            $st_test_sel = "select specimen from test where test_id=(select test_id from subtests where sub_id='$st_observ_row[0]')";
                            $st_test_res = $conn->query($st_test_sel);
                            while ($st_test_row = $st_test_res->fetch_array())
                            {
                                $subtest_specimen = $st_test_row[0];
                            }
                            $temp['value'] = $subtest_observ_value;
                            $temp['ref'] = $subtest_ref;
                            $temp['specimen'] = $subtest_specimen;
                            array_push($results,$temp);
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

                $temp['name'] = $test_name;
                $temp['value'] = $observ_value;
                $temp['ref'] = $test_ref_range;
                $temp['specimen'] = $test_spec;
                array_push($results,$temp);
            }
        }
    }
    echo json_encode($results);

}
else
{
    echo json_encode('required parameters are not available');
}

function isTheseParametersAvailable($params)
{
    foreach($params as $param)
    {
        if (!isset($_POST[$param]))
        {
            return false;
        }
    }

    return true;
}
