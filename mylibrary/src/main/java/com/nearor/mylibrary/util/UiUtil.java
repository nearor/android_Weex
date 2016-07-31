package com.nearor.mylibrary.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;



/**
 * <Pre>
 * 常用UI操作类
 * 包括：弹出和取消加载进度条
 *     弹出toast
 *     显示和隐藏输入法
 *     绑定带删除按钮的文本输入框  典型的如：用户名 密码输入框
 *     判断文本控件是否有值
 * </Pre>
 * 
 * @author Nearor
 * 
 */
public class UiUtil {

	private final Activity activity;

	public UiUtil(Activity activity) {
		this.activity = activity;
	}

	protected ProgressDialog progressDialog; // 加载进度条



	/*************************** ----Progress---- *********************************************/

	/**
	 * 进度条是否已经显示
	 * 
	 * @return
	 */
	public boolean isProgressShowing() {
		return progressDialog != null && progressDialog.isShowing();
	}

	/**
	 * 显示加载进度条
	 */
	public void showProgress() {
		showProgress(true, true);
	}

	/**
	 * 显示加载进度条
	 */
	public void showProgress(boolean canBack) {
		showProgress(canBack, true);
	}

	/**
	 * 显示加载进度条
	 * 
	 * @param canBack
	 *            是否可以通过back键取消
	 * @param cancelOnTouch
	 *            点击对话框区域之外是否可以取消
	 */
	public void showProgress(boolean canBack, boolean cancelOnTouch) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMessage("加载中,请稍候...");
			// 点击屏幕退出
			progressDialog.setCanceledOnTouchOutside(cancelOnTouch);
			// 返回键退出
			progressDialog.setCancelable(canBack);
		}
		
		if (!isProgressShowing()) {
			progressDialog.show();
		}

	}

	/**
	 * 取消进度条
	 */
	public void cancelProgress() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog.cancel();
		}
	}

	/**
	 * 判断控件是否有文本内容
	 *
	 */
	public static boolean isEmpty(TextView textview) {
		return TextUtils.isEmpty(getText(textview));
	}

	/**
	 * 获取控件是否有文本内容
	 *
	 */
	public static String getText(TextView textview) {
		return textview.getText().toString().trim();
	}

	/**
	 * 为当前控件弹出输入法
	 * 
	 * @param view
	 */
	public static void showInputMethod(View view) {
		view.requestFocus();
		InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
	}

	/**
	 * 隐藏数输入法
	 * 
	 * @param view
	 */
	public static void hideInputMethod(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 为带删除按钮的输入框绑定事件 典型的用法如： 用户名密码输入框
	 * 
	 * @param textView
	 * @param clearButton
	 */
	public static void bindButtonAndText(final TextView textView, final View clearButton) {
		clearButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				textView.setText("");
			}
		});

		if (!UiUtil.isEmpty(textView)) {
			clearButton.setVisibility(View.VISIBLE);
		} else {
			clearButton.setVisibility(View.INVISIBLE);
		}

		TextWatcher textWatcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!UiUtil.isEmpty(textView)) {
					clearButton.setVisibility(View.VISIBLE);
				} else {
					clearButton.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};

		textView.addTextChangedListener(textWatcher);
	}







}
