<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
    require_once 'DbConnect.php';
    $user_id = $_POST['user_id'];
    $image = $_POST['image'];
    $doc_name = $_POST['doctor_name'];
    $tr_date = $_POST['test_date'];
    $tr_status = 1;

    $file_name = $user_id . '_USER_PRESCRIPTION';

    $path = "UPLOADS/$file_name.png";

    $actual_path = "http://192.168.43.159/moblab/$path";

    $sql1 = "select pre_loc from test_request where user_id='$user_id' and status=1";
    $res1 = mysqli_fetch_array(mysqli_query($conn, $sql1));
    if ($res1[0] == NULL) {
        $sql = "insert into test_request(user_id,pre_loc,doc_name,tr_date,status) values ('$user_id','$actual_path','$doc_name','$tr_date','$tr_status') ";
        if (mysqli_query($conn, $sql)) {
            file_put_contents($path, base64_decode($image));
            echo "Successfully uploaded.";
        }
    }
    else
    {
        echo "User already booked a test";
    }

    mysqli_close($conn);
}
else
{
    echo "Error";
}