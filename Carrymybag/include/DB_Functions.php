<?php
class DB_Functions {

    private $conn;

    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }

    // destructor
    function __destruct() {

    }

    public function get_result( $Statement ) {
    $RESULT = array();
    $Statement->store_result();
    for ( $i = 0; $i < $Statement->num_rows; $i++ ) {
        $Metadata = $Statement->result_metadata();
        $PARAMS = array();
        while ( $Field = $Metadata->fetch_field() ) {
            $PARAMS[] = &$RESULT[ $i ][ $Field->name ];
        }
        call_user_func_array( array( $Statement, 'bind_result' ), $PARAMS );
        $Statement->fetch();
    }
    return $RESULT;
}

    public function random_string()
	{
	    $character_set_array = array();
	    $character_set_array[] = array('count' => 7, 'characters' => 'abcdefghijklmnopqrstuvwxyz');
	    $character_set_array[] = array('count' => 1, 'characters' => '0123456789');
	    $temp_array = array();
	    foreach ($character_set_array as $character_set) {
	        for ($i = 0; $i < $character_set['count']; $i++) {
	            $temp_array[] = $character_set['characters'][rand(0, strlen($character_set['characters']) - 1)];
	        }
	    }
	    shuffle($temp_array);
	    return implode('', $temp_array);
	}

	public function forgotPassword($forgotpassword, $newpassword, $salt)
	{
    $stmt = $this->conn->prepare("UPDATE `users` SET `encrypted_password` = '$newpassword',`salt` = '$salt' WHERE `email` = '$forgotpassword'");

    $result = $stmt->execute();
		if ($result)
		 {
			return true;
		 }
	    else
	    {
			return false;
	    }

	}

    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $email, $password) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt

        $stmt = $this->conn->prepare("INSERT INTO users(unique_id, name, email, encrypted_password, salt, created_at) VALUES(?, ?, ?, ?, ?, NOW())");
        $stmt->bind_param("sssss", $uuid, $name, $email, $encrypted_password, $salt);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $user = $this->get_result($stmt);
            $stmt->close();

            return $user;
        } else {
            return false;
        }
    }

    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {

        
        $sql = "SELECT * FROM users WHERE email = '$email'";
        $result = $this->conn->query($sql);
        if ($result->num_rows > 0) {
        	while($row = $result->fetch_assoc()) {
        		$salt = $row['salt'];
            	$encrypted_password = $row['encrypted_password'];

            	$hash =$this->checkhashSSHA($salt, $password);
            // check for password equality
            	if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $row;
            	}
        
    		}
        	
            
            
        } 
        else {
            return NULL;
        }
    }

    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $stmt = $this->conn->prepare("SELECT email from users WHERE email = ?");

        $stmt->bind_param("s", $email);

        $stmt->execute();

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // user existed
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }

    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {

        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {

        $hash = base64_encode(sha1($password . $salt, true) . $salt);

        return $hash;
    }

    
    public function getCityList()
    {
      $stmt = $this->conn->prepare("SELECT City_Name from citylist");
      if($stmt->execute())
      {
        $result = $this->get_result($stmt); 
      }

        $stmt->close();
        return $result;
      }

      public function storeData($qtySmall,$qtyMed,$qtyLarge,$priceSmall,$priceMed,$priceLarge,$addr_pickup,$addr_dest,$userId,$pickUp,$dropDown) {




          $query = "INSERT INTO orders(qtySmall,qtyMed,qtyLarge,priceSmall,priceMed,priceLarge,pickaddr,destaddr,userId,pickUp)VALUES('$qtySmall','$qtyMed','$qtyLarge','$priceSmall','$priceMed','$priceLarge','$addr_pickup','$addr_dest','$userId','$pickUp')";
          $stmt = $this->conn->prepare($query);
          $result = $stmt->execute();
          $stmt->close();
          if ($result) {
              return true;
          }
          else {
              return false;
          }
      }
}
?>
