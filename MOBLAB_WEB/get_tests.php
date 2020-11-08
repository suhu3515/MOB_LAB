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

if(isTheseParametersAvailable(array('specimen')))
{
    $specimen = $_POST['specimen'];
    $test_sql = $conn->prepare("select test_name from test where specimen=?");
    $test_sql->bind_param("s",$specimen);
    $test_sql->bind_result($test_name);
    $test_array = array();

    if ($test_sql->execute())
    {
        while ($test_sql->fetch())
        {
            $temp = array();
            $temp['test'] = $test_name;
            array_push($test_array,$temp);
        }
        if (sizeof($test_array) != null)
        {
            echo json_encode($test_array);
        }
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