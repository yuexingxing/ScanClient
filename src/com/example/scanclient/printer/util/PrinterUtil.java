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
			CommandTools.showToast("请检查打印机连接");
			return false;
		}

		if(!getPrinterState(context, printer)){
			return false;
		}

		printer.jpl.page.start(0, 0, 1000, 340, PAGE_ROTATE.x0);

		int startY = 10;
		int lineH = 30;//每行高度

		printer.jpl.text.drawOut(14, startY, "订单号: " + info.getOrderID());
		printer.jpl.text.drawOut(14, startY+=lineH, "条码");
		printer.jpl.text.drawOut(300, startY, "数量");

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
				printer.jpl.text.drawOut(50, startY, "小计");
				printer.jpl.text.drawOut(300, startY, tempLen + "");
				
				sb.append(orderInfo.getCargoID()).append(",");
			}
			
		}

		printer.jpl.text.drawOut(300, startY+=lineH, "");
		printer.jpl.text.drawOut(50, startY+=lineH, "合计: ");
		printer.jpl.text.drawOut(300, startY, len + "");
		
		//打印空白页，方便撕纸
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
			if (!printer.getPrinterState(4000))//此处的读超时需要算上打印内容的时间。请根据打印内容调整,如果你打印的内容更多，就需要设置更多的时间。
			{
				Toast.makeText(MyApplication.getInstance(), "获取打印机状态失败", Toast.LENGTH_SHORT).show(); 
				try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
				continue;
			}			
			if (printer.printerInfo.isCoverOpen)
			{
				CommandTools.showToast("纸仓未关--重新打印");
				return true;
			}
			else if (printer.printerInfo.isNoPaper )
			{
				CommandTools.showToast("缺纸--重新打印");
				return true;
			}				
			if ( printer.printerInfo.isPrinting)
			{
				CommandTools.showToast("正在打印");
				try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			}
			else
			{
				break;
			}				
		}

		Toast.makeText(MyApplication.getInstance(), "打印结束", Toast.LENGTH_SHORT).show(); 
		return true;
	}

	public static boolean getPrinterState(Context context, JQPrinter printer){

		if( printer.getPortState() != PORT_STATE.PORT_OPEND)
		{
			Toast.makeText(context, "蓝牙错误", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (!printer.getPrinterState(3000))
		{
			Toast.makeText(context, "获取打印机状态失败", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (printer.printerInfo.isCoverOpen)
		{
			Toast.makeText(context, "打印机纸仓盖未关闭", Toast.LENGTH_SHORT).show();
			return false;
		}
		else if (printer.printerInfo.isNoPaper)
		{
			Toast.makeText(context, "打印机缺纸", Toast.LENGTH_SHORT).show();			
			return false;
		}
		return true;
	}


}
