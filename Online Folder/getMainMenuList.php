<?php

error_reporting(E_ALL ^ E_DEPRECATED);
$con = $con = mysql_connect("localhost","root","");

if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }
mysql_select_db("lifeguru", $con);

$result = mysql_query("SELECT * FROM mainmenu");

while($row = mysql_fetch_assoc($result))
  {
	$output[]=$row;
  }

print(json_encode($output));

mysql_close($con);


?>