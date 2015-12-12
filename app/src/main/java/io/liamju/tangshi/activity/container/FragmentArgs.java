package io.liamju.tangshi.activity.container;

import android.os.Bundle;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/5
 */
public class FragmentArgs implements Serializable {
    private static final Map<String, Serializable> values = new HashMap<>();

    public FragmentArgs add(String key, Serializable value) {
        if (!TextUtils.isEmpty(key) && value != null) {
            values.put(key, value);
        }
        return this;
    }

    public Serializable get(String key) {
        return values.get(key);
    }

    public static void setToBundle(Bundle bundle, FragmentArgs args) {
        Set<String> keys = args.values.keySet();
        for (String key : keys) {
            Serializable value = args.get(key);
            if (value != null) {
                bundle.putSerializable(key, value);
            }
        }
    }

    public static FragmentArgs transToArgs(Bundle bundle) {
        FragmentArgs args = new FragmentArgs();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            args.add(key, bundle.getSerializable(key));
        }
        return args;
    }

    public static Bundle transToBundle(FragmentArgs values) {
        Bundle bundle = new Bundle();
        setToBundle(bundle,values);
        return bundle;
    }
}
