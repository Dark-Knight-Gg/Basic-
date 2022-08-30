package com.example.basic_app_music;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView m1_txtTitle,m1_txtTimeStart,m1_txtTimeEnd;
    ImageView m1_imgDisc,m1_imgBack,m1_imgPlay,m1_imgEnd;
    SeekBar m1_seekbar;
    ArrayList<Song> listSong = new ArrayList<Song>();
    MediaPlayer mediaPlayer;
    int position =0;
    private void AnhXa(){
        m1_txtTitle =(TextView) findViewById(R.id.m1_txtTitle);
        m1_imgDisc=(ImageView) findViewById(R.id.m1_imgDisc);
        m1_txtTimeStart=(TextView) findViewById(R.id.m1_txtTimeStrart);
        m1_txtTimeEnd=(TextView) findViewById(R.id.m1_txtTimeEnd);
        m1_seekbar=(SeekBar) findViewById(R.id.m1_seekbar);
        m1_imgBack=(ImageView) findViewById(R.id.m1_imgBack);
        m1_imgPlay=(ImageView) findViewById(R.id.m1_imgPlay);
        m1_imgEnd=(ImageView) findViewById(R.id.m1_imgEnd);
    }
    private void addSong(){
        listSong.add(new Song("Ánh sao và bầu trời",R.raw.anh_sao_va_bau_troi));
        listSong.add(new Song("3107_4",R.raw.b3107_4));
        listSong.add(new Song("Bắt cóc con tim",R.raw.bat_coc_con_tim));
        listSong.add(new Song("Bên trên tầng lầu",R.raw.ben_tren__tang_lau));
        listSong.add(new Song("Dạ vũ",R.raw.da_vu));
        listSong.add(new Song("Là do em xui thôi",R.raw.la_do_em_xui_));
        listSong.add(new Song("Lời tạm biệt chưa nói",R.raw.loi_tam_biet_chua_noi));
        listSong.add(new Song("Pháo hồng",R.raw.phao_hong));
        listSong.add(new Song("Tệ thật anh nhớ em",R.raw.te_that_anh_nho_em));
        listSong.add(new Song("Vì mẹ anh bắt chia tay",R.raw.vi_me_anh_bat_chia_tay));
    }
    private void KhoiTao(){
        mediaPlayer = MediaPlayer.create(MainActivity.this,listSong.get(position).getFile());
        m1_txtTitle.setText(listSong.get(position).getNameSong());
    }
    private void setTimeEnd(){
        SimpleDateFormat fo = new SimpleDateFormat("mm:ss");
        m1_txtTimeEnd.setText(fo.format(mediaPlayer.getDuration()));
        m1_seekbar.setMax(mediaPlayer.getDuration());
    }
    private void setTimeStart(){
        SimpleDateFormat fo = new SimpleDateFormat("mm:ss");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                m1_txtTimeStart.setText(fo.format(mediaPlayer.getCurrentPosition()));
                m1_seekbar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        if(position<listSong.size()-1){
                            position ++;
                        }
                        else{
                            position=0;
                        }
                        KhoiTao();
                        mediaPlayer.start();
                        m1_imgPlay.setImageResource(R.drawable.pause);
                        setTimeEnd();
                        setTimeStart();
                    }
                });
                handler.postDelayed(this,800);
            }
        },100);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        addSong();
        KhoiTao();
        m1_imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    Animation anim1 = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_is_pause);
                    mediaPlayer.pause();
                    m1_imgPlay.setImageResource(R.drawable.play);
                    m1_imgDisc.startAnimation(anim1);
                }else{
                    Animation anim2 = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_is_play);
                    mediaPlayer.start();
                    m1_imgPlay.setImageResource(R.drawable.pause);
                    m1_imgDisc.setAnimation(anim2);
                }
                setTimeEnd();
                setTimeStart();
            }
        });
        m1_imgEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
               if(position<listSong.size()-1){
                   position ++;
               }
               else{
                   position=0;
               }

               KhoiTao();
               mediaPlayer.start();
                m1_imgPlay.setImageResource(R.drawable.pause);
                setTimeEnd();
                setTimeStart();
            }
        });
        m1_imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                if(position>0){
                    position--;
                }else{
                    position=9;
                }
                KhoiTao();
                mediaPlayer.start();
                m1_imgPlay.setImageResource(R.drawable.pause);
                setTimeEnd();
                setTimeStart();
            }
        });
        m1_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(m1_seekbar.getProgress());
            }
        });
    }
}