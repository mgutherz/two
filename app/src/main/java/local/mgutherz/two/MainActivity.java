package local.mgutherz.two;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ACCESS_COARSE_LOCATION = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_COARSE_LOCATION:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }

    public void onClrClick(View view) {

        //this is a comment - anything with two forward strokes preceding it will be ignored by Android Studio

        TextView textView = findViewById(R.id.tv1);
        textView.setText("");

    }

    public void onTelClick(View view) {

        //this is a comment - anything with two forward strokes preceding it will be ignored by Android Studio
        String message = "Telephony\n";
        LocalDateTime now = LocalDateTime.now();
        message += now.toString() + "\n";
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        message += tm.getNetworkOperatorName();
        message += " // \n";

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);
        }
        List<CellInfo> cells = tm.getAllCellInfo();
        int cellCount = cells.size();
        Toast toasty = makeText(getApplicationContext(), "Cells: " + cellCount, Toast.LENGTH_LONG);
        toasty.show();

        for (int i = 0; i < cells.size(); i++) {
            message += i;
            message += "\n";            CellInfo cellinfo = cells.get(i);
            message += cellinfo.toString();
            message += " // \n";
        }

        TextView textView = findViewById(R.id.tv1);
        textView.setText(message);

    }

    public void onNetClick(View view) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = connectivityManager.getAllNetworks();
        int netCount = networks.length;
        Toast toasty = makeText(getApplicationContext(), "Networks: " + netCount, Toast.LENGTH_LONG);
        toasty.show();
        TextView textView = findViewById(R.id.tv1);

        String message = "Networking\n";
        LocalDateTime now = LocalDateTime.now();
        message += now.toString() + "\n";
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = connectivityManager.getNetworkInfo(mNetwork);
            message += networkInfo.getTypeName();
            message += " // \n";
            message += networkInfo.toString();
            message += " // \n";

        }

        textView.setText(message);
    }
    public void onLogClick(View view) {

        String LOG_TAG = "onLogClick";

        TextView textView = findViewById(R.id.tv1);
        String message = (String) textView.getText();

        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(this, "The External storage is NOT writable", Toast.LENGTH_LONG).show();
            return;
        }
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String datetext = date.format(formatter);
        String filename = "two_" + datetext + ".txt"; // timestamp YYYYMMDDhhmmss TXT for media scanner

        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
            Toast.makeText(this, "Directory not created", Toast.LENGTH_LONG).show();
            return;
        }


        Toast.makeText(this, "Written", Toast.LENGTH_LONG).show();

    }

}