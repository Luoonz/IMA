package luoonz.self.total_project_1

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room

class LoginActivity : AppCompatActivity() {

    private val loginIdEdt : EditText by lazy {
        findViewById(R.id.loginIdEdt)
    }

    private val loginPwdEdt :EditText by lazy{
        findViewById(R.id.loginPwdEdt)
    }

    private val loginAutoChk : View by lazy{
        findViewById(R.id.loginAutoChk)
    }

    private val loginOkbtn : Button by lazy{
        findViewById(R.id.loginOkBtn)
    }

    private val loginJoinBtn : Button by lazy{
        findViewById(R.id.loginJoinBtn)
    }

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "imaDb").build()
        initViews()
    }

    private fun initViews(){
        loginOkbtn.setOnClickListener {

        }

        loginJoinBtn.setOnClickListener {
            showJoinPopup()
        }

        loginAutoChk.isClickable()
        loginAutoChk.setOnClickListener {

        }
    }

    private fun showJoinPopup(){
        var builder = AlertDialog.Builder(this)
        var customDialogViews = layoutInflater.inflate(R.layout.dialog_join, null)
        builder.setView(customDialogViews)

        var listener = DialogInterface.OnClickListener{ p0, _ ->
            var alert = p0 as AlertDialog
            var idEdt : EditText? = alert.findViewById<EditText>(R.id.joinId)
            var pwEdt : EditText? = alert.findViewById<EditText>(R.id.joinPw)
            var nameEdt : EditText? = alert.findViewById<EditText>(R.id.joinName)


            TODO( "EditText 값들 DB에 넣어주기")
        }

        var dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(0x00000000))

        val okBtn : Button? = builder.
        val cancelBtn :Button? = dialog.window?.findViewById<Button>(R.id.joinCancelBtn)

        okBtn!!.setOnClickListener {
            Toast.makeText(this, "HI", Toast.LENGTH_SHORT).show()
        }

        cancelBtn!!.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

}