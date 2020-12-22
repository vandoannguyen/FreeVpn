package com.freeproxy.vpnmaster.hotspot2.ui.speedTest;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freeproxy.vpnmaster.hotspot2.R;
import com.freeproxy.vpnmaster.hotspot2.common.Config;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogLoading;
import com.freeproxy.vpnmaster.hotspot2.ui.speedTest.test.HttpDownloadTest;
import com.freeproxy.vpnmaster.hotspot2.ui.speedTest.test.HttpUploadTest;
import com.freeproxy.vpnmaster.hotspot2.ui.speedTest.test.PingTest;
import com.oneadx.android.oneads.AdInterstitial;
import com.oneadx.android.oneads.AdListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpeedTest extends AppCompatActivity {
    static int position = 0;
    static int lastPosition = 0;
    GetSpeedTestHost getSpeedTestHost = null;
    HashSet<String> tempBlackList;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.txtPing)
    TextView txtPing;
    @BindView(R.id.txtDownload)
    TextView txtDowload;
    @BindView(R.id.txtUpload)
    TextView txtUpload;
    @BindView(R.id.imgBar)
    ImageView imgBar;
    @BindView(R.id.btnTest)
    Button btnTest;
    final DecimalFormat dec = new DecimalFormat("#.##");
    @BindView(R.id.txtOS)
    TextView txtOS;
    @BindView(R.id.txtModel)
    TextView txtModel;
    @BindView(R.id.txtBrand)
    TextView txtBrand;
    @BindView(R.id.txtCPU1)
    TextView txtCPU1;
    @BindView(R.id.txtCPU2)
    TextView txtCPU2;
    RotateAnimation rotate;
    @BindView(R.id.frmAdsSpeedTest)
    FrameLayout frmAdsSpeedTest;
    private AdInterstitial inter;

    @Override
    public void onResume() {
        super.onResume();
        getSpeedTestHost = new GetSpeedTestHost();
        getSpeedTestHost.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_test);
        ButterKnife.bind(this);
        getDeviceInfor();
        initAds();
        btnTest.setText("Start Test");
        tempBlackList = new HashSet<>();
        getSpeedTestHost = new GetSpeedTestHost();
        getSpeedTestHost.start();
        frmAdsSpeedTest.setVisibility(View.GONE);
