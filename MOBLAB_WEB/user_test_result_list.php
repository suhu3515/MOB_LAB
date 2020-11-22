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
if (isTheseParametersAvailable(array('user_id')))
{
    $temp = array();
    $user_id = $_POST['user_id'];
    $sel_tr = "select tester_id,tr_date,doc_name from test_request where user_id='$user_id' and status=5";
    $res_tr = $conn->query($sel_tr);
    while($row_tr = $res_tr->fetch_array())
    {
        $temp['test_date'] = $row_tr[1];
        $temp['doctor_name'] = $row_tr[2];
        array_push($result_list,$temp);
    }
    echo json_encode($result_list);
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
