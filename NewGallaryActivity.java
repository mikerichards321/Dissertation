package com.example.michael.thegardenapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class NewGallaryActivity extends Activity  {
    //variable for selection intent
    private final int PICKER = 1;
    //variable to store the currently selected image
    private int currentPic = 0;
    //gallery object
    private Gallery picGallery;
    //image view for larger display
    private ImageView picView;
    //adapter for gallery view
    private PicAdapter imgAdapt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gallary);

        //get the large image view
        picView = (ImageView) findViewById(R.id.picture);

//get the gallery view
        picGallery = (Gallery) findViewById(R.id.gallery);
        //create a new adapter
        imgAdapt = new PicAdapter(this);

//set the gallery adapter
        picGallery.setAdapter(imgAdapt);

        //set long click listener for each gallery thumbnail item

        //set the click listener for each item in the thumbnail gallery
        picGallery.setOnItemClickListener(new OnItemClickListener() {
            //handle clicks
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //set the larger image view to display the chosen bitmap calling method of adapter class
                picView.setImageBitmap(imgAdapt.getPic(position));
            }
        });

    }
    public void backToMenu(View view) {
        Intent intent = new Intent( NewGallaryActivity.this, MainViewPageView.class);
        finish();
        startActivity(intent);
    }

    public class PicAdapter extends BaseAdapter {
        //use the default gallery background image
        int defaultItemBackground;

        //gallery context
        private Context galleryContext;

        //array to store bitmaps to display
        private Bitmap[] imageBitmaps;

        //placeholder bitmap for empty spaces in gallery
        Bitmap placeholder;
        Bitmap placeholder1;
        Bitmap placeholder2;
        Bitmap placeholder3;
        Bitmap placeholder4;
        Bitmap placeholder5;
        Bitmap placeholder6;
        Bitmap placeholder7;
        Bitmap placeholder8;
        Bitmap placeholder9;
        Bitmap placeholder10;
        Bitmap placeholder11;
        Bitmap placeholder12;
        Bitmap placeholder13;
        Bitmap placeholder14;
        Bitmap placeholder15;
        Bitmap placeholder16;
        Bitmap placeholder17;
        Bitmap placeholder18;
        Bitmap placeholder19;
        Bitmap placeholder20;
        Bitmap placeholder21;
        Bitmap placeholder22;
        Bitmap placeholder23;
        Bitmap placeholder24;
        Bitmap placeholder25;
        Bitmap placeholder26;
        Bitmap placeholder27;
        Bitmap placeholder28;
        Bitmap placeholder29;





        public PicAdapter(Context c) {

            //instantiate context
            galleryContext = c;

            //create bitmap array
            imageBitmaps  = new Bitmap[28];

            //decode the placeholder image
            placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.clynelogo);
            placeholder1 = BitmapFactory.decodeResource(getResources(), R.drawable.castle);
            placeholder2 = BitmapFactory.decodeResource(getResources(), R.drawable.clynewaterfall);
            placeholder3= BitmapFactory.decodeResource(getResources(), R.drawable.flowers);
            placeholder4= BitmapFactory.decodeResource(getResources(), R.drawable.steps);
            placeholder5= BitmapFactory.decodeResource(getResources(), R.drawable.tower);
            placeholder6= BitmapFactory.decodeResource(getResources(), R.drawable.waterfall);
            placeholder7= BitmapFactory.decodeResource(getResources(), R.drawable.waterfall1);
            placeholder8= BitmapFactory.decodeResource(getResources(), R.drawable.garden);
            placeholder9= BitmapFactory.decodeResource(getResources(), R.drawable.animas);
            placeholder10= BitmapFactory.decodeResource(getResources(), R.drawable.bridge);
            placeholder11= BitmapFactory.decodeResource(getResources(), R.drawable.tree5);
            placeholder12= BitmapFactory.decodeResource(getResources(), R.drawable.doggraves);
            placeholder13= BitmapFactory.decodeResource(getResources(), R.drawable.educationroom);
            placeholder14= BitmapFactory.decodeResource(getResources(), R.drawable.japansesebridge);
            placeholder15= BitmapFactory.decodeResource(getResources(), R.drawable.pond);
            placeholder16= BitmapFactory.decodeResource(getResources(), R.drawable.stonebridge);
            placeholder17= BitmapFactory.decodeResource(getResources(), R.drawable.stonebridge2);
            placeholder18= BitmapFactory.decodeResource(getResources(), R.drawable.tree);
            placeholder19= BitmapFactory.decodeResource(getResources(), R.drawable.tree1);
            placeholder20= BitmapFactory.decodeResource(getResources(), R.drawable.tree2);
            placeholder21= BitmapFactory.decodeResource(getResources(), R.drawable.tree3);
            placeholder22= BitmapFactory.decodeResource(getResources(), R.drawable.tree4);
            placeholder23= BitmapFactory.decodeResource(getResources(), R.drawable.tree6);
            placeholder24= BitmapFactory.decodeResource(getResources(), R.drawable.tree7);
            placeholder25= BitmapFactory.decodeResource(getResources(), R.drawable.tree8);
            placeholder26= BitmapFactory.decodeResource(getResources(), R.drawable.treeman);
            placeholder27= BitmapFactory.decodeResource(getResources(), R.drawable.waterfalllake);


            Bitmap[] arrayOfBitmap = {placeholder, placeholder1,placeholder2,placeholder3};
            //more processing

            //set placeholder as all thumbnail images in the gallery initially
            for(int i=0; i<imageBitmaps.length; i++)
                imageBitmaps[0]=placeholder;
            imageBitmaps[1]=placeholder1;
            imageBitmaps[2]=placeholder2;
            imageBitmaps[3]=placeholder3;
            imageBitmaps[4]=placeholder4;
            imageBitmaps[5]=placeholder5;
            imageBitmaps[6]=placeholder6;
            imageBitmaps[7]=placeholder7;
            imageBitmaps[8]=placeholder8;
            imageBitmaps[9]=placeholder9;
            imageBitmaps[10]=placeholder10;
            imageBitmaps[11]=placeholder11;
            imageBitmaps[12]=placeholder12;
            imageBitmaps[13]=placeholder13;
            imageBitmaps[14]=placeholder14;
            imageBitmaps[15]=placeholder15;
            imageBitmaps[16]=placeholder16;
            imageBitmaps[17]=placeholder17;
            imageBitmaps[18]=placeholder18;
            imageBitmaps[19]=placeholder19;
            imageBitmaps[20]=placeholder20;
            imageBitmaps[21]=placeholder21;
            imageBitmaps[22]=placeholder22;
            imageBitmaps[23]=placeholder23;
            imageBitmaps[24]=placeholder24;
            imageBitmaps[25]=placeholder25;
            imageBitmaps[26]=placeholder26;
            imageBitmaps[27]=placeholder27;



            //get the styling attributes - use default Andorid system resources
            TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);
