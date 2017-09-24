package cn.gzticc.gh.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import cn.gzticc.gh.global.GhApplication;

/**
 * 
 * @ClassName: UIUtils
 * @Description: UI工具
 * @author pin
 * @date 2017-2-24 下午2:23:14类
 */
public class UIUtils {

	/**
	 * 
	 * @Title: getContext
	 * @Description:  获取全局上下文
	 * @author pin
	 * @date 2017-2-24 下午2:14:07
	 * @return Context
	 */
	public static Context getContext() {
		return GhApplication.getContext();
	}

	/**
	 *
	 * @Title: getContext
	 * @Description:  获取全局消息处理者
	 * @author pin
	 * @date 2017-2-24 下午2:14:07
	 * @return Handler
	 */
	public static Handler getHandler() {
		return GhApplication.getHandler();
	}


	/**
	 *
	 * @Title: getContext
	 * @Description:  获取主线程id
	 * @author pin
	 * @date 2017-2-24 下午2:14:07
	 * @return int
	 */
	public static int getMainThreadId() {
		return GhApplication.getMainThreadId();
	}

	/**
	 * 
	 * @Title: showToast
	 * @Description: 显示提示
	 * @author pin
	 * @date 2017-2-24 下午2:17:20
	 * @param msg	要提示的信息
	 * @param type	0为Toast.LENGTH_SHORT，1为Toast.LENGTH_LONG
	 * @return void
	 */
	public static void showToast(String msg, int type) {
		if(type == 0){
			Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
		}		
	}

	// /////////////////加载布局文件//////////////////////////
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

	// /////////////////判断是否运行在主线程//////////////////////////
	public static boolean isRunOnUIThread() {
		// 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
		int myTid = android.os.Process.myTid();
		if (myTid == getMainThreadId()) {
			return true;
		}

		return false;
	}

	// 运行在主线程
	public static void runOnUIThread(Runnable r) {
		if (isRunOnUIThread()) {
			// 已经是主线程, 直接运行
			r.run();
		} else {
			// 如果是子线程, 借助handler让其运行在主线程
			getHandler().post(r);
		}
	}

	// /////////////////加载资源文件 ///////////////////////////

	// 获取字符串
	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

    // /////////////////dip和px转换//////////////////////////

	public static int dip2px(float dip) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}

	public static float px2dip(int px) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return px / density;
	}

}
