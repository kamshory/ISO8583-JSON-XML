<?php
class RoyISO8583{
	private $general_config	= array (
	1	=> array('b', 64, 0),
	2	=> array('an', 19, 1),
	3	=> array('n', 6, 0),
	4	=> array('n', 12, 0),
	5	=> array('n', 12, 0),
	6	=> array('n', 12, 0),
	7	=> array('an', 10, 0),
	8	=> array('n', 8, 0),
	9	=> array('n', 8, 0),
	10	=> array('n', 8, 0),
	11	=> array('n', 6, 0),
	12	=> array('n', 6, 0),
	13	=> array('n', 4, 0),
	14	=> array('n', 4, 0),
	15	=> array('n', 4, 0),
	16	=> array('n', 4, 0),
	17	=> array('n', 4, 0),
	18	=> array('n', 4, 0),
	19	=> array('n', 3, 0),
	20	=> array('n', 3, 0),
	21	=> array('n', 3, 0),
	22	=> array('n', 3, 0),
	23	=> array('n', 3, 0),
	24	=> array('n', 3, 0),
	25	=> array('n', 2, 0),
	26	=> array('n', 2, 0),
	27	=> array('n', 1, 0),
	28	=> array('n', 8, 0),
	29	=> array('an', 9, 0),
	30	=> array('n', 8, 0),
	31	=> array('an', 9, 0),
	32	=> array('n', 11, 1),
	33	=> array('n', 11, 1),
	34	=> array('an', 28, 1),
	35	=> array('z', 37, 1),
	36	=> array('n', 104, 1),
	37	=> array('an', 12, 0),
	38	=> array('an', 6, 0),
	39	=> array('an', 2, 0),
	40	=> array('an', 3, 0),
	41	=> array('ans', 8, 0),
	42	=> array('ans', 15, 0),
	43	=> array('ans', 40, 0),
	44	=> array('an', 25, 1),
	45	=> array('an', 76, 1),
	46	=> array('an', 999, 1),
	47	=> array('an', 999, 1),
	48	=> array('ans', 119, 1),
	49	=> array('an', 3, 0),
	50	=> array('an', 3, 0),
	51	=> array('a', 3, 0),
	52	=> array('an', 16, 0),
	53	=> array('an', 18, 0),
	54	=> array('an', 120, 0),
	55	=> array('ans', 999, 1),
	56	=> array('ans', 999, 1),
	57	=> array('ans', 999, 1),
	58	=> array('ans', 999, 1),
	59	=> array('ans', 99, 1),
	60	=> array('ans', 60, 1),
	61	=> array('ans', 99, 1),
	62	=> array('ans', 999, 1),
	63	=> array('ans', 999, 1),
	64	=> array('b', 16, 0),
	65	=> array('b', 16, 0),
	66	=> array('n', 1, 0),
	67	=> array('n', 2, 0),
	68	=> array('n', 3, 0),
	69	=> array('n', 3, 0),
	70	=> array('n', 3, 0),
	71	=> array('n', 4, 0),
	72	=> array('ans', 999, 1),
	73	=> array('n', 6, 0),
	74	=> array('n', 10, 0),
	75	=> array('n', 10, 0),
	76	=> array('n', 10, 0),
	77	=> array('n', 10, 0),
	78	=> array('n', 10, 0),
	79	=> array('n', 10, 0),
	80	=> array('n', 10, 0),
	81	=> array('n', 10, 0),
	82	=> array('n', 12, 0),
	83	=> array('n', 12, 0),
	84	=> array('n', 12, 0),
	85	=> array('n', 12, 0),
	86	=> array('n', 15, 0),
	87	=> array('an', 16, 0),
	88	=> array('n', 16, 0),
	89	=> array('n', 16, 0),
	90	=> array('an', 42, 0),
	91	=> array('an', 1, 0),
	92	=> array('n', 2, 0),
	93	=> array('n', 5, 0),
	94	=> array('an', 7, 0),
	95	=> array('an', 42, 0),
	96	=> array('an', 8, 0),
	97	=> array('an', 17, 0),
	98	=> array('ans', 25, 0),
	99	=> array('n', 11, 1),
	100	=> array('n', 11, 1),
	101	=> array('ans', 17, 0),
	102	=> array('ans', 28, 1),
	103	=> array('ans', 28, 1),
	104	=> array('an', 100, 1),
	105	=> array('ans', 999, 1),
	106	=> array('ans', 999, 1),
	107	=> array('ans', 999, 1),
	108	=> array('ans', 999, 1),
	109	=> array('ans', 999, 1),
	110	=> array('ans', 999, 1),
	111	=> array('ans', 999, 1),
	112	=> array('ans', 999, 1),
	113	=> array('n', 11, 1),
	114	=> array('ans', 999, 1),
	115	=> array('ans', 999, 1),
	116	=> array('ans', 999, 1),
	117	=> array('ans', 999, 1),
	118	=> array('ans', 999, 1),
	119	=> array('ans', 999, 1),
	120	=> array('ans', 999, 1),
	121	=> array('ans', 999, 1),
	122	=> array('ans', 999, 1),
	123	=> array('ans', 999, 1),
	124	=> array('ans', 255, 1),
	125	=> array('ans', 50, 1),
	126	=> array('ans', 6, 1),
	127	=> array('ans', 999, 1),
	128	=> array('b', 16, 0)
	);
	
