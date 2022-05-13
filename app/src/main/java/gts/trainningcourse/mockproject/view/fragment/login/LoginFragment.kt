package gts.trainningcourse.mockproject.view.fragment.login

/**
 * @author: Nhóm 2
 * To Do: Check Login
 */
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mockproject.R
import com.google.firebase.database.*
import gts.trainningcourse.mockproject.MainActivity
import gts.trainningcourse.mockproject.presenter.loginpresenter.LoginPresenter
import gts.trainningcourse.mockproject.utils.Contacts

class LoginFragment : Fragment(), ILoginView {

    private lateinit var mLoginPresenter: LoginPresenter
    private lateinit var state :String
    private var checkData:Boolean = false
    private lateinit var database: DatabaseReference
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.hide()

        val edtLoginUsername: EditText = view.findViewById(R.id.edtLoginUser)
        val edtLoginPassword: EditText = view.findViewById(R.id.edtLoginPassword)
        val btnLogin: Button = view.findViewById(R.id.btnLogin)
        val btnRegister: Button = view.findViewById(R.id.btnRegister)
        val ghichu: TextView = view.findViewById(R.id.ghichu)

        mLoginPresenter = LoginPresenter(this)
        database = FirebaseDatabase.getInstance().getReference("user")


        btnLogin.setOnClickListener {
            val username = edtLoginUsername.text.toString()
            val password = edtLoginPassword.text.toString()

            /*Check username and password is not empty*/
            if(edtLoginUsername.text.toString().isNotEmpty() && edtLoginPassword.text.toString().isNotEmpty()) {

                /*Set data in firebase*/
                database.child(edtLoginUsername.text.toString()).addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.value.toString()
                        if(user.contains(password) && user.contains(username)) {
                            checkData = true
                            database.child(username).child("login").setValue(true)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }

            ghichu.text = Contacts.CLICK_AGAIN
            mLoginPresenter.stateLogin(edtLoginUsername.text.toString(), edtLoginPassword.text.toString(), checkData)
            when (state) {
                "Success Login"-> {
                    // Navigate to Home Fragment
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                else -> Toast.makeText(context, state, Toast.LENGTH_LONG).show()
            }
        }


        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    override fun state(message: String) {
        state = message
    }
}

