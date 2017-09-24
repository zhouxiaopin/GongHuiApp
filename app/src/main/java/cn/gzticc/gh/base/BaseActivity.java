package cn.gzticc.gh.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gzticc.library.StatusBarUtil;

import butterknife.ButterKnife;
import cn.gzticc.gh.R;
import cn.gzticc.gh.global.GhApplication;

/**
 * Created by user on 2017/9/22.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setStatusBar();

    }
    protected void setStatusBar() {
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent));
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }
    protected void openActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);

    }
    protected void openActivity(Class<?> clazz, Bundle bundle){
        Intent intent = new Intent(this,clazz);
        if(null != bundle){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    protected void openActivityForResult(Class<?> clazz, int requesCode){
        Intent intent = new Intent(this,clazz);
        startActivityForResult(intent,requesCode);
    }
    protected void openActivityForResult(Class<?> clazz, int requesCode, Bundle bundle){
        Intent intent = new Intent(this,clazz);
        if(null != bundle){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requesCode);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //出栈并且结束activity
        GhApplication.finishActivity();
    }
}
