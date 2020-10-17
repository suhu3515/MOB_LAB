<?php

include('DbConnect.php');

if (isset($_POST['specimen']))
{
    $spec_value = $_POST['specimen'];
    $query = $conn->query("select test_id, test_name from test where specimen='$spec_value'");
    $rows = $query->num_rows;

    if ($rows > 0)
    {
        echo '<option value="">Select test</option>';
        while ($row = $query->fetch_assoc())
        {
            echo '<option value="'.$row['test_id'].'">'.$row['test_name'].'</option>';
        }
    }
}
