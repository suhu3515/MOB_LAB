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
if (isTheseParametersAvailable(array('user_id'))) {
    $user_id = $_POST['user_id'];
    $temp = array();
    $requests = array();

    $sql_requests = "select tester_id, tr_date, doc_name, pay_stat,status from test_request where user_id='$user_id'";
    $res_requests = mysqli_query($conn,$sql_requests);
    while ($row_requests = mysqli_fetch_array($res_requests))
    {
        $sel_tester = "select user_name, mobile from users where user_id='$row_requests[0]'";
        $res_tester = mysqli_query($conn,$sel_tester);
        while ($row_tester = mysqli_fetch_array($res_tester))
        {
            $temp['tester_name'] = $row_tester[0];
            $temp['tester_mob'] = $row_tester[1];
        }
        $temp['req_date'] = $row_requests[1];
        $temp['doctor_name'] = $row_requests[2];
        switch ($row_requests[3])
        {
            case 1:
                $temp['pay_status'] = 'Paid';
                break;

            case 0:
                $temp['pay_status'] = 'Not Paid';
                break;

        }
        switch($row_requests[4])
        {
            case 0:
                $temp['status'] = 'Rejected';
                break;

            case 1:
                $temp['status'] = 'Active';
                break;

            case 2:
                $temp['status'] = 'Assigned Tester';
                break;

            case 3:
                $temp['status'] = 'Sample collected';
                break;

            case 4:
                $temp['status'] = 'Generating Report';
                break;

            case 5:
                $temp['status'] = 'Completed';
                break;
        }
        array_push($requests,$temp);
    }
    echo json_encode($requests);
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
