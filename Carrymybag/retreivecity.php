<?php
  require_once 'include/DB_Functions.php';
  $db = new DB_Functions();
  $list = $db->getCityList();
  echo json_encode(array("result"=>$list));

?>
