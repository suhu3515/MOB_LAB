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

$spec_sql = $conn->prepare("select distinct specimen from test");
$spec_sql->bind_result($specimen);
$specimen_array = array();
if ($spec_sql->execute())
{
    while ($spec_sql->fetch())
    {
        $temp = array();
        $temp['specimen'] = $specimen;
        array_push($specimen_array,$temp);
    }
    if (sizeof($specimen_array) != null)
    {
        echo json_encode($specimen_array);
    }
}
else
{
    echo json_encode("Error in fetching...");
}


