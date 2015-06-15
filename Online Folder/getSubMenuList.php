<?php

error_reporting(E_ALL ^ E_DEPRECATED);

	if(isset($_REQUEST['subject']))
	{
		$con = mysql_connect("localhost","root","");
		if (!$con)
		{
			die('Could not connect: ' . mysql_error());
		}
		
		mysql_select_db("lifeguru", $con);
		
		$subject = $_REQUEST['subject'];
		$result = mysql_query("SELECT * FROM $subject") or die("Errant query");
		while($row = mysql_fetch_assoc($result))
		{
			$output[]=$row;
		}
		print(json_encode($output));

		mysql_close($con);
	}
	else
	{
		$output = "not found";
		print(json_encode($output));
	}

?>