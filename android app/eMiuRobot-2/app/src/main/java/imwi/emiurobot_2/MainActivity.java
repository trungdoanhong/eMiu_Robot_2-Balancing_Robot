package imwi.emiurobot_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class MainActivity extends AppCompatActivity {

    BluetoothSPP bt;
    TextView tvConnection;
    SeekBar sbLeftMotorSpeed;
    SeekBar sbRightMotorSpeed;
    TextView tvRightSpeed;
    TextView tvLeftSpeed;
    SeekBar sbDirection;
    SeekBar sbTwoMotorSpeed;
    TextView tvTwoMotorSpeed;
    TextView tvYawValue;
    TextView tvPitchValue;
    TextView tvRollValue;
    TextView tvSteeringAngle;

    EditText etMinP;
    SeekBar sbP;
    TextView tvP;
    EditText etMaxP;

    EditText etMinI;
    SeekBar sbI;
    TextView tvI;
    EditText etMaxI;

    EditText etMinD;
    SeekBar sbD;
    TextView tvD;
    EditText etMaxD;

    EditText etMinO;
    SeekBar sbO;
    TextView tvO;
    EditText etMaxO;

    EditText etMinA;
    SeekBar sbA;
    TextView tvA;
    EditText etMaxA;

    EditText etMinM;
    SeekBar sbM;
    TextView tvM;
    EditText etMaxM;

    TextView tvLog;

    Button btDecrease[];
    Button btIncrease[];

    Button btRefresh;
    Button btHelp;

    CheckBox cbDivide;

    SeekBar[] sbValues;
    TextView[] tvValues;
    EditText[] etMin;

    int LeftDirectionValue = 0;
    int RightDirectionValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(imwi.emiurobot_2.R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        InitWidget();
        InitEvent();
        InitGlobalVariable();
    }

    void InitWidget()
    {
        tvConnection = (TextView) findViewById(imwi.emiurobot_2.R.id.tvConnection);
        sbLeftMotorSpeed = (SeekBar) findViewById(imwi.emiurobot_2.R.id.sbLeftMotorSpeed);
        sbRightMotorSpeed = (SeekBar) findViewById(imwi.emiurobot_2.R.id.sbRightMotorSpeed);
        tvRightSpeed = (TextView) findViewById(imwi.emiurobot_2.R.id.tvRightSpeed);
        tvLeftSpeed = (TextView) findViewById(imwi.emiurobot_2.R.id.tvLeftSpeed);
        sbDirection = (SeekBar) findViewById(imwi.emiurobot_2.R.id.sbDirection);
        sbTwoMotorSpeed = (SeekBar) findViewById(imwi.emiurobot_2.R.id.sbTwoMotorSpeed);
        tvTwoMotorSpeed = (TextView) findViewById(imwi.emiurobot_2.R.id.tv2MotorSpeed);
        tvYawValue = (TextView) findViewById(imwi.emiurobot_2.R.id.tvYawValue);
        tvPitchValue = (TextView) findViewById(imwi.emiurobot_2.R.id.tvPitchValue);
        tvRollValue = (TextView) findViewById(imwi.emiurobot_2.R.id.tvRollValue);
        tvSteeringAngle = (TextView) findViewById(imwi.emiurobot_2.R.id.tvSteeringAngle);
        bt = new BluetoothSPP(this);

        etMinP = (EditText) findViewById(R.id.etMinP);
        sbP = (SeekBar) findViewById(R.id.sbP);
        tvP = (TextView) findViewById(R.id.tvP);
        etMaxP = (EditText) findViewById(R.id.etMaxP);

        etMinI = (EditText) findViewById(R.id.etMinI);
        sbI = (SeekBar) findViewById(R.id.sbI);
        tvI = (TextView) findViewById(R.id.tvI);
        etMaxI = (EditText) findViewById(R.id.etMaxI);

        etMinD = (EditText) findViewById(R.id.etMinD);
        sbD = (SeekBar) findViewById(R.id.sbD);
        tvD = (TextView) findViewById(R.id.tvD);
        etMaxD = (EditText) findViewById(R.id.etMaxD);

        etMinO = (EditText) findViewById(R.id.etMinO);
        sbO = (SeekBar) findViewById(R.id.sbO);
        tvO = (TextView) findViewById(R.id.tvO);
        etMaxO = (EditText) findViewById(R.id.etMaxO);

        etMinA = (EditText) findViewById(R.id.etMinA);
        sbA = (SeekBar) findViewById(R.id.sbA);
        tvA = (TextView) findViewById(R.id.tvA);
        etMaxA = (EditText) findViewById(R.id.etMaxA);

        etMinM = (EditText) findViewById(R.id.etMinM);
        sbM = (SeekBar) findViewById(R.id.sbM);
        tvM = (TextView) findViewById(R.id.tvM);
        etMaxM = (EditText) findViewById(R.id.etMaxM);

        tvLog = (TextView) findViewById(R.id.tvLog);

        btDecrease = new Button[6];
        btIncrease = new Button[6];

        btDecrease[0] = (Button) findViewById(R.id.btDecreaseP);
        btDecrease[1] = (Button) findViewById(R.id.btDecreaseI);
        btDecrease[2] = (Button) findViewById(R.id.btDecreaseD);
        btDecrease[3] = (Button) findViewById(R.id.btDecreaseO);
        btDecrease[4] = (Button) findViewById(R.id.btDecreaseA);
        btDecrease[5] = (Button) findViewById(R.id.btDecreaseM);

        btIncrease[0] = (Button) findViewById(R.id.btIncreaseP);
        btIncrease[1] = (Button) findViewById(R.id.btIncreaseI);
        btIncrease[2] = (Button) findViewById(R.id.btIncreaseD);
        btIncrease[3] = (Button) findViewById(R.id.btIncreaseO);
        btIncrease[4] = (Button) findViewById(R.id.btIncreaseA);
        btIncrease[5] = (Button) findViewById(R.id.btIncreaseM);

        btRefresh = (Button) findViewById(R.id.btRefresh);
        btHelp = (Button) findViewById(R.id.btHelp);

        cbDivide = (CheckBox) findViewById(R.id.cbDivide);

        sbValues = new SeekBar[6];

        sbValues[0] = sbP;
        sbValues[1] = sbI;
        sbValues[2] = sbD;
        sbValues[3] = sbO;
        sbValues[4] = sbA;
        sbValues[5] = sbM;

        tvValues = new TextView[6];

        tvValues[0] = tvP;
        tvValues[1] = tvI;
        tvValues[2] = tvD;
        tvValues[3] = tvO;
        tvValues[4] = tvA;
        tvValues[5] = tvM;

        etMin = new EditText[6];

        etMin[0] = etMinP;
        etMin[1] = etMinI;
        etMin[2] = etMinD;
        etMin[3] = etMinO;
        etMin[4] = etMinA;
        etMin[5] = etMinM;
    }

    void InitEvent()
    {
        tvConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                bt.setDeviceTarget(BluetoothState.DEVICE_OTHER);
                Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
            }
        });

        if(!bt.isBluetoothAvailable()) {
            tvConnection.setText("Bluetooth is not available");
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message)
            {
                if (message.charAt(0) == 'Y')
                {
                    tvYawValue.setText(message.substring(1));
                }

                if (message.charAt(0) == 'P')
                {
                    tvPitchValue.setText(message.substring(1));
                }

                if (message.charAt(0) == 'R')
                {
                    tvRollValue.setText(message.substring(1));
                }

                // PIDO

                if (message.substring(0, 2).equals("kp"))
                {
                    SetParameter(etMinP, sbP, tvP, etMaxP, message);
                }

                if (message.substring(0, 2).equals("ki"))
                {
                    SetParameter(etMinI, sbI, tvI, etMaxI, message);
                }

                if (message.substring(0, 2).equals("kd"))
                {
                    SetParameter(etMinD, sbD, tvD, etMaxD, message);
                }

                if (message.substring(0, 2).equals("os"))
                {
                    SetParameter(etMinO, sbO, tvO, etMaxO, message);
                }

                if (message.substring(0, 2).equals("ba"))
                {
                    SetParameter(etMinA, sbA, tvA, etMaxA, message);
                }

                if (message.substring(0, 2).equals("om"))
                {
                    SetParameter(etMinM, sbM, tvM, etMaxM, message);
                }

                if (message.substring(0,2).equals("op"))
                {
                    Log(message);
                }
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceDisconnected()
            {
                tvConnection.setText("Status : Not connect");
            }

            public void onDeviceConnectionFailed() {
                tvConnection.setText("Status : Connection failed");
            }

            public void onDeviceConnected(String name, String address)
            {
                tvConnection.setText("Status : Connected to " + name);

                bt.send("spido", true);
            }
        });

        sbLeftMotorSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser)
            {
                int value = (progressValue - 51) * 5 + LeftDirectionValue;
                tvLeftSpeed.setText("Left Speed: " + Integer.toString(value));
                bt.send(new String("ls-") + Integer.toString(value), true);
                bt.send("ss", true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(51);
                tvLeftSpeed.setText(Integer.toString(0));
                bt.send(new String("ls-") + Integer.toString(0), true);
                bt.send("ss", true);

            }
        });

        sbRightMotorSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser)
            {
                int value = (progressValue - 51) * 5 + RightDirectionValue;
                tvRightSpeed.setText("Right Speed: " + Integer.toString(value));
                bt.send(new String("rs-") + Integer.toString(value), true);
                bt.send("ss", true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(51);
                tvRightSpeed.setText(Integer.toString(0));
                bt.send(new String("rs-") + Integer.toString(0), true);
                bt.send("ss", true);
            }
        });

        sbTwoMotorSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser)
            {
                int value = (progressValue - 51) * 5;
                tvTwoMotorSpeed.setText("2 Motor Speed: " + Integer.toString(value));
                bt.send(new String("ma-") + Integer.toString(value), true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(51);

            }
        });

        sbDirection.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser)
            {
                int value = (progressValue - 20) * 15;
                if (value > 0)
                {
                    LeftDirectionValue = 0;
                    RightDirectionValue = 0 - value;
                }
                else
                {
                    LeftDirectionValue = 0 + value;
                    RightDirectionValue = 0;
                }

                tvSteeringAngle.setText("Stearing Angle: " + Integer.toString(value));

                int output = (sbLeftMotorSpeed.getProgress() - 51) * 5 + LeftDirectionValue;
                tvLeftSpeed.setText("Left Speed: " + Integer.toString(output));
                bt.send(new String("ls-") + Integer.toString(output), true);
                bt.send("ss", true);

                int output2 = (sbRightMotorSpeed.getProgress() - 51) * 5 + RightDirectionValue;
                tvRightSpeed.setText("Right Speed: " + Integer.toString(output2));
                bt.send(new String("rs-") + Integer.toString(output2), true);
                bt.send("ss", true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(20);
                LeftDirectionValue = 0;
                RightDirectionValue = 0;

                int output = (sbLeftMotorSpeed.getProgress() - 51) * 5 + LeftDirectionValue;
                tvLeftSpeed.setText("Left Speed: " + Integer.toString(output));
                bt.send(new String("ls-") + Integer.toString(output), true);
                bt.send("ss", true);

                int output2 = (sbRightMotorSpeed.getProgress() - 51) * 5 + RightDirectionValue;
                tvRightSpeed.setText("Right Speed: " + Integer.toString(output2));
                bt.send(new String("rs-") + Integer.toString(output2), true);
                bt.send("ss", true);
            }
        });

        SeekBar.OnSeekBarChangeListener ChangePIDOEvent = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser)
            {
                ResetMinMaxPIDValue();

                EditText etMin;
                TextView tvValue;
                String prefix;

                if (seekBar == sbP) {
                    prefix = "kp";
                    etMin = etMinP;
                    tvValue = tvP;
                }
                else if (seekBar == sbI) {
                    prefix = "ki";
                    etMin = etMinI;
                    tvValue = tvI;
                }
                else if (seekBar == sbD){
                    prefix = "kd";
                    etMin = etMinD;
                    tvValue = tvD;
                }
                else if (seekBar == sbO){
                    prefix = "os";
                    etMin = etMinO;
                    tvValue = tvO;
                }

                else if (seekBar == sbA){
                    prefix = "ba";
                    etMin = etMinA;
                    tvValue = tvA;
                }

                else {
                    prefix = "om";
                    etMin = etMinM;
                    tvValue = tvM;
                }


                int value = progressValue + (int)Float.parseFloat(etMin.getText().toString());

                tvValue.setText(tvValue.getText().toString().substring(0, 3) + Integer.toString(value));

                tvLog.setText(new String("sb") + value);

                bt.send(prefix + new String("-") + Integer.toString(value), true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                bt.send("se", true);
            }
        };

        sbP.setOnSeekBarChangeListener(ChangePIDOEvent);
        sbI.setOnSeekBarChangeListener(ChangePIDOEvent);
        sbD.setOnSeekBarChangeListener(ChangePIDOEvent);
        sbO.setOnSeekBarChangeListener(ChangePIDOEvent);
        sbA.setOnSeekBarChangeListener(ChangePIDOEvent);
        sbM.setOnSeekBarChangeListener(ChangePIDOEvent);

        Button.OnClickListener PressAdjustButtonEvent = new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;

                String[] prefixs = {"kp", "ki", "kd", "os", "ba", "om"};

                for (int i = 0; i < 6; i++)
                {
                    if (cbDivide.isChecked() == true) {

                        if (btDecrease[i] == button) {
                            SetAndSendValue(tvValues[i], etMin[i], sbValues[i], prefixs[i], -0.1f);
                        }

                        if (button == btIncrease[i]) {
                            SetAndSendValue(tvValues[i], etMin[i], sbValues[i], prefixs[i], 0.1f);
                        }
                    }
                    else
                    {
                        if (btDecrease[i] == button) {
                            sbValues[i].setProgress(sbValues[i].getProgress() - 1);
                        }

                        if (button == btIncrease[i]) {
                            sbValues[i].setProgress(sbValues[i].getProgress() + 1);
                        }
                    }
                }

                bt.send("se", true);
            }
        };

        for( int i = 0; i < 6; i++)
        {
            btIncrease[i].setOnClickListener(PressAdjustButtonEvent);
            btDecrease[i].setOnClickListener(PressAdjustButtonEvent);
        }

        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt.send("spido", true);
            }
        });

        btHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Help");
                alertDialog.setMessage("Receive : [prefix][value]\nkp4.5, ki10, kd0.2, os26, ba6, Y20, P30, R40 \n\n Transmit : [prefix]-[value]\\r\\n\n kp-4.5\\r\\n, ki-10\\r\\n, kd-0.2\\r\\n, os-26\\r\\n, ba-6\\r\\n" +
                        "\nSave Eeprom Command : se" +
                        "\nRequest Parameters From eMiu : spdio");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

    }

    void InitGlobalVariable()
    {
        ResetMinMaxPIDValue();
    }

    void ResetMinMaxPIDValue()
    {
        sbP.setMax(Integer.parseInt(etMaxP.getText().toString()) - Integer.parseInt(etMinP.getText().toString()));
        sbI.setMax(Integer.parseInt(etMaxI.getText().toString()) - Integer.parseInt(etMinI.getText().toString()));
        sbD.setMax(Integer.parseInt(etMaxD.getText().toString()) - Integer.parseInt(etMinD.getText().toString()));
        sbO.setMax(Integer.parseInt(etMaxO.getText().toString()) - Integer.parseInt(etMinO.getText().toString()));
        sbA.setMax(Integer.parseInt(etMaxO.getText().toString()) - Integer.parseInt(etMinO.getText().toString()));
        sbM.setMax(Integer.parseInt(etMaxM.getText().toString()) - Integer.parseInt(etMinM.getText().toString()));
    }

    void Log(String ms)
    {
        tvLog.setText(new String("Log: ") + ms);
    }

    void SetParameter(EditText etMin, SeekBar sbProcess, TextView tvValue, EditText etMax, String message)
    {
        String valueStr = message.substring(2);

        float value = Float.parseFloat(valueStr);
        int min = (int)value - 50;
        int max = (int)value + 50;

        tvValue.setText(tvValue.getText().toString().substring(0, 3) + valueStr);
        etMin.setText(Integer.toString(min));
        etMax.setText(Integer.toString(max));
        sbProcess.setProgress(Math.abs((int)value - min));
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }

    void SetAndSendValue(TextView tvValue, EditText etMin, SeekBar sbValue, String prefix, float subNum)
    {
        float value = Float.parseFloat(tvValue.getText().toString().substring(3));
        value = value + subNum;



        value = Math.round(value * 1000)/1000.0f;

        tvLog.setText(new String("") + value);

        if ((int)(value) != sbValue.getProgress())
        {
            sbValue.setProgress((int)value - Integer.parseInt(etMin.getText().toString()));
        }

        tvValue.setText(tvValue.getText().toString().substring(0, 3) + value);
        bt.send(prefix + new String("-") + Float.toString(value), true);
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
            } else {
                tvConnection.setText("Bluetooth was not enabled.");
                finish();
            }
        }
    }
}