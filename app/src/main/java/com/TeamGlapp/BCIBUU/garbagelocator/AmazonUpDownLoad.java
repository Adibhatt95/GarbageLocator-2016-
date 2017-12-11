package com.TeamGlapp.BCIBUU.garbagelocator;

import android.app.AlertDialog;
import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;


/**
 * Created by User on 27-03-2016.
 */
public class AmazonUpDownLoad {
    AmazonS3Client p_amazonS3Client = null;
    TransferUtility p_transferUtil = null;

    public AmazonUpDownLoad(Context l_cntxt) {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                l_cntxt,
                "us-east-1:d47a0d70-50e8-4ddd-bc11-cc890ff327f5", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setProtocol(Protocol.HTTP);
        configuration.setSocketTimeout(5 * 10000);
        configuration.setConnectionTimeout(5 * 10000);
        configuration.setMaxErrorRetry(3);
        p_amazonS3Client =  new AmazonS3Client(credentialsProvider, configuration);
        p_transferUtil = new TransferUtility(p_amazonS3Client, l_cntxt);

    }

    public void BeginUpload(final Context l_cntxt, File file) {

        TransferObserver observer = p_transferUtil.upload("djacm", file.getName(),
                file);
        /*
         * Note that usually we set the transfer listener after initializing the
         * transfer. However it isn't required in this sample app. The flow is
         * click upload button -> start an activity for image selection
         * startActivityForResult -> onActivityResult -> beginUpload -> onResume
         * -> set listeners to in progress transfers.
         */
        //observer.setTransferListener(new UploadListener());

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {

            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                 // Do something in the callback.
            }

            @Override
            public void onError(int id, Exception e) {

                new AlertDialog.Builder(l_cntxt)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Error")
                        .setMessage(e.getMessage())
                        .show();
                return;
            }
        });
    }

    private void UploadListener() {

    }



}
