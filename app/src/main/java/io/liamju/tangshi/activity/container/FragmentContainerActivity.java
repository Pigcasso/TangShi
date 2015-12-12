package io.liamju.tangshi.activity.container;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.liamju.tangshi.AppConstants;
import io.liamju.tangshi.R;
import io.liamju.tangshi.activity.BaseActivity;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/5
 */
public class FragmentContainerActivity extends BaseActivity {

    public static void launch(Activity activity, Class<? extends Fragment> clazz, FragmentArgs args) {
        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra(AppConstants.KEY_CLASS_NAME, clazz.getName());
        if (args != null) {
            intent.putExtra(AppConstants.KEY_ARGS, args);
        }
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra(AppConstants.KEY_CLASS_NAME);
        if (TextUtils.isEmpty(className)) {
            finish();
            return;
        }
        FragmentArgs values = (FragmentArgs) getIntent().getSerializableExtra(AppConstants.KEY_ARGS);

        Fragment fragment = null;
        if (savedInstanceState == null) {
            try {
                Class clazz = Class.forName(className);
                fragment = (Fragment) clazz.newInstance();
                // 设置参数给Fragment
                if (values != null) {
                    try {
                        Method method = clazz.getMethod("setArguments", new Class<?>[]{Bundle.class});
                        method.invoke(fragment, FragmentArgs.transToBundle(values));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_fragment_container, fragment, AppConstants.FRAGMENT_TAG)
                    .commit();
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment_container;
    }
}
