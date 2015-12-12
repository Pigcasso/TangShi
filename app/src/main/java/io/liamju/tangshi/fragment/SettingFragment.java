package io.liamju.tangshi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.InjectView;
import butterknife.OnClick;
import io.liamju.tangshi.AppConstants;
import io.liamju.tangshi.IntentStarter;
import io.liamju.tangshi.R;
import io.liamju.tangshi.db.PoetryUtil;
import io.liamju.tangshi.fragment.CleanCacheDialogFragment.OnCacheDataListener;
import io.liamju.tangshi.utils.DataCleanManager;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/11/30
 */
public class SettingFragment extends BaseFragment {

    @InjectView(R.id.text_setting_cache_size)
    TextView cacheSize;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            cacheSize.setText(DataCleanManager.getTotalCacheSize(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_setting;
    }

    @OnClick(R.id.linear_setting_history)
    public void showHistoryList() {
        IntentStarter.showHistoryList(getActivity());
    }

    @OnClick(R.id.linear_setting_collection)
    public void showCollectionList() {
        IntentStarter.showCollectionList(getActivity());
    }

    @OnClick(R.id.linear_setting_about)
    public void showAbout() {
        IntentStarter.showAbout(getActivity());
    }

    @OnClick(R.id.linear_setting_clean)
    public void cleanCacheData() {
        CleanCacheDialogFragment fragment = new CleanCacheDialogFragment();
        fragment.setOnCacheDataListener(new OnCacheDataListener() {
            @Override
            public void onSuccess() {
                try {
                    cacheSize.setText(DataCleanManager.getTotalCacheSize(getContext()));
                    PoetryUtil.getInstance(getContext()).reset();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {

            }
        });
        fragment.show(getFragmentManager(), null);
    }

    @OnClick(R.id.linear_setting_software)
    public void updateSoftware() {
        Toast.makeText(getContext(), R.string.toast_latest_version, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.linear_setting_feed_back)
    public void sendFeedback() {
        String title = getString(R.string.label_feed_back);
        String[] address = new String[]{AppConstants.MAIL_DEVELOPER};
        String subject = getString(R.string.feedback_email_subject);
        String text = getString(R.string.feedback_email_prefix);
        IntentStarter.chooseFeedbackApp(getActivity(), title, address, subject, text);
    }
}
