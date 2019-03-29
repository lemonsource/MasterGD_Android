package com.wisedu.xuechengapp.demo.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wisedu.xuechengapp.demo.R;




public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView tvItem;
    TextView textView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.recycler_view);
    }









//    public List<Team> teams = new ArrayList<>();
//
////    RecyclerViewHolder(View itemView) {
////        super(itemView);
////        tvItem = (TextView) itemView.findViewById(com.githang.groundrecycleradapter.R.id.tv_item);
////    }
//
//    final LayoutInflater layoutInflater = LayoutInflater.from(this);
//    RecyclerView recyclerView = recyclerView.findViewById();
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//    public GroupRecyclerAdapter<Team, TeamViewHolder, MemberViewHolder> recyclerAdapter =
//            new GroupRecyclerAdapter<Team, TeamViewHolder, MemberViewHolder>(teams) {
//                @Override
//                protected TeamViewHolder onCreateGroupViewHolder(ViewGroup parent) {
//                    return new TeamViewHolder(layoutInflater.inflate(R.layout.item_team_title, parent, false));
//                }
//
//                @Override
//                protected MemberViewHolder onCreateChildViewHolder(ViewGroup parent) {
//
//
//                    return new MemberViewHolder(layoutInflater.inflate(R.layout.item_team_member, parent, false));
//                }
//
//                @Override
//                protected void onBindGroupViewHolder(TeamViewHolder holder, int groupPosition) {
//                    holder.update(getGroup(groupPosition));
//                }
//
//                @Override
//                protected void onBindChildViewHolder(MemberViewHolder holder, int groupPosition, int childPosition) {
//                    holder.update(getGroup(groupPosition).members.get(childPosition));
//                }
//
//                @Override
//                protected int getChildCount(Team group) {
//                    return group.members.size();
//                }
//            };


}