//get the background resource
            defaultItemBackground = styleAttrs.getResourceId(
                    R.styleable.PicGallery_android_galleryItemBackground, 0);
//recycle attributes
            styleAttrs.recycle();
        }


        @Override
        public int getCount() {
            return imageBitmaps.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //create the view
            ImageView imageView = new ImageView(galleryContext);
            //specify the bitmap at this position in the array
            imageView.setImageBitmap(imageBitmaps[position]);
            //set layout options
            imageView.setLayoutParams(new Gallery.LayoutParams(600, 400));
            //scale type within view area
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //set default gallery item background
            imageView.setBackgroundResource(defaultItemBackground);
            //return the view
            return imageView;
        }
        //helper method to add a bitmap to the gallery when the user chooses one
        public void addPic(Bitmap newPic)
        {
            //set at currently selected index
            imageBitmaps[currentPic] = newPic;
        }
        //return bitmap at specified position for larger display
        public Bitmap getPic(int posn)
        {
            //return bitmap at posn index
            return imageBitmaps[posn];
        }
        }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //check if we are returning from picture selection
            if (requestCode == PICKER) {
                //import the image
                //the returned picture URI
                Uri pickedUri = data.getData();
                //declare the bitmap
                Bitmap pic = null;

//declare the path string
                String imgPath = "";
                //retrieve the string using media data
                String[] medData = { MediaStore.Images.Media.DATA };
//query the data
                Cursor picCursor = getContentResolver().query(pickedUri,
                        medData, null, null, null);
                if(picCursor!=null)
                {
                    //get the path string
                    int index = picCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    picCursor.moveToFirst();
                    imgPath = picCursor.getString(index);
                }
                else
                    imgPath = pickedUri.getPath();
//if we have a new URI attempt to decode the image bitmap
                if(pickedUri!=null) {
//set the width and height we want to use as maximum display
                    int targetWidth = 600;
                    int targetHeight = 400;
                    //create bitmap options to calculate and use sample size
                    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                    //first decode image dimensions only - not the image bitmap itself
                    bmpOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imgPath, bmpOptions);
                    //pass bitmap to ImageAdapter to add to array
                    imgAdapt.addPic(pic);
//redraw the gallery thumbnails to reflect the new addition
                    picGallery.setAdapter(imgAdapt);
                    //display the newly selected image at larger size
                    picView.setImageBitmap(pic);
//scale options
                    picView.setScaleType(ImageView.ScaleType.FIT_CENTER);

//image width and height before sampling
                    int currHeight = bmpOptions.outHeight;
                    int currWidth = bmpOptions.outWidth;
                    //variable to store new sample size
                    int sampleSize = 1;
                    //calculate the sample size if the existing size is larger than target size
                    if (currHeight>targetHeight || currWidth>targetWidth)
                    {
                        //use either width or height
                        if (currWidth>currHeight)
                            sampleSize = Math.round((float)currHeight/(float)targetHeight);
                        else
                            sampleSize = Math.round((float)currWidth/(float)targetWidth);
                    }
                    //use the new sample size
                    bmpOptions.inSampleSize = sampleSize;
                    //now decode the bitmap using sample options
                    bmpOptions.inJustDecodeBounds = false;
                    //get the file as a bitmap
                    pic = BitmapFactory.decodeFile(imgPath, bmpOptions);
                }
            }

        }
//superclass method
        super.onActivityResult(requestCode, resultCode, data);
    }
    }

