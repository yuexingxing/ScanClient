package com.example.scanclient.view;

import java.util.List;
import com.example.scanclient.R;
import com.example.scanclient.adapter.CommonAdapter;
import com.example.scanclient.adapter.ViewHolder;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ���ص��ĵ�ѡ��Ի���
 * 
 * @author yxx
 * 
 *         2015-12-3
 */
public class SingleItemDialog {

	public static boolean isShow = false;
	public static Dialog builder;

	public SingleItemDialog() {

	}

	// ��������ص�����
	public static abstract class SingleItemCallBack {
		public abstract void callback(int pos);
	}

	/**
	 * @param context
	 * @param strTitle
	 * @param flag
	 *            ����ⲿ�Ƿ���ԹرնԻ���
	 * @param list
	 * @param resultCallBack
	 */
	public static void showDialog(final Context context, String strTitle, boolean flag, List<String> list, final SingleItemCallBack resultCallBack) {

		if (builder != null) {
			builder.dismiss();
		}

		View view = LayoutInflater.from(context).inflate(R.layout.dialog_item, null);

		TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_item);
		ListView listView = (ListView) view.findViewById(R.id.listView_dialog);

		tvTitle.setText(strTitle);

		try {

			CommonAdapter<String> commonAdapter = new CommonAdapter<String>(context, list, R.layout.layout_single_item) {
				@Override
				public void convert(ViewHolder helper, String item) {
					helper.setText(R.id.tv_dialog_single_item, item);
				}
			};
			listView.setAdapter(commonAdapter);

			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					resultCallBack.callback(arg2);
					builder.dismiss();
				}
			});

			builder = new Dialog(context, R.style.dialogSupply);
			builder.setContentView(view);

			if (flag == true) {
				builder.setCanceledOnTouchOutside(true);
			}
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
