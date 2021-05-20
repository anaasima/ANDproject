package com.example.sem4_andproject.ui.seeTasks;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sem4_andproject.R;
import com.example.sem4_andproject.data.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> tasks;
    private OnItemClickListener listener;
    public static final int IMPORTANT = -18059920;
    private Task currentTask;

    public TaskAdapter() {
        tasks = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentTask = tasks.get(position);
        holder.textViewTitle.setText(currentTask.getTitle());
        holder.textViewDescription.setText(currentTask.getDescription());
        if (currentTask.getHasPriority() == 1)
        holder.textViewPriority.setText("!");
        else
            holder.textViewPriority.setText("");
        if (currentTask.getDueTime().equals(""))
            holder.hourView.setText("");
        else
            holder.hourView.setText(currentTask.getDueTime());
        if (currentTask.isCompleted() == 1)
//            holder.layout.setAlpha((float) 0.3);
            holder.textViewTitle.setPaintFlags( holder.textViewTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        else
//            holder.layout.setAlpha(1);
            holder.textViewTitle.setPaintFlags( holder.textViewTitle.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;

        notifyDataSetChanged();
    }

    public Task getTaskAtPosition(int position) {
        return tasks.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private LinearLayout optionsLinear;
        private ImageView editButton;
        private ImageView checkButton;
        private ImageView priorityButton;
        private ImageView dateButton;
        private TextView hourView;
        private RelativeLayout layout;
        private int clickCounts = 1;

        public String currentDateTaskFormat()
        {
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);


            String date = day + "/" + month;
            return date;
        }

        ViewHolder(View itemView) {
            super(itemView);
            optionsLinear = itemView.findViewById(R.id.linearMenu);
            textViewTitle = itemView.findViewById(R.id.task_Title);
            textViewDescription = itemView.findViewById(R.id.task_Description);
            textViewPriority = itemView.findViewById(R.id.task_Priority);
            editButton = itemView.findViewById(R.id.editButton);
            checkButton = itemView.findViewById(R.id.checkButton);
            priorityButton = itemView.findViewById(R.id.priorityButton);
            dateButton = itemView.findViewById(R.id.dateButton);
            hourView = itemView.findViewById(R.id.hourView);
            layout = itemView.findViewById(R.id.taskViewHolder);

            textViewPriority.setTextColor(IMPORTANT);

            optionsLinear.setVisibility(View.GONE);



            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (tasks.get(position).getDate().equals(currentDateTaskFormat())) {
                        if (listener != null && position != RecyclerView.NO_POSITION) {

                            listener.onItemClick(tasks.get(position));
                            if (clickCounts % 2 == 0)

                                optionsLinear.setVisibility(View.GONE);
                            else {
                                optionsLinear.setVisibility(View.VISIBLE);
                            }
                            clickCounts++;
                        }
                    }
                }
            });
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (tasks.get(position).getDate().equals(currentDateTaskFormat())) {
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(tasks.get(position));
                        }
                    }
                }
            });
            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (tasks.get(position).getDate().equals(currentDateTaskFormat())) {
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onCheckClick(tasks.get(position));
                        }
                    }
                }
            });
            priorityButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (tasks.get(position).getDate().equals(currentDateTaskFormat())) {
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onPriorityClick(tasks.get(position));
                        }
                    }
                }
            });
            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (tasks.get(position).getDate().equals(currentDateTaskFormat())) {
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onDateClick(tasks.get(position));
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
        void onEditClick(Task task);
        void onCheckClick(Task task);
        void onPriorityClick(Task task);
        void onDateClick(Task task);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
