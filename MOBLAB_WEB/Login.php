<?php

$host = "localhost";
$user = "root";
$password = '';
$db_name = "moblab";

$con = mysqli_connect($host,$user,$password,$db_name);


if(isset($_POST['submit']))
{
    $username = $_POST['username'];
    $pass = $_POST['pass'];
    $sql = mysqli_query($con, "select * from login where mobile='$username'and password='$pass' and l_role='ADMIN'");
    $count = mysqli_num_rows($sql);
    if ($count > 0) {

        echo "<script>alert('Welcome ADMIN')</script>";
        echo "<script>window.location.href='homepage.php'</script>";

        $_SESSION['logged_in'] = true;
    }
    else
    {
        echo "<script>alert('Please check the credentials...')</script>";
        echo "<script>window.location.href='index.html'</script>";
    }
}
?>