<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
    require_once 'DbConnect.php';
    $user_id = $_POST['user_id'];
    $image = $_POST['image'];

    $file_name = $user_id . '_USER_PRESCRIPTION';

    $path = "UPLOADS/$file_name.png";

    $actual_path = "http://192.168.43.159/moblab/$path";

    $sql1 = "select pre_loc from test_request where user_id='$user_id'";
    $res1 = mysqli_fetch_array(mysqli_query($conn, $sql1));
    if ($res1[0] == NULL) {
        $sql = "update test_request set pre_loc='$actual_path' where user_id='$user_id' and status=1";
        if (mysqli_query($conn, $sql)) {
            file_put_contents($path, base64_decode($image));
            echo "Successfully uploaded.";
        }
    }

    mysqli_close($conn);
}
else
{
    echo "Error";
}