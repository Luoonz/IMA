package luoonz.self.total_project_1

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import luoonz.self.total_project_1.model.User


class LoginActivity : AppCompatActivity() {

    private val loginIdEdt: EditText by lazy {
        findViewById(R.id.loginIdEdt)
    }

    private val loginPwdEdt: EditText by lazy {
        findViewById(R.id.loginPwdEdt)
    }

    private val loginAutoChk: CheckBox by lazy {
        findViewById(R.id.loginAutoChk)
    }

    private val loginOkbtn: Button by lazy {
        findViewById(R.id.loginOkBtn)
    }

    private val loginJoinBtn: Button by lazy {
        findViewById(R.id.loginJoinBtn)
    }

    private val prefsFileName = "loginPrefs"
    private lateinit var Lid: String
    private lateinit var Lpw: String
    private lateinit var db: AppDatabase
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "imaDb")
            .fallbackToDestructiveMigration() // db 삭제 및 재생성 허용코드 <- 기존 데이터 날아감 주의 / 기존 데이터 살릴거면
            //addMigrations(MIGRATION_0_1) <- https://ichi.pro/ko/jeonche-room-maigeuleisyeon-gaideu-238092699374086 참고
            .build()

        initViews()
    }

    private fun initViews() {
        sharedPreferences = this.getSharedPreferences(prefsFileName, 0)
        editor = sharedPreferences.edit()

        checkSharedPerference()

        loginOkbtn.setOnClickListener {
            loginCheck()
        }

        loginJoinBtn.setOnClickListener {
            showJoinPopup()
        }

        loginAutoChk.setOnCheckedChangeListener { _, isChecked ->

            Lid = loginIdEdt.text.toString()
            Lpw = loginPwdEdt.text.toString()

            if(isChecked) {
                editor.putString("id", Lid)
                editor.putString("pw", Lpw)
                editor.commit()
            }else{
                editor.putString("id", "")
                editor.putString("pw", "")
                editor.commit()
            }
        }

    }

    private fun checkSharedPerference() {
        loginAutoChk.isChecked = sharedPreferences.getBoolean(getString(R.string.autoLogin), false)
        val loginid : String? = sharedPreferences.getString("id","")
        val loginpw : String? = sharedPreferences.getString("pw","")

        if(loginid.isNullOrBlank() || loginpw.isNullOrBlank()){
            Toast.makeText(this, "정보를 입력해주세요", Toast.LENGTH_SHORT).show()
        }else{
            loginThread(loginid, loginpw)
        }
    }

    private fun loginCheck() {

        Lid = loginIdEdt.text.toString()
        Lpw = loginPwdEdt.text.toString()
        if (Lid.isBlank() || Lpw.isBlank()) {
            Toast.makeText(this, "정보를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        loginThread(Lid, Lpw)
    }

    private fun showJoinPopup() {
        var builder = AlertDialog.Builder(this)
        var customDialogViews = layoutInflater.inflate(R.layout.dialog_join, null)
        builder.setView(customDialogViews)


        var idEdt: EditText = customDialogViews.findViewById<EditText>(R.id.joinId)
        var pwEdt: EditText = customDialogViews.findViewById<EditText>(R.id.joinPw)
        var nameEdt: EditText = customDialogViews.findViewById<EditText>(R.id.joinName)

        val okBtn: Button = customDialogViews.findViewById(R.id.joinOkBtn)
        val cancelBtn: Button = customDialogViews.findViewById(R.id.joinCancelBtn)

        var dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(0x00000000))
        dialog.setCancelable(false) // 다이얼로그 외부 영역 클릭시 dismiss 실행 차단.

        okBtn.setOnClickListener {

            if (idEdt.text.isBlank() || pwEdt.text.isBlank() || nameEdt.text.isBlank()) {
                Toast.makeText(this, "정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Thread(Runnable {
                db.userDao().insertUser(
                    User(
                        null,
                        idEdt.text.toString(),
                        pwEdt.text.toString(),
                        nameEdt.text.toString()
                    )
                )
            }).start()

            Toast.makeText(this, "${nameEdt.text} 님으로 등록되었습니다.", Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun loginThread(id : String, pw:String){

        Thread(Runnable {
            db.userDao().loginUser(id, pw).forEach {
                runOnUiThread {
                    if (it.names.isNullOrBlank()) {
                        Toast.makeText(this, "정보를 확인해 주세요", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }

                }
            }
        }).start()
    }

}

