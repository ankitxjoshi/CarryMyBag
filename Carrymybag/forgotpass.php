<?php
  require_once 'include/DB_Functions.php';
  $db = new DB_Functions();
  $forgotpassword = htmlentities($_POST['forgotpassword']);
  $randomcode = $db->random_string();
  $hash = $db->hashSSHA($randomcode);
  $encrypted_password = $hash["encrypted"]; // encrypted password
  $salt = $hash["salt"];
  $subject = "Password Recovery";
  $message = "Hello User,\n\nYour Password is sucessfully changed. Your new Password is $randomcode . Login with your new Password and change it in the User Panel.\n\nRegards,\nCarryMyBag Team.";
  $from = "contact@carrmybag.com";
  $headers = "From:" . $from;
  if ($db->isUserExisted($forgotpassword))
  {

     $user = $db->forgotPassword($forgotpassword, $encrypted_password, $salt);
     if ($user)
     {
           $response["success"] = 1;
           mail($forgotpassword,$subject,$message,$headers);
           echo json_encode($response);
     }
     else
     {
         $response["error"] = 1;
        echo json_encode($response);
      }


            // user is already existed - error response
  }
  else {

        $response["error"] = 2;
        $response["error_msg"] = "User not exist";
       echo json_encode($response);

      }


?>
