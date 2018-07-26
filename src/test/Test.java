package test;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bgw.translator.MessageTranslator;

public class Test {
	public static void main(String[] args) throws IOException {
		String configStr = "[{\"field\":\"2\",\"format\":\"%-6s\",\"variable\":\"pan\",\"options\":\"\",\"type\":\"LLVAR\",\"field_length\":\"6\"},{\"field\":\"3\",\"format\":\"%06d\",\"variable\":\"processing_code\",\"options\":\"\",\"type\":\"NUMERIC\",\"field_length\":\"6\"},{\"field\":\"4\",\"format\":\"%012d\",\"variable\":\"amount\",\"options\":\"\",\"type\":\"NUMERIC\",\"field_length\":\"12\"},{\"field\":\"7\",\"format\":\"%-10s\",\"variable\":\"transmission_date_time\",\"options\":\"\",\"type\":\"STRING\",\"field_length\":\"10\"},{\"field\":\"11\",\"format\":\"%06d\",\"variable\":\"stan\",\"options\":\"\",\"type\":\"NUMERIC\",\"field_length\":\"6\"},{\"field\":\"12\",\"format\":\"%-6s\",\"variable\":\"local_time\",\"options\":\"\",\"type\":\"STRING\",\"field_length\":\"6\"},{\"field\":\"13\",\"format\":\"%-4s\",\"variable\":\"local_date\",\"options\":\"\",\"type\":\"STRING\",\"field_length\":\"4\"},{\"field\":\"15\",\"format\":\"%-4s\",\"variable\":\"settlement_date\",\"options\":\"\",\"type\":\"STRING\",\"field_length\":\"4\"},{\"field\":\"18\",\"format\":\"%04d\",\"variable\":\"merchant_type\",\"options\":\"\",\"type\":\"NUMERIC\",\"field_length\":\"4\"},{\"field\":\"32\",\"format\":\"%-3s\",\"variable\":\"acq_institution_code\",\"options\":\"\",\"type\":\"LLVAR\",\"field_length\":\"3\"},{\"field\":\"37\",\"format\":\"%012d\",\"variable\":\"reference_number\",\"options\":\"\",\"type\":\"NUMERIC\",\"field_length\":\"12\"},{\"field\":\"41\",\"format\":\"%-8s\",\"variable\":\"card_acceptor_terminal\",\"options\":\"\",\"type\":\"STRING\",\"field_length\":\"8\"},{\"field\":\"42\",\"format\":\"%15s\",\"variable\":\"acceptor_identification_code\",\"options\":\"\",\"type\":\"STRING\",\"field_length\":\"15\"},{\"field\":\"48\",\"format\":\"%-7s%011d%012d%01d%-32s%-32s%-25s%4s%-9s\",\"variable\":\"switcher_id, meter_id, customer_id, id_selector, pln_reference_number, ba_reference_number, customer_name, tariff, ceiling\",\"options\":\"\",\"type\":\"LLLVAR\",\"field_length\":\"133\"},{\"field\":\"49\",\"format\":\"%03d\",\"variable\":\"transaction_currency_code\",\"options\":\"\",\"type\":\"NUMERIC\",\"field_length\":\"3\"}]";

		MessageTranslator mt = new MessageTranslator();
		String mti_id = "0210";
		String iso = "0210723A400108C1800006364235361000000000050000072751515134657351515107270728678903555232442364723376832  234822         1339876   7183712217171837122171212462348242265823582523523547223323582375872385728357823758235235KAMSHORY, MT               R51350     360";
		String iso_new = "";
		String xml = "";
		JSONArray config = new JSONArray();
		JSONObject json = new JSONObject();
	
		JSONParser parser = new JSONParser();
		
		try 
		{
			config = (JSONArray) parser.parse(configStr);
			json = mt.parseISO8583(iso, config);
			iso_new = new String(mt.buildISO8583(json, config, mti_id));
			xml = mt.buildXML(json, "data");
			
			System.out.println("Demonstration of conversion of ISO 8583 - JSON - XML");
			System.out.println("Config : ");
			System.out.println(config.toJSONString());
			System.out.println("=============================================================");
			
			System.out.println("Original ISO 8583 : ");
			System.out.println("'"+iso+"'");
			System.out.println("Convert ISO to JSON");
			System.out.println("JSON : ");
			System.out.println(json.toJSONString());
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
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
	}
}
