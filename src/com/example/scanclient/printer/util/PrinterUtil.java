package com.example.scanclient.printer.util;

import java.util.List;

import com.example.scanclient.MyApplication;
import com.example.scanclient.db.dao.PupScanDao;
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

		PupScanDao pupScanDao = new PupScanDao();
		JQPrinter printer = SettingFragment.printer;
		if(printer == null){
			CommandTools.showToast("�����ӡ������");
			return false;
		}

		if(!getPrinterState(context, printer)){
			return false;
		}

		printer.jpl.page.start(0, 0, 1000, 340, PAGE_ROTATE.x0);

		int startY = 10;
		int lineH = 30;//ÿ�и߶�

		printer.jpl.text.drawOut(14, startY, "������: " + info.getOrderID());
		printer.jpl.text.drawOut(14, startY+=lineH, "����");
		printer.jpl.text.drawOut(300, startY, "����");

		StringBuilder sb = new StringBuilder();
		int len = dataList.size();
		for(int i=0; i<len; i++){

			OrderInfo orderInfo = dataList.get(i);
			if(!sb.toString().contains(orderInfo.getCargoID())){
				
				List<OrderInfo> tempList = pupScanDao.selectDataById(orderInfo);
				int tempLen = tempList.size();
				for(int k=0; k<tempLen; k++){
					
					OrderInfo tempOrderInfo = tempList.get(k);
					startY = startY + 30;
					printer.jpl.text.drawOut(14, startY, tempOrderInfo.getCargoID());
					printer.jpl.text.drawOut(300, startY, "1");
				}
				
				startY = startY + 30;
				printer.jpl.text.drawOut(50, startY, "С��");
				printer.jpl.text.drawOut(300, startY, tempLen + "");
				
				sb.append(orderInfo.getCargoID()).append(",");
			}
			
		}

		printer.jpl.text.drawOut(300, startY+=lineH, "");
		printer.jpl.text.drawOut(50, startY+=lineH, "�ϼ�: ");
		printer.jpl.text.drawOut(300, startY, len + "");
		
		//��ӡ�հ�ҳ������˺ֽ
		printer.jpl.text.drawOut(300, startY+=lineH, "");
		printer.jpl.text.drawOut(300, startY+=lineH, "");

		printer.jpl.page.end();
		printer.jpl.page.print();
		//		printer.jpl.feedMarkOrGap(5);
		//		printer.jpl.feedNextLabelBegin();
		//		printer.jpl.feedMarkOrGap(0);//printer.jpl.feedNextLabelEnd(48);//printer.jpl.feedNextLabelBegin();
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
