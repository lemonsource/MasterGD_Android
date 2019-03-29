/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.wisedu.xuechengapp.demo.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wisedu.xuechengapp.demo.R;
import com.wisedu.xuechengapp.demo.entity.Team;


public class TeamViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTitleView;

    public TeamViewHolder(View itemView) {
        super(itemView);
        mTitleView = itemView.findViewById(R.id.title);
    }

    public void update(Team team) {
        mTitleView.setText(team.title);
    }
}
