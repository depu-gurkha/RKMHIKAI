package online.rkmhikai.ui.MyQuiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import online.rkmhikai.R;


public class PlayActivity extends AppCompatActivity {

    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Bundle bundle= getIntent().getExtras();
        int contentID=getIntent().getIntExtra("ContentID",0);

        Button buttonPlay = findViewById(R.id.bt_play);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PlayActivity.this, QuizActivity.class);

                intent.putExtra("myContentId",contentID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {


            new AlertDialog.Builder(this)
                    .setTitle("Do you want to Exit ?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setResult(RESULT_OK, new Intent().putExtra("Exit", true));
                            finish();
                        }
                    }).create().show();



    }

}
