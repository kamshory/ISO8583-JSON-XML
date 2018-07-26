# ISO8583-JSON-XML
This library is used to convert messages from one format to another. The supported formats are ISO 8583, JSON, and XML.

Beginner programmers often experience difficulties when parsing and building data with ISO 8583 format. Most programmers are more familiar with human readable formats such as JSON and XML. On the other hand, however, the company requires the ISO 8583 format because some or all of the partners use that format.

Using this library, both senior programmers and novice programmers will be easier to build applications that use the ISO 8583 message format.

Example

```
Demonstration of conversion of ISO 8583 - JSON - XML
Config : 
[{"field":"2","format":"%-6s","variable":"pan","options":"","field_length":"6","type":"LLVAR"},{"field":"3","format":"%06d","variable":"processing_code","options":"","field_length":"6","type":"NUMERIC"},{"field":"4","format":"%012d","variable":"amount","options":"","field_length":"12","type":"NUMERIC"},{"field":"7","format":"%-10s","variable":"transmission_date_time","options":"","field_length":"10","type":"STRING"},{"field":"11","format":"%06d","variable":"stan","options":"","field_length":"6","type":"NUMERIC"},{"field":"12","format":"%-6s","variable":"local_time","options":"","field_length":"6","type":"STRING"},{"field":"13","format":"%-4s","variable":"local_date","options":"","field_length":"4","type":"STRING"},{"field":"15","format":"%-4s","variable":"settlement_date","options":"","field_length":"4","type":"STRING"},{"field":"18","format":"%04d","variable":"merchant_type","options":"","field_length":"4","type":"NUMERIC"},{"field":"32","format":"%-3s","variable":"acq_institution_code","options":"","field_length":"3","type":"LLVAR"},{"field":"37","format":"%012d","variable":"reference_number","options":"","field_length":"12","type":"NUMERIC"},{"field":"41","format":"%-8s","variable":"card_acceptor_terminal","options":"","field_length":"8","type":"STRING"},{"field":"42","format":"%15s","variable":"acceptor_identification_code","options":"","field_length":"15","type":"STRING"},{"field":"48","format":"%-7s%011d%012d%01d%-32s%-32s%-25s%4s%-9s","variable":"switcher_id, meter_id, customer_id, id_selector, pln_reference_number, ba_reference_number, customer_name, tariff, ceiling","options":"","field_length":"133","type":"LLLVAR"},{"field":"49","format":"%03d","variable":"transaction_currency_code","options":"","field_length":"3","type":"NUMERIC"}]
=============================================================
Original ISO 8583 : 
'0210723A400108C1800006364235361000000000050000072751515134657351515107270728678903555232442364723376832  234822         1339876   7183712217171837122171212462348242265823582523523547223323582375872385728357823758235235KAMSHORY, MT               R51350     360'
Convert ISO to JSON
JSON : 
{"ceiling":"1350     ","amount":"50000","settlement_date":"0728","switcher_id":"9876   ","ba_reference_number":"23582375872385728357823758235235","processing_code":"361000","transaction_currency_code":"360","id_selector":"1","reference_number":"232442364723","local_date":"0727","pln_reference_number":"24623482422658235825235235472233","local_time":"515151","transmission_date_time":"0727515151","stan":"346573","acq_institution_code":"555","card_acceptor_terminal":"376832  ","tariff":"  R5","acceptor_identification_code":"234822         ","customer_name":"KAMSHORY, MT             ","pan":"364235","customer_id":"718371221712","meter_id":"71837122171","merchant_type":"6789"}
=============================================================
Now, convert JSON to new ISO
New ISO 8583 : 
'0210723A400108C1800006364235361000000000050000072751515134657351515107270728678903555232442364723376832  234822         1339876   7183712217171837122171212462348242265823582523523547223323582375872385728357823758235235KAMSHORY, MT               R51350     360'
=============================================================
Now, convert JSON to XML
XML : 
<data>
	<ceiling>1350     </ceiling>
	<amount>50000</amount>
	<settlement_date>0728</settlement_date>
	<switcher_id>9876   </switcher_id>
	<ba_reference_number>23582375872385728357823758235235</ba_reference_number>
	<processing_code>361000</processing_code>
	<transaction_currency_code>360</transaction_currency_code>
	<id_selector>1</id_selector>
	<reference_number>232442364723</reference_number>
	<local_date>0727</local_date>
	<pln_reference_number>24623482422658235825235235472233</pln_reference_number>
	<local_time>515151</local_time>
	<transmission_date_time>0727515151</transmission_date_time>
	<stan>346573</stan>
	<acq_institution_code>555</acq_institution_code>
	<card_acceptor_terminal>376832  </card_acceptor_terminal>
	<tariff>  R5</tariff>
	<acceptor_identification_code>234822         </acceptor_identification_code>
	<customer_name>KAMSHORY, MT             </customer_name>
	<pan>364235</pan>
	<customer_id>718371221712</customer_id>
	<meter_id>71837122171</meter_id>
	<merchant_type>6789</merchant_type>
</data>
=============================================================
```
