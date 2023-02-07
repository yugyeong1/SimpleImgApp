package com.dbrud1032.imageglideapp.adapter;

// 1. RecyclerView.Adapter 를 상속받는다.

// 2. 상속받은 클래스가 abstract 이므로, unimplemented method 오버라이드!

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbrud1032.imageglideapp.MainActivity;
import com.dbrud1032.imageglideapp.PhotoActivity;
import com.dbrud1032.imageglideapp.R;
import com.dbrud1032.imageglideapp.model.Profile;

import org.w3c.dom.Text;

import java.util.ArrayList;

// 6. RecyclerView.Adapter 의 데이터 타입을 적어주어야 한다.
//    우리가 만든 ViewHolder 로 적는다.


// 7. 빨간색 에러가 뜨면, 우리가 만든 ViewHolder 로
// onCreateViewHolder, onBindViewHolder 변경해준다.

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    // 5. 어댑터 클래스의 멤버변수와 생성자를 만들어 준다.

    Context context;
    ArrayList<Profile> profileList;

    public ProfileAdapter(Context context, ArrayList<Profile> profileList) {
        this.context = context;
        this.profileList = profileList;
    }

    // 8. 오버라이드 함수 3개를 전부 구현 해주면 끝!
    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // xml 파일을 연결하는 작업
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_row, parent, false);
        return new ProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        // 뷰에 데이터를 셋팅한다!
        Profile profile = profileList.get(position);

        holder.txtTitle.setText( profile.title );
        holder.txtId.setText( "id : " + profile.id );
        holder.txtAlbumId.setText( "albumId : " + profile.albumId );

        // 이미지뷰에 사진 셋팅
        // URL 을 가지고, 네트워크를 통해서 사진 데이터를 받아온다.
        Glide.with(context).load(profile.thumbnailUrl).placeholder(R.drawable.baseline_person_24).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    // 3. ViewHolder 클래스를 만든다.
    //      이 클래스는 row.xml 화면에 있는 뷰를 연결시키는 클래스다.
    // RecyclerView.ViewHolder 상속받는다.

    // 4. 생성자를 만든다.
    //    생성자에서, 뷰를 연결시키는 코드를 작성하고,
    //    클릭 이벤트도 작성한다.

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtId;
        TextView txtAlbumId;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtId = itemView.findViewById(R.id.txtId);
            txtAlbumId = itemView.findViewById(R.id.txtAlbumId);
            imageView = itemView.findViewById(R.id.imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int index = getAdapterPosition();
                    Profile profile = profileList.get(index);

                    Intent intent = new Intent(context, PhotoActivity.class);
                    intent.putExtra("url", profile.url);

                    context.startActivity(intent);

                }
            });

        }
    }

}
