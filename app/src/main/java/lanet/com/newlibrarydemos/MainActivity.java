package lanet.com.newlibrarydemos;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import lanet.com.newlibrarydemos.fragments.MainFragment;
import lanet.com.newlibrarydemos.fragments.SecondFragment;


public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();
    NavigationView navigationView;
    public static final int GLIDE = 10;
    Toolbar toolbar;
    ActionBar actionBar;
    public static final int SCALEX = 0;
    public static final int SCALEY = 1;
    public static final int SCALEXY = 2;
    public static final int FADE = 3;
    public static final int FLIP_HORIZONTAL = 4;
    public static final int FLIP_VERTICAL = 5;
    public static final int SLIDE_VERTICAL = 6;
    public static final int SLIDE_HORIZONTAL = 7;
    public static final int SLIDING = 11;
    public static final int STACK = 12;
    public static final int SLIDE_HORIZONTAL_PUSH_TOP = 8;
    public static final int ROTATE_UP = 15;
    public static final int SLIDE_VERTICAL_PUSH_LEFT = 9;
    public static final int CUBE = 13;
    public static final int ROTATE_DOWN = 14;
    public static final int ACCORDION = 16;
    public static final int TABLE_HORIZONTAL = 17;
    public static final int TABLE_VERTICAL = 18;
    public static final int ZOOM_FROM_LEFT_CORNER = 19;
    public static final int ZOOM_FROM_RIGHT_CORNER = 20;
    public static final int ZOOM_SLIDE_HORIZONTAL = 21;
    public static final int ZOOM_SLIDE_VERTICAL = 22;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setContentInsetsAbsolute(0, 0);
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        mFragmentTransaction = getFragmentManager().beginTransaction();
        SpinnerAdapter adapter = ArrayAdapter.createFromResource(this, R.array.animation_type, android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(adapter, mNavigationCallback);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Snackbar snackbar = Snackbar.make(navigationView, "Hello THis is Test", Snackbar.LENGTH_SHORT);
        snackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                Log.d(TAG, "We select menu item :" + menuItem.getTitle());
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_item_1:
                        if (menuItem.isChecked())
                        {
                            menuItem.setChecked(false);
                        }
                        else
                        {
                            menuItem.setChecked(true);
                        }
                        Intent intent1 = new Intent(MainActivity.this, Xfermodes.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_item_2:
                        if (menuItem.isChecked())
                        {
                            menuItem.setChecked(false);
                        }
                        else
                        {
                            menuItem.setChecked(true);
                        }
                        break;
                    case R.id.navigation_sub_item_1:
                        Intent intent = new Intent(MainActivity.this, PictureActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_sub_item_2:
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.animator.slide_fragment_in, 0, 0,
                                R.animator.slide_fragment_out);
                        transaction.replace(R.id.container, SecondFragment.newInstance("test1", "Tes2"));
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    default:
                }
                return false;
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit();
    }

    boolean isshowFirst = false;
    ActionBar.OnNavigationListener mNavigationCallback = new ActionBar.OnNavigationListener()
    {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId)
        {
            mFragmentTransaction = getFragmentManager().beginTransaction();
            switch (itemPosition)
            {
                case SCALEX:
                    transitionScaleX();
                    break;
                case SCALEY:
                    transitionScaleY();
                    break;
                case SCALEXY:
                    transitionScaleXY();
                    break;
                case FADE:
                    transitionFade();
                    break;
                case FLIP_HORIZONTAL:
                    transitionFlipHorizontal();
                    break;
                case FLIP_VERTICAL:
                    transitionFlipVertical();
                    break;
                case SLIDE_VERTICAL:
                    transitionSlideVertical();
                    break;
                case SLIDE_HORIZONTAL:
                    transitionSlideHorizontal();
                    break;
                case SLIDE_HORIZONTAL_PUSH_TOP:
                    transitionSlideHorizontalPushTop();
                    break;
                case SLIDE_VERTICAL_PUSH_LEFT:
                    transitionSlideVerticalPushLeft();
                    break;
                case GLIDE:
                    transitionGlide();
                    break;
                case STACK:
                    transitionStack();
                    break;
                case CUBE:
                    transitionCube();
                    break;
                case ROTATE_DOWN:
                    transitionRotateDown();
                    break;
                case ROTATE_UP:
                    transitionRotateUp();
                    break;
                case ACCORDION:
                    transitionAccordion();
                    break;
                case TABLE_HORIZONTAL:
                    transitionTableHorizontal();
                    break;
                case TABLE_VERTICAL:
                    transitionTableVertical();
                    break;
                case ZOOM_FROM_LEFT_CORNER:
                    transitionZoomFromLeftCorner();
                    break;
                case ZOOM_FROM_RIGHT_CORNER:
                    transitionZoomFromRightCorner();
                    break;
                case ZOOM_SLIDE_HORIZONTAL:
                    transitionZoomSlideHorizontal();
                    break;
                case ZOOM_SLIDE_VERTICAL:
                    transitionZoomSlideVertical();
                    break;
            }
            if (isshowFirst)
            {
                mFragmentTransaction.replace(R.id.container, SecondFragment.newInstance("test1", "Tes2"));
            }
            else
            {
                mFragmentTransaction.replace(R.id.container, MainFragment.newInstance());
            }
            isshowFirst = !isshowFirst;
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
            return false;
        }
    };

    private void transitionFade()
    {
        mFragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
    }

    private void transitionScaleX()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.scalex_enter, R.animator.scalex_exit, R.animator.scalex_enter, R.animator.scalex_exit);
    }


    private void transitionScaleY()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.scaley_enter, R.animator.scaley_exit, R.animator.scaley_enter, R.animator.scaley_exit);
    }

    private void transitionScaleXY()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.scalexy_enter, R.animator.scalexy_exit, R.animator.scalexy_enter, R.animator.scalexy_exit);
    }


    private void transitionSlideVertical()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_vertical_left_out,
                R.animator.slide_fragment_vertical_left_in, R.animator.slide_fragment_vertical_right_out);
    }


    private void transitionSlideHorizontal()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out,
                R.animator.slide_fragment_horizontal_left_in, R.animator.slide_fragment_horizontal_right_out);
    }


    private void transitionSlideHorizontalPushTop()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_vertical_left_out,
                R.animator.slide_fragment_vertical_left_in, R.animator.slide_fragment_horizontal_right_out);
    }


    private void transitionSlideVerticalPushLeft()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_horizontal_left_out,
                R.animator.slide_fragment_horizontal_left_in, R.animator.slide_fragment_vertical_right_out);
    }

    private void transitionGlide()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.glide_fragment_horizontal_in, R.animator.glide_fragment_horizontal_out, R.animator.glide_fragment_horizontal_in,
                R.animator.glide_fragment_horizontal_out);
    }

    private void transitionStack()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.stack_right_in, R.animator.stack_left_out, R.animator.stack_left_in, R.animator.stack_right_out);
    }

    private void transitionCube()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.cube_right_in, R.animator.cube_left_out, R.animator.cube_left_in, R.animator.cube_right_out);
    }

    private void transitionRotateDown()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.rotatedown_right_in, R.animator.rotatedown_left_out, R.animator.rotatedown_left_in, R.animator.rotatedown_right_out);
    }

    private void transitionRotateUp()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.rotateup_right_in, R.animator.rotateup_left_out, R.animator.rotateup_left_in, R.animator.rotateup_right_out);
    }

    private void transitionAccordion()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.accordion_right_in, R.animator.accordion_left_out, R.animator.accordion_left_in, R.animator.accordion_right_out);
    }

    private void transitionTableHorizontal()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.table_horizontal_right_in, R.animator.table_horizontal_left_out, R.animator.table_horizontal_left_int,
                R.animator.table_horizontal_right_out);
    }

    private void transitionTableVertical()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.table_vertical_right_in, R.animator.table_vertical_left_out, R.animator.table_vertical_left_int,
                R.animator.table_vertical_right_out);
    }

    private void transitionFlipHorizontal()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.card_flip_horizontal_right_in, R.animator.card_flip_horizontal_left_out, R.animator.card_flip_horizontal_left_in,
                R.animator.card_flip_horizontal_right_out);
    }

    private void transitionFlipVertical()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.card_flip_vertical_right_in, R.animator.card_flip_vertical_left_out, R.animator.card_flip_vertical_left_in,
                R.animator.card_flip_vertical_right_out);
    }

    private void transitionZoomFromLeftCorner()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.zoom_from_left_corner_right_in, R.animator.zoom_from_left_corner_left_out, R.animator.zoom_from_left_corner_left_in,
                R.animator.zoom_from_left_corner_right_out);
    }

    private void transitionZoomFromRightCorner()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.zoom_from_right_corner_right_in, R.animator.zoom_from_right_corner_left_out, R.animator.zoom_from_right_corner_left_in,
                R.animator.zoom_from_right_corner_right_out);
    }

    private void transitionZoomSlideHorizontal()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.zoom_slide_horizontal_tablet_right_in, R.animator.zoom_slide_horizontal_left_out,
                R.animator.zoom_slide_horizontal_tablet_left_in, R.animator.zoom_slide_horizontal_right_out);
    }

    private void transitionZoomSlideVertical()
    {
        mFragmentTransaction.setCustomAnimations(R.animator.zoom_slide_vertical_tablet_right_in, R.animator.zoom_slide_vertical_left_out,
                R.animator.zoom_slide_vertical_tablet_left_in, R.animator.zoom_slide_vertical_right_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        else if (id == R.id.navigation_sub_item_2)
        {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_fragment_in, 0, 0,
                    R.animator.slide_fragment_out);
            transaction.replace(R.id.container, SecondFragment.newInstance("test1", "Tes2"));
            transaction.addToBackStack(null);
            transaction.commit();
        }


        return super.onOptionsItemSelected(item);
    }
}
