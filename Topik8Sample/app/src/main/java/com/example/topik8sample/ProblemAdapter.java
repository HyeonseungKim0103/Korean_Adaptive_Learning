package com.example.topik8sample;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ProblemAdapter extends BaseAdapter{
    final private List<ProblemSet> mData;
    private UserAdapter uAdapter= new UserAdapter();

    //듣기 파일
    int pos; // 재생 멈춘 시점
    boolean isPlaying = false; // 재생중인지 확인할 변수
    public boolean flag_restart =false; // 재시작을 눌렀는지 확인
    public boolean flag_mp_gone =false; // mp가 존재하는지 확인

    public ProblemAdapter(List<ProblemSet> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    } //아이템 갯수

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isChecked1(int position){
        return mData.get(position).checked1;
    }

    public boolean isChecked2(int position){
        return mData.get(position).checked2;
    }

    public boolean isChecked3(int position){
        return mData.get(position).checked3;
    }

    public boolean isChecked4(int position){
        return mData.get(position).checked4;
    }


    //최적화 작업을 위해서 뷰를 한번 로드하면 재사용하고 표시할 내용만 교체하기 위한 클래스
    static class ViewHolder{
        LinearLayout number_linear;
        LinearLayout radio_linear;
        LinearLayout image_linear;

        TextView arranged_num;
        TextView number;
        TextView common_question;
        TextView plural_question;
        TextView problemTextView;
        TextView textTextView;

        TextView choiceNumber1;
        TextView choiceNumber2;
        TextView choiceNumber3;
        TextView choiceNumber4;

        RadioButton choice1Radio;
        RadioButton choice2Radio;
        RadioButton choice3Radio;
        RadioButton choice4Radio;

        TextView exampleText;
        TextView solutionText;

        TextView problemUserAnswer;
        TextView problemRealAnswer;
        TextView score;
        TextView probSet;

        FrameLayout frameDraw;

        CardView imageCard1;
        CardView imageCard2;
        CardView imageCard3;
        CardView imageCard4;

        ImageView choiceImage1;
        ImageView choiceImage2;
        ImageView choiceImage3;
        ImageView choiceImage4;

        ImageView textImage;

        //듣기 파일
        ImageButton bPlay;
        ImageButton bPause;
        ImageButton bRestart;
        ImageButton bStop;

        SeekBar sb; // 음악 재생위치를 나타내는 시크바
        MediaPlayer mp; // 음악 재생을 위한 객체

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //아이템 한 칸에 들어갈 레이아웃
        //MainActivity와 같은 activity에서는 setContentView해서 바로 가져오면 되지만
        //activity가 아닌 일반 class에서는 LayoutInflater로 로드한 View에서 명시적으로 findViewVyId를 사용해야함.
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_problem,parent,false); //item_weather에서 가져오기!

            TextView arranged_num = convertView.findViewById(R.id.arrangedNumber);
            TextView number = convertView.findViewById(R.id.number);
            TextView common_question = convertView.findViewById(R.id.common_question);
            TextView plural_question = convertView.findViewById(R.id.plural_question);
            TextView problemTextView = convertView.findViewById(R.id.problemTextView);
            TextView textTextView = convertView.findViewById(R.id.textTextView);
            TextView choiceNumber1 = convertView.findViewById(R.id.choiceNumber1);
            TextView choiceNumber2 = convertView.findViewById(R.id.choiceNumber2);
            TextView choiceNumber3 = convertView.findViewById(R.id.choiceNumber3);
            TextView choiceNumber4 = convertView.findViewById(R.id.choiceNumber4);
            RadioButton choice1Radio = convertView.findViewById(R.id.choice1Radio);
            RadioButton choice2Radio = convertView.findViewById(R.id.choice2Radio);
            RadioButton choice3Radio = convertView.findViewById(R.id.choice3Radio);
            RadioButton choice4Radio = convertView.findViewById(R.id.choice4Radio);
            TextView exampleText = convertView.findViewById(R.id.exampleText);
            TextView solutionText = convertView.findViewById(R.id.solutionText);
            TextView problemUserAnswer = convertView.findViewById(R.id.problemUserAnswer);
            TextView problemRealAnswer = convertView.findViewById(R.id.problemRealAnswer);
            TextView score = convertView.findViewById(R.id.prob_point);

            FrameLayout frameDraw = convertView.findViewById(R.id.frameDraw);

            //이미지
            CardView imageCard1 = convertView.findViewById(R.id.image_card1);
            CardView imageCard2 = convertView.findViewById(R.id.image_card2);
            CardView imageCard3 = convertView.findViewById(R.id.image_card3);
            CardView imageCard4 = convertView.findViewById(R.id.image_card4);

            ImageView choiceImage1 = convertView.findViewById(R.id.choiceImage1);
            ImageView choiceImage2 = convertView.findViewById(R.id.choiceImage2);
            ImageView choiceImage3 = convertView.findViewById(R.id.choiceImage3);
            ImageView choiceImage4 = convertView.findViewById(R.id.choiceImage4);
            ImageView textImage = convertView.findViewById(R.id.textImage);

            TextView probSet = convertView.findViewById(R.id.probSet);

            ImageButton bPlay = convertView.findViewById(R.id.play_button);
            ImageButton bPause = convertView.findViewById(R.id.pause_button);
            ImageButton bRestart= convertView.findViewById(R.id.restart_button);
            ImageButton bStop = convertView.findViewById(R.id.stop_button);
            SeekBar sb = convertView.findViewById(R.id.seekBar);

            holder.arranged_num = arranged_num;
            holder.number = number;
            holder.common_question = common_question;
            holder.plural_question = plural_question;
            holder.problemTextView = problemTextView;
            holder.textTextView = textTextView;

            holder.choiceNumber1 = choiceNumber1;
            holder.choiceNumber2 = choiceNumber2;
            holder.choiceNumber3 = choiceNumber3;
            holder.choiceNumber4 = choiceNumber4;

            holder.choice1Radio = choice1Radio;
            holder.choice2Radio = choice2Radio;
            holder.choice3Radio = choice3Radio;
            holder.choice4Radio = choice4Radio;
            holder.exampleText = exampleText;
            holder.solutionText = solutionText;
            holder.frameDraw = frameDraw;

            holder.problemUserAnswer = problemUserAnswer;
            holder.problemRealAnswer = problemRealAnswer;
            holder.score = score;

            holder.probSet = probSet;

            //이미지
            holder.imageCard1 = imageCard1;
            holder.imageCard2 = imageCard2;
            holder.imageCard3 = imageCard3;
            holder.imageCard4 = imageCard4;

            holder.choiceImage1 = choiceImage1;
            holder.choiceImage2 = choiceImage2;
            holder.choiceImage3 = choiceImage3;
            holder.choiceImage4 = choiceImage4;

            holder.textImage = textImage;

            //듣기 파일
            holder.bPlay = bPlay;
            holder.bPause = bPause;
            holder.bRestart = bRestart;
            holder.bStop = bStop;
            holder.sb = sb;

            convertView.setTag(holder);
        } else{ //재사용 할 때
            holder = (ViewHolder) convertView.getTag();
        }
        ProblemSet problemSet = mData.get(position);
