package android.tom.playground.filehandling;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.MainActivity;
import android.tom.playground.R;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

public class DownloadActivity extends AppCompatActivity {
String url;
String type;
    private DownloadManager downloadManager;
    Button downlad,status,cancel;
    private long Music_DownloadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        downlad = (Button) findViewById(R.id.download);
        status = (Button) findViewById(R.id.download_status);
        cancel = (Button) findViewById(R.id.download_cancel);
     //   url = getIntent().getExtras().getString("url");
      //  type = getIntent().getExtras().getString("type");

        url = "http://iconicimagesinternational.com/wp-content/uploads/Aperture-Shutter-Speed-ISO-Explanations.pdf";
        type = "pdf";
        final Uri uri = Uri.parse(url);

        downlad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadData(uri,type);
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  String filename = Environment.DIRECTORY_DOWNLOADS+"sampletest.jpg";
                String filename =   "file://"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/sampletest.pdf";
                openfile(filename);

            }
        });
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        getBaseContext().registerReceiver(downloadReceiver,filter);
    }


    private long DownloadData (Uri uri, String type) {

        long downloadReference;

        // Create request for android download manager
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("Data Download");

        //Setting description of request
        request.setDescription("");

        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
        if(type.equalsIgnoreCase("mp3")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "sampletest.mp3");
        }
        else if(type.equalsIgnoreCase("jpg")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "sampletest.jpg");
        }else if(type.equalsIgnoreCase("pdf")){
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "sampletest.pdf");
        }
        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

        Button DownloadStatus = (Button) findViewById(R.id.download_status);
        DownloadStatus.setEnabled(true);
        Button CancelDownload = (Button) findViewById(R.id.download_cancel);
        CancelDownload.setEnabled(true);

        return downloadReference;
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,0);
            DownloadManager.Query myDownloadQuery = new DownloadManager.Query();
            //set the query filter to our previously Enqueued download
            myDownloadQuery.setFilterById(referenceId);
            Cursor cursor = downloadManager.query(myDownloadQuery);
            context.unregisterReceiver(downloadReceiver);
            if(cursor.moveToFirst()){
                DownloadStatus(cursor,referenceId);
            }



        }
    };

    private void DownloadStatus(Cursor cursor, long DownloadId){

        //column for download  status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        //get the download filename
        int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
        String filename = cursor.getString(filenameIndex);

        String statusText = "";
        String reasonText = "";

        switch(status){
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                switch(reason){
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                }
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                switch(reason){
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                }
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "STATUS_SUCCESSFUL";
                reasonText = "Filename:\n" + filename;
                onDownloadSuccess();
                break;
        }



            Toast toast = Toast.makeText(DownloadActivity.this,
                    "Image Download Status:"+ "\n" + statusText + "\n" +
                            reasonText,
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();

            // Make a delay of 3 seconds so that next toast (Music Status) will not merge with this one.
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 3000);
        }

    private void onDownloadSuccess() {
        status.setText("OPen");

    }

    private void openfile(String filename) {
        Uri uri =  Uri.parse(filename);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        String mime = "*/*";
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (mimeTypeMap.hasExtension(
                mimeTypeMap.getFileExtensionFromUrl(uri.toString())))
            mime = mimeTypeMap.getMimeTypeFromExtension(
                    mimeTypeMap.getFileExtensionFromUrl(uri.toString()));
        intent.setDataAndType(uri,mime);
        startActivity(intent);
    }

}
