<?php

require_once 'DbConnect.php';

$response = array();

if (isset($_GET['apicall']))
{
    switch ($_GET['apicall'])
    {
        case 'signup':

            if (isTheseParametersAvailable(array('user_name','dob','hname','place','pin','mobile','email','password')))
            {
                $user_name = $_POST['user_name'];
                $user_dob = $_POST['dob'];
                $user_hname = $_POST['hname'];
                $user_place = $_POST['place'];
                $user_pin = $_POST['pin'];
                $user_mobile = $_POST['mobile'];
                $user_email = $_POST['email'];
                $user_pass = $_POST['password'];
                $log_role = "USER";

                $stmt = $conn->prepare("SELECT * from login where mobile = ?");
                $stmt->bind_param("s",$user_mobile);
                $stmt->execute();
                $stmt->store_result();

                if ($stmt->num_rows > 0)
                {
                    $response['error'] = true;
                    $response['message'] = 'User Already Registered';
                    $stmt->close();
                }
                else
                {
                    $stmt = $conn->prepare("INSERT INTO users (user_name,dob,h_name,place,pin,mobile,email) VALUES (?,?,?,?,?,?,?)");
                    $stmt->bind_param("sssssss",$user_name,$user_dob,$user_hname,$user_place,$user_pin,$user_mobile,$user_email);
                    $stmt1 = $conn->prepare("INSERT INTO login (mobile,password,l_role) VALUES (?,?,?)");
                    $stmt1->bind_param("sss",$user_mobile,$user_pass,$log_role);


                    if ($stmt->execute() && $stmt1->execute() )
                    {
                        $stmt = $conn->prepare("SELECT user_id, user_name, dob, h_name, place , pin, mobile, email from users where mobile=?" );
                        $stmt->bind_param("s",$user_mobile);
                        $stmt->execute();
                        $stmt->bind_result($user_id,$user_name, $user_dob, $user_hname,  $user_place, $user_pin, $user_mobile, $user_email);
                        $stmt->fetch();

                        $user = array
                        (
                            'user_id'=>$user_id,
                            'user_name'=>$user_name,
                            'user_dob'=>$user_dob,
                            'user_hname'=>$user_hname,
                            'user_place'=>$user_place,
                            'user_pin'=>$user_pin,
                            'user_mobile'=>$user_mobile,
                            'user_email'=>$user_email
                        );

                        $stmt->close();

                        $response['error']= false;
                        $response['message'] = 'User registered Successfully';
                        $response['user'] = $user;
                    }
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';

            }
        break;

        case "add_loc":

            if (isTheseParametersAvailable(array('user_loc','logged_user')))
            {

                $user_location = $_POST['user_loc'];
                $logged_user_id = $_POST['logged_user'];

                $stmt2 = $conn->prepare("UPDATE users SET location=? where user_id=?");
                $stmt2->bind_param("ss",$user_location,$logged_user_id);
                if ($stmt2->execute())
                {
                    $stmt2 = $conn->prepare("select user_id,user_name,dob,h_name,place,pin,mobile,email,location from users where user_id=?");
                    $stmt2->bind_param("s",$logged_user_id);
                    $stmt2->execute();
                    $stmt2->bind_result($user_id,$user_name,$user_dob,$user_hname,$user_place,$user_pin,$user_mobile,$user_email,$user_location);
                    $stmt2->fetch();

                    $user = array
                    (
                        'user_id'=>$user_id,
                        'user_name'=>$user_name,
                        'user_dob'=>$user_dob,
                        'user_hname'=>$user_hname,
                        'user_place'=>$user_place,
                        'user_pin'=>$user_pin,
                        'user_mobile'=>$user_mobile,
                        'user_email'=>$user_email,
                        'user_location'=>$user_location
                    );

                    $stmt2->close();

                }
                $response['error'] = false;
                $response['message'] = 'Location Added Successfully';
                $response['user'] = $user;
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "book_test":

            if (isTheseParametersAvailable(array('logged_user','doc_name','tr_date')))
            {
                $logged_user_id2 = $_POST['logged_user'];
                $doctor_name = $_POST['doc_name'];
                $testreq_date = $_POST['tr_date'];

                $stmt3 = $conn->prepare("SELECT * FROM test_request where user_id=? and status=1 or status<>5");
                $stmt3->bind_param("s",$logged_user_id2);
                $stmt3->execute();
                $stmt3->store_result();
                if ($stmt3->num_rows > 0 )
                {
                    $response['error'] = true;
                    $response['message'] = 'User already booked a test';
                    $stmt3->close();
                }
                else
                {
                    $stmt3 = $conn->prepare("INSERT INTO test_request(user_id,doc_name,tr_date) VALUES (?,?,?)");
                    $stmt3->bind_param("sss",$logged_user_id2,$doctor_name,$testreq_date);
                    if ($stmt3->execute())
                    {
                        $response['error'] = false;
                        $response['message'] = 'Booked Test Successfully';
                    }
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }

        break;

        case "get_specimen":

            $stmt4 = $conn->prepare("select distinct specimen from test");
            $stmt4->execute();
            $stmt4->bind_result($specimen);
            $tspecimen = array();

            while ($stmt4->fetch())
            {
                $temp = array();
                $temp = $specimen;
                array_push($tspecimen,$temp);
            }

            if (sizeof($tspecimen) != null)
            {
                $response['error'] = false;
                $response['message'] = 'Recieved specimen list ';
                $response['specimen'] = $tspecimen;
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'Problems in fetching';
            }

        break;

        case "add_user_test":

            if(isTheseParametersAvailable(array('user_test','user_id','doctor_name','tr_date')))
            {
                $user_test = $_POST['user_test'];
                $logged_user_id3 = $_POST['user_id'];
                $doc_name = $_POST['doctor_name'];
                $req_date = $_POST['tr_date'];
                $status = 1;

                $stmt5 = $conn->prepare("insert into test_request(user_id,doc_name,tr_date,user_test,status) values (?,?,?,?,?)");
                $stmt5->bind_param("sssss", $logged_user_id3,$doc_name,$req_date,$user_test,$status);
                if ($stmt5->execute())
                {
                    $response['error'] = false;
                    $response['message'] = 'Added test.';
                }
                else
                {
                    $response['error'] = true;
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }

        break;

        case "check_subtests":

            if (isTheseParametersAvailable(array('specimen','test')))
            {
                $test_name = $_POST['test'];
                $test_specimen = $_POST['specimen'];
                $stmt6 = $conn->prepare("select count(*) from subtests where test_id = (select test_id from test where test_name=? and specimen=?)");
                $stmt6->bind_param("ss",$test_name,$test_specimen);
                $stmt6->bind_result($sub_count);
                if ($stmt6->execute())
                {
                    while ($stmt6->fetch())
                    {
                        if ($sub_count>0)
                        {
                            $response['subtests'] = true;
                            $response['subtests_count'] = $sub_count;
                        }
                        else
                        {
                            $response['subtests'] = false;
                        }
                    }
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "add_subtest_result":

            if (isTheseParametersAvailable(array('subname','st_value','test_name','test_specimen','tester_id','tester_id','subtest_num')))
            {
                $subtest_name = $_POST['subname'];
                $svalue = $_POST['st_value'];
                $specimen = $_POST['test_specimen'];
                $tname = $_POST['test_name'];
                $tester = $_POST['tester_id'];
                $user_id1 = $_POST['user_id'];
                $subtest_no = $_POST['subtest_num'];

                switch ($subtest_no)
                {
                    case 1:
                        $field_name = "observ_1";
                        $first_insert = true;
                        break;

                    case 2 :
                        $field_name = "observ_2";
                        break;

                    case 3:
                        $field_name = "observ_3";
                        break;

                    case 4:
                        $field_name = "observ_4";
                        break;

                    case 5:
                        $field_name = "observ_5";
                        break;

                    case 6:
                        $field_name = "observ_6";
                        break;

                    case 7:
                        $field_name = "observ_7";
                        break;

                    case 8:
                        $field_name = "observ_8";
                        break;

                    case 9:
                        $field_name = "observ_9";
                        break;

                    case 10:
                        $field_name = "observ_10";
                        break;

                    default:
                        $response['error'] = true;
                }

                $stmt11 = "select * from results where asgn_test_id in (select ass_id from assigned_test where test_id in (select test_id from test where test_name='$tname' and specimen='$specimen') and (select tr_id from test_request where tester_id='$tester' and user_id='$user_id1' and status=4))";
                $res_stmt11 = mysqli_query($conn, $stmt11);
                if ($res_stmt11)
                {
                    if (mysqli_num_rows($res_stmt11)>0)
                    {
                        $stmt12 = "select sub_id from subtests where sub_name='$subtest_name' and test_id in (select test_id from test where test_name='$tname' and specimen='$specimen')";
                        $res_stmt12 = mysqli_query($conn,$stmt12);
                        if ($res_stmt12)
                        {
                            while ($row_stmt12 = mysqli_fetch_array($res_stmt12))
                            {
                                $subtest_id = $row_stmt12[0];
                                $stmt13 = "insert into observation(observ_value,subtest_id) values ('$svalue','$subtest_id')";
                                $res_stmt13 = mysqli_query($conn,$stmt13);
                                if ($stmt13)
                                {
                                    $stmt14 = "select LAST_INSERT_ID()";
                                    $res_stmt14 = mysqli_query($conn,$stmt14);
                                    while ($row_stmt14 = mysqli_fetch_array($res_stmt14))
                                    {
                                        $obser_id = $row_stmt14[0];
                                        $stmt15 = "update results set $field_name = '$obser_id' where asgn_test_id in (select ass_id from assigned_test where test_id in (select test_id from test where test_name='$tname' and specimen='$specimen') and (select tr_id from test_request where tester_id='$tester' and user_id='$user_id1' and status=4))";
                                        $res_stmt15 = mysqli_query($conn,$stmt15);
                                        if ($res_stmt15)
                                        {
                                            $response['error'] = false;
                                            $response['message'] = "Result added";
                                        }
                                        else
                                        {
                                            $response['error'] = true;
                                            $response['message'] = "Cannot add result..";
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        if ($first_insert == true)
                        {
                            $stmt9 = "select sub_id from subtests where sub_name='$subtest_name' and test_id in (select test_id from test where test_name='$tname' and specimen='$specimen')";
                            $res_stmt9 = mysqli_query($conn,$stmt9);
                            if ($res_stmt9)
                            {
                                while ($row_stmt9 = mysqli_fetch_array($res_stmt9))
                                {
                                    $subtest_id = $row_stmt9[0];
                                    $stmt7 = "insert into observation(observ_value,subtest_id) values ('$svalue','$subtest_id')";
                                    $res_stmt7 = mysqli_query($conn,$stmt7);
                                    if ($res_stmt7)
                                    {
                                        $stmt8 = "select LAST_INSERT_ID()";
                                        $res_stmt8 = mysqli_query($conn,$stmt8);
                                        if ($res_stmt8)
                                        {
                                            while($row_stmt8 = mysqli_fetch_array($res_stmt8))
                                            {
                                                $obser_id = $row_stmt8[0];
                                                $stmt10  = "insert into results(asgn_test_id, observ_1) values ((select ass_id from assigned_test where test_id in (select test_id from test where test_name='$tname' and specimen='$specimen') and (select tr_id from test_request where tester_id='$tester' and user_id='$user_id1' and status=4)),$obser_id)";
                                                $res_stmt10  = mysqli_query($conn, $stmt10);
                                                if ($res_stmt10)
                                                {
                                                    $response['error'] = false;
                                                    $response['message'] = "Result added";
                                                    $first_insert = false;
                                                }
                                                else
                                                {
                                                    $response['error'] = true;
                                                    $response['message'] = "find it";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = "Some error occurred..";
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }

        break;

        case "test_result":

        if (isTheseParametersAvailable(array('test_name','test_specimen','tester_id','user_id','t_value')))
        {
            $test_name2 = $_POST['test_name'];
            $test_specimen2 = $_POST['test_specimen'];
            $tester_id = $_POST['tester_id'];
            $user_id2 = $_POST['user_id'];
            $test_value = $_POST['t_value'];

            $stmt16 = "select * from results where asgn_test_id in (select ass_id from assigned_test where test_id in (select test_id from test where test_name='$test_name2' and specimen='$test_specimen2') and (select tr_id from test_request where tester_id='$tester_id' and user_id='$user_id2' and status=4))";
            $res_stmt16 = mysqli_query($conn,$stmt16);
            if ($res_stmt16)
            {
                if (mysqli_num_rows($res_stmt16) > 0)
                {
                    $response['error'] = true;
                    $response['message'] = "Result already added.";
                }
                else
                {
                    $stmt17 = "insert into observation(observ_value) values ('$test_value')";
                    $res_stmt17 = mysqli_query($conn,$stmt17);
                    if ($res_stmt17)
                    {
                        $stmt18 = "select LAST_INSERT_ID()";
                        $res_stmt18 = mysqli_query($conn,$stmt18);
                        if ($res_stmt18)
                        {
                            while ($row_stmt18 = mysqli_fetch_array($res_stmt18))
                            {
                                $observation_id = $row_stmt18[0];
                                $stmt19 = "insert into results(asgn_test_id,observ_1) values((select ass_id from assigned_test where test_id in (select test_id from test where test_name='$test_name2' and specimen ='$test_specimen2') and testreq_id in (select tr_id from test_request where tester_id='$tester_id' and user_id='$user_id2' and status=4)),$observation_id)";
                                $res_stmt19 = mysqli_query($conn,$stmt19);
                                if ($res_stmt19)
                                {
                                    $response['error'] = false;
                                    $response['message'] = "Successfully added result";
                                }
                                else
                                {
                                    $response['error'] = true;
                                    $response['message'] = "Error occurred in insertion";
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            $response['error'] = true;
            $response['message'] = 'required parameters are not available';
        }

        break;

        case "check_status":

            if (isTheseParametersAvailable(array('user_id','tester_id')))
            {
                $tester_id2 = $_POST['tester_id'];
                $user_id3 = $_POST['user_id'];

                $stmt20 = "select pay_stat,status from test_request where user_id='$user_id3' and tester_id='$tester_id2'";
                $res_stmt20 = mysqli_query($conn,$stmt20);
                if ($res_stmt20)
                {
                    while ($row_stmt20 = mysqli_fetch_array($res_stmt20))
                    {
                        $response['error'] = false;
                        $response['pay_stat'] = $row_stmt20[0];
                        $response['tr_status'] = $row_stmt20[1];
                    }
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = "failed to get status";
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }

        break;

        case "update_status":

            if (isTheseParametersAvailable(array('user_id','tester_id','payment','tr_stat')))
            {
                $user_id4 = $_POST['user_id'];
                $tester_id3 = $_POST['tester_id'];
                $payment = $_POST['payment'];
                $tr_status = $_POST['tr_stat'];

                $stmt21 = "update test_request set pay_stat='$payment',status='$tr_status' where user_id='$user_id4' and tester_id='$tester_id3' and status<>5";
                $res_stmt21 = mysqli_query($conn,$stmt21);
                if ($res_stmt21)
                {
                    $response['error'] = false;
                    $response['message'] = "updated status successfully...";
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = "failed to update status";
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "user_login":

            if (isTheseParametersAvailable(array('mobile','password'))) {
                $mobile = $_POST['mobile'];
                $password = $_POST['password'];
                $stmt = $conn->prepare("SELECT * from login where l_role='USER' and mobile=? and password=?");
                $stmt->bind_param("ss", $mobile, $password);
                $stmt->execute();
                $stmt->store_result();
                if ($stmt->num_rows > 0) {
                    $stmt1 = $conn->prepare("select user_id,user_name,dob,h_name,place,pin,mobile,email,location from users where mobile=?");
                    $stmt1->bind_param("s", $mobile);
                    $stmt1->execute();
                    $stmt1->bind_result($user_id, $user_name, $dob, $h_name, $place, $pin, $mobile, $email, $location);
                    $stmt1->fetch();

                    $USER = array
                    (
                        'user_id' => $user_id,
                        'user_name' => $user_name,
                        'dob' => $dob,
                        'h_name' => $h_name,
                        'place' => $place,
                        'pin' => $pin,
                        'mobile' => $mobile,
                        'email' => $email,
                        'location' => $location
                    );

                    $response['error'] = false;
                    $response['message'] = 'Succesfully logged in';
                    $response['USER'] = $USER;
                    $stmt1->close();
                    $stmt->close();
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = 'No user found';
                    $stmt->close();
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "tester_login":

            if (isTheseParametersAvailable(array('mobile','password'))) {
                $mobile = $_POST['mobile'];
                $password = $_POST['password'];
                $stmt = $conn->prepare("SELECT * from login where l_role='TESTER' and mobile=? and password=?");
                $stmt->bind_param("ss", $mobile, $password);
                $stmt->execute();
                $stmt->store_result();
                if ($stmt->num_rows > 0) {
                    $stmt1 = $conn->prepare("select user_id,user_name,dob,h_name,place,pin,mobile,email,location from users where mobile=?");
                    $stmt1->bind_param("s", $mobile);
                    $stmt1->execute();
                    $stmt1->bind_result($user_id, $user_name, $dob, $h_name, $place, $pin, $mobile, $email, $location);
                    $stmt1->fetch();

                    $TESTER = array
                    (
                        'user_id' => $user_id,
                        'user_name' => $user_name,
                        'dob' => $dob,
                        'h_name' => $h_name,
                        'place' => $place,
                        'pin' => $pin,
                        'mobile' => $mobile,
                        'email' => $email,
                        'location' => $location
                    );

                    $response['error'] = false;
                    $response['message'] = 'Succesfully logged in';
                    $response['TESTER'] = $TESTER;
                    $stmt1->close();
                    $stmt->close();
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = 'No tester found';
                    $stmt->close();
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "change_pass":

            if (isTheseParametersAvailable(array('mobile','role','pass','new_pass')))
            {
                $mobile = $_POST['mobile'];
                $log_role = $_POST['role'];
                $pass = $_POST['pass'];
                $new_pass = $_POST['new_pass'];

                $sel_password = "select * from login where mobile='$mobile' and l_role='$log_role' and password='$pass'";
                $res_password = mysqli_query($conn,$sel_password);
                if (mysqli_num_rows($res_password)>0)
                {
                    $sql_pass = "update login set password='$new_pass' where mobile='$mobile' and l_role='$log_role' and password='$pass'";
                    $res_pass = mysqli_query($conn,$sql_pass);
                    if($res_pass)
                    {
                        $response['error'] = false;
                        $response['message'] = "Changed password successfully...";
                    }
                    else
                    {
                        $response['error'] = true;
                        $response['message'] = "error occurred!";
                    }
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = "error occurred!";
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "user_feedback":

            if (isTheseParametersAvailable(array('user_id','tester_name','doctor_name','tr_date','rating_star','feedback')))
            {
                $user_id5 = $_POST['user_id'];
                $tester_name = $_POST['tester_name'];
                $doctor_name1 = $_POST['doctor_name'];
                $tr_date1 = $_POST['tr_date'];
                $rating = $_POST['rating_star'];
                $feedback = $_POST['feedback'];

                $stmt22 = "insert into feedback(tr_id,feedback,rating) values ((select tr_id from test_request where user_id='$user_id5' and status=5 and tr_date='$tr_date1' and doc_name='$doctor_name1' and tester_id=(select user_id from users where user_name='$tester_name')),'$feedback','$rating')";
                $res_stmt22 = mysqli_query($conn,$stmt22);
                if ($res_stmt22 == true)
                {
                    $response['error'] = false;
                    $response['message'] = "Thank you for your feedback!";
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = "Something went wrong!";
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }

        break;

        default:
            $response['error'] = true;
            $response['message'] = 'Invalid operation Called';
    }

}
else
{
    $response['error'] = true;
    $response['message'] = 'Invalid API Call';
}

echo json_encode($response);

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
