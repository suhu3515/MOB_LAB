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
$stmt = "select distinct testreq_id from assigned_test where status=1";
$res_assgn = mysqli_query($conn,$stmt);
$users = array();
while($row_assgn = mysqli_fetch_array($res_assgn))
{
    $temp = array();

    $stmt2 = "select user_id from test_request where status=2 and tester_id='$tester_id' and tr_id='$row_assgn[0]'";
    $res_tr = mysqli_query($conn,$stmt2);
    while($row_tr = mysqli_fetch_array($res_tr))
    {
        $stmt3 = "select distinct user_name,dob,location from users where user_id='$row_tr[0]'";
        $res_usr = mysqli_query($conn, $stmt3);
        while ($row_usr = mysqli_fetch_array($res_usr))
        {

	        $temp['userid'] = $row_tr[0];
            $temp['username'] = $row_usr[0];
            $temp['userdob'] = $row_usr[1];
            $temp['userloc'] = $row_usr[2];

            array_push($users,$temp);

        }
    }
}

echo json_encode($users);
