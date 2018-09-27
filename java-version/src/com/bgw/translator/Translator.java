package com.bgw.translator;

import java.io.File;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.io.IOException;  
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.bgw.utility.Utility;

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * ParserBuilder is class to parsing and building transaction message. The supported format are ISO 8583, JSON, XML.
 * Parsing and building message need configuration according to message format. 
 * @author Kamshory, MT
 *
 */
@SuppressWarnings("unused")
public class Translator {
	/**
	 * RoyISO8583 to get all ISO 8583 fields
	 */
	public RoyISO8583 royISO8583 = new RoyISO8583();
	/**
	 * Cache configuration size
	 */
	private long cachedConfig = 0;
	/**
	 * Cache configuration
	 */
	public static JSONObject cacheSpec = new JSONObject();
	/**
	 * Option to use cache configuration.
	 */
	public static boolean cacheSpecConfiguration = false;
	/**
	 * Maximum configuration
	 */
	public static long cacheSpecConfigurationMax = 100;	
	/**
	 * Default constructor
	 */
	public Translator()
	{
	}
	/**
	 * Initialize object
	 */
	public void initConfig()
	{
	}
	/**
	 * Strip characters from the beginning of a string
	 * @param input String to be stripped
	 * @param mask Character mask to strip string
	 * @return Stripped string
	 */
	public static String lTrim(String input, String mask)
	{
		int lastLen = input.length();
		int curLen = lastLen;
		do
		{
			lastLen = input.length();
			input = input.replaceAll("^"+mask, "");
			curLen = input.length();
		}
		while (curLen < lastLen);
		return input;
	}
	/**
	 * Strip characters from the end of a string
	 * @param input String to be stripped
	 * @param mask Character mask to strip string
	 * @return Stripped string
	 */
	public static String rTrim(String input, String mask)
	{
		int lastLen = input.length();
		int curLen = lastLen;
		do
		{
			lastLen = input.length();
			input = input.replaceAll(mask+"$", "");
			curLen = input.length();
		}
		while (curLen < lastLen);
		return input;
	}
	/**
	 * Get N right string
	 * @param input Input string
	 * @param length Expected length
	 * @return N right string
	 */
	public static String right(String input, int length)
	{
		if(length >= input.length())
		{
			return input;
		}
		else
		{
			return input.substring(input.length() - length, input.length()-1);
		}
	}
	/**
	 * Get N left string
	 * @param input Input string
	 * @param length Expected length
	 * @return N left string
	 */
	public static String left(String input, int length)
	{
		if(length >= input.length())
		{
			return input;
		}
		else
		{
			return input.substring(0, length);
		}
	}
	/**
	 * Encode parameter
	 * @param param Parameter to be encoded
	 * @return Encoded parameter
	 */
	public static String escapeParameter(String param)
	{
		String output = param;
		output = output.replaceAll("&", "%26");
		output = output.replaceAll("=", "%3D");
		return output;
	}
	/**
	 * Decode parameter
	 * @param param Parameter to be decoded
	 * @return Decoded parameter
	 */
	public static String deescapeParameter(String param)
	{
		String output = param;
		output = output.replaceAll("%26", "&");
		output = output.replaceAll("%3D", "=");
		return output;
	}
	/**
	 * Escape the XML data
	 * @param input Data to be escaped
	 * @return Escaped string
	 */
	public static String escapeXML(String input)
	{
		String output = input;
		output = output.replaceAll("&", "&amp;");
		output = output.replaceAll("\"", "&quot;");
		output = output.replaceAll("<", "&lt;");
		output = output.replaceAll(">", "&gt;");
		return output;
	}
	/**
	 * Remove escape character of the XML data
	 * @param input Data to be escaped
	 * @return Escaped string
	 */
	public static String deescapeXML(String input)
	{
		String output = input;
		output = output.replaceAll("&lt;", "<");
		output = output.replaceAll("&gt;", ">");
		output = output.replaceAll("&amp;", "&");
		output = output.replaceAll("&quot;", "\"");
		return output;
	}
	/**
	 * Escape string before use it in a SQL command
	 * @param s Input string
	 * @return Escapedstring
	 */
	public static String escapeSQL(String s)
	{
	    s = s.replaceAll("\\00", "\\\\0");
	    s = s.replaceAll("'", "''");
	    return s;
	}
	/**
	 * Remove escape character from a escaped string
	 * @param s Escaped string
	 * @return Clear string
	 */
	public static String deescapeSQL(String s)
	{
	    s = s.replaceAll("\\\\00", "\\0");
	    s = s.replaceAll("''", "'");
	    return s;
	}
	/**
	 * Remove escape character of the JSON data
	 * @param input Data to be escaped
	 * @return Escaped string
	 */
	public static String escapeJSON(String input)
	{
		String output = "";
		if(input != null)
		{
			output = input.replaceAll("\"", "\\\"");
		}
		else
		{		
		}
		return output;
	}
	/**
	 * Remove escape characters of JSON string
	 * @param input JSON string to be clean up
	 * @return Clean JSON string
	 */
	public static String deescapeJSON(String input)
	{
		String output = input;
		output = output.replaceAll("\\\"", "\"");
		return output;
	}
	/**
	 * Escape HTML characters
	 * @param input HTML to be escaped
	 * @return Escaped HTML
	 */
    public static String escapeHTML(String input)
    {
    	String ret = input;
		ret = ret.replaceAll("&", "&amp;");
		ret = ret.replaceAll("\"", "&quot;");
		ret = ret.replaceAll("<", "&lt;");
		ret = ret.replaceAll(">", "&gt;");
    	return ret;
    }
    /**
     * Remove escape characters of HTML
     * @param input HTML to be clean up
     * @return Clean HTML
     */
    public static String deescapeHTML(String input)
    {
    	String ret = input;
 		ret = ret.replaceAll("&lt;", "<");
		ret = ret.replaceAll("&gt;", ">");
		ret = ret.replaceAll("&quot;", "\"");
		ret = ret.replaceAll("&amp;", "&");
     	return ret;
    }
	/**
	 * Parse JSON message with template and variable. All keys will mapped to the variable
	 * @param message Message in JSON format
	 * @param format Data format
	 * @param variable Variable to receive the data
	 * @return Common specs in JSON format
	 */
	public JSONObject parseJSON(String message, String format, String variable)
	{
		variable = variable.replaceAll(" ", "");
		if(message == null)
			return null;
		if(message.equals(""))
			return null;
		String[] variables = variable.split(",");		
		Pattern p = Pattern.compile("\\%([0-9\\+\\-\\,\\.]*)[sdf]", Pattern.MULTILINE|Pattern.DOTALL); 
		Matcher m = p.matcher(format);
		String dataType = "s";
		String fmt = "";	
		String result = "";				
		int curstart = 0, curend = 0;
		int i = 0;
		String currentData = "";
		String dataIndex = "";
		while(m.find()) 
		{
			fmt = m.group(0);
			if(fmt.contains("s"))
				dataType = "s";
			if(fmt.contains("d"))
				dataType = "d";
			curend = m.start();		
			result += substringOf(format, curstart, curend);
			try
			{
				dataIndex = variables[i];
			}
			catch(Exception e)
			{
				dataIndex = "";
				e.printStackTrace();
			}
			currentData = "";
			if(dataIndex == null)
			{
				currentData = "";
			}
			else if(!dataIndex.equals(""))
			{
				if(dataType == "s")
				{
					currentData = "${"+dataIndex+"}";
				}
				if(dataType == "d")
				{
					currentData = "${"+dataIndex+"}";
				}
			}
			else
			{
				currentData = "";
			}		
			result += currentData;
			curstart = m.end();
			i++;			
		}				
		result += substringOf(format, curstart);			
		
		JSONObject data = new JSONObject();
		JSONObject specs = new JSONObject();
		JSONObject json = new JSONObject();
		String buff = "";
		if(result.length() > 2)
		{
			try 
			{
				data = new JSONObject(message);
				specs = new JSONObject(result);
				Set<?> s =  data.keySet();
			    Iterator<?> iter = s.iterator();
			    do
			    {
			        String key = iter.next().toString();		        
			        if(specs.get(key) != null)
			        {
				        String keySpecs = specs.get(key).toString();	
				        keySpecs = keySpecs.trim();
				        if(!keySpecs.equals(""))
				        {
					        keySpecs = keySpecs.replaceAll("\\$", "");
					        keySpecs = keySpecs.replaceAll("\\{", "");
					        keySpecs = keySpecs.replaceAll("\\}", "");
					        keySpecs = keySpecs.trim();
					        String value = data.get(key).toString();
					        if(value == null)
					        {
					        	value = "";
					        }
					        value = escapeJSON(value);
					        keySpecs = escapeJSON(keySpecs);
				        	try
				        	{
				        		buff += "\""+keySpecs+"\":\""+value+"\",";
				        	}
				        	catch(NullPointerException ne)
				        	{	        		
				        	}
				        }
			        }
			    }
			    while(iter.hasNext());
			    if(!buff.equals(""))
			    {
			    	buff = rTrim(buff, ",");
			    	buff = "{"+buff+"}";
			    	json = new JSONObject(buff);
			    }
			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
		}
		return json;
	}
	/**
	 * Convert XML to JSON. This function only process one level of the object
	 * @param message XML containing the data
	 * @return JSONObject containing the data
	 */
	public JSONObject XMLToJSON(String message)
	{
		
		JSONObject json = XML.toJSONObject(message);
		return json;
	}
	/**
	 * Parse XML message using template. All the data will be stored on variables given
	 * @param message XML containing message
	 * @param format Format data
	 * @param variable Variable where the data will be stored
	 * @return JSONObject containing the data
	 */
	public JSONObject parseXML(String message, String format, String variable)
	{
		variable = variable.replaceAll(" ", "");
		if(message == null)
			return null;
		if(message.equals(""))
			return null;		
		// Add variable to format
		String[] variables = variable.split(",");		
		Pattern p = Pattern.compile("\\%([0-9\\+\\-\\,\\.]*)[sdf]", Pattern.MULTILINE|Pattern.DOTALL); 
		Matcher m = p.matcher(format);
		String dataType = "s";
		String fmt = "";	
		String result = "";			
		int curstart = 0, curend = 0;
		int i = 0;
		String currentData = "";
		String dataIndex = "";
		while(m.find()) 
		{
			fmt = m.group(0);
			if(fmt.contains("s"))
				dataType = "s";
			if(fmt.contains("d"))
				dataType = "d";
			curend = m.start();		
			result += substringOf(format, curstart, curend);
			try
			{
				dataIndex = variables[i];
			}
			catch(Exception e)
			{
				dataIndex = "";
				e.printStackTrace();
			}
			currentData = "";
			if(dataIndex == null)
			{
				currentData = "";
			}
			else if(!dataIndex.equals(""))
			{
				if(dataType == "s")
				{
					currentData = "${"+dataIndex+"}";
				}
				if(dataType == "d")
				{
					currentData = "${"+dataIndex+"}";
				}
			}
			else
			{
				currentData = "";
			}			
			result += currentData;
			curstart = m.end();
			i++;			
		}		
		result += substringOf(format, curstart);
		
		JSONObject data = null;
		JSONObject specs = null;
		JSONObject json = null;
		String buff = "";
		try 
		{			
			data = XMLToJSON(message);
			specs = XMLToJSON(result);
			Set<?> s =  data.keySet();
		    Iterator<?> iter = s.iterator();
		    do
		    {
		        String key = iter.next().toString();		   		        
		        if(specs.get(key) != null)
		        {		        	
			        String keySpecs = specs.get(key).toString();	
			        keySpecs = keySpecs.trim();
			        if(!keySpecs.equals(""))
			        {
				        keySpecs = keySpecs.replaceAll("\\$", "");
				        keySpecs = keySpecs.replaceAll("\\{", "");
				        keySpecs = keySpecs.replaceAll("\\}", "");
				        String value = data.get(key).toString();
				        if(value == null)
				        {
				        	value = "";
				        }			        
			        	keySpecs = keySpecs.trim();
			        	value = value.trim();
			        	value = deescapeXML(value);
			        	value = escapeJSON(value);
				        keySpecs = escapeJSON(keySpecs);
			        	try
			        	{
			        		buff += "\""+keySpecs+"\":\""+value+"\",";
			        	}
			        	catch(NullPointerException ne)
			        	{			        		
			        	}			        
			        }
		        }
		    }
		    while(iter.hasNext());
		    if(!buff.equals(""))
		    {
		    	buff = rTrim(buff, ",");
		    	buff = "{"+buff+"}";
		    	json = new JSONObject(buff);
		    }
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		return json;	
	}	
	/**
	 * List formatMap into string array
	 * @param format Data format 
	 * @return String array containing single format
	 */
	public String[] listSubformat(String format)
	{
		Pattern p = Pattern.compile("\\%([0-9\\+\\-\\,\\.]*)[sdf]", Pattern.MULTILINE|Pattern.DOTALL); 
		Matcher m = p.matcher(format);
		String fmt = "";		
		int i = 0;
		while(m.find()) 
		{
			i++;
		}	
		String subformats[] = new String[i];
		p = Pattern.compile("\\%([0-9\\+\\-\\,\\.]*)[sdf]", Pattern.MULTILINE|Pattern.DOTALL); 
		m = p.matcher(format);
		i = 0;
		while(m.find()) 
		{
			fmt = m.group(0);
			fmt = fmt.trim();
			if(fmt.equals(""))
			{
				fmt = "0";
			}
			subformats[i] = fmt;
			i++;		
		}	
		return subformats;
	}
	/**
	 * List the all format to string list 
	 * @param formatMap JSON containing the format
	 * @return String list of the format 
	 */
	public List<String> listFormatMapKey(JSONObject formatMap)
	{
		Set<?> s =  formatMap.keySet();
	    Iterator<?> iter = s.iterator();
	    List<String> keys = new ArrayList<>();
	    String key;
	    do
	    {
	        key = iter.next().toString();
	        keys.add(key);
	    }
	    while(iter.hasNext());
	    return keys;
	}
	/**
	 * Convert string list to string array
	 * @param stringList String list to be converted
	 * @return String array converted from form string list
	 */
	public String[] listStringToArrayString(List<String> stringList)
	{
		String[] strarray = new String[stringList.size()];
		stringList.toArray(strarray);
	    return strarray;	    
	}
	/**
	 * Check whether the word is exists on the string array 
	 * @param haystack String array from where the word to be search
	 * @param needle Word to be search
	 * @param strict Flag for case sensitive or insensitive
	 * @return true if word is in array. Otherwise return false
	 */
	public static boolean inArray(String[] haystack, String needle, boolean strict)
	{
		int i;
		String t1, t2;
		for(i = 0; i<haystack.length; i++)
		{
			if(strict)
			{
				t1 = haystack[i];
				t2 = needle;
				if(t1.equals(t2))
				{
					return true;
				}
			}
			else
			{
				t1 = haystack[i];
				t1 = t1.toLowerCase();
				t2 = needle;
				t2 = t2.toLowerCase();
				if(t1.equals(t2))
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Check whether the word is exists on the string array and ignore case
	 * @param haystack String array from where the word to be search
	 * @param needle Word to be search
	 * @return true if word is in array. Otherwise return false
	 */
	public static boolean inArray(String[] haystack, String needle)
	{
		return inArray(haystack, needle, false);
	}

	/**
	 * Parse semi URL encoded data to object. It almost equivalent to parse_str in PHP but with less encoding
	 * @param parameters String containing semi URL encoded
	 * @return JSONObject parsed from parameters
	 */
	public JSONObject parseParameter(String parameters)
	{
		JSONObject json = new JSONObject();
		try 
		{			
			int i;
			String param, key, value, buff = "";
			if(parameters.contains("&"))
			{
				String[] array_parameter = parameters.split("&");
				for(i = 0; i<array_parameter.length;i++)
				{
					param = array_parameter[i];
					if(param.contains("="))
					{
						String[] arr = param.split("=");
						key = arr[0];
						value = arr[1];
						json.put(key.trim(), escapeJSON(value));
					}
					else
					{
						key = param;
						json.put(key.trim(), "");
					}
				}
			}
			else
			{
				param = parameters;
				if(param.contains("="))
				{
					String[] arr = param.split("=");
					key = arr[0];
					value = arr[1];
					json.put(key.trim(), escapeJSON(value));
				}
				else
				{
					key = param;
					json.put(key.trim(), "");
				}				
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
		return json;
	}
	/**
	 * JSON format message builder
	 * @param json Data package on common specs
	 * @return Return string containing semi URL encoded format message
	 */
	public String buildParameter(JSONObject json)
	{
		Set<?> s =  json.keySet();
	    Iterator<?> iter = s.iterator();
	    String key = "", value = "";
	    String message = "";
	    do
	    {
	        key = iter.next().toString();
	        if(json.get(key) != null)
	        {
		        value = json.get(key).toString();
	        }
	        else
	        {
	        	value = "";
	        }
	        message += escapeParameter(key)+"="+escapeParameter(value)+"&";
	    }
	    while(iter.hasNext());
	    message = rTrim(message, "&");
	    return message;
	}
	
	/**
	 * Check if format more than one
	 * @param format Format to be checked
	 * @return true if more than one format. Otherwise return false
	 */
	public boolean hasSubfield(String format)
	{
		Pattern p = Pattern.compile("\\%([0-9\\+\\-\\,\\.]*)[sdf]", Pattern.MULTILINE|Pattern.DOTALL); 
		Matcher m = p.matcher(format);
		int i = 0;
		while(m.find()) 
		{
			i++;
		}
		if(i > 1)
		{
			return true;			
		}
		return false;
	}
	/**
	 * Get length of all subfields
	 * @param format Format of the field
	 * @return Array containing each of subfield length
	 */
	public int[] getSubfieldLength(String format)
	{
		Pattern p = Pattern.compile("\\%([0-9\\+\\-\\,\\.]*)[sdf]", Pattern.MULTILINE|Pattern.DOTALL); 
		Matcher m = p.matcher(format);
		String fmt = "";		
		int i = 0;
		while(m.find()) 
		{
			i++;
		}
		
		int lengths[] = new int[i];
		p = Pattern.compile("\\%([0-9\\+\\-\\,\\.]*)[sdf]", Pattern.MULTILINE|Pattern.DOTALL); 
		m = p.matcher(format);
		i = 0;
		while(m.find()) 
		{			
			fmt = m.group(0);
			fmt = fmt.replaceAll("-", "");
			fmt = lTrim(fmt, "%");
			fmt = lTrim(fmt, "+");
			fmt = lTrim(fmt, "-");
			fmt = lTrim(fmt, ",");
			fmt = rTrim(fmt, "s");
			fmt = rTrim(fmt, "d");
			if(fmt.contains("."))
			{
				String[] tmp = fmt.split(".");
				fmt = tmp[0];
			}
			fmt = lTrim(fmt, "0");
			fmt = fmt.trim();
			if(fmt.equals(""))
			{
				fmt = "0";
			}
			lengths[i] = Integer.parseInt(fmt);
			i++;		
		}	
		return lengths;
	}
	/**
	 * Calculate all subfields length
	 * @param format Format of the field
	 * @return Total length of the subfields
	 */
	public int getSubfieldLengthTotal(String format)
	{
		Pattern p = Pattern.compile("\\%([0-9\\+\\-\\,\\.]*)[sdf]", Pattern.MULTILINE|Pattern.DOTALL); 
		Matcher m = p.matcher(format);
		String fmt = "";		
		int length = 0;
		while(m.find()) 
		{			
			fmt = m.group(0);
			fmt = fmt.replaceAll("-", "");
			fmt = lTrim(fmt, "%");
			fmt = lTrim(fmt, "+");
			fmt = lTrim(fmt, "-");
			fmt = lTrim(fmt, ",");
			fmt = rTrim(fmt, "s");
			fmt = rTrim(fmt, "d");
			if(fmt.contains("."))
			{
				String[] tmp = fmt.split(".");
				fmt = tmp[0];
			}
			fmt = lTrim(fmt, "0");
			fmt = fmt.trim();
			if(fmt.equals(""))
			{
				fmt = "0";
			}
			length += Integer.parseInt(fmt);			
		}	
		return length;
	}
	/**
	 * Plain text message builder
	 * @param json Data package on common specs
	 * @param format Format message
	 * @param variable Reserved word
	 * @return Return string containing XML format message
	 */
	public String buildString(JSONObject json, String format, String variable)
	{
		String[] data = variable.split(",");		
		Pattern p = Pattern.compile("\\%([0-9\\+\\-\\,\\.]*)[sdf]", Pattern.MULTILINE|Pattern.DOTALL); 
		Matcher m = p.matcher(format);
		String dataType = "s";
		String fmt = "";		
		String result = "";				
		int curstart = 0, curend = 0;
		int i = 0;
		String currentData = "";
		long currentDataInt = 0;
		String formater = "";
		String dataIndex = "";
		while(m.find()) 
		{
			fmt = m.group(0);
			if(fmt.contains("s"))
				dataType = "s";
			if(fmt.contains("d"))
				dataType = "d";			
			curend = m.start();			
			result += substringOf(format, curstart, curend);
			try
			{
				dataIndex = data[i];
			}
			catch(Exception e)
			{
				dataIndex = "";
				e.printStackTrace();
			}
			currentData = "";
			if(dataIndex == null)
			{
				currentData = "";
			}
			else if(!dataIndex.equals(""))
			{
				if(dataType == "s")
				{
					currentData = (String) json.get(dataIndex);
				}
				if(dataType == "d")
				{
					currentData = (String) json.get(dataIndex);
				}
			}
			else
			{
				currentData = "";
			}
			if(!currentData.equals(""))
			{
				if(dataType == "s")
				{
					formater = m.group(0);
					currentData = String.format(formater, currentData);
				}
				if(dataType == "d")
				{
					formater = m.group(0);
					currentData = currentData.replaceAll("[^\\d.\\-]", "");
					currentData = Translator.lTrim(currentData, "0");
					if(currentData.equals(""))
					{
						currentData = "0";
					}
					currentDataInt = Long.parseLong(currentData);
					currentData = String.format(formater, currentDataInt);
				}				
			}			
			result += currentData;
			curstart = m.end();
			i++;			
		}				
		result += substringOf(format, curstart);		
		return result;		
	}
	/**
	 * Because substring in Java not supported multi line string, so use this function instead
	 * @param input Input string
	 * @param start Start offset
	 * @param end End offset
	 * @return Return substring from start to end
	 */
	public static String substringOf(String input, int start, int end)
	{
		byte inputByte[] = input.getBytes();
		String tmp2;
		byte tmp3[] = new byte[1];
		int i;
		String output = "";
		try
		{
			for(i = start; i<end; i++)
			{
				tmp3[0] = inputByte[i];
				tmp2 = new String(tmp3);			
				output += tmp2;		
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return input;
		}
		return output;
	}
	/**
	 * Because substring in Java not supported multi line string, so use this function instead
	 * @param input Input string
	 * @param start Start offset
	 * @return Return substring from start 
	 */
	public static String substringOf(String input, int start)
	{
		byte inputByte[] = input.getBytes();
		int end = input.length();
		String tmp2;
		byte tmp3[] = new byte[1];
		int i;
		String output = "";
		try
		{
			for(i = start; i<end; i++)
			{
				tmp3[0] = inputByte[i];
				tmp2 = new String(tmp3);			
				output += tmp2;		
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return input;
		}
		return output;
	}
	/**
	 * General parsing ISO message. This method only parse message into its fields and does not parse private field.
	 * @param message ISO message 
	 * @return JSONObject contains basic information
	 */
	public JSONObject generalParseIncommingISO(String message)
    {
		String mti_id = (message.length() > 4)?message.substring(0, 4):"0800";
		JSONObject config = new JSONObject();
		
		RoyISO8583 royISO = new RoyISO8583();
		
		String processing_code = "";
		String product_code = "";
		String stan = "";
		String reference_number = "";
		String response_code = "";
		String acq_institution_code = "";
		String fwd_institution_code = "";
		JSONObject json = new JSONObject();
		try
		{
			royISO.parse(message);
        	
			// I hope this one is faster
        	processing_code      = royISO.getValue(3,  "");
        	stan 			     = royISO.getValue(11, "");
        	reference_number     = royISO.getValue(37, "");
        	acq_institution_code = royISO.getValue(32, "");
        	fwd_institution_code = royISO.getValue(33, "");
        	product_code 	     = royISO.getValue(63, "");

        	/**
        	 * Filter before put it into object
        	 */
        	
        	/*
        	If require filter
        	processing_code 	 = ParserBuilder.escapeSQL(processing_code);
        	stan 				 = ParserBuilder.escapeSQL(stan);
        	reference_number 	 = ParserBuilder.escapeSQL(reference_number);
        	product_code 		 = ParserBuilder.escapeSQL(product_code);
        	acq_institution_code = ParserBuilder.escapeSQL(acq_institution_code);
        	fwd_institution_code = ParserBuilder.escapeSQL(fwd_institution_code);
        	*/
        	
          	json.put("processing_code", processing_code);
        	json.put("stan", stan);
        	json.put("reference_number", reference_number);
         	json.put("product_code", product_code);
         	json.put("acq_institution_code", acq_institution_code);
         	json.put("fwd_institution_code", fwd_institution_code);
		}
		catch(Exception e)
		{
          	json.put("processing_code", "");
        	json.put("stan", "");
        	json.put("reference_number", "");
         	json.put("product_code", "");
         	json.put("acq_institution_code", "");
         	json.put("fwd_institution_code", "");
         	e.printStackTrace();
		}
		return json;
    }

	/**
	 * General parsing JSON message to get basic information about the transaction.<br>
	 * Message must contain at least:<br>
	 * 1. processing_code        : Processing code<br>
	 * 2. stan                   : System Trace Audit Number<br>
	 * 3. reference_number       : Reference Number<br>
	 * 4. product_code           : Product code<br>
	 * 5. acq_institution_code   : Acquiring Institution Code<br>
	 * 6. fwd_institution_code   : Forwarding Institution Code<br>
	 * @param message JSON string
	 * @return JSONObject contains basic information about the transaction
	 */
	public JSONObject generalParseIncommingJSON(String message)
	{	
		JSONObject json = new JSONObject();
		try 
		{
			json = new JSONObject(message);
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * General parsing XML message to get basic information about the transaction.<br>
	 * Message must contain at least:<br>
	 * 1. processing_code        : Processing code<br>
	 * 2. stan                   : System Trace Audit Number<br>
	 * 3. product_code           : Product code<br>
	 * 4. acq_institution_code   : Acquiring Institution Code<br>
	 * 5. fwd_institution_code   : Forwarding Institution Code<br>
	 * @param message XML string
	 * @return JSONObject contains basic information about the transaction
	 */
	public JSONObject genaralParseIncommingXML(String message)
	{
		return this.XMLToJSON(message);
	}
	
	/**
	 * Get actual message length received from member
	 * @param message Raw message
	 * @param headerLength Header length
	 * @param headerDirection Header direction. true = LSB left MSB right, false = MSB left LSB right
	 * @return Actual message length
	 */
    public long getLength(String message, int headerLength, boolean headerDirection)
    {
    	int i;
    	long result = 0;
    	byte[] tmp = new byte[4];
    	tmp = message.getBytes();
    	// LTR
    	if(headerDirection)
    	{
	    	for(i = 0; i < headerLength; i++)
	    	{
	    		result *= 256;
	    		result += tmp[i];
	    	}
    	}
    	else
    	{
	    	for(i = headerLength-1; i >= 0; i--)
	    	{
	    		result *= 256;
	    		result += tmp[i];
	    	}  		
    	}
    	return result;
    }
    /**
     * Create header contains message length
     * @param messageLength Message length
     * @param headerLength Header length
     * @param headerDirection Header direction
     * @return Array byte of message header header
     */
    public byte[] createHeader(int messageLength, int headerLength, boolean headerDirection)
    {
    	int i; 
    	int j = 0;
    	int k = 0;
       	byte[] tmp;
       	tmp = new byte[headerLength];
       	// LTR
    	if(headerDirection)
    	{
    		j = messageLength;
	    	for(i = 0; i < headerLength; i++)
	    	{
	    		k = j % 256;
	    		tmp[i] = (byte) k;
	    		j = j / 256;
	    	}
      	}
    	else
    	{
       		j = messageLength;
	    	for(i = headerLength-1; i >= 0; i--)
	    	{
	    		k = j % 256;
	    		tmp[i] = (byte) k;
	    		j = j / 256;
	    	}  		
    	}
    	return tmp;
    }
    /**
     * Concat 2 array of byte
     * @param bytes1 First byte
     * @param bytes2 Second byte
     * @return Array byte combination of 2 array of byte given
     */
    public static byte[] addBytes( byte[] bytes1, byte[] bytes2 )
	{
		byte[] bb = new byte[ bytes1.length + bytes2.length ];
		int count = 0;
		for( int v=0; v<bytes1.length; v++ )
		{
			bb[count] = bytes1[v];
			count++;
		}
		for( int v=0; v<bytes2.length; v++ )
		{
			bb[count] = bytes2[v];
			count++;
		}
		return bb;
	}
    /**
     * Concat 3 array of byte
     * @param bytes1 First byte
     * @param bytes2 Second byte
     * @param bytes3 Third byte
     * @return Array byte combination of 3 array of byte given
     */
    public static byte[] addBytes( byte[] bytes1, byte[] bytes2, byte[] bytes3 )
	{
		byte[] bbb = new byte[ bytes1.length + bytes2.length + bytes3.length ];
		int count = 0;
		for( int v=0; v<bytes1.length; v++ )
		{
			bbb[count] = bytes1[v];
			count++;
		}
		for( int v=0; v<bytes2.length; v++ )
		{
			bbb[count] = bytes2[v];
			count++;
		}
		for( int v=0; v<bytes3.length; v++ )
		{
			bbb[count] = bytes3[v];
			count++;
		}
		return bbb;
	}
    /**
     * Apply function to data
     * @param value Value of data
     * @param option Function
     * @return String result of the function
     */
    public String applyFunction(String value, String option)
    {
    	/**
    	 * substring(start)
    	 * substring(start, end)
    	 * left(length)
    	 * right(length)
    	 * lower()
    	 * upper()
    	 * reverse()
    	 * times(times)
    	 * division(div)
    	 * add(add)
    	 * subtract(sub)
    	 * inverse(n)
    	 * before(string)
    	 * after(string)
    	 */
    	option = option.trim();
    	String opt = "";
    	String val = value;
    	
    	int iargs0, iargs1;
    	double dargs0, dval;
    	
    	if(option.contains("substring("))
    	{
        	// TODO: substring
    		opt = option.substring("substring".length());
    		opt = lTrim(opt, "\\(");
    		opt = rTrim(opt, "\\)");
    		opt = opt.trim();
    		
    		if(opt.length() > 0)
    		{
    			if(opt.contains(","))
    			{
    				String[] args = opt.split(",");
    				args[0] = args[0].trim();
    				args[1] = args[1].trim();
    				if(args[0].equals(""))
    				{
    					args[0] = "0";
    				}
    				if(args[1].equals(""))
    				{
    					args[1] = "0";
    				}
    				iargs0 = Integer.parseInt(args[0]);
    				iargs1 = Integer.parseInt(args[1]);
    				if(iargs0 < 0) iargs0 = 0;
    				if(iargs1 < 0) iargs1 = 0;
    				if(iargs1 > iargs0)
    				{
    					iargs1 = iargs0;
    				}
    				if(val.length() == 0)
    				{
    					
    				}
    				else if(value.length() < iargs0)
    				{
    					val = "";
    				}
    				else if(value.length() < iargs1)
    				{
    					val = val.substring(iargs0);
    				}
    				else if(value.length() >= iargs1)
    				{
    					val = val.substring(iargs0, iargs1);
    				}
    				else
    				{
    					// do nothing
    				}
    			}
    			else
    			{
    				if(opt.equals(""))
    				{
    					opt = "0";
    				}
    				iargs0 = Integer.parseInt(opt);
    				if(iargs0 < 0) iargs0 = 0;
    				if(value.length() < iargs0)
    				{
    					val = "";
    				}
    				else
    				{
    					val = val.substring(iargs0);
    				}
    			}
    		}
    	}
     	else if(option.contains("left("))
    	{
     	   	// TODO: left
     		opt = option.substring("left".length());
    		opt = lTrim(opt, "\\(");
    		opt = rTrim(opt, "\\)");
    		opt = opt.trim();
    		
    		if(opt.length() > 0)
    		{
				if(opt.equals(""))
				{
					opt = "0";
				}
				iargs0 = Integer.parseInt(opt);
				if(iargs0 < 0) iargs0 = 0;
				if(value.length() < iargs0)
				{
					
				}
				else
				{
					val = val.substring(0, iargs0);
				}
     		}
    	}
     	else if(option.contains("right("))
    	{
     	   	// TODO: right
     		opt = option.substring("right".length());
    		opt = lTrim(opt, "\\(");
    		opt = rTrim(opt, "\\)");
    		opt = opt.trim();
    		
    		if(opt.length() > 0)
    		{
				if(opt.equals(""))
				{
					opt = "0";
				}
				iargs0 = Integer.parseInt(opt);
				
				if(value.length() < iargs0)
				{					
				}
				else
				{
					int length = value.length();
					int start = length - iargs0;
					int end = length;
					if(start < 0)
					{
						start = 0;
					}
					if(end < 0)
					{
						end = 0;
					}
					if(end < start)
					{
						end = start;
					}
					val = val.substring(start, end);
				}
     		}
    	}
    	else if(option.contains("upper("))
    	{
    		val = val.toUpperCase();
    	}
    	else if(option.contains("lower("))
    	{
    		val = val.toLowerCase();
    	}
    	else if(option.contains("reverse("))
    	{
    		// TODO: reverse
    		try
    		{
    			val = new StringBuilder(val).reverse().toString();
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
     	else if(option.contains("times("))
    	{
     	   	// TODO: times
     		opt = option.substring("times".length());
    		opt = lTrim(opt, "\\(");
    		opt = rTrim(opt, "\\)");
    		opt = opt.trim();
    		
    		if(opt.length() > 0)
    		{
    			opt = opt.replaceAll("[^\\d.\\-]", "");
				if(opt.equals(""))
				{
					opt = "0";
				}
				dargs0 = Double.parseDouble(opt);
				val = val.replaceAll("[^\\d.\\-]", "");
				if(val.equals(""))
				{
					val = "0";
				}			
				dval = Double.parseDouble(val);
				dval = dval * dargs0;
				val = dval + "";
     		}
    	}
     	else if(option.contains("division("))
    	{
     	   	// TODO: division
     		opt = option.substring("division".length());
    		opt = lTrim(opt, "\\(");
    		opt = rTrim(opt, "\\)");
    		opt = opt.trim();
    		
    		if(opt.length() > 0)
    		{
    			opt = opt.replaceAll("[^\\d.\\-]", "");
				if(opt.equals(""))
				{
					opt = "0";
				}
				double args0 = Double.parseDouble(opt);
				val = val.replaceAll("[^\\d.\\-]", "");
				if(val.equals(""))
				{
					val = "0";
				}			
				dval = Double.parseDouble(val);
				dval = dval / args0;
				val = dval + "";
     		}
    	}
     	else if(option.contains("add("))
    	{
     	   	// TODO: add
     		opt = option.substring("add".length());
    		opt = lTrim(opt, "\\(");
    		opt = rTrim(opt, "\\)");
    		opt = opt.trim();
    		
    		if(opt.length() > 0)
    		{
    			opt = opt.replaceAll("[^\\d.\\-]", "");
				if(opt.equals(""))
				{
					opt = "0";
				}
				double args0 = Double.parseDouble(opt);
				val = val.replaceAll("[^\\d.\\-]", "");
				if(val.equals(""))
				{
					val = "0";
				}			
				dval = Double.parseDouble(val);
				dval = dval + args0;
				val = dval + "";
     		}
    	}
     	else if(option.contains("subtract("))
    	{
     	   	// TODO: subtract
     		opt = option.substring("subtract".length());
    		opt = lTrim(opt, "\\(");
    		opt = rTrim(opt, "\\)");
    		opt = opt.trim();
    		
    		if(opt.length() > 0)
    		{
    			opt = opt.replaceAll("[^\\d.\\-]", "");
				if(opt.equals(""))
				{
					opt = "0";
				}
				double args0 = Double.parseDouble(opt);
				val = val.replaceAll("[^\\d.\\-]", "");
				if(val.equals(""))
				{
					val = "0";
				}			
				dval = Double.parseDouble(val);
				dval = dval - args0;
				val = dval + "";
     		}
    	}
     	else if(option.contains("inverse("))
    	{
     	   	// TODO: inverse
     		opt = option.substring("inverse".length());
    		opt = lTrim(opt, "\\(");
    		opt = rTrim(opt, "\\)");
    		opt = opt.trim();
    		opt = opt.replaceAll("[^\\d.\\-]", "");
			if(opt.equals(""))
			{
				opt = "1";
			}
			double args0 = Double.parseDouble(opt);
			
			val = val.replaceAll("[^\\d.\\-]", "");
			if(val.equals(""))
			{
				val = "0";
			}			
			dval = Double.parseDouble(val);
			if(dval == 0)
			{
				val = dval + "";
			}
			else
			{
				dval = args0 / dval;
				val = dval + "";
			}
     		
    	}
    	else if(option.contains("before("))
    	{
     	   	// TODO: before
     		opt = option.substring("before".length());
    		opt = lTrim(opt, "\\(");
    		opt = rTrim(opt, "\\)");
    		opt = opt.trim();
    		opt = urlDecode(opt);
   		
    		if(opt.length() > 0)
    		{
				val = opt + val;
     		}
    	}
    	else if(option.contains("after("))
    	{
     	   	// TODO: after
     		opt = option.substring("after".length());
    		opt = lTrim(opt, "\\(");
    		opt = rTrim(opt, "\\)");
    		opt = opt.trim();
    		opt = urlDecode(opt); 		
    		if(opt.length() > 0)
    		{
				val = val + opt;
     		}
    	}
    	return val;
    }
    /**
     * Apply options to transaction data. It will modify data
     * @param json JSONObject contains transaction data
     * @param options String containing the function
     * @return JSONObject after the operation
     */
 	public JSONObject applyOption(JSONObject json, String options)
    {
    	if(options.length() > 0)
    	{
	      	options = options.replaceAll("substring \\(", "substring\\(");
	       	options = options.replaceAll("left \\(", "left\\(");
	       	options = options.replaceAll("right \\(", "right\\(");
	       	options = options.replaceAll("lower \\(", "lower\\(");
	       	options = options.replaceAll("upper \\(", "upper\\(");
	       	options = options.replaceAll("reverse \\(", "reverse\\(");
	       	options = options.replaceAll("times \\(", "times\\(");
	       	options = options.replaceAll("division \\(", "division\\(");
	       	options = options.replaceAll("add \\(", "add\\(");
	       	options = options.replaceAll("subtract \\(", "subtract\\(");
	       	options = options.replaceAll("inverse \\(", "inverse\\(");
	       	options = options.replaceAll("before \\(", "before\\(");
	       	options = options.replaceAll("after \\(", "after\\(");
	 
	       	options = options.replaceAll("substring \\(", "substring\\(");
	       	options = options.replaceAll("left \\(", "left\\(");
	       	options = options.replaceAll("right \\(", "right\\(");
	       	options = options.replaceAll("lower \\(", "lower\\(");
	       	options = options.replaceAll("upper \\(", "upper\\(");
	       	options = options.replaceAll("reverse \\(", "reverse\\(");
	       	options = options.replaceAll("times \\(", "times\\(");
	       	options = options.replaceAll("division \\(", "division\\(");
	       	options = options.replaceAll("add \\(", "add\\(");
	       	options = options.replaceAll("subtract \\(", "subtract\\(");
	       	options = options.replaceAll("inverse \\(", "inverse\\(");
	       	options = options.replaceAll("before \\(", "before\\(");
	       	options = options.replaceAll("after \\(", "after\\(");
	  
	    	/**
	    	 * TODO : Function list provided bellow
	    	 * substring(start)
	    	 * substring(start, end)
	    	 * left(length)
	    	 * right(length)
	    	 * lower()
	    	 * upper()
	    	 * reverse()
	    	 * times(times)
	    	 * division(div)
	    	 * add(add)
	    	 * subtract(sub)
	    	 * inverse(n)
	    	 * before(string)
	    	 * after(string)
	    	 */
	    	if(options.length() == 0)
	    	{  		
	    	}
	    	else
	    	{
	    		String[] options_array;
	    		String key = "";
	    		String opt = "";
	    		String option = "";
	    		String value = "";
	    		String[] args;
	    		if(options.contains("&"))
	    		{
	    			options_array = options.split("&");
	    		}
	    		else
	    		{
	    			options_array = new String[1];
	    			options_array[0] = options;
	    		}
	    		int count, i, j;
	    		count = options_array.length;
	    		
	    		for(i = 0; i<count; i++)
	    		{
	    			opt = options_array[i];
	    			if(opt.contains("="))
	    			{
	    				args = opt.split("=", 2);
	    				key = args[0].trim();
	    				option = args[1];
	    				try
	    				{
	    					if(json.has(key))
	    					{
	    						value = json.optString(key, "").toString();
	    						if(options.length() > 0)
	    						{
	    							value = this.applyFunction(value, option);
	    						}
	    						json.put(key, value);
	    					}
	    				}
	    				catch(Exception e)
	    				{
	    					e.printStackTrace();
	    				}
	    			}
	    		}
	    	}
    	}
    	return json;
    }
    /**
     * Decode URL
     * @param input Encoded string
     * @return Decoded string
     */
    public static String urlDecode(String input)
    {
    	String result = "";
		try 
		{
			result = java.net.URLDecoder.decode(input, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
    	return result;
    }
    /**
     * Encode URL
     * @param input String to be encoded
     * @return Encoded string
     */
    public static String urlEncode(String input)
    {
    	String result = "";
		try 
		{
			result = java.net.URLEncoder.encode(input, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
    	return result;
    }
	/**
	 * Overrides <strong>toString</strong> method to convert object to JSON String. This method is useful to debug or show value of each properties of the object.
	 */
	public String toString()
	{
		Field[] fields = this.getClass().getDeclaredFields();
		int i, max = fields.length;
		String fieldName = "";
		String fieldType = "";
		String ret = "";
		String value = "";
		boolean skip = false;
		int j = 0;
		for(i = 0; i < max; i++)
		{
			fieldName = fields[i].getName().toString();
			fieldType = fields[i].getType().toString();
			if(i == 0)
			{
				ret += "{";
			}
			if(fieldType.equals("int") || fieldType.equals("long") || fieldType.equals("float") || fieldType.equals("double") || fieldType.equals("boolean"))
			{
				try 
				{
					value = fields[i].get(this).toString();
				}  
				catch (Exception e) 
				{
					value = "0";
				}
				skip = false;
			}
			else if(fieldType.contains("String"))
			{
				try 
				{
					value = "\""+Utility.escapeJSON((String) fields[i].get(this))+"\"";
				} 
				catch (Exception e) 
				{
					value = "\""+"\"";
				}
				skip = false;
			}
			else
			{
				value = "\""+"\"";
				skip = true;
			}
			if(!skip)
			{
				if(j > 0)
				{
					ret += ",";
				}
				j++;
				ret += "\r\n\t\""+fieldName+"\":"+value;
			}
			if(i == max-1)
			{
				ret += "\r\n}";
			}
		}
		return ret;
	}
	public JSONObject arrayToObject(JSONArray config)
	{
		JSONObject jo = new JSONObject();
		JSONObject output = new JSONObject();
		int length = config.length();
		int i, j;
		String field = "";
		for(i = 0; i<length; i++)
		{
			jo = (JSONObject) config.get(i);
			
			field = jo.get("field").toString().trim();
			output.put("f"+field, jo);
		}
		return output;
	}
}
