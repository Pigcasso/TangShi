package io.liamju.tangshi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import io.liamju.tangshi.activity.BaseActivity;
import io.liamju.tangshi.activity.MainActivity;
import io.liamju.tangshi.activity.PoetryDetailActivity;
import io.liamju.tangshi.activity.SearchableActivity;
import io.liamju.tangshi.activity.container.FragmentArgs;
import io.liamju.tangshi.activity.container.FragmentContainerActivity;
import io.liamju.tangshi.entity.Poetry;
import io.liamju.tangshi.fragment.AboutFragment;
import io.liamju.tangshi.fragment.CollectionFragment;
import io.liamju.tangshi.fragment.CommListFragment;
import io.liamju.tangshi.fragment.HistoryFragment;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/1
 */
public class IntentStarter {

    public static void showPoetryDetail(Context context, Poetry poetry) {
        Intent intent = new Intent(context, PoetryDetailActivity.class);
        intent.putExtra(AppConstants.KEY_POETRY_DETAIL, poetry);
        context.startActivity(intent);
    }

    public static void showPoetryDetails(Context context, int currentIndex, ArrayList<Poetry> poetryList) {
        Intent intent = new Intent(context, PoetryDetailActivity.class);
        intent.putExtra(AppConstants.KEY_POETRY_CURRENT_INDEX, currentIndex);
        intent.putParcelableArrayListExtra(AppConstants.KEY_POETRY_DETAIL_LIST, poetryList);
        context.startActivity(intent);
    }

    public static void showPoetryDetailForResult(Fragment fragment, Poetry poetry) {
        Intent intent = new Intent(fragment.getContext(), PoetryDetailActivity.class);
        intent.putExtra(AppConstants.KEY_POETRY_DETAIL, poetry);
        intent.putExtra(AppConstants.KEY_FOOTPRINT_TYPE, AppConstants.VALUE_FOOTPRINT_COLLECTION);
        fragment.startActivityForResult(intent, AppConstants.REQUEST_CODE_DETAIL);
    }

    public static void showCommList(Activity activity, int type, String value) {
        FragmentArgs args = new FragmentArgs();
        args.add(AppConstants.KEY_COMM_TYPE, type);
        args.add(AppConstants.KEY_COMM_VALUE, value);
        FragmentContainerActivity.launch(activity, CommListFragment.class, args);
    }

    public static void showHistoryList(Activity activity) {
        FragmentContainerActivity.launch(activity, HistoryFragment.class, null);
    }

    public static void showCollectionList(Activity activity) {
        FragmentContainerActivity.launch(activity, CollectionFragment.class, null);
    }

    public static void showAbout(Activity activity) {
        FragmentContainerActivity.launch(activity, AboutFragment.class, null);
    }

    public static void showAuthorIntroduce(Activity activity, String title, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        activity.startActivity(Intent.createChooser(intent, title));
    }

    public static void chooseFeedbackApp(Activity activity, String title, String[] address, String subject, String text) {
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL, address);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, text);
        activity.startActivity(Intent.createChooser(i, title));
    }

    public static void searchPoetry(Activity activity) {
        Intent intent = new Intent(activity, SearchableActivity.class);
        activity.startActivity(intent);
    }

    public static void showMain(BaseActivity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
}
