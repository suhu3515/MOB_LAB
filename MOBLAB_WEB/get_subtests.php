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
if (isTheseParametersAvailable(array('test_name','test_spec')))
{
    $test_name = $_POST['test_name'];
    $test_spec = $_POST['test_spec'];
    $stmt = $conn->prepare("select sub_name from subtests where test_id in(select test_id from test where specimen=? and test_name=?)");
    $stmt->bind_param("ss", $test_spec, $test_name);
    $stmt->bind_result($subname);
    $tsubtests = array();
    if ($stmt->execute())
    {
        while ($stmt->fetch())
        {
            $temp = array();
            $temp['subtest'] = $subname;
            array_push($tsubtests,$temp);
        }
	echo json_encode($tsubtests);
    }
    else
    {
        echo json_encode("Error in fetching...");
    }
}
else
{
    echo json_encode("Parameters not available");
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