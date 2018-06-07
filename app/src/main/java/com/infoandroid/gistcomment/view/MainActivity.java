

package com.infoandroid.gistcomment.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.infoandroid.gistcomment.QRcode.QRDataListener;
import com.infoandroid.gistcomment.QRcode.QREader;
import com.infoandroid.gistcomment.R;
import com.infoandroid.gistcomment.RPResultListener;
import com.infoandroid.gistcomment.RuntimePermissionUtil;
import com.infoandroid.gistcomment.preferences.AppSharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

  private static final String cameraPerm = Manifest.permission.CAMERA;

  @BindView(R.id.btnStartStop)
  public Button stateBtn;

  @BindView(R.id.camera_view)
   public SurfaceView mySurfaceView;

  private QREader qrEader;

  boolean hasCameraPermission = false;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_qr);
    ButterKnife.bind(this);
    hasCameraPermission = RuntimePermissionUtil.checkPermissonGranted(this, cameraPerm);


    // change of reader state in dynamic
    stateBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (qrEader.isCameraRunning()) {
          stateBtn.setText("Start QREader");
          qrEader.stop();
        } else {
          stateBtn.setText("Stop QREader");
          qrEader.start();
        }
      }
    });

    stateBtn.setVisibility(View.VISIBLE);
    if (hasCameraPermission) {
      // Setup QREader
      setupQREader();
    } else {
      RuntimePermissionUtil.requestPermission(MainActivity.this, cameraPerm, 100);
    }
  }

  void restartActivity() {
    startActivity(new Intent(MainActivity.this, MainActivity.class));
    finish();
  }

  void setupQREader() {
    // Init QREader
    // ------------
    qrEader = new QREader.Builder(this, mySurfaceView, new QRDataListener() {
      @Override
      public void onDetected(final String data) {
        Log.d("QREader", "Value : " + data);
        AppSharedPreference.putString("url",data,MainActivity.this);
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        finish();
      }
    }).facing(QREader.BACK_CAM)
        .enableAutofocus(true)
        .height(mySurfaceView.getHeight())
        .width(mySurfaceView.getWidth())
        .build();
  }

  @Override
  protected void onPause() {
    super.onPause();

    if (hasCameraPermission) {

      // Cleanup in onPause()
      // --------------------
      qrEader.releaseAndCleanup();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    if (hasCameraPermission) {

      // Init and Start with SurfaceView
      // -------------------------------
      qrEader.initAndStart(mySurfaceView);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
      @NonNull final int[] grantResults) {
    if (requestCode == 100) {
      RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
        @Override
        public void onPermissionGranted() {
          if ( RuntimePermissionUtil.checkPermissonGranted(MainActivity.this, cameraPerm)) {
            restartActivity();
          }
        }

        @Override
        public void onPermissionDenied() {
          // do nothing
        }
      });
    }
  }
}
