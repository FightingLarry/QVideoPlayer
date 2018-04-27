package com.iamlarry.qvideoplayer.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamlarry.qvideoplayer.sample.utils.JumpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author larryycliu on 2018/4/24.
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(new MainAdapter(this));
    }

    public class MainAdapter extends RecyclerView.Adapter<MainAdapter.NormalTextViewHolder> {
        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private String[] mTitles;

        public MainAdapter(Context context) {
            mTitles = context.getResources().getStringArray(R.array.main_list);
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_main, parent, false));
        }

        @Override
        public void onBindViewHolder(NormalTextViewHolder holder, int position) {
            holder.mTextView.setText(mTitles[position]);
        }

        @Override
        public int getItemCount() {
            return mTitles == null ? 0 : mTitles.length;
        }

        public class NormalTextViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_title)
            TextView mTextView;

            NormalTextViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JumpUtils.goToVideoPlayer(MainActivity.this, v);
                    }
                });
            }
        }
    }
}