//        Log.d("지문",problemSet.getText());

        //이미지 처리
        holder.textImage.setVisibility(GONE);
        holder.textTextView.setVisibility(VISIBLE);
        holder.textTextView.setText(problemSet.getText());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("topik").child("topik1").child("이미지");

        //1. 지문
        if(problemSet.getText().equals("image")){
            holder.textTextView.setVisibility(GONE);
            holder.textImage.setVisibility(VISIBLE);
            String str_image = problemSet.getImage();

            if (pathReference == null) {
                Log.d("사진없음", "사진이 없습니다.");
            } else {
                Log.d("이미지 이름", str_image);
                StorageReference submitProfile_image = storageReference.child("topik/topik1/이미지/" + str_image + ".PNG");
                submitProfile_image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(parent.getContext()).load(uri).into(holder.textImage);
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }

        if(problemSet.getText().equals("")){
            holder.textTextView.setVisibility(GONE);
        }
//        else if(problemSet.getText().equals("image")){
//            holder.textTextView.setVisibility(View.GONE);
//            holder.textImage.setVisibility(VISIBLE);
//            String str_image = problemSet.getImage();
//
//            if (pathReference == null) {
//                Log.d("사진없음", "사진이 없습니다.");
//            } else {
//                StorageReference submitProfile_image = storageReference.child("topik1/" + str_image + ".PNG");
//                submitProfile_image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Glide.with(parent.getContext()).load(uri).into(holder.textImage);
//                    }
//                }).addOnFailureListener(new OnFailureListener(){
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//            }
//
//        }


//        if(!problemSet.getText().equals("image") && !holder.textTextView.getText().equals("")){
//            holder.textTextView.setVisibility(VISIBLE);
//            holder.textTextView.setText(problemSet.getText());
//        } else if(problemSet.getText().equals("image")) {
//            holder.textTextView.setVisibility(View.GONE);
//            String str_image = problemSet.getImage();
//
//            if (pathReference == null) {
//                Log.d("사진없음", "사진이 없습니다.");
//            } else {
//                StorageReference submitProfile_image = storageReference.child("topik1/" + str_image + ".PNG");
//                submitProfile_image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Glide.with(parent.getContext()).load(uri).into(holder.textImage);
//                    }
//                }).addOnFailureListener(new OnFailureListener(){
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//            }
//        }

        //2. 선지
        holder.imageCard1.setVisibility(GONE);
        holder.imageCard2.setVisibility(GONE);
        holder.imageCard3.setVisibility(GONE);
        holder.imageCard4.setVisibility(GONE);

        holder.choiceImage1.setVisibility(GONE);
        holder.choiceImage2.setVisibility(GONE);
        holder.choiceImage3.setVisibility(GONE);
        holder.choiceImage4.setVisibility(GONE);

        if(!problemSet.getChoice1().equals("image")){
            holder.choice1Radio.setText(problemSet.getChoice1());
            holder.choice2Radio.setText(problemSet.getChoice2());
            holder.choice3Radio.setText(problemSet.getChoice3());
            holder.choice4Radio.setText(problemSet.getChoice4());
        } else{
            holder.imageCard1.setVisibility(VISIBLE);
            holder.imageCard2.setVisibility(VISIBLE);
            holder.imageCard3.setVisibility(VISIBLE);
            holder.imageCard4.setVisibility(VISIBLE);

            holder.choiceImage1.setVisibility(VISIBLE);
            holder.choiceImage2.setVisibility(VISIBLE);
            holder.choiceImage3.setVisibility(VISIBLE);
            holder.choiceImage4.setVisibility(VISIBLE);

            holder.choice1Radio.setText("");
            holder.choice2Radio.setText("");
            holder.choice3Radio.setText("");
            holder.choice4Radio.setText("");

            String str = problemSet.getImage();
            String [] array = str.split(",");

            if(pathReference == null) {
                Log.d("사진없음", "사진이 없습니다.");
            } else{
                StorageReference submitProfile = storageReference.child("topik/topik1/이미지/" + array[0] + ".PNG");
                submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(parent.getContext()).load(uri).into(holder.choiceImage1);
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                Log.d("사진1", array[0]);

                StorageReference submitProfile2 = storageReference.child("topik/topik1/이미지/" + array[1] + ".PNG");
                submitProfile2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(parent.getContext()).load(uri).into(holder.choiceImage2);
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                StorageReference submitProfile3 = storageReference.child("topik/topik1/이미지/" + array[2] + ".PNG");
                submitProfile3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(parent.getContext()).load(uri).into(holder.choiceImage3);
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                StorageReference submitProfile4 = storageReference.child("topik/topik1/이미지/" + array[3] + ".PNG");
                submitProfile4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(parent.getContext()).load(uri).into(holder.choiceImage4);
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }

        //듣기 구현
        class MyThread extends Thread {
            @Override
            public void run() { // 쓰레드가 시작되면 콜백되는 메서드
                // 씨크바 막대기 조금씩 움직이기 (노래 끝날 때까지 반복)
                while(isPlaying) {
                    holder.sb.setProgress(holder.mp.getCurrentPosition());
                }
            }
        }

        View finalConvertView = convertView;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        String str_mp3 =  mData.get(position).getMp3();
        Log.d("Str_mp3",str_mp3);


        StorageReference submitProfile_mp3 = storageReference.child("topik/topik1/듣기/" + str_mp3 + ".mp3");

        File localFile= null;
        try {
            //localFile = File.createTempFile(str_mp3, "mp3",new File("topik/topik1/듣기/"));
            localFile = File.createTempFile(str_mp3, ".mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(localFile == null) {
            Log.d("localFile은", "null입니다");
        }
        final File finalLocalFile = localFile;

        submitProfile_mp3.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                Log.d("taskSnapshot", taskSnapshot.toString());
                Toast.makeText(parent.getContext(), "파일 불러오기 성공", Toast.LENGTH_LONG).show();
                Toast.makeText(parent.getContext(), finalLocalFile.getPath()+"", Toast.LENGTH_LONG).show();
                System.out.println("getpath : " + finalLocalFile.getPath());
                if(finalLocalFile == null) {
                    Log.d("finalLocalFile", "null입니다");
                }
                holder.mp = MediaPlayer.create(
                        finalConvertView.getContext(), // 현재 화면의 제어권자
                        Uri.fromFile(finalLocalFile)); // 음악파일 -##################### 임의로 넣은  TRACK 1
                int a = holder.mp.getDuration(); // 노래의 재생시간(miliSecond)
                holder.sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정
                new MyThread().start(); // 씨크바 그려줄 쓰레드 시작
                isPlaying = true; // 씨크바 쓰레드 반복 하도록

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(parent.getContext(), "파일 저장 실패", Toast.LENGTH_LONG).show();

            }
        });

        if(problemSet.getMp3() ==null){
            holder.sb.setVisibility(View.GONE);
            holder.bPlay.setVisibility(View.GONE);
            holder.bPause.setVisibility(View.GONE);
            holder.bRestart.setVisibility(View.GONE);
            holder.bStop.setVisibility(View.GONE);
        } else {
            holder.sb = convertView.findViewById(R.id.seekBar);
            holder.sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                    isPlaying = true;
                    int ttt = seekBar.getProgress(); // 사용자가 움직여놓은 위치
                    holder.mp.seekTo(ttt);
                    holder.mp.start();
                    Log.d("hi", "onStopTrackingTouch");

                    new MyThread().start();
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                    isPlaying = false;
                    holder.mp.pause();
                    Log.d("hi", "onStart");
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (seekBar.getMax() == progress) {
                        holder.bPlay.setVisibility(View.VISIBLE);
                        holder.bPause.setVisibility(View.GONE);
                        holder.bRestart.setVisibility(View.GONE);
                        isPlaying = false;
                        Log.d("hi", "onProgressChanged");
                        holder.mp.stop();
                    }
                }
            });

            holder.sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                    isPlaying = true;
                    int ttt = seekBar.getProgress(); // 사용자가 움직여놓은 위치
                    holder.mp.seekTo(ttt);
                    holder.mp.start();
                    new MyThread().start();
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                    isPlaying = false;
                    holder.mp.pause();
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (seekBar.getMax() == progress) {
                        holder.bPlay.setVisibility(View.VISIBLE);

                        holder.bPause.setVisibility(View.GONE);
                        holder.bRestart.setVisibility(View.GONE);
                        isPlaying = false;
                        holder.mp.stop();
                    }
                }
            });

            //MediaPlayer 객체 초기화 , 재생
