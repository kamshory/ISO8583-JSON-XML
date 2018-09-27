package test;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bgw.translator.MessageTranslator;

public class Test {
	public static void main(String[] args) throws IOException {
		String configStr = "{\"f41\":{\"format\":\"%-8s\", \"variable\":\"card_acceptor_terminal\", \"options\":\"\", \"field_length\":\"8\", \"type\":\"STRING\"},\"f63\":{\"format\":\"%-32s%-30s%-50s%-18s\", \"variable\":\"locket_code, locket_name, locket_address, locket_phone\", \"options\":\"\", \"field_length\":\"130\", \"type\":\"LLLVAR\"},\"f32\":{\"format\":\"%-11s\", \"variable\":\"acq_institution_code\", \"options\":\"\", \"field_length\":\"11\", \"type\":\"LLVAR\"},\"f42\":{\"format\":\"%15s\", \"variable\":\"acceptor_identification_code\", \"options\":\"\", \"field_length\":\"15\", \"type\":\"STRING\"},\"f121\":{\"format\":\"%-32s\", \"variable\":\"payment_reference\", \"options\":\"\", \"field_length\":\"32\", \"type\":\"LLLVAR\"},\"f12\":{\"format\":\"%-6s\", \"variable\":\"local_time\", \"options\":\"\", \"field_length\":\"6\", \"type\":\"STRING\"},\"f120\":{\"format\":\"%-20s\", \"variable\":\"product_code\", \"options\":\"\", \"field_length\":\"20\", \"type\":\"LLLVAR\"},\"f11\":{\"format\":\"%06d\", \"variable\":\"stan\", \"options\":\"\", \"field_length\":\"6\", \"type\":\"NUMERIC\"},\"f33\":{\"format\":\"%-11s\", \"variable\":\"fwd_institution_code\", \"options\":\"\", \"field_length\":\"11\", \"type\":\"LLVAR\"},\"f13\":{\"format\":\"%-4s\", \"variable\":\"local_date\", \"options\":\"\", \"field_length\":\"4\", \"type\":\"STRING\"},\"f49\":{\"format\":\"%03d\", \"variable\":\"transaction_currency_code\", \"options\":\"\", \"field_length\":\"3\", \"type\":\"NUMERIC\"},\"f15\":{\"format\":\"%-4s\", \"variable\":\"settlement_date\", \"options\":\"\", \"field_length\":\"4\", \"type\":\"STRING\"},\"f37\":{\"format\":\"%012d\", \"variable\":\"reference_number\", \"options\":\"\", \"field_length\":\"12\", \"type\":\"NUMERIC\"},\"f48\":{\"format\":\"%11s%12s%01d\", \"variable\":\"meter_id, customer_id, id_selector\", \"options\":\"\", \"field_length\":\"24\", \"type\":\"LLLVAR\"},\"f2\":{\"format\":\"%-19s\", \"variable\":\"pan\", \"options\":\"\", \"field_length\":\"19\", \"type\":\"LLVAR\"},\"f18\":{\"format\":\"%04d\", \"variable\":\"merchant_type\", \"options\":\"\", \"field_length\":\"4\", \"type\":\"NUMERIC\"},\"f3\":{\"format\":\"%06d\", \"variable\":\"processing_code\", \"options\":\"\", \"field_length\":\"6\", \"type\":\"NUMERIC\"},\"f4\":{\"format\":\"%012d\", \"variable\":\"amount\", \"options\":\"\", \"field_length\":\"12\", \"type\":\"NUMERIC\"},\"f7\":{\"format\":\"%-10s\", \"variable\":\"transmission_date_time\", \"options\":\"\", \"field_length\":\"10\", \"type\":\"STRING\"},\"f127\":{\"format\":\"%-20s%-32s\", \"variable\":\"username, password\", \"options\":\"\", \"field_length\":\"52\", \"type\":\"LLLVAR\"}}";
		
		MessageTranslator mt = new MessageTranslator();
		String mti_id = "0210";
		String iso = "0200F23A400188C180060000000000000182196048200000002731   300000000000020000092513425200007213425209250926602111597        1112345      000000000072DEVALT0120090010080000014514987654321149999999911030E8597E3B2F1646505FDD6E210000090MUP210ZBE957561167FCD8506326E4AHAMDANIE LESTALUHUANI    R1  00000090000000090000000011653600505151106123            0600000000000000000000000000130                                ALTO                          Jalan Anggrek Neli Murni                          02199999          02000500050001         03214987654321                     052tester1             tester1                         ";
		String iso_new = "";
		String xml = "";
		JSONObject config = new JSONObject();
		JSONObject json = new JSONObject();
	
		
		try 
		{
			config = new JSONObject(configStr);
			json = mt.parseISO8583(iso, config);
			iso_new = new String(mt.buildISO8583(json, config, mti_id));
			xml = mt.buildXML(json, "data");
			
			System.out.println("Demonstration of conversion of ISO 8583 - JSON - XML");
			System.out.println("Config : ");
			System.out.println(config.toString());
			System.out.println("=============================================================");
			
			System.out.println("Original ISO 8583 : ");
			System.out.println("'"+iso+"'");
			System.out.println("Convert ISO to JSON");
			System.out.println("JSON : ");
			System.out.println(json.toString());
			System.out.println("=============================================================");
			
			System.out.println("Now, convert JSON to new ISO");
			System.out.println("New ISO 8583 : ");
			System.out.println("'"+iso_new+"'");
			System.out.println("=============================================================");
			
			System.out.println("Now, convert JSON to XML");
			System.out.println("XML : ");
			System.out.println(xml);
			System.out.println("=============================================================");
			
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}
}
