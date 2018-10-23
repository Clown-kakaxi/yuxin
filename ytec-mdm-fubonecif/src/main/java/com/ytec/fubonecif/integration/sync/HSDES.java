package com.ytec.fubonecif.integration.sync;
import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;


public class HSDES {
	
	/**
	 * 使用"12345678"为密钥生成4字节的MAC，ASCII字符
	 * @param datain 整个报文
	 * @return 4字节的MAC，ASCII字符
	 * @throws UnsupportedEncodingException 
	 */
	public static byte[] genMAC(byte datain[]) throws UnsupportedEncodingException
	{
		return genMAC(datain, "12345678".getBytes());
	}

	/**
	 * 生成4字节的MAC，ASCII字符
	 * @param datain 整个报文
	 * @param key DES加密密钥
	 * @return 4字节的MAC，ASCII字符
	 */
	public static byte[] genMAC(byte datain[], byte key[])
	{
		try
		{
			//初始化密钥基础设施
			SecretKey desKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key));
			Cipher c = Cipher.getInstance("DES/ECB/NoPadding");
	        c.init(Cipher.ENCRYPT_MODE, desKey);

	        //至少补一个8字节块
			int len = (datain.length / 8 + 1) * 8;
	
			byte data[] = new byte[len];
			System.arraycopy(datain, 0, data, 0, datain.length);
			//补的数据以0x80打头，后面是0;
			data[datain.length] = (byte)0x80;
			
			//加密缓冲区
			byte buff[] = new byte[8];
			for (int j=0;j<len;j+=8)
			{
				//以8字节为一块循环
				for (int i=0;i<8;i++)
				{
					if (j == 0)
						buff[i] = data[i];
					else
						buff[i] ^= data[j + i];
				}
				buff = c.doFinal(buff);
				
				//使用加密后值的前四字节的大写HEX码作为下一次运算的值 —— 略奇葩
				char hex[] = Hex.encodeHex(buff,false);
				for (int i=0;i<8;i++)
					buff[i] = (byte)hex[i];
			}
			
			//取最后值的前四字节为MAC值
			return buff;
		}
		catch (Exception e)
		{
			//所有异常都当运行时抛出
			throw new IllegalArgumentException(e);
		}
	}
	
	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	} 
	 
//	public static void main(String[] args) throws UnsupportedEncodingException{
//		String str = "<?xml version=\"1.0\" encoding=\"GB18030\"?>"+
//"<TransBody><RequestHeader><ReqSysCd>ecif01</ReqSysCd><ReqSeqNo>20160719100312151000</ReqSeqNo><ReqDt>20160719</ReqDt><ReqTm>100312</ReqTm><DestSysCd>LOANS</DestSysCd><ChnlNo></ChnlNo><BrchNo></BrchNo><BizLine></BizLine><TrmNo></TrmNo><TrmIP></TrmIP><TlrNo></TlrNo></RequestHeader><RequestBody><customer><coreNo>501000040083</coreNo><custId>110000088633</custId><custType>1</custType><identType>2X</identType><identNo>637478052</identNo><custName>高尚控股有限公司</custName><enName>GODDESSUN HOLDINGS LTD.</enName><shortName></shortName><vipFlag></vipFlag><inoutFlag>F</inoutFlag><custLevel></custLevel><createBranchNo>501</createBranchNo><cardLvl></cardLvl></customer><person><pinyinName></pinyinName><birthday></birthday><highestSchooling></highestSchooling><careerType></careerType><citizenship></citizenship><annualIncomeScope></annualIncomeScope></person><org><orgCustType></orgCustType><orgBizCustType>3</orgBizCustType><legalReprName>郑志斌</legalReprName><legalReprIdentType>0</legalReprIdentType><legalReprIdentNo>332627197902101517</legalReprIdentNo><lncustp>039</lncustp><registerDate></registerDate><businessScope>1057</businessScope></org><contmeth><contmethId>100006850</contmethId><contmethType>102</contmethType><contmethInfo>86-13501822251</contmethInfo></contmeth><contmeth><contmethId>100006852</contmethId><contmethType>103</contmethType><contmethInfo>13564886320</contmethInfo></contmeth><contmeth><contmethId>100006522</contmethId><contmethType>300</contmethType><contmethInfo>50390010</contmethInfo></contmeth><address><addrId>100001688</addrId><addrType>01</addrType><addr>上海市浦东大道3号29楼2296室123</addr><zipcode></zipcode><countryOrRegion>VGB</countryOrRegion></address><address><addrId>100000464</addrId><addrType>07</addrType><addr>Morgan&amp;Morgan Building, P.O. Box958,Pasea Estate, Road Town,</addr><zipcode>Tortola,British Virgin Islands</zipcode><countryOrRegion></countryOrRegion></address><belongManager><custManagerNo>515N1675</custManagerNo><orgId>515</orgId><accountName>515N1675</accountName><userName>连寅威</userName></belongManager></RequestBody></TransBody>";
//		StringBuffer temp=new StringBuffer();
//		int length=str.getBytes("GBK").length;
//		temp.append(String.format("%08d", length+8));
//		temp.append(str);
//		String strRequest = new String(HSDES.genMAC(temp.toString().getBytes("GBK")));
//		System.out.println(strRequest.substring(0,4));
//	}
}

