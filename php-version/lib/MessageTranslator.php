<?php
include "RoyISO8583.php";
class MessageTranslator extends RoyISO8583
{
	public $iso_config = array();
	function __construct()
	{
	}
	function setConfig($iso_config)
	{
		if(!is_array($iso_config))
		{
			$this->iso_config = json_decode($iso_config, true);			
		}
		$keys = array_keys($this->iso_config);
		if(in_array(0, $keys))
		{
			$tmp = $this->iso_config;
			$this->iso_config = array();
			foreach($tmp as $key=>$value)
			{
				$this->iso_config['f'.$value['field']] = $value;
			}
		}
	}
	function parseISO($message, $iso_config = null)
	{
		if($iso_config !== null)
		{
			$this->setConfig($iso_config);
		}
		$this->parse($message);
		$data = array();
		foreach($this->iso_config as $field_str=>$config)
		{
			$field = substr($field_str, 1);
			$raw_data = $this->values[$field];
			$variables = $config['variable'];
			$formats = $config['format'];
			$array_variable = explode(",", preg_replace('/\s+/', '', $variables));
			$array_format = explode("%", $formats);
			$offset = 0;
			foreach($array_variable as $idx=>$variable)
			{
				$fmt = preg_replace("/[^0-9]/", "", $array_format[$idx+1]);
				$len = abs($fmt * 1);
				$variable = trim($variable);
				$data[$variable] = substr($raw_data, $offset, $len);
				$offset += $len;
			}
		}
		return $data;
	}
	function buildISO($json, $iso_config = null, $mti)
	{
		if($iso_config !== null)
		{
			$this->setConfig($iso_config);
		}
		$this->setType($mti);
		$data = array();
		
		foreach($this->iso_config as $field_str=>$config)
		{
			$field = substr($field_str, 1);
			$raw_data = "";
			$variables = $config['variable'];
			$formats = $config['format'];
			$array_variable = explode(",", preg_replace('/\s+/', '', $variables));
			$array_format = explode("%", $formats);
			$offset = 0;
			foreach($array_variable as $idx=>$variable)
			{
				$fmt = $array_format[$idx+1];
				$len = abs($fmt * 1);
				$format = "%".$fmt;
				$variable = trim($variable);
				$data = @$json[$variable];
				if(stripos($fmt, "d") !== false)
				{
					$data = $data * 1;
				}
				$raw_data .= sprintf($format, $data);
			}
			$this->addBit($field, $raw_data);
		}
		return $this->getISO();
	}

}
?>