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
