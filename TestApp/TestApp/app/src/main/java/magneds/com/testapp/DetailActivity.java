package magneds.com.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author glenn
 * @since 04-10-16
 */

public class DetailActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "DetailActivity";

    public static String AGE_EXTRA = "age_extra";
    private static int DEF_AGE = -1;

    protected RelativeLayout mLayout;

    protected TextInputLayout mAgeTextLayout;

    protected FloatingActionButton mFAB;

    private int mAge;
    protected int mRandomAgeToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.mAge = DEF_AGE;
        try {
            this.mAge = getIntent().getIntExtra(AGE_EXTRA, DEF_AGE);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }

        mLayout = (RelativeLayout) findViewById(R.id.detail_layout);
        mAgeTextLayout = (TextInputLayout) findViewById(R.id.detail_age_layout);
        mFAB = (FloatingActionButton) findViewById(R.id.detail_fab);
        mFAB.setOnClickListener(this);

        mRandomAgeToAdd = Utils.randomAgeToAdd();

        mAgeTextLayout.getEditText().setHint(String.format(getResources().getString(R.string.detail_age_hint), mRandomAgeToAdd));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.detail_fab:
                int result = mRandomAgeToAdd + mAge;
                String formatted = String.format(getResources().getString(R.string.detail_age_hint), mRandomAgeToAdd);
                mAgeTextLayout.getEditText().setText(formatted + " " + result);
                break;
            default:
                Log.e(TAG, "onClick() not handled");
        }
    }
}
