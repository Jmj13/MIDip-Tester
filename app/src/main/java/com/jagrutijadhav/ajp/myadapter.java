package com.jagrutijadhav.ajp;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class myadapter extends FirebaseRecyclerAdapter<Student,myadapter.myviewholder>
{
    String subject;
    //double max=0.0;
   // int ranknumber=0;
    public myadapter( FirebaseRecyclerOptions<Student> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Student student)
    {
        if(student.getUsername().equals(HomePage.username)){ holder.cardView.setCardBackgroundColor(Color.parseColor("#800080")); }
        else { holder.cardView.setCardBackgroundColor(Color.parseColor("#3f3f3f"));}
        holder.name.setText(student.getUsername());
        holder.course.setText(student.getName());
        if(subject.equals("EST")){ holder.rank.setText("RP: "+student.getEstrp());}
        else if(subject.equals("MAN")){ holder.rank.setText("RP: "+student.getManrp());}
        else { holder.rank.setText("RP: "+student.getAjprp());}
//        if(max < Double.parseDouble(student.getAjprp())){
//            ranknumber++;
//            max=Double.parseDouble(student.getAjprp());
//        }
//        holder.rank.setText("RANK: "+ranknumber);


        String url_name=student.getUrlname();
        if(!(url_name.equals("empty"))){ Picasso.with(holder.img.getContext()).load(url_name).into(holder.img);
        }else{holder.img.setImageResource(R.drawable.profile1);}
        //Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
    }


    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        CircularImageView img;
        TextView name,course,rank,rp;
        CardView cardView;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
            img=(CircularImageView) itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            course=(TextView)itemView.findViewById(R.id.coursetext);
            rank=(TextView)itemView.findViewById(R.id.emailtext);
          //  rp=(TextView)itemView.findViewById(R.id.ranking_rp_text);
        }
    }
    public  void subjectset(String sub){
        subject=sub;
    }
}
