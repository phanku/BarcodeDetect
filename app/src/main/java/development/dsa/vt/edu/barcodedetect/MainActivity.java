package development.dsa.vt.edu.barcodedetect;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {

    private SurfaceView cameraPreview;
    private RelativeLayout overlay;

    final int IMAGE_SIZE = 1024;
    private Camera mCamera;
    private CameraPreview mPreview;


    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        BarcodeDetector detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE | Barcode.UPC_A)
                        .build();

//        if(!detector.isOperational()){
//            txtView.setText("Could not set up the detector!");
//            return;
//        }
//
        TextView txtView = findViewById(R.id.txtView);

        Bitmap myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                mPreview);

        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Barcode> barcodes = detector.detect(frame);
//
        Barcode thisCode = barcodes.valueAt(0);
        txtView.setText(thisCode.rawValue);
//
//        overlay = findViewById(R.id.overlay);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//        // Get the preview size
//        int previewWidth = cameraPreview.getMeasuredWidth(),
//                previewHeight = cameraPreview.getMeasuredHeight();

//        // Set the height of the overlay so that it makes the preview a square
//        RelativeLayout.LayoutParams overlayParams = (RelativeLayout.LayoutParams) overlay.getLayoutParams();
//        overlayParams.height = previewHeight - previewWidth;
//        overlay.setLayoutParams(overlayParams);

//        Camera camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
//        Camera.Parameters camParams = camera.getParameters();
//
//        // Find a preview size that is at least the size of our IMAGE_SIZE
//        Camera.Size previewSize = camParams.getSupportedPreviewSizes().get(0);
//        for (Camera.Size size : camParams.getSupportedPreviewSizes()) {
//            if (size.width >= IMAGE_SIZE && size.height >= IMAGE_SIZE) {
//                previewSize = size;
//                break;
//            }
//        }
//        camParams.setPreviewSize(previewSize.width, previewSize.height);
//
//        // Try to find the closest picture size to match the preview size.
//        Camera.Size pictureSize = camParams.getSupportedPictureSizes().get(0);
//        for (Camera.Size size : camParams.getSupportedPictureSizes()) {
//            if (size.width == previewSize.width && size.height == previewSize.height) {
//                pictureSize = size;
//                break;
//            }
//        }
//        camParams.setPictureSize(pictureSize.width, pictureSize.height);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
