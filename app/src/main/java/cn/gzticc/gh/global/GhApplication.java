package cn.gzticc.gh.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Stack;

/**
 * Created by user on 2017/9/22.
 */

public class GhApplication extends Application {
    //全局上下文
    private static Context context;
    //消息处理者
    private static Handler handler;
    //主线程id
    private static int mainThreadId;
    //activity栈
    public static Stack<Activity> activityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        context = this;
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();

        activityLifecycleCallbacks();
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    private void activityLifecycleCallbacks(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                //入栈
                GhApplication.addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }
            @Override
            public void onActivityResumed(Activity activity) {
            }
            @Override
            public void onActivityPaused(Activity activity) {
            }
            @Override
            public void onActivityStopped(Activity activity) {
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    /**
     * 添加Activity到栈
     * @param activity
     */
    public static void addActivity(Activity activity){
        if(activityStack == null){
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     *  获取当前Activity（栈中最后一个压入的）
     * @return
     */
    public static Activity currentActivity(){
        Activity activity = activityStack.lastElement();
        return activity;
    }
    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public static void finishActivity(){
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     * @param activity
     */
    private static void finishActivity(Activity activity){
        if(activity != null){
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    //结束所有Activity
    private static void finishAllActivity(){
        for(int i = 0,len = activityStack.size(); i < len; i++){
            if(null != activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public static void appExit(){
        try {
            finishAllActivity();
        } catch (Exception e) {
            Log.e("appExit", "退出应用程序失败");
        }

    }

}
