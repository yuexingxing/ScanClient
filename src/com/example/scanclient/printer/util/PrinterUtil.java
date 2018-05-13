package com.example.scanclient.printer.util;

import java.util.List;

import com.example.scanclient.MyApplication;
import com.example.scanclient.fragment.SettingFragment;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.printer.JQPrinter;
import com.example.scanclient.printer.Port.PORT_STATE;
import com.example.scanclient.printer.jpl.Page.PAGE_ROTATE;
import com.example.scanclient.util.CommandTools;

import android.content.Context;
import android.widget.Toast;

public class PrinterUtil {

	public static boolean printLabel(Context context, OrderInfo info, List<OrderInfo> dataList){

		JQPrinter printer = SettingFragment.printer;
		if(printer == null){
			CommandTools.showToast("�����ӡ������");
			return false;
		}
		
		if(!getPrinterState(context, printer)){
			return false;
		}

		printer.jpl.page.start(0, 0, 576, 424, PAGE_ROTATE.x0);
		//		printer.jpl.barcode.code128(ALIGN.CENTER, 0, 64, BAR_UNIT.x2, BAR_ROTATE.ANGLE_0, bar_str);//printer.jpl.barcode.code128(16, 0, 64, BAR_UNIT.x3, BAR_ROTATE.ANGLE_0, bar_str);
		//	printer.jpl.barcode.PDF417(16, 68, 5, 3, 4, BAR_UNIT.x2, ROTATE.ROTATE_0, bar_str);
		//		printer.jpl.text.drawOut(ALIGN.CENTER, 66, bar_str, 16, false, false, false, false, TEXT_ENLARGE.x2, TEXT_ENLARGE.x1, ROTATE.ROTATE_0);//printer.jpl.text.drawOut(96, 64, bar_str);
		//	printer.jpl.barcode.QRCode(0, 120, 0, QRCODE_ECC.LEVEL_M, BAR_UNIT.x3, ROTATE.ROTATE_0, bar_str);


		//		printer.jpl.graphic.line(new Point(8,96), new Point(568,96), 3);
		//		printer.jpl.graphic.line(new Point(8, 160), new Point(568, 160), 3);
		//		printer.jpl.graphic.line(new Point(8, 224), new Point(568, 224), 3);
		//		printer.jpl.graphic.line(new Point(8, 288), new Point(568, 288), 3);
		//
		//		printer.jpl.graphic.line(new Point(8,96), new Point(8, 288), 3);
		//		printer.jpl.graphic.line(new Point(568, 96), new Point(568, 288), 3);
		//		printer.jpl.graphic.line(new Point(304, 96), new Point(304, 224), 3);
		//		printer.jpl.graphic.line(new Point(456, 96), new Point(456, 160), 3);
		//
		//		printer.jpl.text.drawOut(14, 104, "���ݡ���" + "�Ϻ���", 24, true, false, false, false, TEXT_ENLARGE.NORMAL, TEXT_ENLARGE.NORMAL, ROTATE.ROTATE_0);			
		//		printer.jpl.text.drawOut(320, 104, String.valueOf(1)+"/"+1, 48, true, false, false, false, TEXT_ENLARGE.NORMAL, TEXT_ENLARGE.NORMAL, ROTATE.ROTATE_0);
		//		printer.jpl.text.drawOut(464, 104, "�ؿ�", 48, true, true, false, false, TEXT_ENLARGE.NORMAL, TEXT_ENLARGE.NORMAL, ROTATE.ROTATE_0);
		//		printer.jpl.text.drawOut(14, 168, "�Ϻ���ǿ���ӿƼ����޹�˾", 24, true, false, false, false, TEXT_ENLARGE.NORMAL, TEXT_ENLARGE.NORMAL, ROTATE.ROTATE_0);
		//		printer.jpl.text.drawOut(320, 168, "����", 24, true, false, false, false, TEXT_ENLARGE.NORMAL, TEXT_ENLARGE.NORMAL, ROTATE.ROTATE_0);
		//		printer.jpl.text.drawOut(320, 168+26, "021-61645760", 24, false, false, false, false, TEXT_ENLARGE.NORMAL, TEXT_ENLARGE.NORMAL, ROTATE.ROTATE_0);
		//		printer.jpl.text.drawOut(14, 232, "�Ϻ��ֶ����·258��2��¥2¥", 24, true, false, false, false, TEXT_ENLARGE.NORMAL, TEXT_ENLARGE.NORMAL, ROTATE.ROTATE_0);			
		//		printer.jpl.text.drawOut(14, 296, "ȫ������www.qs-express.com", 32, true, false, false, false, TEXT_ENLARGE.NORMAL, TEXT_ENLARGE.NORMAL, ROTATE.ROTATE_0);

		int startY = 10;
		int lineH = 30;//ÿ�и߶�
		
		printer.jpl.text.drawOut(14, startY+=lineH, "������: " + info.getOrderID());
		printer.jpl.text.drawOut(14, startY+=lineH, CommandTools.getTime());
		printer.jpl.text.drawOut(14, startY+=lineH, "");

		int len = dataList.size();
		for(int i=0; i<len; i++){
			
			printer.jpl.text.drawOut(14, startY, info.getCargoID());
			printer.jpl.text.drawOut(100, startY, "1");
			startY = startY + 30;
		}
		
		printer.jpl.text.drawOut(14, startY+=lineH, "");
		printer.jpl.text.drawOut(14, startY+=lineH, "�ϼ�: ");
		printer.jpl.text.drawOut(100, startY, len + "");

		printer.jpl.page.end();
		printer.jpl.page.print();
		printer.jpl.feedMarkOrGap(0);//printer.jpl.feedNextLabelEnd(48);//printer.jpl.feedNextLabelBegin();
		int i = 0;
		for(i=0;i<10;i++)
		{
			try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
			if (!printer.getPrinterState(4000))//�˴��Ķ���ʱ��Ҫ���ϴ�ӡ���ݵ�ʱ�䡣����ݴ�ӡ���ݵ���,������ӡ�����ݸ��࣬����Ҫ���ø����ʱ�䡣
			{
				Toast.makeText(MyApplication.getInstance(), "��ȡ��ӡ��״̬ʧ��", Toast.LENGTH_SHORT).show(); 
				try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
				continue;
			}			
			if (printer.printerInfo.isCoverOpen)
			{
				CommandTools.showToast("ֽ��δ��--���´�ӡ");
				return true;
			}
			else if (printer.printerInfo.isNoPaper )
			{
				CommandTools.showToast("ȱֽ--���´�ӡ");
				return true;
			}				
			if ( printer.printerInfo.isPrinting)
			{
				CommandTools.showToast("���ڴ�ӡ");
				try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			}
			else
			{
				break;
			}				
		}

		Toast.makeText(MyApplication.getInstance(), "��ӡ����", Toast.LENGTH_SHORT).show(); 
		return true;
	}

	public static boolean getPrinterState(Context context, JQPrinter printer){
		
		if( printer.getPortState() != PORT_STATE.PORT_OPEND)
		{
			Toast.makeText(context, "��������", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (!printer.getPrinterState(3000))
		{
			Toast.makeText(context, "��ȡ��ӡ��״̬ʧ��", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (printer.printerInfo.isCoverOpen)
		{
			Toast.makeText(context, "��ӡ��ֽ�ָ�δ�ر�", Toast.LENGTH_SHORT).show();
			return false;
		}
		else if (printer.printerInfo.isNoPaper)
		{
			Toast.makeText(context, "��ӡ��ȱֽ", Toast.LENGTH_SHORT).show();			
			return false;
		}
		return true;
	}


}