	public $fields = array();
	public $values = array();
	public function addBit($bit, $value = null)
	{
		$this->fields[] = $bit*1;
		$this->fields = array_unique($this->fields);
		if($value !== null)
		{
			if($this->general_config[$bit][0] == 'n')
			{
				// numeric
				$val = $value*1;
				if($this->general_config[$bit][2] == 0)
				{
					// fix length
					$value = sprintf("%0".$this->general_config[$bit][1]."d", $val);
				}
			}
			else
			{
				// non numeric
				if($this->general_config[$bit][2] == 0)
				{
					// fix length
					$value = str_pad($value, $this->general_config[$bit][1], ' ', STR_PAD_RIGHT);
				}
			}
			$this->values[$bit] = $value;
		}
		else
		{
			$this->values[$bit] = null;
		}
		sort($this->fields, SORT_NUMERIC );
		ksort($this->values, SORT_NUMERIC );
	}
	public function addValue($bit, $value)
	{
		$this->addBit($bit, $value);
	}
	public function addData($bit, $value)
	{
		$this->addBit($bit, $value);
	}
	public $maxField = 1;
	
	public $segment1 = array(0,0);
	public $segment2 = array(0,0);
	public $segment3 = array(0,0);

	function getBitmap()
	{
        $tmp	= sprintf("%064d", 0);    
        $tmp2	= sprintf("%064d", 0);  
        foreach ($this->values as $key=>$val) 
		{
            if($key<65) 
			{
                $tmp[$key-1]	= 1;
            }
            else 
			{
                $tmp[0]	= 1;
                $tmp2[$key-65]	= 1;
            }
        }
        $result	= "";
        if($tmp[0]==1) 
		{
            while ($tmp2!='') 
			{
                $result .= base_convert(substr($tmp2, 0, 4), 2, 16);
                $tmp2 = substr($tmp2, 4, strlen($tmp2)-4);
            }
        }
        $main	= "";
        while ($tmp!='') 
		{
            $main .= base_convert(substr($tmp, 0, 4), 2, 16);
            $tmp = substr($tmp, 4, strlen($tmp)-4);
        }
        $this->_bitmap	= strtoupper($main. $result);
        
        return $this->_bitmap;
	}
	public $type = "0000";
	public $valid = array('mti'=>false, 'bitmap'=>false, 'data'=>false);
	public function parse3($message)
	{
		// parse type and bitmap
		$fields = array();
		
		$this->iso = $message;
		
		$this->valid['bitmap']	= false;
        $inp	= substr($this->iso, 4, 32);
        if (strlen($inp)>=16) 
		{
            $primary	= '';
            $secondary	= '';
            for ($i=0; $i<16; $i++) 
			{
                $primary .= sprintf("%04d", base_convert($inp[$i], 16, 2));
            }
            if ($primary[0]==1 && strlen($inp)>=32) 
			{
                for ($i=16; $i<32; $i++)
				{
                    $secondary .= sprintf("%04d", base_convert($inp[$i], 16, 2));
                }
                $this->valid['bitmap'] = true;
            }
            if ($secondary=='')
			{
				$this->valid['bitmap'] = true;
			}
        }
        //save to data element with ? character
        $tmp	= $primary. $secondary;
        for ($i=0; $i<strlen($tmp); $i++) 
		{
            if ($tmp[$i]==1) 
			{
                $this->values[$i+1]	= '?';
				$this->fields[] = $i+1;
            }
        }
        $this->bitmap	= $tmp;

        $bitmapLength = strlen($this->bitmap);
		
		// parse body
		$message = substr($message, $bitmapLength+4);
		foreach($this->fields as $field)
		{
			$element = $this->general_config[$field];
			if($element[2] == 1)
			{
				// dynamic length
				$fl = $element[1];
				$shift = strlen(sprintf("%d", $fl));
				$field_length = substr($message, 0, $shift)*1;
				$message = substr($message, $shift);
				if(strlen($message) >= $field_length)
				{
					$data = substr($message, 0, $field_length);
					$message = substr($message, $field_length);
				}
				else
				{
					$data = $message;
				}
			}
			else
			{
				// fix length
				$field_length = $element[1];
				$data = substr($message, 0, $field_length);
				$message = substr($message, $field_length);
			}
			$this->addValue($field, $data);
		}
	}
	public function parse($message)
	{
		// parse type and bitmap
		$fields = array();
		
		$ln = "";
		
		$segment10Str = "";
		$segment11Str = "";
		$segment10Int = 0;
		$segment11Int = 0;
		
		$segment20Str = "";
		$segment21Str = "";
		$segment20Int = 0;
		$segment21Int = 0;
		
		$segment30Str = "";
		$segment31Str = "";
		$segment30Int = 0;
		$segment31Int = 0;		
		
		$typeStr = substr($message, 0, 4);
		$this->type = $typeStr;		
		$segment10Str = substr($message, 4, 8);
		$segment11Str = substr($message, 12, 8);
		$segment10Int = hexdec($segment10Str);
		$segment11Int = hexdec($segment11Str);
		$this->segment1[0] = $segment10Int;
		$this->segment1[1] = $segment11Int;

		if(sprintf("%08x", ($this->segment1[0] & 0x80000000)) == "80000000")
		{
			$segment20Str = substr($message, 20, 8);
			$segment21Str = substr($message, 28, 8);
			$segment20Int = hexdec($segment20Str);
			$segment21Int = hexdec($segment21Str);
			$this->segment2[0] = $segment20Int;
			$this->segment2[1] = $segment21Int;
		}		
		if(sprintf("%08x", ($this->segment2[0] & 0x80000000)) == "80000000")
		{
			$segment30Str = substr($message, 36, 8);
			$segment31Str = substr($message, 44, 8);
			$segment30Int = hexdec($segment30Str);
			$segment31Int = hexdec($segment31Str);
			$this->segment3[0] = $segment30Int;
			$this->segment3[1] = $segment31Int;
		}	
		$iter = 0;
		// get field list from bitmap
		$i = 0;
		$k = 0;
		
		$k = $this->segment1[0];
		for($i = 1; $i <= 32; $i++)
		{
			if(sprintf("%08x", ($k & 0x80000000)) == "80000000" && $i > 1)
			{
				$this->addBit($i);
			}
			$k = $k - 0x80000000;
			$k = $k << 1;
		}
		$k = $this->segment1[1];
		for($i = 33; $i <= 64; $i++)
		{
			if(sprintf("%08x", ($k & 0x80000000)) == "80000000")
			{
				$this->addBit($i);
			}
			$k = $k - 0x80000000;
			$k = $k << 1;
		}
		$bitmapLength = 16;
		if(sprintf("%08x", ($this->segment1[0] & 0x80000000)) == "80000000")
		{
			$bitmapLength = 32;
			$k = $this->segment2[0];
			for($i = 65; $i <= 96; $i++)
			{
				if(sprintf("%08x", ($k & 0x80000000)) == "80000000")
				{
					$this->addBit($i);
				}
				$k = $k - 0x80000000;
				$k = $k << 1;
			}
			$k = $this->segment2[1];
			for($i = 97; $i <= 128; $i++)
			{
				if(sprintf("%08x", ($k & 0x80000000)) == "80000000")
				{
					$this->addBit($i);
				}
				$k = $k - 0x80000000;
				$k = $k << 1;				
			}
			if(sprintf("%08x", ($this->segment2[0] & 0x80000000)) == "80000000")
			{
				$bitmapLength = 48;
				$k = $this->segment3[0];
				for($i = 129; $i <= 160; $i++)
				{
					if(sprintf("%08x", ($k & 0x80000000)) == "80000000")
					{
						$this->addBit($i);
					}
					$k = $k - 0x80000000;
					$k = $k << 1;					
				}
				$k = $this->segment3[1];
				for($i = 161; $i <= 192; $i++)
				{
					if(sprintf("%08x", ($k & 0x80000000)) == "80000000")
					{
						$this->addBit($i);
					}
					$k = $k - 0x80000000;
					$k = $k << 1;					
				}
			}
		}
		
		
		// parse body
		$message = substr($message, $bitmapLength+4);
		foreach($this->fields as $field)
		{
			$element = $this->general_config[$field];
			if($element[2] == 1)
			{
				// dynamic length
				$fl = $element[1];
				$shift = strlen(sprintf("%d", $fl));
				$field_length = substr($message, 0, $shift)*1;
				$message = substr($message, $shift);
				if(strlen($message) >= $field_length)
				{
					$data = substr($message, 0, $field_length);
					$message = substr($message, $field_length);
				}
				else
				{
					$data = $message;
				}
			}
			else
			{
				// fix length
				$field_length = $element[1];
				$data = substr($message, 0, $field_length);
				$message = substr($message, $field_length);
			}
			$this->addValue($field, $data);
		}
	}
	public function maxField()
	{
		if(count($this->fields))
		{
			return max($this->fields);
		}
		else
		{
			return 0;
		}
	}
	public function getField()
	{
		$header = "";
		$header .= $this->type;
		$maxField = $this->maxField();
		$seg1 = sprintf("%08x%08x", $this->segment1[0], $this->segment1[1]);
		$header .= $seg1;
		if($maxField > 64)
		{
			$seg2 = sprintf("%08x%08x", $this->segment2[0], $this->segment2[1]);
			$header .= $seg2;
		}
		if($maxField > 128)
		{
			$seg3 = sprintf("%08x%08x", $this->segment3[0], $this->segment3[1]);
			$header .= $seg3;
		}
		return $header;
	}
	function getBody()
	{
		$body = "";
		$this->fields = array_unique($this->fields);
		sort($this->fields, SORT_NUMERIC);
		foreach($this->fields as $field)
		{
			if($this->general_config[$field][2] == 1)
			{
				// dynamic
				$value = $this->values[$field];
				$length = strlen($value);
				$sl = strlen(sprintf("%d", $this->general_config[$field][1]));
				$sf = sprintf("%0".$sl."d", $length);
				$body .= $sf;
				$body .= $value;
			}
			else
			{
				// fix length
				$value = $this->values[$field];
				$length = $this->general_config[$field][1];
				if($this->general_config[$field][0] == 'n')
				{
					$val = $value * 1;
					$body .= sprintf("%0".$length."d", $val);
				}
				else
				{
					$body .= str_pad($value, $length, ' ', STR_PAD_RIGHT);
				}
			}
		}
		return $body;
	}
	public function getBitmapField()
	{
	}
	public function binary_to_hexadecimal($binary) 
	{
        $len=strlen($binary);
        $rows=($len/4)-1;
        if (($len%4)>0) {
                $pad=$len+(4-($len%4));
                $binary=str_pad($binary,$pad,"0",STR_PAD_LEFT);
                $len=strlen($binary);
                $rows=($len/4)-1;
        }
        $x=0;
        for ($x=0;$x<=$rows;$x++) {
                $s=($x*4);
                $bins=$binary[$s].$binary[$s+1].$binary[$s+2].$binary[$s+3];
                $num=base_convert($bins,2,10);
                if ($num>9) {
                        die("the string is not a proper binary coded decimal\n");
                } else {
                        $res.=$num;
                }
        }
        return $res;
	} 
	public function getData()
	{
		return $this->values;
	}
	public function getISO()
	{
		return $this->getType().$this->getBitmap().$this->getBody();
	}
	public function setISO($iso)
	{
		return $this->parse($iso);
	}
	public function setType($type)
	{
		$this->type = $type;
	}
	public function getType()
	{
		return sprintf("%04d", $this->type);
	}
}
?>
