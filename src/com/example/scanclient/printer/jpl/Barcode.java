package com.example.scanclient.printer.jpl;

import com.example.scanclient.printer.JQPrinter.ALIGN;
import com.example.scanclient.printer.esc.Code128;
import com.example.scanclient.printer.jpl.JPL.ROTATE;

public class Barcode extends BaseJPL
{
	/*
	 * ö�����ͣ�JPL������������
	 */
    public static enum BAR_1D_TYPE
    {
        UPCA_AUTO(0x41),
        UPCE_AUTO(0x42),
        EAN8_AUTO(0x43),
        EAN13_AUTO(0x44),
        CODE39_AUTO(0x45),
        ITF25_AUTO(0x46),
        CODABAR_AUTO(0x47),
        CODE93_AUTO(0x48),
        CODE128_AUTO(0x49);
        private int _value;
		private BAR_1D_TYPE(int id)
		{
			_value = id;
		}		
		public int value()
		{
			return _value;
		}	
    }
    /*
	 * ö�����ͣ����뵥Ԫ,JPL����
	 */
    public enum BAR_UNIT
    {
        x1(1),
        x2(2),
        x3(3),
        x4(4),
        x5(5),
        x6(6),
        x7(7);
        private int _value;
		private BAR_UNIT(int id)
		{
			_value = id;
		}		
		public int value()
		{
			return _value;
		}
    }
    // <summary>
    /// ��ӡ������ת�Ƕ�
    /// </summary>
    public enum BAR_ROTATE
    {
        ANGLE_0,
        ANGLE_90,
        ANGLE_180,
        ANGLE_270
    } 
    /*
     * ���캯��
     */
	public Barcode(JPL_Param param) {
		super(param);
	}

	/*
	 *һά�������
	 */
	private boolean _1D_barcode(int x, int y, BAR_1D_TYPE type, int height, BAR_UNIT unit_width, BAR_ROTATE rotate, String text)
    {
        byte[] cmd = { 0x1A, 0x30, 0x00 };
        port.write(cmd);
        port.write((short)x);
        port.write((short)y);
        port.write((byte)type.value());
        port.write((short)height);
        port.write((byte)unit_width.value());
        port.write((byte)rotate.ordinal());
        return port.write(text);
    }
	/*
	 * code128
	 */
	public boolean code128(int x,int y, int bar_height, BAR_UNIT unit_width,BAR_ROTATE rotate,String text)
	{
		return _1D_barcode(x,y, BAR_1D_TYPE.CODE128_AUTO,bar_height, unit_width, rotate, text);
	}
	/*
	 * Code128
	 */
	public boolean code128(ALIGN align,int y, int bar_height, BAR_UNIT unit_width,BAR_ROTATE rotate,String text)
	{
		int x = 0;
		Code128 code128 = new Code128(text);
		if (code128.encode_data == null)
			return false;
		if (!code128.decode(code128.encode_data))
			return false;
		int bar_width = code128.decode_string.length();
		if (align ==ALIGN.CENTER)
			x = (param.pageWidth - bar_width* unit_width.value())/2;
		else if(align ==ALIGN.RIGHT)
			x = param.pageWidth - bar_width* unit_width.value();
		else
			x = 0;
		return _1D_barcode(x,y, BAR_1D_TYPE.CODE128_AUTO,bar_height, unit_width, rotate, text);
	}
	
	public enum QRCODE_ECC
	{
		LEVEL_L,//�ɾ���7%
		LEVEL_M,//�ɾ���15%
		LEVEL_Q,//�ɾ���25%
		LEVEL_H,//�ɾ���30%	
	}
	/*
	 * QRCode
	 * int version:�汾�ţ����Ϊ0�����Զ�����汾�š�
	 *             ÿ���汾�����ɵ��ֽ���Ŀ��һ���ġ�������ݲ��㣬���Զ����հס�ͨ������һ����İ汾�����̶�QRCode��С��
	 * int ecc������ʽ,ȡֵ0, 1��2��3��������Խ�ߣ���Ч�ַ�Խ�٣�ʶ����Խ�ߡ�ȱʡΪ2
	 * int unit_width��������Ԫ��С
	 */
	public boolean QRCode(int x, int y,int version, QRCODE_ECC ecc,  BAR_UNIT unit_width,ROTATE rotate, String text)
	{
		byte[] cmd = {0x1A, 0x31, 0x00};
		port.write(cmd);
		port.write((byte)version);
		port.write((byte)ecc.ordinal());
		port.write((short)x);
		port.write((short)y);
		port.write((byte)unit_width.value());
		port.write((byte)rotate.value());
		return port.write(text);
	}
	/*
	 * PDF417
	 */
	public boolean PDF417(int x, int y, int col_num, int ecc,int LW_ratio,  BAR_UNIT unit_width,	ROTATE rotate, String text)
	{
		byte[] cmd = {0x1A, 0x31, 0x01};
		port.write(cmd);
		port.write((byte)col_num);
		port.write((byte)ecc);
		port.write((byte)LW_ratio);
		port.write((short)x);
		port.write((short)y);
		port.write((byte)unit_width.value());
		port.write((byte)rotate.value());
		return port.write(text);
	}
	
	public boolean DataMatrix(int x, int y, BAR_UNIT unit_width,ROTATE rotate, String text)
	{
		byte[] cmd = {0x1A, 0x31, 0x02};
		port.write(cmd);
		port.write((short)x);
		port.write((short)y);
		port.write((byte)unit_width.value());
		port.write((byte)rotate.value());
		return port.write(text);		
	}
	
	public boolean GridMatrix(int x, int y,byte ecc,  BAR_UNIT unit_width, ROTATE rotate, String text)
	{
		byte[] cmd = {0x1A, 0x31, 0x03};
		port.write(cmd);
		port.write((byte)ecc);
		port.write((short)x);
		port.write((short)y);
		port.write((byte)unit_width.value());
		port.write((byte)rotate.value());
		return port.write(text);		
	}
}
