package com.bgw.translator;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * RoyISO8583 is simple ISO 8583 parser and builder. 
 * @author Kamshory, MT
 *
 */
public class RoyISO8583 
{
	/**
	 * ISO8583 configuration
	 */
	private JSONObject config = new JSONObject();
	/**
	 * General ISO8583 configuration
	 */
	private static JSONObject generalConfig = null;
	/**
	 * Use general ISO8583 configuration. When server receive ISO8583 string message and contains any field that is not configured in specs, it will be parsed using general config of ISO8583.
	 */
	/**
	 * JSONObject of ISO8583 field
	 */
	private JSONObject item = new JSONObject();
	/**
	 * Array of ISO8583 field name
	 */
	public String[] fields = null;
	/**
	 * ISO8583 field name
	 */
	private String fieldStr = "";
	/**
	 * Segment 1 of ISO8583 bitmap
	 */
	private int[] segment1 = new int[2];
	/**
	 * Segment 2 of ISO8583 bitmap
	 */
	private int[] segment2 = new int[2];
	/**
	 * Segment 3 of ISO8583 bitmap
	 */
	private int[] segment3 = new int[2];
	/**
	 * Message Type Indicator
	 */
	private String type = "0200";
	/**
	 * The greatest field number
	 */
	public int maxField = 1;
	/**
	 * Default constructor
	 */
	public RoyISO8583()
	{
		segment1[0] = 0;
		segment1[1] = 0;
		segment2[0] = 0;
		segment2[1] = 0;
		segment3[0] = 0;
		segment3[1] = 0;
		this.item = new JSONObject();
		if(RoyISO8583.generalConfig == null)
		{
			RoyISO8583.parseGeneralConfig();
		}
	}
	/**
	 * Constructor with Message Type Indicator
	 * @param mti_id Message Type Indicator
	 */
	public RoyISO8583(String mti_id)
	{
		segment1[0] = 0;
		segment1[1] = 0;
		segment2[0] = 0;
		segment2[1] = 0;
		segment3[0] = 0;
		segment3[1] = 0;
		this.item = new JSONObject();
		this.setType(mti_id);
		if(RoyISO8583.generalConfig == null)
		{
			RoyISO8583.parseGeneralConfig();
		}
	}
	/**
	 * Constructor with Message Type Indicator and configuration of the specs
	 * @param mti_id Message Type Indicator
	 * @param config Configuration of the specs
	 */
	public RoyISO8583(String mti_id, JSONObject config)
	{
		segment1[0] = 0;
		segment1[1] = 0;
		segment2[0] = 0;
		segment2[1] = 0;
		segment3[0] = 0;
		segment3[1] = 0;
		this.item = new JSONObject();
		this.setType(mti_id);
		this.setConfig(config);
	}
	/**
	 * Get maximum field
	 * @return Maximum field
	 */
	public int getMaxField()
	{
		return this.maxField;
	}
	/**
	 * Create general configuration of ISO 8583
	 */
	public static void parseGeneralConfig()
	{
		try 
		{
			RoyISO8583.generalConfig = new JSONObject("{\"f94\":{\"format\":\"%-7s\",\"variable\":\"F_94\",\"options\":\"\",\"field_length\":7,\"type\":\"STRING\"},\"f93\":{\"format\":\"%05d\",\"variable\":\"F_93\",\"options\":\"\",\"field_length\":5,\"type\":\"NUMERIC\"},\"f96\":{\"format\":\"%-8s\",\"variable\":\"F_96\",\"options\":\"\",\"field_length\":8,\"type\":\"STRING\"},\"f95\":{\"format\":\"%-42s\",\"variable\":\"F_95\",\"options\":\"\",\"field_length\":42,\"type\":\"STRING\"},\"f10\":{\"format\":\"%08d\",\"variable\":\"F_10\",\"options\":\"\",\"field_length\":8,\"type\":\"NUMERIC\"},\"f98\":{\"format\":\"%-25s\",\"variable\":\"F_98\",\"options\":\"\",\"field_length\":25,\"type\":\"STRING\"},\"f97\":{\"format\":\"%-17s\",\"variable\":\"F_97\",\"options\":\"\",\"field_length\":17,\"type\":\"STRING\"},\"f12\":{\"format\":\"%06d\",\"variable\":\"F_12\",\"options\":\"\",\"field_length\":6,\"type\":\"NUMERIC\"},\"f11\":{\"format\":\"%06d\",\"variable\":\"F_11\",\"options\":\"\",\"field_length\":6,\"type\":\"NUMERIC\"},\"f99\":{\"format\":\"%011d\",\"variable\":\"F_99\",\"options\":\"\",\"field_length\":11,\"type\":\"LLVAR\"},\"f14\":{\"format\":\"%04d\",\"variable\":\"F_14\",\"options\":\"\",\"field_length\":4,\"type\":\"NUMERIC\"},\"f13\":{\"format\":\"%04d\",\"variable\":\"F_13\",\"options\":\"\",\"field_length\":4,\"type\":\"NUMERIC\"},\"f16\":{\"format\":\"%04d\",\"variable\":\"F_16\",\"options\":\"\",\"field_length\":4,\"type\":\"NUMERIC\"},\"f15\":{\"format\":\"%04d\",\"variable\":\"F_15\",\"options\":\"\",\"field_length\":4,\"type\":\"NUMERIC\"},\"f18\":{\"format\":\"%04d\",\"variable\":\"F_18\",\"options\":\"\",\"field_length\":4,\"type\":\"NUMERIC\"},\"f17\":{\"format\":\"%04d\",\"variable\":\"F_17\",\"options\":\"\",\"field_length\":4,\"type\":\"NUMERIC\"},\"f19\":{\"format\":\"%03d\",\"variable\":\"F_19\",\"options\":\"\",\"field_length\":3,\"type\":\"NUMERIC\"},\"f126\":{\"format\":\"%-6s\",\"variable\":\"F_126\",\"options\":\"\",\"field_length\":6,\"type\":\"LVAR\"},\"f125\":{\"format\":\"%-50s\",\"variable\":\"F_125\",\"options\":\"\",\"field_length\":50,\"type\":\"LLVAR\"},\"f124\":{\"format\":\"%-255s\",\"variable\":\"F_124\",\"options\":\"\",\"field_length\":255,\"type\":\"LLLVAR\"},\"f123\":{\"format\":\"%-999s\",\"variable\":\"F_123\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f21\":{\"format\":\"%03d\",\"variable\":\"F_21\",\"options\":\"\",\"field_length\":3,\"type\":\"NUMERIC\"},\"f122\":{\"format\":\"%-999s\",\"variable\":\"F_122\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f20\":{\"format\":\"%03d\",\"variable\":\"F_20\",\"options\":\"\",\"field_length\":3,\"type\":\"NUMERIC\"},\"f121\":{\"format\":\"%-999s\",\"variable\":\"F_121\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f23\":{\"format\":\"%03d\",\"variable\":\"F_23\",\"options\":\"\",\"field_length\":3,\"type\":\"NUMERIC\"},\"f120\":{\"format\":\"%-999s\",\"variable\":\"F_120\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f22\":{\"format\":\"%03d\",\"variable\":\"F_22\",\"options\":\"\",\"field_length\":3,\"type\":\"NUMERIC\"},\"f25\":{\"format\":\"%02d\",\"variable\":\"F_25\",\"options\":\"\",\"field_length\":2,\"type\":\"NUMERIC\"},\"f24\":{\"format\":\"%03d\",\"variable\":\"F_24\",\"options\":\"\",\"field_length\":3,\"type\":\"NUMERIC\"},\"f27\":{\"format\":\"%01d\",\"variable\":\"F_27\",\"options\":\"\",\"field_length\":1,\"type\":\"NUMERIC\"},\"f26\":{\"format\":\"%02d\",\"variable\":\"F_26\",\"options\":\"\",\"field_length\":2,\"type\":\"NUMERIC\"},\"f29\":{\"format\":\"%-9s\",\"variable\":\"F_29\",\"options\":\"\",\"field_length\":9,\"type\":\"STRING\"},\"f28\":{\"format\":\"%08d\",\"variable\":\"F_28\",\"options\":\"\",\"field_length\":8,\"type\":\"NUMERIC\"},\"f127\":{\"format\":\"%-999s\",\"variable\":\"F_127\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f30\":{\"format\":\"%08d\",\"variable\":\"F_30\",\"options\":\"\",\"field_length\":8,\"type\":\"NUMERIC\"},\"f32\":{\"format\":\"%011d\",\"variable\":\"F_32\",\"options\":\"\",\"field_length\":11,\"type\":\"LLVAR\"},\"f31\":{\"format\":\"%-9s\",\"variable\":\"F_31\",\"options\":\"\",\"field_length\":9,\"type\":\"STRING\"},\"f34\":{\"format\":\"%-28s\",\"variable\":\"F_34\",\"options\":\"\",\"field_length\":28,\"type\":\"LLVAR\"},\"f33\":{\"format\":\"%011d\",\"variable\":\"F_33\",\"options\":\"\",\"field_length\":11,\"type\":\"LLVAR\"},\"f36\":{\"format\":\"%0104d\",\"variable\":\"F_36\",\"options\":\"\",\"field_length\":104,\"type\":\"LLLVAR\"},\"f35\":{\"format\":\"%-28s\",\"variable\":\"F_35\",\"options\":\"\",\"field_length\":37,\"type\":\"LLVAR\"},\"f38\":{\"format\":\"%-6s\",\"variable\":\"F_38\",\"options\":\"\",\"field_length\":6,\"type\":\"STRING\"},\"f37\":{\"format\":\"%-12s\",\"variable\":\"F_37\",\"options\":\"\",\"field_length\":12,\"type\":\"STRING\"},\"f39\":{\"format\":\"%-2s\",\"variable\":\"F_39\",\"options\":\"\",\"field_length\":2,\"type\":\"STRING\"},\"f41\":{\"format\":\"%-8s\",\"variable\":\"F_41\",\"options\":\"\",\"field_length\":8,\"type\":\"STRING\"},\"f40\":{\"format\":\"%-3s\",\"variable\":\"F_40\",\"options\":\"\",\"field_length\":3,\"type\":\"STRING\"},\"f43\":{\"format\":\"%-40s\",\"variable\":\"F_43\",\"options\":\"\",\"field_length\":40,\"type\":\"STRING\"},\"f42\":{\"format\":\"%-15s\",\"variable\":\"F_42\",\"options\":\"\",\"field_length\":15,\"type\":\"STRING\"},\"f45\":{\"format\":\"%-76s\",\"variable\":\"F_45\",\"options\":\"\",\"field_length\":76,\"type\":\"LLVAR\"},\"f44\":{\"format\":\"%-25s\",\"variable\":\"F_44\",\"options\":\"\",\"field_length\":25,\"type\":\"LLVAR\"},\"f47\":{\"format\":\"%-999s\",\"variable\":\"F_47\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f46\":{\"format\":\"%-999s\",\"variable\":\"F_46\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f49\":{\"format\":\"%-3s\",\"variable\":\"F_49\",\"options\":\"\",\"field_length\":3,\"type\":\"STRING\"},\"f48\":{\"format\":\"%-119s\",\"variable\":\"F_48\",\"options\":\"\",\"field_length\":119,\"type\":\"LLLVAR\"},\"f50\":{\"format\":\"%-3s\",\"variable\":\"F_50\",\"options\":\"\",\"field_length\":3,\"type\":\"STRING\"},\"f52\":{\"format\":\"%-16s\",\"variable\":\"F_52\",\"options\":\"\",\"field_length\":16,\"type\":\"STRING\"},\"f51\":{\"format\":\"%-3s\",\"variable\":\"F_51\",\"options\":\"\",\"field_length\":3,\"type\":\"STRING\"},\"f54\":{\"format\":\"%-120s\",\"variable\":\"F_54\",\"options\":\"\",\"field_length\":120,\"type\":\"STRING\"},\"f53\":{\"format\":\"%-18s\",\"variable\":\"F_53\",\"options\":\"\",\"field_length\":18,\"type\":\"STRING\"},\"f56\":{\"format\":\"%-999s\",\"variable\":\"F_56\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f55\":{\"format\":\"%-999s\",\"variable\":\"F_55\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f58\":{\"format\":\"%-999s\",\"variable\":\"F_58\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f57\":{\"format\":\"%-999s\",\"variable\":\"F_57\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f59\":{\"format\":\"%-99s\",\"variable\":\"F_59\",\"options\":\"\",\"field_length\":99,\"type\":\"LLVAR\"},\"f2\":{\"format\":\"%-19s\",\"variable\":\"F_2\",\"options\":\"\",\"field_length\":19,\"type\":\"LLVAR\"},\"f3\":{\"format\":\"%06d\",\"variable\":\"F_3\",\"options\":\"\",\"field_length\":6,\"type\":\"NUMERIC\"},\"f4\":{\"format\":\"%012d\",\"variable\":\"F_4\",\"options\":\"\",\"field_length\":12,\"type\":\"NUMERIC\"},\"f5\":{\"format\":\"%012d\",\"variable\":\"F_5\",\"options\":\"\",\"field_length\":12,\"type\":\"NUMERIC\"},\"f6\":{\"format\":\"%012d\",\"variable\":\"F_6\",\"options\":\"\",\"field_length\":12,\"type\":\"NUMERIC\"},\"f7\":{\"format\":\"%-10s\",\"variable\":\"F_7\",\"options\":\"\",\"field_length\":10,\"type\":\"STRING\"},\"f8\":{\"format\":\"%08d\",\"variable\":\"F_8\",\"options\":\"\",\"field_length\":8,\"type\":\"NUMERIC\"},\"f9\":{\"format\":\"%08d\",\"variable\":\"F_9\",\"options\":\"\",\"field_length\":8,\"type\":\"NUMERIC\"},\"f61\":{\"format\":\"%-99s\",\"variable\":\"F_61\",\"options\":\"\",\"field_length\":99,\"type\":\"LLVAR\"},\"f60\":{\"format\":\"%-60s\",\"variable\":\"F_60\",\"options\":\"\",\"field_length\":60,\"type\":\"LLVAR\"},\"f63\":{\"format\":\"%-999s\",\"variable\":\"F_63\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f62\":{\"format\":\"%-999s\",\"variable\":\"F_62\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f67\":{\"format\":\"%02d\",\"variable\":\"F_67\",\"options\":\"\",\"field_length\":2,\"type\":\"NUMERIC\"},\"f66\":{\"format\":\"%01d\",\"variable\":\"F_66\",\"options\":\"\",\"field_length\":1,\"type\":\"NUMERIC\"},\"f69\":{\"format\":\"%03d\",\"variable\":\"F_69\",\"options\":\"\",\"field_length\":3,\"type\":\"NUMERIC\"},\"f68\":{\"format\":\"%03d\",\"variable\":\"F_68\",\"options\":\"\",\"field_length\":3,\"type\":\"NUMERIC\"},\"f70\":{\"format\":\"%03d\",\"variable\":\"F_70\",\"options\":\"\",\"field_length\":3,\"type\":\"NUMERIC\"},\"f72\":{\"format\":\"%-999s\",\"variable\":\"F_72\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f115\":{\"format\":\"%-999s\",\"variable\":\"F_115\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f71\":{\"format\":\"%04d\",\"variable\":\"F_71\",\"options\":\"\",\"field_length\":4,\"type\":\"NUMERIC\"},\"f114\":{\"format\":\"%-999s\",\"variable\":\"F_114\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f74\":{\"format\":\"%010d\",\"variable\":\"F_74\",\"options\":\"\",\"field_length\":10,\"type\":\"NUMERIC\"},\"f113\":{\"format\":\"%011d\",\"variable\":\"F_113\",\"options\":\"\",\"field_length\":11,\"type\":\"LLVAR\"},\"f73\":{\"format\":\"%06d\",\"variable\":\"F_73\",\"options\":\"\",\"field_length\":6,\"type\":\"NUMERIC\"},\"f112\":{\"format\":\"%-999s\",\"variable\":\"F_112\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f76\":{\"format\":\"%010d\",\"variable\":\"F_76\",\"options\":\"\",\"field_length\":10,\"type\":\"NUMERIC\"},\"f111\":{\"format\":\"%-999s\",\"variable\":\"F_111\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f75\":{\"format\":\"%010d\",\"variable\":\"F_75\",\"options\":\"\",\"field_length\":10,\"type\":\"NUMERIC\"},\"f110\":{\"format\":\"%-999s\",\"variable\":\"F_110\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f78\":{\"format\":\"%010d\",\"variable\":\"F_78\",\"options\":\"\",\"field_length\":10,\"type\":\"NUMERIC\"},\"f77\":{\"format\":\"%010d\",\"variable\":\"F_77\",\"options\":\"\",\"field_length\":10,\"type\":\"NUMERIC\"},\"f79\":{\"format\":\"%010d\",\"variable\":\"F_79\",\"options\":\"\",\"field_length\":10,\"type\":\"NUMERIC\"},\"f119\":{\"format\":\"%-999s\",\"variable\":\"F_119\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f118\":{\"format\":\"%-999s\",\"variable\":\"F_118\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f81\":{\"format\":\"%010d\",\"variable\":\"F_81\",\"options\":\"\",\"field_length\":10,\"type\":\"NUMERIC\"},\"f117\":{\"format\":\"%-999s\",\"variable\":\"F_117\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f80\":{\"format\":\"%010d\",\"variable\":\"F_80\",\"options\":\"\",\"field_length\":10,\"type\":\"NUMERIC\"},\"f116\":{\"format\":\"%-999s\",\"variable\":\"F_116\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f83\":{\"format\":\"%012d\",\"variable\":\"F_83\",\"options\":\"\",\"field_length\":12,\"type\":\"NUMERIC\"},\"f104\":{\"format\":\"%-100s\",\"variable\":\"F_104\",\"options\":\"\",\"field_length\":100,\"type\":\"LLLVAR\"},\"f82\":{\"format\":\"%012d\",\"variable\":\"F_82\",\"options\":\"\",\"field_length\":12,\"type\":\"NUMERIC\"},\"f103\":{\"format\":\"%-28s\",\"variable\":\"F_103\",\"options\":\"\",\"field_length\":28,\"type\":\"LLVAR\"},\"f85\":{\"format\":\"%012d\",\"variable\":\"F_85\",\"options\":\"\",\"field_length\":12,\"type\":\"NUMERIC\"},\"f102\":{\"format\":\"%-28s\",\"variable\":\"F_102\",\"options\":\"\",\"field_length\":28,\"type\":\"LLVAR\"},\"f84\":{\"format\":\"%012d\",\"variable\":\"F_84\",\"options\":\"\",\"field_length\":12,\"type\":\"NUMERIC\"},\"f101\":{\"format\":\"%-17s\",\"variable\":\"F_101\",\"options\":\"\",\"field_length\":17,\"type\":\"STRING\"},\"f87\":{\"format\":\"%-16s\",\"variable\":\"F_87\",\"options\":\"\",\"field_length\":16,\"type\":\"STRING\"},\"f100\":{\"format\":\"%011d\",\"variable\":\"F_100\",\"options\":\"\",\"field_length\":11,\"type\":\"LLVAR\"},\"f86\":{\"format\":\"%015d\",\"variable\":\"F_86\",\"options\":\"\",\"field_length\":15,\"type\":\"NUMERIC\"},\"f89\":{\"format\":\"%016d\",\"variable\":\"F_89\",\"options\":\"\",\"field_length\":16,\"type\":\"NUMERIC\"},\"f88\":{\"format\":\"%016d\",\"variable\":\"F_88\",\"options\":\"\",\"field_length\":16,\"type\":\"NUMERIC\"},\"f109\":{\"format\":\"%-999s\",\"variable\":\"F_109\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f90\":{\"format\":\"%-42s\",\"variable\":\"F_90\",\"options\":\"\",\"field_length\":42,\"type\":\"STRING\"},\"f108\":{\"format\":\"%-999s\",\"variable\":\"F_108\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f107\":{\"format\":\"%-999s\",\"variable\":\"F_107\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f92\":{\"format\":\"%02d\",\"variable\":\"F_92\",\"options\":\"\",\"field_length\":2,\"type\":\"NUMERIC\"},\"f106\":{\"format\":\"%-999s\",\"variable\":\"F_106\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"},\"f91\":{\"format\":\"%-1s\",\"variable\":\"F_91\",\"options\":\"\",\"field_length\":1,\"type\":\"STRING\"},\"f105\":{\"format\":\"%-999s\",\"variable\":\"F_105\",\"options\":\"\",\"field_length\":999,\"type\":\"LLLVAR\"}}");
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * Constructor with Message Type Indicator, configuration of the specs and message to be parse
	 * @param mti_id Message Type Indicator
	 * @param config Configuration of the specs
	 * @param message Message to be parse
	 */
	public RoyISO8583(String mti_id, JSONObject config, String message)
	{
		segment1[0] = 0;
		segment1[1] = 0;
		segment2[0] = 0;
		segment2[1] = 0;
		segment3[0] = 0;
		segment3[1] = 0;
		this.item = new JSONObject();
		this.setType(mti_id);
		this.setConfig(config);
		this.parse(message);
	}
	/**
	 * Set configuration of the specs
	 * @param config JSONObject contians configuration of the specs
	 */
	public void setConfig(JSONObject config)
	{
		this.config = config;
	}
	/**
	 * Get configuration of the specs
	 * @return JSONObject contians configuration of the specs
	 */
	public JSONObject getConfig()
	{
		return this.config;
	}
	/**
	 * Get ISO 8583 bitmap.
	 * @return String contains ISO 8583 bitmap
	 */
	public String getBitmap()
	{
		String bitmap = "";
		int maxField = 0;
		String fieldStr = "";
		int fieldInt = 0;
		int i = 0;
		for(i = 0; i<fields.length; i++)
		{			
			fieldStr = this.fields[i];
			if(fieldStr.equals(""))
			{
				fieldStr = "0";
			}
			fieldInt = Integer.parseInt(fieldStr);
			if(fieldInt > maxField)
			{
				maxField = fieldInt;
			}
			if(fieldInt >= 1 && fieldInt <= 32)
			{
				segment1[0] = (segment1[0] | (1 << (32-fieldInt)));
			}
			if(fieldInt >= 33 && fieldInt <= 64)
			{
				segment1[1] = (segment1[1] | (1 << (32-fieldInt)));
			}
			if(fieldInt >= 65 && fieldInt <= 96)
			{
				segment2[0] = (segment2[0] | (1 << (32-fieldInt)));
			}
			if(fieldInt >= 97 && fieldInt <= 128)
			{
				segment2[1] = (segment2[1] | (1 << (32-fieldInt)));
			}
			if(fieldInt >= 129 && fieldInt <= 160)
			{
				segment3[0] = (segment3[0] | (1 << (32-fieldInt)));
			}
			if(fieldInt >= 161 && fieldInt <= 192)
			{
				segment3[1] = (segment3[1] | (1 << (32-fieldInt)));
			}
		}
		String h1 = "", h2 = "", h3 = "";
		if(maxField > 128)
		{
			segment1[0] = (segment1[0] | (1 << 31));
			segment2[0] = (segment2[0] | (1 << 31));
			h1 = String.format("%08x%08x", segment1[0], segment1[1]);
			h2 = String.format("%08x%08x", segment2[0], segment2[1]);
			h3 = String.format("%08x%08x", segment3[0], segment3[1]);
			bitmap = h1+h2+h3;
		}
		else if(maxField > 64)
		{
			segment1[0] = (segment1[0] | (1 << 31));
			h1 = String.format("%08x%08x", segment1[0], segment1[1]);
			h2 = String.format("%08x%08x", segment2[0], segment2[1]);
			bitmap = h1+h2;
		}
		else
		{
			h1 = String.format("%08x%08x", segment1[0], segment1[1]);
			bitmap = h1;
		}
		return bitmap.toUpperCase();	
	}
	/**
	 * Get message body.
	 * This method will pack all field to a string. The length of each field is set when the field is added/modified
	 * @return String contains ISO 8583 message body
	 */
	public String getBody()
	{
		String fieldStr = "";
		JSONObject jo = new JSONObject();
		String body = "";
		String data = "";
		String finalItemData = "";
		String dataType = "";
		int dataLength = 0;	
		int iter = 0;
		
		long val = 0;
		String len = "";
		
		for(iter = 0; iter < this.fields.length; iter++)
		{
			fieldStr = this.fields[iter];
			jo = (JSONObject) this.item.get("f_"+fieldStr.trim());
			try
			{
				data = jo.get("data").toString();
				finalItemData = data;
				dataType = jo.get("type").toString();
				dataLength = Integer.parseInt(jo.get("length").toString());
				if(dataType.equals("AMOUNT"))
				{
					dataLength = 12;
					data = data.trim();
					if(data.equals(""))
					{
						data = "0";
					}
					val = Long.parseLong(data);
					finalItemData = String.format("%012d", val);
				}
				else if(dataType.equals("LLLVAR"))
				{
					len = String.format("%03d", data.length());
					finalItemData = len+data;
				}
				else if(dataType.equals("LLVAR"))
				{
					len = String.format("%02d", data.length());
					finalItemData = len+data;
				}
				else if(dataType.equals("LVAR"))
				{
					len = String.format("%1d", data.length());
					finalItemData = len+data;
				}
				else if(dataType.equals("NUMERIC"))
				{
					data = data.trim();
					if(data.equals(""))
					{
						data = "0";
					}
					val = Long.parseLong(data);
					finalItemData = String.format("%0"+dataLength+"d", val);
				}
				else
				{
					finalItemData = String.format("%-"+dataLength+"s", data);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			body += finalItemData;
		}
		return body;
	}
	/**
	 * Add bit to ISO 8583 message. It will affect to the bitmap.
	 * @param field ISO 8583 field number
	 */
	public void addBit(int field)
	{
		if(field > this.maxField)
		{
			this.maxField = field;
		}
		if(this.fields != null)
		{
			this.fieldStr = String.join(",", this.fields);
			int i;
			boolean duplicated = false;
			for(i = 0; i < this.fields.length; i++)
			{
				if(this.fields[i].trim().equals(field+""))
				{
					duplicated = true;
				}
			}
			if(!duplicated)
			{
				this.fieldStr += ","+field;
			}
		}
		else
		{
			this.fieldStr = field+"";
		}
		this.fields = this.fieldStr.split(",");
	}
	/**
	 * Add field value. It seem with the setValue method unless invoke addBit first before set the field value
	 * @param field ISO 8583 field number
	 * @param data String data
	 * @param dataType field Type
	 * @param dataLength Data length
	 */
	public void addValue(int field, String data, String dataType, int dataLength)
	{
		this.addBit(field);
		this.setValue(field, data, dataType, dataLength);
	}
	/**
	 * Set field value
	 * @param field ISO 8583 field number
	 * @param data String data
	 * @param dataType field Type
	 * @param dataLength Data length
	 */
	public void setValue(int field, String data, String dataType, int dataLength)
	{
		JSONObject jo = new JSONObject();
		JSONObject j = new JSONObject();
		
		if(dataType.equals("AMOUNT"))
		{
			dataLength = 12;
		}
		if(dataType.equals("LVAR") || dataType.equals("LLVAR") || dataType.equals("LLLVAR"))
		{
			dataLength = data.length();
		}
		if(dataLength == 0)
		{
			String f = String.format("f", field);
			j = this.config.optJSONObject(f);
			if(j != null)
			{
				int c_field_length = Integer.parseInt(j.get("field_length").toString());
				String c_type = j.get("type").toString();
				if(dataLength < c_field_length)
				{
					dataLength = c_field_length;
				}
				if(dataType.equals(""))
				{
					dataType = c_type;
				}
			}
		}		
		jo.put("data", data);
		jo.put("type", dataType);
		jo.put("length", dataLength);
		this.item.put("f_"+field, jo);		
	}
	/**
	 * Get value of field
	 * @param field ISO 8583 field number
	 * @return String data of the field
	 */
	public String getValue(int field)
	{
		JSONObject jo = new JSONObject();
		try
		{
			jo = (JSONObject) this.item.get("f_"+field);
			if(jo != null)
			{
				return jo.optString("data", "").toString();
			}
			else
			{
				return "";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * Get value
	 * @param field Specified field
	 * @param defaultValue Default value
	 * @return String value of field
	 */
	public String getValue(int field, String defaultValue)
	{
		JSONObject jo = new JSONObject();
		try
		{
			jo = (JSONObject) this.item.get("f_"+field);
			if(jo != null)
			{
				return jo.optString("data", defaultValue).toString();
			}
			else
			{
				return defaultValue;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return defaultValue;
		}
	}
	/**
	 * Set Message Type Indicator
	 * @param type Message Type Indicator
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	/**
	 * Get Message Type Indicator
	 * @return Message Type Indicator
	 */
	public String getType()
	{
		return this.type;
	}
	/**
	 * Set field
	 * @param field ISO8583 field number
	 * @param data ISO8583 field data
	 */
	public void setField(int field, RoyISO8583Field data)
	{
		this.addBit(field);
		this.setValue(field, data.data, data.type, data.length);
	}
	/**
	 * Get ISO8583 field
	 * @param field ISO8583 field number
	 * @return RoyISO8583Field contains field object
	 */
	public RoyISO8583Field getField(int field)
	{
		RoyISO8583Field fieldData = new RoyISO8583Field();
		JSONObject data = new JSONObject();
		try
		{
			data = (JSONObject) this.item.get("f_"+field);	
			fieldData.setField(data.optString("data", "").toString(), data.optString("type", "STRING").toString(), Integer.parseInt(data.optString("length", "1").toString()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return fieldData;
	}
	/**
	 * Check if the field is present or not
	 * @param field ISO8583 field number
	 * @return true if field is present and false if field is not present
	 */
	public boolean hasField(int field)
	{
		int i;
		for(i = 0; i<this.fields.length; i++)
		{
			if(this.fields[i].equals(field+""))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * Parse ISO 8583 Message
	 * @param message String contains ISO 8583 message
	 */
	public void parse(String message)
	{
		this.fields = null;
		
		String ln = "";
		
		String segment10Str = "";
		String segment11Str = "";
		int segment10Int = 0;
		int segment11Int = 0;
		
		String segment20Str = "";
		String segment21Str = "";
		int segment20Int = 0;
		int segment21Int = 0;
		
		String segment30Str = "";
		String segment31Str = "";
		int segment30Int = 0;
		int segment31Int = 0;		
		
		String typeStr = message.substring(0, 4);
		this.type = typeStr;		
		segment10Str = message.substring(4, 12).replaceAll("[^A-Fa-f0-9]", "");
		segment11Str = message.substring(12, 20).replaceAll("[^A-Fa-f0-9]", "");
		if(segment10Str.equals(""))
		{
			segment10Str = "0";
		}
		if(segment11Str.equals(""))
		{
			segment11Str = "0";
		}
		segment10Int = (int)Long.parseLong(segment10Str, 16);
		segment11Int = (int)Long.parseLong(segment11Str, 16);
		this.segment1[0] = segment10Int;
		this.segment1[1] = segment11Int;		
		if((this.segment1[0] & 0x80000000) == 0x80000000)
		{
			segment20Str = message.substring(20, 28).replaceAll("[^A-Fa-f0-9]", "");
			segment21Str = message.substring(28, 36).replaceAll("[^A-Fa-f0-9]", "");
			if(segment20Str.equals(""))
			{
				segment20Str = "0";
			}
			if(segment21Str.equals(""))
			{
				segment21Str = "0";
			}
			segment20Int = (int)Long.parseLong(segment20Str, 16);
			segment21Int = (int)Long.parseLong(segment21Str, 16);
			this.segment2[0] = segment20Int;
			this.segment2[1] = segment21Int;
		}		
		if((this.segment2[0] & 0x80000000) == 0x80000000)
		{
			segment30Str = message.substring(36, 44).replaceAll("[^A-Fa-f0-9]", "");
			segment31Str = message.substring(44, 52).replaceAll("[^A-Fa-f0-9]", "");
			if(segment30Str.equals(""))
			{
				segment30Str = "0";
			}
			if(segment31Str.equals(""))
			{
				segment31Str = "0";
			}
			segment30Int = (int)Long.parseLong(segment30Str, 16);
			segment31Int = (int)Long.parseLong(segment31Str, 16);
			this.segment3[0] = segment30Int;
			this.segment3[1] = segment31Int;
		}	
		int iter = 0;
		// get field list from bitmap
		int i = 0;
		int k = 0;
		
		k = this.segment1[0];
		for(i = 1; i <= 32; i++)
		{
			if((k & 0x80000000) == 0x80000000 && i > 1)
			{
				this.addBit(i);
			}
			k = k - 0x80000000;
			k = k << 1;
		}
		k = this.segment1[1];
		for(i = 33; i <= 64; i++)
		{
			if((k & 0x80000000) == 0x80000000)
			{
				this.addBit(i);
			}
			k = k - 0x80000000;
			k = k << 1;
		}
		int bitmapLength = 16;
		if((this.segment1[0] & 0x80000000) == 0x80000000)
		{
			bitmapLength = 32;
			k = this.segment2[0];
			for(i = 65; i <= 96; i++)
			{
				if((k & 0x80000000) == 0x80000000)
				{
					this.addBit(i);
				}
				k = k - 0x80000000;
				k = k << 1;
			}
			k = this.segment2[1];
			for(i = 97; i <= 128; i++)
			{
				if((k & 0x80000000) == 0x80000000)
				{
					this.addBit(i);
				}
				k = k - 0x80000000;
				k = k << 1;				
			}
			if((this.segment2[0] & 0x80000000) == 0x80000000)
			{
				bitmapLength = 48;
				k = this.segment3[0];
				for(i = 129; i <= 160; i++)
				{
					if((k & 0x80000000) == 0x80000000)
					{
						this.addBit(i);
					}
					k = k - 0x80000000;
					k = k << 1;					
				}
				k = this.segment3[1];
				for(i = 161; i <= 192; i++)
				{
					if((k & 0x80000000) == 0x80000000)
					{
						this.addBit(i);
					}
					k = k - 0x80000000;
					k = k << 1;					
				}
			}
		}		
		JSONObject jo = new JSONObject();
		String dataType = "";
		int dataLength = 0;	
		int realLength = 0;
		int fieldLength = 0;
		String rawData = "";
		String shiftedData = "";
		int field = 0;
		
		boolean validMessage = true;
		for(iter = 0; iter<this.fields.length; iter++)
		{
			field = Integer.parseInt(this.fields[iter]);
			// get config
			jo = this.config.optJSONObject("f"+field);
			if(jo == null)
			{
				
				if(RoyISO8583.generalConfig != null)
				{
					jo = (JSONObject) RoyISO8583.generalConfig.get("f"+field);
					if(jo == null)
					{
						validMessage = false;
					}
				}
				else
				{
					validMessage = false;
				}				
			}
		}
		if(validMessage)
		{
			if(message.length() > bitmapLength+4)
			{
				shiftedData = message.substring(bitmapLength+4);
				for(iter = 0; iter<this.fields.length; iter++)
				{	
					field = Integer.parseInt(this.fields[iter]);
					jo = this.config.optJSONObject("f"+field);
					if(jo == null)
					{
						if(RoyISO8583.generalConfig != null)
						{
							jo = (JSONObject) RoyISO8583.generalConfig.get("f"+field);
						}
					}
					if(jo != null)
					{
						if(jo.get("type") != null)
						{
							dataType = jo.get("type").toString();
							fieldLength = Integer.parseInt(jo.get("field_length").toString());
							dataLength = fieldLength;
							realLength = dataLength;
							rawData = "";
							if(dataType.equals("LLLVAR"))
							{
								if(shiftedData.length() >= 3)
								{
									rawData = shiftedData.substring(0, 3);
									ln = rawData.replaceAll("[^\\d.]", "");
									ln = Translator.lTrim(ln, "0");
									if(ln.equals(""))
									{
										ln = "0";
									}
									realLength = Integer.parseInt(ln);
									shiftedData = shiftedData.substring(3);
									if(shiftedData.length() >= realLength)
									{
										rawData = shiftedData.substring(0, realLength);
										shiftedData = shiftedData.substring(realLength);
									}
									else
									{
										// insufficient data
										rawData = "";
									}
								}
							}
							else if(dataType.equals("LLVAR"))
							{
								if(shiftedData.length() >= 2)
								{
									rawData = shiftedData.substring(0, 2);
									ln = rawData.replaceAll("[^\\d.]", "");
									ln = Translator.lTrim(ln, "0");
									if(ln.equals(""))
									{
										ln = "0";
									}
									realLength = Integer.parseInt(ln);
									shiftedData = shiftedData.substring(2);
									if(shiftedData.length() >= realLength)
									{
										rawData = shiftedData.substring(0, realLength);
										shiftedData = shiftedData.substring(realLength);
									}
									else
									{
										// insufficient data
										rawData = "";
									}
								}
							}
							else if(dataType.equals("LVAR"))
							{
								if(shiftedData.length() >= 1)
								{
									rawData = shiftedData.substring(0, 1);
									ln = rawData.replaceAll("[^\\d.]", "");
									ln = Translator.lTrim(ln, "0");
									if(ln.equals(""))
									{
										ln = "0";
									}
									realLength = Integer.parseInt(ln);
									shiftedData = shiftedData.substring(1);
									if(shiftedData.length() >= realLength)
									{
										rawData = shiftedData.substring(0, realLength);
										shiftedData = shiftedData.substring(realLength);
									}
									else
									{
										// insufficient data
										rawData = "";
									}
								}
							}
							else if(dataType.equals("AMOUNT"))
							{
								if(shiftedData.length() >= 12)
								{
									rawData = shiftedData.substring(0, 12);
									rawData = rawData.replaceAll("[^\\d]", "");
									rawData = Translator.lTrim(rawData, "0");
									if(rawData.equals(""))
									{
										rawData = "0";
									}
									long numVal = Long.parseLong(rawData);
									rawData = String.format("%012d", numVal);
									shiftedData = shiftedData.substring(12);
									realLength = 12;
								}
							}
							else 
							{
								if(shiftedData.length() >= dataLength)
								{
									rawData = shiftedData.substring(0, dataLength);
									shiftedData = shiftedData.substring(dataLength);
									realLength = dataLength;
								}
							}
							this.setValue(field, rawData, dataType, realLength);
						}
					}
				}
			}
		}
	}
	public String showAsList()
	{
		int i;
		String ret = "";
		for(i = 2; i<=this.maxField; i++)
		{
			if(this.hasField(i))
			{
				ret += String.format("%3d %s\r\n", i, this.getValue(i));
			}
		}
		return ret;
	}
	/**
	 * Parse ISO 8583 Message
	 * @param message String contains ISO 8583 message
	 * @param config JSONObject contains specs
	 */
	public void parse(String message, JSONObject config)
	{
		this.setConfig(config);
		this.parse(message);
	}
	/**
	 * Generate ISO 8583 message contains Message Type Indicator, bitmap, and all fields 
	 */
	public String toString()
	{
		String message = "";
		int i;
		int[] fieldsInt;
		if(this.fields.length > 0)
		{
			fieldsInt = new int[this.fields.length];
			for(i = 0; i < this.fields.length; i++)
			{
				fieldsInt[i] = Integer.parseInt(this.fields[i]);
			}
			Arrays.sort(fieldsInt);
			for(i = 0; i < this.fields.length; i++)
			{
				this.fields[i] = fieldsInt[i]+"";
			}
			this.fieldStr = String.join(",", this.fields);
			message = this.type+this.getBitmap()+this.getBody();
		}
		return message;
	}
	/**
	 * Padding string on right with specified character
	 * @param s Padding string
	 * @param n Length expected
	 * @return Padded string
	 */
	public static String padRight(String s, int n) 
	{
	     return String.format("%1$-" + n + "s", s);  
	}
	/**
	 * Padding string on left with specified character
	 * @param s Padding string
	 * @param n Length expected
	 * @return Padded string
	 */
	public static String padLeft(String s, int n) 
	{
	    return String.format("%1$" + n + "s", s);  
	}
	
}
