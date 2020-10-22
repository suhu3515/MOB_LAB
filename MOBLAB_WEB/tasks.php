<?php

define('DB_HOST',"localhost");
define('DB_USER',"root");
define('DB_PASS',"");
define('DB_NAME',"moblab");

$conn = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);

if (mysqli_connect_errno())
{
    echo "Failed to connect to MySQL: ". mysqli_connect_error();
    die();
}

$tester_id = $_POST['tester_id'];
$user_id = $_POST['user_id'];
$stmt = "select test_id from assigned_test where status=1 and testreq_id in(select tr_id from test_request where user_id='$user_id' and tester_id='$tester_id' and status=2)";
$res_assgn = mysqli_query($conn,$stmt);
$tasks = array();
$temp = array();

while($row_assgn = mysqli_fetch_array($res_assgn))
{

    $stmt4 = "select test_name, specimen from test where test_id='$row_assgn[0]'";
    $res_test = mysqli_query($conn, $stmt4);
    while ($row_test = mysqli_fetch_array($res_test))
    {
        $temp['testname'] = $row_test[0];
        $temp['testspecimen'] = $row_test[1];
    }
    array_push($tasks, $temp);


}

echo json_encode($tasks);
