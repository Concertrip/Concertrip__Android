package concertrip.sopt.com.concertrip.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import concertrip.sopt.com.concertrip.R
import kotlinx.android.synthetic.main.dialog_message.*
import kotlin.properties.Delegates

class  CustomDialog(context: Context, var content: String) : Dialog(context){

    constructor(context: Context) : this(context,context.getString(R.string.txt_calendar_added))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_message);



        dialog_tv_content.text=content
    }



}