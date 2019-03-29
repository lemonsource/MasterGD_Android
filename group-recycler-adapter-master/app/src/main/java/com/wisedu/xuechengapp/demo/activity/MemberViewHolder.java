

package com.wisedu.xuechengapp.demo.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wisedu.xuechengapp.demo.R;
import com.wisedu.xuechengapp.demo.entity.Member;


public class MemberViewHolder extends RecyclerView.ViewHolder {

    private final TextView mNameView;
//    private final TextView mNameView2;
    private final TextView mMemoView;
//    private final TextView mMemoView2;
    private final TextView mMemoIssuingDate;
//    private final TextView mMemoIssuingDate2;

    public MemberViewHolder(View itemView) {
        super(itemView);
        mNameView = itemView.findViewById(R.id.name2);
//        mNameView2 = itemView.findViewById(R.id.name2);
        mMemoView = itemView.findViewById(R.id.status2);
//        mMemoView2 = itemView.findViewById(R.id.status2);
        mMemoIssuingDate=itemView.findViewById(R.id.memo2);
//        mMemoIssuingDate2=itemView.findViewById(R.id.memo2);
    }

    public void update(Member member) {

        mMemoIssuingDate.setText(member.issuingDate);
        mNameView.setText(member.name);
        if(member.downloadStatus==0) {

        mMemoView.setText("未下载");
        mMemoView.setBackgroundResource(R.drawable.shape_oval);


        }else {
            mMemoView.setText("已下载");
            mMemoView.setBackgroundResource(R.drawable.shape_ovaldownload);
//            mMemoIssuingDate.setText(member.issuingDate);
        }

    }
}
