package edu.temple.activityextras;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends Activity {

    Button toastButton, dialogButton, menuButton;
    EditText toastMessage;
    ArrayList<String> allToastMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toastButton = (Button) findViewById(R.id.toastButton);
        dialogButton = (Button) findViewById(R.id.dialogButton);
        menuButton = (Button) findViewById(R.id.menuButton);

        toastMessage = (EditText) findViewById(R.id.toastMessage);

        //  Check if state was previously saved
        if (savedInstanceState != null)
            allToastMessages = savedInstanceState.getStringArrayList("toast_items");
        else
            allToastMessages = new ArrayList<>();

        /*
            Update the list and invoke a Toast with all items
        */
        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!toastMessage.getText().toString().isEmpty()) {
                    allToastMessages.add(toastMessage.getText().toString());
                    Toast.makeText(MainActivity.this, allToastMessages.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                //  Set alert dialog title
                alertDialogBuilder.setTitle("BUTTON CLICKED");

                //  Set alert dialog message
                alertDialogBuilder
                        .setMessage("Did you mean to click that button?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Toast.makeText(MainActivity.this, "Alright - If you say so", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(MainActivity.this, "I didn't think so", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                //  Create dialog from Builder class
                AlertDialog alertDialog = alertDialogBuilder.create();

                //  Display the dialog
                alertDialog.show();
            }
        });

        /*
            Invoke a Popup menu using the same menu resource used for the action bar
        */
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(MainActivity.this, findViewById(R.id.menuButton));

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.action_camera) {
                            Toast.makeText(MainActivity.this, "You clicked on the Camera in the Popup Menu", Toast.LENGTH_LONG).show();
                            return true;
                        } else if (item.getItemId() == R.id.action_rotate){
                            RotateAnimation animButton=new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                            animButton.setDuration(700);
                            animButton.setFillAfter(true);

                            menuButton.startAnimation(animButton);
                        }
                        return false;
                    }
                });

                //  Inflate our menu resource
                menu.inflate(R.menu.menu_main);

                //  Remove unwanted menu items
                menu.getMenu().removeItem(R.id.action_help);
                menu.getMenu().removeItem(R.id.action_settings);

                //  Display menu
                menu.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("toast_items", allToastMessages);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allToastMessages = savedInstanceState.getStringArrayList("toast_items");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_camera) {
            Toast.makeText(MainActivity.this, "You clicked on the Camera in the Action Bar", Toast.LENGTH_LONG).show();
        } else if (id == R.id.action_rotate){
            RotateAnimation animButton=new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            animButton.setDuration(700);
            animButton.setFillAfter(true);

            toastButton.startAnimation(animButton);
        }

        return super.onOptionsItemSelected(item);
    }
}
