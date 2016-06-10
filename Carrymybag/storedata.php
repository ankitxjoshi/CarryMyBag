<?php



require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array

if (isset($_POST['qtySmall']) && isset($_POST['qtyMed']) && isset($_POST['qtyLarge']) && isset($_POST['priceSmall']) && isset($_POST['priceMed']) && isset($_POST['priceLarge'])) {

    // receiving the post params
    $qtySmall = $_POST['qtySmall'];
    $qtyMed = $_POST['qtyMed'];
    $qtyLarge = $_POST['qtyLarge'];
    $priceSmall = $_POST['priceSmall'];
    $priceMed = $_POST['priceMed'];
    $priceLarge = $_POST['priceLarge'];

    $success = $db->storeData($qtySmall,$qtyMed,$qtyLarge,$priceSmall,$priceMed,$priceLarge);
      if ($success) {
          // user stored successfully
          echo "Successfully Registered";

      } else {
          // user failed to store
          echo "Registeration Failed";
      }
}

?>