//        Ads.getInstance(this).banner(frmAdsSpeedTest, Ads.AdsSize.BANNER);
    }

    private void initAds() {
        inter = new AdInterstitial(this);
        inter.load();
    }

    @OnClick({R.id.btnBack, R.id.btnTest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnTest:
                if (Config.getRandom()) {
                    DialogLoading.showDialog(this);
                    inter.show(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            DialogLoading.dismish();
                            startTest();
                        }
                    });
                } else {
                    startTest();
                }
                break;
        }
    }

    private void startTest() {
        btnTest.setEnabled(false);
        if (getSpeedTestHost == null) {
            getSpeedTestHost = new GetSpeedTestHost();
            getSpeedTestHost.start();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnTest.setText("Selecting best server based on ping...");
                    }
                });
                //Get egcodes.speedtest hosts
                int timeCount = 600;
                while (!getSpeedTestHost.isFinished()) {
                    timeCount--;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                    if (timeCount <= 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SpeedTest.this, "No Connection...", Toast.LENGTH_LONG).show();
                                btnTest.setEnabled(true);
                                btnTest.setTextSize(16);
                                btnTest.setText("Restart Test");
                            }
                        });
                        getSpeedTestHost = null;
                        return;
                    }
                }
                //Find closest server
                HashMap<Integer, String> mapKey = getSpeedTestHost.getMapKey();
                HashMap<Integer, List<String>> mapValue = getSpeedTestHost.getMapValue();
                double selfLat = getSpeedTestHost.getSelfLat();
                double selfLon = getSpeedTestHost.getSelfLon();
                double tmp = 19349458;
                double dist = 0.0;
                int findServerIndex = 0;
                for (int index : mapKey.keySet()) {
                    if (tempBlackList.contains(mapValue.get(index).get(5))) {
                        continue;
                    }

                    Location source = new Location("Source");
                    source.setLatitude(selfLat);
                    source.setLongitude(selfLon);

                    List<String> ls = mapValue.get(index);
                    Location dest = new Location("Dest");
                    dest.setLatitude(Double.parseDouble(ls.get(0)));
                    dest.setLongitude(Double.parseDouble(ls.get(1)));

                    double distance = source.distanceTo(dest);
                    if (tmp > distance) {
                        tmp = distance;
                        dist = distance;
                        findServerIndex = index;
                    }
                }
                String uploadAddr = mapKey.get(findServerIndex);
                final List<String> info = mapValue.get(findServerIndex);
                final double distance = dist;

                if (info == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnTest.setTextSize(12);
                            btnTest.setText("There was a problem in getting Host Location. Try again later.");
                        }
                    });
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnTest.setTextSize(13);
                        btnTest.setText(String.format("Host Location: %s [Distance: %s km]", info.get(2), new DecimalFormat("#.##").format(distance / 1000)));
                    }
                });
                //Reset value, graphics
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtPing.setText("0.00");
                        txtDowload.setText("0.00");
                        txtUpload.setText("0.00");
                    }
                });
                final List<Double> pingRateList = new ArrayList<>();
                final List<Double> downloadRateList = new ArrayList<>();
                final List<Double> uploadRateList = new ArrayList<>();
                Boolean pingTestStarted = false;
                Boolean pingTestFinished = false;
                Boolean downloadTestStarted = false;
                Boolean downloadTestFinished = false;
                Boolean uploadTestStarted = false;
                Boolean uploadTestFinished = false;
                //Init Test
                final PingTest pingTest = new PingTest(info.get(6).replace(":8080", ""), 6);
                final HttpDownloadTest downloadTest = new HttpDownloadTest(uploadAddr.replace(uploadAddr.split("/")[uploadAddr.split("/").length - 1], ""));
                final HttpUploadTest uploadTest = new HttpUploadTest(uploadAddr);
                //Tests
                while (true) {
                    if (!pingTestStarted) {
                        pingTest.start();
                        pingTestStarted = true;
                    }
                    if (pingTestFinished && !downloadTestStarted) {
                        downloadTest.start();
                        downloadTestStarted = true;
                    }
                    if (downloadTestFinished && !uploadTestStarted) {
                        uploadTest.start();
                        uploadTestStarted = true;
                    }
                    //Ping Test
                    if (pingTestFinished) {
                        //Failure
                        if (pingTest.getAvgRtt() == 0) {
                            System.out.println("Ping error...");
                        } else {
                            //Success
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtPing.setText(dec.format(pingTest.getAvgRtt()) + " ms");
                                }
                            });
                        }
                    } else {
                        pingRateList.add(pingTest.getInstantRtt());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtPing.setText(dec.format(pingTest.getInstantRtt()) + " ms");
                            }
                        });

                    }
                    //Download Test
                    if (pingTestFinished) {
                        if (downloadTestFinished) {
                            //Failure
                            if (downloadTest.getFinalDownloadRate() == 0) {
                                System.out.println("Download error...");
                            } else {
                                //Success
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtDowload.setText(dec.format(downloadTest.getFinalDownloadRate()) + " Mbps");
                                        Log.e("Dow", "run: dowload");
                                    }
                                });
                            }
                        } else {
                            //Calc position
                            double downloadRate = downloadTest.getInstantDownloadRate();
                            downloadRateList.add(downloadRate);
                            position = getPositionByRate(downloadRate);
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    setRotate(lastPosition, position);
                                    txtDowload.setText(dec.format(downloadTest.getInstantDownloadRate()) + " Mbps");
                                }

                            });
                            lastPosition = position;
                        }
                    }
                    //Upload Test
                    if (downloadTestFinished) {

                        if (uploadTestFinished) {
                            //Failure
                            if (uploadTest.getFinalUploadRate() == 0) {
                                System.out.println("Upload error...");
                            } else {
                                //Success
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtUpload.setText(dec.format(uploadTest.getFinalUploadRate()) + " Mbps");
                                    }
                                });
                            }
                        } else {
                            Log.e("Upload ", "run: up--");
                            //Calc position
                            double uploadRate = uploadTest.getInstantUploadRate();
                            uploadRateList.add(uploadRate);
                            position = getPositionByRate(uploadRate);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setRotate(lastPosition, position);
                                    txtUpload.setText(dec.format(uploadTest.getInstantUploadRate()) + " Mbps");
                                    Log.e("Upload ", "run: " + dec.format(uploadTest.getInstantUploadRate()));
                                }

                            });
                            lastPosition = position;
                        }
                    }
                    //Test bitti
                    if (pingTestFinished && downloadTestFinished && uploadTest.isFinished()) {
                        break;
                    }

                    if (pingTest.isFinished()) {
                        pingTestFinished = true;
                    }
                    if (downloadTest.isFinished()) {
                        downloadTestFinished = true;
                    }
                    if (uploadTest.isFinished()) {
                        uploadTestFinished = true;
                    }

                    if (pingTestStarted && !pingTestFinished) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                        }
                    } else {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                //Thread bitiminde button yeniden aktif ediliyor
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnTest.setEnabled(true);
                        btnTest.setTextSize(16);
                        btnTest.setText("Restart Test");
                    }
                });

            }
        }).start();
    }

    private void setRotate(int lastPosition, int position) {
        rotate = new RotateAnimation(lastPosition, position, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(200);
        imgBar.startAnimation(rotate);
    }

    public int getPositionByRate(double rate) {
        if (rate <= 1) {
            return (int) (rate * 30);

        } else if (rate <= 10) {
            return (int) (rate * 6) + 30;

        } else if (rate <= 30) {
            return (int) ((rate - 10) * 3) + 90;

        } else if (rate <= 50) {
            return (int) ((rate - 30) * 1.5) + 150;

        } else if (rate <= 100) {
            return (int) ((rate - 50) * 1.2) + 180;
        }

        return 0;
    }

    private void getDeviceInfor() {
        System.getProperty("os.version");
        String version = Build.VERSION.RELEASE;
        String model = Build.MODEL;
        String brand = Build.BRAND;
        String cpu1 = Build.CPU_ABI;
        String cpu2 = Build.CPU_ABI2;
        txtOS.setText(version);
        txtModel.setText(model);
        txtBrand.setText(brand);
        txtCPU1.setText(cpu1);
        txtCPU2.setText(cpu2);
    }
}