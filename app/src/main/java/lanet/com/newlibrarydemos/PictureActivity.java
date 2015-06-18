package lanet.com.newlibrarydemos;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lanet.com.newlibrarydemos.views.CameraPreview;


public class PictureActivity extends ActionBarActivity
{

    public static final String TAG = PictureActivity.class.getSimpleName();
    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private Button capture, switchCamera;
    private Context myContext;
    private LinearLayout cameraPreview;
    private boolean cameraFront = false;
    int cameraId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        myContext = this;
        initialize();
    }

    public void initialize()
    {
        switchCamera = (Button) findViewById(R.id.button_ChangeCamera);
        cameraPreview = (LinearLayout) findViewById(R.id.camera_preview);
        mPreview = new CameraPreview(myContext, mCamera);
        cameraPreview.addView(mPreview);

        capture = (Button) findViewById(R.id.button_capture);
        capture.setOnClickListener(captrureListener);

        switchCamera.setOnClickListener(switchCameraListener);
    }

    View.OnClickListener switchCameraListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //get the number of cameras
            int camerasNumber = Camera.getNumberOfCameras();
            if (camerasNumber > 1)
            {
                //release the old camera instance
                //switch camera, from the front and the back and vice versa
                releaseCamera();
                chooseCamera();
            }
            else
            {
                Toast toast = Toast.makeText(myContext, "Sorry, your phone has only one camera!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    };

    private int findFrontFacingCamera()
    {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        Log.d(TAG, "Number of cameras :" + numberOfCameras);
        for (int i = 0; i < numberOfCameras; i++)
        {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            Log.d(TAG, "Info : " + info.orientation + ":facing :" + info.facing);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
            {
                cameraId = i;
                Log.d(TAG, "Camera id :" + cameraId);
                cameraFront = true;
                break;
            }
        }
        return cameraId;
    }

    private int findBackFacingCamera()
    {

        //Search for the back facing camera
        //get the number of cameras
        int numberOfCameras = Camera.getNumberOfCameras();
        //for every camera check
        for (int i = 0; i < numberOfCameras; i++)
        {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
            {
                cameraId = i;
                cameraFront = false;
                break;
            }
        }
        return cameraId;
    }

    public void onResume()
    {
        super.onResume();
        if (!hasCamera(myContext))
        {
            Toast toast = Toast.makeText(myContext, "Sorry, your phone does not have a camera!", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }

        if (mCamera == null)
        {
            //if the front facing camera does not exist
            if (findFrontFacingCamera() == 1)
            {
                //release the old camera instance
                //switch camera, from the front and the back and vice versa
                releaseCamera();
                chooseCamera();
            }
            else
            {
                Toast toast = Toast.makeText(myContext, "Sorry, your phone has only one camera!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public void chooseCamera()
    {
        //if the camera preview is the front
        try
        {
            if (cameraFront)
            {
                int cameraId = findBackFacingCamera();
                if (cameraId >= 0)
                {
                    //open the backFacingCamera
                    //set a picture callback
                    //refresh the preview

                    mCamera = Camera.open(cameraId);
                    mCamera.setDisplayOrientation(90);
                    mPicture = getPictureCallback();
                    mPreview.refreshCamera(mCamera);
                }
            }
            else
            {
                //set a picture callback

                mCamera = Camera.open(cameraId);
                mPicture = getPictureCallback();
            }
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0)
            {
                //open the backFacingCamera
                //refresh the preview
                mPreview.refreshCamera(mCamera);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height)
    {
        Camera.Size result = null;
        Camera.Parameters p = mCamera.getParameters();
        for (Camera.Size size : p.getSupportedPreviewSizes())
        {
            if (size.width <= width && size.height <= height)
            {
                if (result == null)
                {
                    result = size;
                }
                else
                {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea)
                    {
                        result = size;
                    }
                }
            }
        }
        return result;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //when on Pause, release camera in order to be used from other applications
        releaseCamera();
    }

    private boolean hasCamera(Context context)
    {
        //check if the device has camera
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private Camera.PictureCallback getPictureCallback()
    {
        Camera.PictureCallback picture = new Camera.PictureCallback()
        {

            @Override
            public void onPictureTaken(byte[] data, Camera camera)
            {
                //make a new picture file
                File pictureFile = getOutputMediaFile();

                if (pictureFile == null)
                {
                    return;
                    //write the file

                }
                try
                {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    Toast toast = Toast.makeText(myContext, "Picture saved: " + pictureFile.getName(), Toast.LENGTH_LONG);
                    toast.show();
                }
                catch (FileNotFoundException e)
                {
                }
                catch (IOException e)
                {
                }

                //refresh camera to continue preview
                mPreview.refreshCamera(mCamera);
            }
        };
        return picture;
    }

    View.OnClickListener captrureListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            mCamera.takePicture(null, null, mPicture);
        }
    };

    //make picture and save to a folder
    private static File getOutputMediaFile()
    {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File("/sdcard/", "JCG Camera");

        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists())
        {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs())
            {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        //and make a media file:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    private void releaseCamera()
    {
        // stop and release camera
        if (mCamera != null)
        {
            mCamera.release();
            mCamera = null;
        }
    }

}
