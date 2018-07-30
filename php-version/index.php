<?php
include "lib/MessageTranslator.php";
$config = '[{"field":"2","format":"%-6s","variable":"pan","options":"","type":"LLVAR","field_length":"6"},{"field":"3","format":"%06d","variable":"processing_code","options":"","type":"NUMERIC","field_length":"6"},{"field":"4","format":"%012d","variable":"amount","options":"","type":"NUMERIC","field_length":"12"},{"field":"7","format":"%-10s","variable":"transmission_date_time","options":"","type":"STRING","field_length":"10"},{"field":"11","format":"%06d","variable":"stan","options":"","type":"NUMERIC","field_length":"6"},{"field":"12","format":"%-6s","variable":"local_time","options":"","type":"STRING","field_length":"6"},{"field":"13","format":"%-4s","variable":"local_date","options":"","type":"STRING","field_length":"4"},{"field":"15","format":"%-4s","variable":"settlement_date","options":"","type":"STRING","field_length":"4"},{"field":"18","format":"%04d","variable":"merchant_type","options":"","type":"NUMERIC","field_length":"4"},{"field":"32","format":"%-3s","variable":"acq_institution_code","options":"","type":"LLVAR","field_length":"3"},{"field":"37","format":"%012d","variable":"reference_number","options":"","type":"NUMERIC","field_length":"12"},{"field":"41","format":"%-8s","variable":"card_acceptor_terminal","options":"","type":"STRING","field_length":"8"},{"field":"42","format":"%15s","variable":"acceptor_identification_code","options":"","type":"STRING","field_length":"15"},{"field":"48","format":"%-7s%011d%012d%01d%-32s%-32s%-25s%4s%-9s","variable":"switcher_id, meter_id, customer_id, id_selector, pln_reference_number, ba_reference_number, customer_name, tariff, ceiling","options":"","type":"LLLVAR","field_length":"133"},{"field":"49","format":"%03d","variable":"transaction_currency_code","options":"","type":"NUMERIC","field_length":"3"}]';
?><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Test</title>
</head>

<body>
<?php
$message1 = '0210723A400108C1800006053502380000000000050000072716540600095616540607270728601703008000000000956DEVALT01200900100800000133JTL53L3021474836470021474836470B442A6A890AF4000956FA2D175B64F7115F12A23556F457089008D648D23A4D3AMRAN JAIKAMALANISYAH    R1  000001300360';
$translator = new MessageTranslator();
$json = $translator->parseISO($message1, $config);

$message2 = $translator->buildISO($json, null, '0210');
?>

<pre>
Original ISO :
<?php
echo "'".$message1."'\r\n";
?>
</pre>
<p>Parsed Into JSON</p>
<pre>
JSON : 
<?php
print_r($json);
?>
</pre>
<p>Converted Into ISO</p>
<pre>
New ISO :
<?php
echo "'".$message2."'\r\n";
?>
</pre>
</body>
</html>