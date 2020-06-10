package com.example.dinokeylas.ewaste.ui.Message;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.MessageModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context context;
    private List<MessageModel> messageList;
    private MessageModel message;

    public MessageAdapter(Context context, List<MessageModel> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageAdapter.MessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_message, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
    message = messageList.get(position);
    String read;

        if(message.getRead()){
            read = "sudah dibaca";
        } else {
            read = "belum dibaca";
        }

        Date dates = message.getSendDate();
        String DATE_FORMAT = "dd-MM-yyyy";
        String DATE_FORMAT2 = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(DATE_FORMAT2);
        String mDate = "" + simpleDateFormat.format(dates);
        String mTime = "" + simpleDateFormat2.format(dates);

        holder.tvSender.setText(message.getSender());
        holder.tvDate.setText(mDate);
        holder.tvTime.setText(mTime);
        holder.tvRead.setText(read);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvSender, tvDate, tvTime, tvRead;
        CardView cvMessageItem;
        public MessageViewHolder(View itemView) {
            super(itemView);
            tvSender = itemView.findViewById(R.id.tv_sender);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvRead = itemView.findViewById(R.id.tv_read);
            cvMessageItem = itemView.findViewById(R.id.cv_message_item);

            cvMessageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageModel mMessage = messageList.get(getAdapterPosition());
                    Intent intent = new Intent (context, DetailMessageActivity.class);
                    intent.putExtra("message", mMessage);
                    context.startActivity(intent);
                }
            });
        }
    }
}