//            holder.mp = MediaPlayer.create(
//                    convertView.getContext(), // 현재 화면의 제어권자
//                    R.raw.track1); // 음악파일 -##################### 임의로 넣은  TRACK 1
            //MediaPlayer 객체 초기화 , 재생


            Toast.makeText(parent.getContext(), finalLocalFile.getPath()+"", Toast.LENGTH_LONG).show();
            System.out.println("getpath : " + finalLocalFile.getPath());
            Log.d("uri로그",Uri.fromFile(finalLocalFile).toString());
            //holder.mp =MediaPlayer.create(parent.getContext(), Uri.fromFile(finalLocalFile));

//                        holder.mp = MediaPlayer.create(
//                                finalConvertView.getContext(), // 현재 화면의 제어권자
//                                Uri.fromFile(finalLocalFile)); // 음악파일
//            int a = holder.mp.getDuration(); // 노래의 재생시간(miliSecond)
//            holder.sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정
//            new MyThread().start(); // 씨크바 그려줄 쓰레드 시작
//            isPlaying = true; // 씨크바 쓰레드 반복 하도록


            holder.bPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag_restart == true || flag_mp_gone == true) {
                        //MediaPlayer 객체 초기화 , 재생
                        holder.mp = MediaPlayer.create(
                                finalConvertView.getContext(), // 현재 화면의 제어권자
                                Uri.fromFile(finalLocalFile)); // 음악파일
//                        holder.mp = MediaPlayer.create(
//                                finalConvertView.getContext(), // 현재 화면의 제어권자
//                                R.raw.track1); // 음악파일
                        flag_restart = false;

                        int a = holder.mp.getDuration(); // 노래의 재생시간(miliSecond)
                        holder.sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정
                        new MyThread().start(); // 씨크바 그려줄 쓰레드 시작
                        isPlaying = true; // 씨크바 쓰레드 반복 하도록
                    }

                    holder.mp.setLooping(false); // true:무한반복
                    holder.mp.start(); // 노래 재생 시작

                    holder.bPlay.setVisibility(View.GONE);
                    holder.bPause.setVisibility(View.VISIBLE);

                }
            });

            holder.bPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag_restart = false;
                    // 일시중지
                    pos = holder.mp.getCurrentPosition();
                    holder.mp.pause(); // 일시중지
                    isPlaying = false; // 쓰레드 정지
                    holder.bPlay.setVisibility(View.VISIBLE);
                    holder.bPause.setVisibility(View.GONE);
                }
            });
            holder.bRestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag_mp_gone == true) {
                        //MediaPlayer 객체 초기화 , 재생
                        holder.mp = MediaPlayer.create(
                                finalConvertView.getContext(), // 현재 화면의 제어권자
                                Uri.fromFile(finalLocalFile)); // 음악파일
//                        holder.mp = MediaPlayer.create(
//                                finalConvertView.getContext(), // 현재 화면의 제어권자
//                                R.raw.track1); // 음악파일
                        flag_restart = false;

                        int a = holder.mp.getDuration(); // 노래의 재생시간(miliSecond)
                        holder.sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정
                        new MyThread().start(); // 씨크바 그려줄 쓰레드 시작
                        isPlaying = true; // 씨크바 쓰레드 반복 하도록
                    }
                    // 멈춘 지점부터 재시작
                    holder.mp.setLooping(false); // true:무한반복
                    holder.mp.seekTo(0); // 맨처음으로 이동
                    holder.mp.start(); // 시작
                    isPlaying = true; // 재생하도록 flag 변경
                    new MyThread().start(); // 쓰레드 시작

                    holder.bPlay.setVisibility(View.GONE);
                    holder.bPause.setVisibility(View.VISIBLE);
                    flag_restart = true;
                }
            });

            holder.bStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 음악 종료
                    if (flag_mp_gone != true) {
                        isPlaying = false; // 쓰레드 종료
                        holder.mp.stop(); // 멈춤
                        holder.mp.release(); // 자원 해제

                        holder.sb.setProgress(0); // 씨크바 초기화
                        flag_mp_gone = true;
                    }
                }
            });
        }

        holder.choice1Radio.setChecked(mData.get(position).checked1);
        holder.choice2Radio.setChecked(mData.get(position).checked2);
        holder.choice3Radio.setChecked(mData.get(position).checked3);
        holder.choice4Radio.setChecked(mData.get(position).checked4);

        holder.choice1Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState1 = !mData.get(position).isChecked1();
                mData.get(position).checked1 = newState1;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = false;

                holder.choice2Radio.setChecked(false);
                holder.choice3Radio.setChecked(false);
                holder.choice4Radio.setChecked(false);
                ChangeAnswer("1");
            }
            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set()),String.valueOf(problemSet.getScore())));
                //Log.d("prob_set,PAdapter",mData.get(position).getProb_set());
            }
        });

        holder.choice2Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState2 = !mData.get(position).isChecked2();
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = newState2;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = false;

                holder.choice1Radio.setChecked(false);
                holder.choice3Radio.setChecked(false);
                holder.choice4Radio.setChecked(false);
                ChangeAnswer("2");
            }

            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set()),String.valueOf(problemSet.getScore())));
                //Log.d("prob_set,PAdapter",mData.get(position).getProb_set());
            }
        });

        holder.choice3Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState3 = !mData.get(position).isChecked3();
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = newState3;
                mData.get(position).checked4 = false;

                holder.choice1Radio.setChecked(false);
                holder.choice2Radio.setChecked(false);
                holder.choice4Radio.setChecked(false);
                ChangeAnswer("3");
            }

            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set()),String.valueOf(problemSet.getScore())));
                //Log.d("prob_set,PAdapter",mData.get(position).getProb_set());
            }

        });

        holder.choice4Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState4 = !mData.get(position).isChecked4();
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = newState4;

                holder.choice1Radio.setChecked(false);
                holder.choice2Radio.setChecked(false);
                holder.choice3Radio.setChecked(false);
                ChangeAnswer("4");
            }

            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set()),String.valueOf(problemSet.getScore())));
                Log.d("prob_set,PAdapter",problemSet.getProb_set());
            }
        });

        //데이터 설정
        holder.number.setText(problemSet.getProb_num());
        holder.arranged_num.setText(problemSet.getArranged_num());
        holder.common_question.setText(problemSet.getQuestion());
        holder.probSet.setText(problemSet.getProb_set());
        if(holder.common_question.getText().equals("")){
            holder.common_question.setVisibility(GONE);
        }
        holder.plural_question.setText(problemSet.getPlural_question());
        if(!holder.plural_question.getText().equals("")){
            holder.plural_question.setVisibility(VISIBLE);
        }
        holder.problemTextView.setText(problemSet.getQuestion_example());
        if(!holder.problemTextView.getText().equals("")){
            holder.exampleText.setVisibility(VISIBLE);
        } else{
            holder.exampleText.setVisibility(GONE);
        }

        holder.score.setText(problemSet.getScore());
        holder.solutionText.setText(problemSet.getSolution());

        holder.problemRealAnswer.setText(problemSet.getAnswer());
        holder.problemUserAnswer.setText(problemSet.getUser_answer());

        String user_answer = problemSet.getUser_answer();
        String real_answer = problemSet.getAnswer();

        if (user_answer != null){
            //O, X그리기
            O_drawing O_drawing = new O_drawing(convertView.getContext());
            X_drawing X_drawing = new X_drawing(convertView.getContext());
            if(Integer.parseInt(user_answer) ==Integer.parseInt(real_answer) ){
                //Log.d("O","O");
                holder.frameDraw.addView(O_drawing);
            } else {
                //Log.d("X","X");
                holder.frameDraw.addView(X_drawing);
            }

            //solution 용
            if(user_answer.equals("1")){
                holder.choice1Radio.setChecked(TRUE);
                holder.choice2Radio.setChecked(FALSE);
                holder.choice3Radio.setChecked(FALSE);
                holder.choice4Radio.setChecked(FALSE);
            }
            else if(user_answer.equals("2")){
                holder.choice1Radio.setChecked(FALSE);
                holder.choice2Radio.setChecked(TRUE);
                holder.choice3Radio.setChecked(FALSE);
                holder.choice4Radio.setChecked(FALSE);
            }
            else if(user_answer.equals("3")){
                holder.choice1Radio.setChecked(FALSE);
                holder.choice2Radio.setChecked(FALSE);
                holder.choice3Radio.setChecked(TRUE);
                holder.choice4Radio.setChecked(FALSE);
            }
            else{
                holder.choice1Radio.setChecked(FALSE);
                holder.choice2Radio.setChecked(FALSE);
                holder.choice3Radio.setChecked(FALSE);
                holder.choice4Radio.setChecked(TRUE);
            }
        }
        return convertView;
    }

    public UserAdapter return_uAdapter(){
        final UserAdapter uAdapter = this.uAdapter;
        return uAdapter;
    }
}
