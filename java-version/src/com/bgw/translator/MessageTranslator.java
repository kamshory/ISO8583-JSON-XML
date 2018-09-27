package com.bgw.translator;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bgw.utility.Utility;

/**
 * MessageTranslator is ISO - JSON - XML message translator. 
 * @author Kamshory, MT
 *
 */
public class MessageTranslator extends Translator{
	public MessageTranslator()
	{
		
	}
	/**
	 * Parse ISO 8583 message and convert it into JSONObject using configuration specs
	 * @param message Raw ISO 8583 message
	 * @param formatMap Object containing format and variable
	 * @return Return JSON Object containing common specs that exists on the message
	 */
	public JSONObject parseISO8583(String message, JSONArray formatMap)
	{
		return this.parseISO8583(message, this.arrayToObject(formatMap));
	}
	/**
	 * Parse ISO 8583 message and convert it into JSONObject using configuration specs
	 * @param message Raw ISO 8583 message
	 * @param formatMap Object containing format and variable
	 * @return Return JSON Object containing common specs that exists on the message
	 */
	public JSONObject parseISO8583(String message, JSONObject formatMap)
	{
		JSONObject json = new JSONObject();
		if(!formatMap.equals(new JSONObject()))
		{		
			String tmp1 = "", tmp2 = "", key = "", value = "";
			if(message.length() > 36)
			{
				String type = (message.length() > 4)?message.substring(0, 4):"0800";
				this.royISO8583 = new RoyISO8583(type, formatMap);
				royISO8583.parse(message);
				String[] arrFormat;
				try 
				{
					try
					{
										
						String[] fieldFromTemplate = this.listStringToArrayString(this.listFormatMapKey(formatMap));			
						int idx = 2;
						String subfield = "";
						String fieldName = "";
						int maxField = 128;	
						
						String format, variable;
						int[] lengths;
						int j, begin = 0, end = 0;
						String[] variables;
						JSONObject fieldFormat;
						
						for(idx = 2; idx <= maxField; idx++)
						{
							fieldName = "f"+idx;
							if(royISO8583.hasField(idx))
							{
								subfield = royISO8583.getValue(idx).toString();
								if(inArray(fieldFromTemplate, fieldName))
								{
									String jostr = formatMap.get(fieldName).toString();								
									try 
									{
										fieldFormat = new JSONObject(jostr);
										format = fieldFormat.get("format").toString();
										variable = fieldFormat.get("variable").toString();
										variable = variable.replaceAll(" ", "");
										variable = variable.replaceAll(",,,", ",");
										variable = variable.replaceAll(",,", ",");
										variables = variable.split(","); 
										tmp1 = "";
										tmp2 = "";
										key = "";
										value = "";
										if(this.hasSubfield(format))
										{
											lengths = this.getSubfieldLength(format);
											arrFormat = null; 
											if(format.contains("%"))
											{
												arrFormat = format.split("%");
											}
											begin = 0; end = 0;
											for(j = 0; j < lengths.length; j++)
											{
												end = begin + lengths[j];
												if(subfield.length() >= end && begin >= 0 && begin <= end)
												{
													tmp1 = subfield.substring(begin, end);
												}
												else if(begin >= 0 && subfield.length() >= begin)
												{
													tmp1 = subfield.substring(begin);
												}
												else
												{
													tmp1 = "";
												}
												tmp2 = tmp1;
												
												if(arrFormat[j+1] != null)
												{
													if(arrFormat[j+1].contains("s"))
													{
														tmp2 = tmp1;
													}
													if(arrFormat[j+1].contains("d"))
													{
														tmp2 = lTrim(tmp1, "0");
														if(tmp2.equals(""))
														{
															tmp2 = "0";
														}
													}
												}
												else
												{
													tmp2 = tmp1;
												}
												key = variables[j].trim();
												value = tmp2;
												json.put(key, value);
												begin = end;
											}
										}
										else
										{
											key = variables[0].trim();
											tmp1 = subfield;
											if(format.contains("s"))
											{
												value = tmp1;
											}
											if(format.contains("d"))
											{
												value = lTrim(tmp1, "0");
												if(value.equals(""))
												{
													value = "0";
												}
											}											
											json.put(key, value);										
										}							
									} 
									catch (JSONException e) 
									{
										e.printStackTrace();
									}							
								}
								else
								{
									value = "";
								}
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				} 
				catch (NullPointerException e) 
				{
					e.printStackTrace();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			Utility.println("Unable to parse message because format map is empty. Please check spec.");
		}
		return json;
	}	
	/**
	 * Parse ISO 8583 message and convert it into JSONObject using configuration specs
	 * @param royISO8583 RoyISO8583 object instead of text
	 * @param formatMap Object containing format and variable
	 * @return Return JSON Object containing common specs that exists on the message
	 */
	public JSONObject parseISO8583(RoyISO8583 royISO8583, JSONArray formatMap)
	{
		return this.parseISO8583(royISO8583, this.arrayToObject(formatMap));
	}
	/**
	 * Parse ISO 8583 message and convert it into JSONObject using configuration specs
	 * @param royISO8583 RoyISO8583 object instead of text
	 * @param formatMap Object containing format and variable
	 * @return Return JSON Object containing common specs that exists on the message
	 */
	public JSONObject parseISO8583(RoyISO8583 royISO8583, JSONObject formatMap)
	{
		JSONObject json = new JSONObject();
		if(!formatMap.equals(new JSONObject()))
		{		
			String tmp1 = "", tmp2 = "", key = "", value = "";
			try 
			{
				try
				{
									
					String[] fieldFromTemplate = this.listStringToArrayString(this.listFormatMapKey(formatMap));			
					int idx = 2;
					String subfield = "";
					String fieldName = "";
					int maxField = 128;	
					
					String format, variable;
					int[] lengths;
					int j, begin = 0, end = 0;
					String[] variables;
					String[] arrFormat;
					JSONObject fieldFormat;
					for(idx = 2; idx <= maxField; idx++)
					{
						fieldName = "f"+idx;
						if(royISO8583.hasField(idx))
						{
							subfield = royISO8583.getValue(idx).toString();
							if(inArray(fieldFromTemplate, fieldName))
							{
								String jostr = formatMap.get(fieldName).toString();								
								try 
								{
									fieldFormat = new JSONObject(jostr);
									
									format = fieldFormat.get("format").toString();
									variable = fieldFormat.get("variable").toString();
									variable = variable.replaceAll(" ", "");
									variable = variable.replaceAll(",,,", ",");
									variable = variable.replaceAll(",,", ",");
									variables = variable.split(","); 
									tmp1 = "";
									tmp2 = "";
									key = "";
									value = "";
									if(this.hasSubfield(format))
									{
										lengths = this.getSubfieldLength(format);
										arrFormat = null; 
										if(format.contains("%"))
										{
											arrFormat = format.split("%");
										}
										begin = 0; end = 0;
										for(j = 0; j < lengths.length; j++)
										{
											end = begin + lengths[j];
											if(subfield.length() >= end && begin >= 0 && begin <= end)
											{
												tmp1 = subfield.substring(begin, end);
											}
											else if(begin >= 0 && subfield.length() >= begin)
											{
												tmp1 = subfield.substring(begin);
											}
											else
											{
												tmp1 = "";
											}
											tmp2 = tmp1;
											
											if(arrFormat[j+1] != null)
											{
												if(arrFormat[j+1].contains("s"))
												{
													tmp2 = tmp1;
												}
												if(arrFormat[j+1].contains("d"))
												{
													tmp2 = lTrim(tmp1, "0");
													if(tmp2.equals(""))
													{
														tmp2 = "0";
													}
												}
											}
											else
											{
												tmp2 = tmp1;
											}
											key = variables[j].trim();
											value = tmp2;
											json.put(key, value);
											begin = end;
										}
									}
									else
									{
										key = variables[0].trim();
										tmp1 = subfield;
										if(format.contains("s"))
										{
											value = tmp1;
										}
										if(format.contains("d"))
										{
											value = lTrim(tmp1, "0");
											if(value.equals(""))
											{
												value = "0";
											}
										}											
										json.put(key, value);										
									}							
								} 
								catch (JSONException e) 
								{
									e.printStackTrace();
								}							
							}
							else
							{
								value = "";
							}
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			} 
			catch (NullPointerException e) 
			{
				e.printStackTrace();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return json;
	}
	
	/**
	 * ISO 8583 message builder
	 * @param json Data package on common specs
	 * @param formatMap Object containing format, variable, minimum length, maximum length, and other options
	 * @param mti_id Message type
	 * @return Return byte contains ISO 8583 message
	 */
	public byte[] buildISO8583(JSONObject json, JSONArray formatMap, String mti_id)
	{
		return this.buildISO8583(json, this.arrayToObject(formatMap), mti_id);
	}
	/**
	 * ISO 8583 message builder
	 * @param json Data package on common specs
	 * @param formatMap Object containing format, variable, minimum length, maximum length, and other options
	 * @param mti_id Message type
	 * @return Return byte contains ISO 8583 message
	 */
	public byte[] buildISO8583(JSONObject json, JSONObject formatMap, String mti_id)
	{
		this.royISO8583 = new RoyISO8583(mti_id, formatMap);
		
		byte[] message = null;
		JSONObject row = new JSONObject();
		String format = "";
		String variable = "";
		int field_length = 0;
		String data = "";
		String subdata = "";
		String fmt = "";
		int field = 0;
		String options = "";
		String key_tmp = "";	
		String field_type = "";
		try 
		{
			try
			{
	            Set<?> s =  formatMap.keySet();
			    Iterator<?> iter = s.iterator();
			    do
			    {
			        String key = iter.next().toString();
			        key_tmp = key.replaceAll("f", "");
			        key_tmp = key_tmp.replaceAll("_", "");
			        if(key_tmp.equals(""))
			        {
			        	key_tmp = "0";
			        }
			        field = Integer.parseInt(key_tmp);
			        
			        if(formatMap.get(key) != null)
			        {			        	
				        String keySpecs = formatMap.get(key).toString();
				        row = new JSONObject(keySpecs);			        
				        format = row.get("format").toString();
				        variable = row.get("variable").toString();
				        variable = variable.trim();
				        field_length = Integer.parseInt(row.get("field_length").toString());
				        options = row.get("options").toString();
				        field_type = row.get("type").toString();
				        if(options.length() > 0)
				        {
				        	json = this.applyOption(json, options);
				        }
				        String subfield = "";	
				        if(variable.length() > 0)
				        {
					        if(variable.contains(","))
					        {
					        	// split format
					        	String subformat[] = listSubformat(format);
					        	String vars[] = variable.split(",");
					        	String subfiedldata = "";
					        	int[] sfl = getSubfieldLength(format);
					        	int i;
					        	for(i = 0; i<vars.length && i<subformat.length; i++)
					        	{
					        		vars[i] = vars[i].trim();
					        		if(json.get(vars[i]) != null)
					        		{
					        			subdata = json.get(vars[i]).toString();
					        		}
					        		else
					        		{
					        			subdata = "";
					        		}
					        		if(subformat[i].contains("d"))
					        		{
					        			String tmp = subdata;
					        			tmp = tmp.replaceAll("[^\\d.\\-]", "");
					        			tmp = Translator.lTrim(tmp, "0");
					        			if(tmp.equals(""))
					        			{
					        				tmp = "0";
					        			}
					        			long int_data = Long.parseLong(tmp);
					        			subfiedldata = String.format(subformat[i], int_data);
					        			if(subfiedldata.length() > sfl[i])
					        			{
					        				subfiedldata = right(subfiedldata, sfl[i]);
					        			}
					        			subfield += subfiedldata;
					        		}
					        		else
					        		{
					        			subfiedldata = String.format(subformat[i], subdata);
					        			if(subfiedldata.length() > sfl[i])
					        			{
					        				subfiedldata = left(subfiedldata, sfl[i]);
					        			}
					        			subfield += subfiedldata;
					        		}				        		
					        	}
					        	field_length = getSubfieldLengthTotal(format);
					        	if(field_length > 0)
					        	{
					        		fmt = "%-"+field_length+"s";
					        		data = String.format(fmt, subfield);
					        	}
					        	else
					        	{
					        		fmt = "%-s";
					        		data = String.format(fmt, subfield);
					        	}
					        }
					        else
					        {
					        	variable = variable.trim();
					        	if(json.get(variable) != null)
					        	{
					        		subfield = json.get(variable).toString();
					        	}
					        	else
					        	{
					        		subfield = "";
					        	}
					        	String data_type = "string";
					        	if(format.contains("d") || field_type.equals("NUMERIC"))
					        	{
					        		subfield = subfield.replaceAll("[^\\d.\\-]", "");
					        		subfield = Translator.lTrim(subfield, "0");
					        		if(subfield.equals(""))
					        		{
					        			subfield = "0";
					        		}
					        		long val = Long.parseLong(subfield);
					        		subfield = String.format(format, val);
					        		data_type = "numeric";
					        	}
					        	
					        	field_length = getSubfieldLengthTotal(format);
					        	if(!data_type.equals("numeric"))
					        	{
						        	if(field_length > 0)
						        	{
						        		fmt = "%-"+field_length+"s";
						        		data = String.format(fmt, subfield);
						        		
						        	}
						        	else
						        	{
						        		fmt = "%s";
						        		data = String.format(fmt, subfield);					        		
						        	}
						        }
					        	else
					        	{
					        		subfield = subfield.replaceAll("[^\\d.\\-]", "");
					        		subfield = Translator.lTrim(subfield, "0");
					        		if(subfield.equals(""))
					        		{
					        			subfield = "0";
					        		}
					        		long val = Long.parseLong(subfield);
						        	if(field_length > 0)
						        	{
						        		fmt = "%0"+field_length+"d";
						        		data = String.format(fmt, val);					        		
						        	}
						        	else
						        	{
						        		fmt = "%d";
						        		data = String.format(fmt, val);					        		
						        	}				        		
					        	}
					        }				        
					        if(data.length() < field_length)
					        {
					        	data = String.format("%-"+field_length+"s", data);
					        }
					        if(data.length() > field_length)
					        {
					        	data = data.substring(0, field_length);
					        }
					        if(field_type.equals("AMOUNT"))
					        {
					        	long data_amount = 0;
					        	data = Translator.lTrim(data, "0");
					        	if(data.equals(""))
					        	{
					        		data = "0";
					        	}
					        	data_amount = Long.parseLong(data);
					        	data = String.format("%012d", data_amount);
					        	royISO8583.addValue(field, data, "AMOUNT", 12);
					        }
					        else if(field_type.equals("CNUMERIC"))
					        {
					        	long data_amount = 0;
					        	data = Translator.lTrim(data, "0");
					        	if(data.equals(""))
					        	{
					        		data = "0";
					        	}
					        	data_amount = Long.parseLong(data);
					        	data = String.format("C%0"+field_length+"d", data_amount);
					        	royISO8583.addValue(field, data, "CNUMERIC", field_length+1);
					        }
					        else if(field_type.equals("DNUMERIC"))
					        {
					        	long data_amount = 0;
					        	data = Translator.lTrim(data, "0");
					        	if(data.equals(""))
					        	{
					        		data = "0";
					        	}
					        	data_amount = Long.parseLong(data);
					        	data = String.format("D%0"+field_length+"d", data_amount);
					        	royISO8583.addValue(field, data, "DNUMERIC", field_length+1);
					        }
					        else if(field_type.equals("NUMERIC"))
					        {
					        	long data_amount = 0;					        	
					        	data = Translator.lTrim(data, "0");
					        	data = data.trim();
					        	if(data.equals(""))
					        	{
					        		data = "0";
					        	}
					        	data_amount = Long.parseLong(data);
					        	data = String.format("%0"+field_length+"d", data_amount);
					        	royISO8583.addValue(field, data, "NUMERIC", field_length);
					        }
					        else
					        {
					        	if((field_type.equals("LVAR") || field_type.equals("LLVAR") || field_type.equals("LLLVAR")) && !variable.contains(","))
					        	{
					        		field_length = data.length();
					        		royISO8583.addValue(field, data, field_type, field_length);
					        	}
					        	else
					        	{
					        		royISO8583.addValue(field, data, field_type, field_length);
					        	}
					        }	
				        }
			        }
			    }
			    while(iter.hasNext());
			    message = royISO8583.toString().getBytes();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		} 
		catch (NullPointerException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return message;
	}
	/**
	 * Build ISO 8583 message based on data, configuration and type given
	 * @param json JSONObject contains data
	 * @param formatMap Specs configuration
	 * @param mti_id Message Type Indicator
	 * @return Array byte contains ISO 8583 message
	 */
	public byte[] buildRoyISO8583(JSONObject json, JSONObject formatMap, String mti_id)
	{
		byte[] message = null;
		if(!formatMap.equals(new JSONObject()))
		{
			RoyISO8583 royIso = new RoyISO8583(mti_id, formatMap);
			
			JSONObject row = new JSONObject();
			String format = "";
			String variable = "";
			int field_length = 0;
			String data = "";
			String subdata = "";
			String fmt = "";
			int field = 0;
			String options = "";
			String key_tmp = "";	
			String field_type = "";
			String keySpecs;
			String subfield;
			String subfiedldata;
			String[] vars;
			int[] sfl;
			int i;
			long int_data;
			String data_type;
			long val;
			try 
			{
				try
				{
		            Set<?> s =  formatMap.keySet();
				    Iterator<?> iter = s.iterator();
				    String key = "";
				    String[] subformat;
				    String tmp;
				    do
				    {
				        key = iter.next().toString();
				        key_tmp = key.replaceAll("f", "");
				        key_tmp = key_tmp.replaceAll("_", "");
				        if(key_tmp.equals(""))
				        {
				        	key_tmp = "0";
				        }
				        field = Integer.parseInt(key_tmp);
				        
				        if(formatMap.get(key) != null)
				        {			        	
					        keySpecs = formatMap.get(key).toString();
					        row = new JSONObject(keySpecs);			        
					        format = row.get("format").toString();
					        variable = row.get("variable").toString();
					        field_length = Integer.parseInt(row.get("field_length").toString());
					        options = row.get("options").toString();
					        field_type = row.get("type").toString();
					        if(options.length() > 0)
					        {
					        	json = this.applyOption(json, options);
					        }
					        subfield = "";			        
					        if(variable.contains(","))
					        {
					        	// split format
					        	subformat = listSubformat(format);
					        	sfl = getSubfieldLength(format);
					        	vars = variable.split(",");
					        	subfiedldata = "";
					        	for(i = 0; i<vars.length && i<subformat.length; i++)
					        	{
					        		vars[i] = vars[i].trim();
					        		if(json.get(vars[i]) != null)
					        		{
					        			subdata = json.get(vars[i]).toString();
					        		}
					        		else
					        		{
					        			subdata = "";
					        		}
					        		if(subformat[i].contains("d"))
					        		{
					        			tmp = subdata;
					        			tmp = tmp.replaceAll("[^\\d.\\-]", "");
					        			tmp = Translator.lTrim(tmp, "0");
					        			if(tmp.equals(""))
					        			{
					        				tmp = "0";
					        			}
					        			int_data = Long.parseLong(tmp);
					        			subfiedldata = String.format(subformat[i], int_data);
					        			if(subfiedldata.length() > sfl[i])
					        			{
					        				subfiedldata = left(subfiedldata, sfl[i]);
					        			}
					        			subfield += subfiedldata;
					        		}
					        		else
					        		{
					        			subfiedldata = String.format(subformat[i], subdata);
					        			if(subfiedldata.length() > sfl[i])
					        			{
					        				subfiedldata = left(subfiedldata, sfl[i]);
					        			}
					        			subfield += subfiedldata;
					        		}				        		
					        	}
					        	field_length = getSubfieldLengthTotal(format);
					        	if(field_length > 0)
					        	{
					        		fmt = "%-"+field_length+"s";
					        		data = String.format(fmt, subfield);
					        	}
					        	else
					        	{
					        		fmt = "%-s";
					        		data = String.format(fmt, subfield);
					        	}
					        }
					        else
					        {
					        	variable = variable.trim();
					        	if(json.get(variable) != null)
					        	{
					        		subfield = json.get(variable).toString();
					        	}
					        	else
					        	{
					        		subfield = "";
					        	}
					        	data_type = "string";
					        	if(format.contains("d") || field_type.equals("NUMERIC"))
					        	{
					        		subfield = subfield.replaceAll("[^\\d.\\-]", "");
					        		subfield = Translator.lTrim(subfield, "0");
					        		if(subfield.equals(""))
					        		{
					        			subfield = "0";
					        		}
					        		val = Long.parseLong(subfield);
					        		subfield = String.format(format, val);
					        		data_type = "numeric";
					        	}
					        	
					        	field_length = getSubfieldLengthTotal(format);
					        	if(!data_type.equals("numeric"))
					        	{
						        	if(field_length > 0)
						        	{
						        		fmt = "%-"+field_length+"s";
						        		data = String.format(fmt, subfield);
						        	}
						        	else
						        	{
						        		fmt = "%s";
						        		data = String.format(fmt, subfield);
						        	}
						        }
					        	else
					        	{
					        		subfield = subfield.replaceAll("[^\\d.\\-]", "");
					        		subfield = Translator.lTrim(subfield, "0");
					        		if(subfield.equals(""))
					        		{
					        			subfield = "0";
					        		}
					        		val = Long.parseLong(subfield);
						        	if(field_length > 0)
						        	{
						        		fmt = "%0"+field_length+"d";
						        		data = String.format(fmt, val);
						        	}
						        	else
						        	{
						        		fmt = "%d";
						        		data = String.format(fmt, val);
						        		
						        	}				        		
					        	}
					        }				        
					        if(data.length() < field_length)
					        {
					        	data = String.format("%-"+field_length+"s", data);
					        }
					        if(data.length() > field_length)
					        {
					        	data = data.substring(0, field_length);
					        }
					        royIso.addValue(field, data, field_type, field_length);
					        				        
				        }
				    }
				    while(iter.hasNext());
				    message = royIso.toString().getBytes();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			} 
			catch (NullPointerException e) 
			{
				e.printStackTrace();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			Utility.println("Unable to build message because format map is empty. Please check spec.");
		}
		return message;
	}
	/**
	 * Build JSON string using data and configuration
	 * @param json Data package on common specs
	 * @param format Format message
	 * @param variable Reserved word
	 * @return Return byte containing JSON format message
	 */
	public byte[] buildJSON(JSONObject json, String format, String variable)
	{
		variable = variable.replaceAll(" ", "");
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
					currentData = escapeJSON(currentData);
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
		return result.getBytes();
	}
	/**
	 * XML format message builder
	 * @param json Data package on common specs
	 * @param format Format message
	 * @param variable Reserved word
	 * @return Return byte containing XML format message
	 */
	public byte[] buildXML(JSONObject json, String format, String variable)
	{
		variable = variable.replaceAll(" ", "");
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
					currentData = escapeXML(currentData);
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
					currentData = Translator.lTrim(currentData, "0").trim();
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
		return result.getBytes();
	}
	/**
	 * Convert JSON to XML String without require any spec configuration
	 * @param json JSONObject contains data
	 * @param rootTag Root tag to wrap the XML
	 * @return Return byte containing XML format message
	 */
	public String buildXML(JSONObject json, String rootTag)
	{
		String xml = "";
		String body = "";
        Set<?> s =  json.keySet();
	    Iterator<?> keys = s.iterator();
	    String key = "";
	    do
	    {
	        key = keys.next().toString();
	    	body += "\t<"+key+">"+Translator.escapeXML(json.optString(key, "").toString())+"</"+key+">\r\n";
	    }
	    while(keys.hasNext());
		xml = "<"+rootTag+">\r\n"+body+"</"+rootTag+">";
		return xml;
	}

}
