package com.aribhatt.automate.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.aribhatt.automate.R;
import com.aribhatt.automate.data.db.entity.PriorityContact;
import com.aribhatt.automate.util.Utils;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;

/**
 * Created by aribhatt on 12/01/18.
 */

public class PriorityContactsAdapter extends RecyclerView.Adapter<PriorityContactsAdapter.PriorityContactViewHolder>  implements View.OnClickListener, View.OnLongClickListener,FastScrollRecyclerView.SectionedAdapter, FastScrollRecyclerView.MeasurableAdapter {
    public interface Callback {
        void onItemClicked(int index, boolean longClick);
    }

    ArrayList<PriorityContact> contactList;
    ArrayList<PriorityContact> selectedList;
    Context mContext;
    private ContactListAdapter.Callback callback;


    public PriorityContactsAdapter(Context context, ContactListAdapter.Callback callback){

        this.callback = callback;
        mContext = context;
        contactList = new ArrayList<>();
        selectedList = new ArrayList<>();
    }

    @Override
    public PriorityContactsAdapter.PriorityContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_regular, parent, false);
        return new PriorityContactsAdapter.PriorityContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PriorityContactsAdapter.PriorityContactViewHolder holder, int position) {
        try {
            setItemDetails(holder, contactList.get(position));
            holder.view.setTag(""+position);
            holder.view.setOnClickListener(this);
            holder.view.setOnLongClickListener(this);
        }catch (Exception e){

        }
    }

    @Override
    public int getViewTypeHeight(RecyclerView recyclerView, int viewType) {
        if(viewType == R.layout.list_item_regular){
            return recyclerView.getResources().getDimensionPixelSize(R.dimen.list_item_height);
        }
        return 0;
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        try {
            return contactList.get(position).getContactName().substring(0, 1);
        }catch (Exception e){
            return "";
        }
    }

    private void setItemDetails(PriorityContactsAdapter.PriorityContactViewHolder holder, PriorityContact contact){
        try {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig().fontSize(50).bold().endConfig()
                    .buildRound(contact.getContactName().substring(0,1), Utils.getColorCode(contact.getContactName().substring(0,1)));
            String title = contact.getContactName();
            //String detail = ;

            holder.icon.setImageDrawable(drawable);
            holder.title.setText(title);
            //holder.detail.setText(detail);
            if(selectedList.contains(contact)){
                ((CardView)holder.view).setCardBackgroundColor(Color.parseColor("#EEEEEE"));
            }else{
                ((CardView)holder.view).setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }catch (Exception e){

        }
    }

    public void addAll(ArrayList<PriorityContact> contacts){
        this.contactList = contacts;
        notifyDataSetChanged();
    }

    public void add(PriorityContact contact){
        this.contactList.add(contact);
        notifyItemInserted(this.contactList.size() - 1);
    }

    public void toggleSelected(PriorityContact contact) {
        final boolean newState = !selectedList.contains(contact);
        if (newState) {
            selectedList.add(contact);
        } else {
            selectedList.remove(contact);
        }
        notifyItemChanged(contactList.indexOf(contact));
    }

    public void toggleSelected(int i){
        if(selectedList.contains(contactList.get(i))){
            selectedList.remove(contactList.get(i));
        }else{
            selectedList.add(contactList.get(i));
        }
        notifyItemChanged(i);
    }

    public ArrayList<PriorityContact> getSelected(){
        return selectedList;
    }

    public ArrayList<PriorityContact> getContactList(){
        return contactList;
    }

    @SuppressWarnings("unchecked")
    public void restoreState(Bundle in) {
        contactList = (ArrayList<PriorityContact>) in.getSerializable("[main_adapter_items]");
        selectedList = (ArrayList<PriorityContact>) in.getSerializable("[main_adapter_selected]");
        notifyDataSetChanged();
    }

    public void saveState(Bundle out) {
        out.putSerializable("[prioritycontact_list_items]", contactList);
        out.putSerializable("[prioritycontact_list_selected]", selectedList);
    }

    public void clearSelected() {
        selectedList.clear();
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size();
    }

    public int getSelectedCount() {
        return selectedList.size();
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        int index = Integer.parseInt(tag);
        if (callback != null) {
            callback.onItemClicked(index, false);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        String tag = (String) view.getTag();
        int index = Integer.parseInt(tag);
        if (callback != null) {
            callback.onItemClicked(index, true);
        }
        return false;
    }

    class PriorityContactViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView icon;
        TextView title, detail;

        PriorityContactViewHolder(View itemView){
            super(itemView);
            view = itemView;
            title = (TextView) view.findViewById(R.id.title);
            icon = (ImageView) view.findViewById(R.id.list_image);

        }

    }
}
