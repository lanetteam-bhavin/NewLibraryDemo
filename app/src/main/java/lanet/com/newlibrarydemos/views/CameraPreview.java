package lanet.com.newlibrarydemos.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import lanet.com.newlibrarydemos.PictureActivity;

/**
 * Created by lcom15 on 5/6/15.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera)
    {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public CameraPreview(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        if (mCamera == null)
        {
            try
            {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void refreshCamera(Camera camera)
    {
        if (mHolder.getSurface() == null)
        {
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try
        {
            mCamera.stopPreview();
        }
        catch (Exception e)
        {
            // ignore: tried to stop a non-existent preview
        }
        // set preview size and make any resize, rotate or

        // reformatting changes here
        // start preview with new settings
        setCamera(camera);
        try
        {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }
        catch (Exception e)
        {
            Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void setCamera(Camera camera)
    {
        //method to set a camera instance
        mCamera = camera;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        Log.d(PictureActivity.TAG, "Surface changed :" + format + "::width::" + width + "::" + height);
        refreshCamera(mCamera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        try
        {
            mCamera.release();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
