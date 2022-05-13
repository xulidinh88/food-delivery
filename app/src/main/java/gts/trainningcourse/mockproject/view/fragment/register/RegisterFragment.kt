package gts.trainningcourse.mockproject.view.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mockproject.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import gts.trainningcourse.mockproject.model.user.User
import gts.trainningcourse.mockproject.presenter.registerpresenter.RegisterPresenter

class RegisterFragment : Fragment(), IRegisterView {

    private lateinit var database: DatabaseReference
    private lateinit var state:String
    private lateinit var mRegisterPresenter: RegisterPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val edtRegisterUsername: EditText = view.findViewById(R.id.edtRegisterUsername)
        val edtRegisterPassword: EditText = view.findViewById(R.id.edtRegisterPassword)
        val edtRegisterEmail: EditText = view.findViewById(R.id.edtRegisterEmail)
        val edtRegisterPhone: EditText = view.findViewById(R.id.edtRegisterPhone)
        val btnRegisterOk: Button = view.findViewById(R.id.btnRegisterOk)

        mRegisterPresenter = RegisterPresenter(this)

        btnRegisterOk.setOnClickListener {
            val username = edtRegisterUsername.text.toString()
            val password = edtRegisterPassword.text.toString()
            val email = edtRegisterEmail.text.toString()
            val phone = edtRegisterPhone.text.toString()

            val user = User(username, password, email, phone)
            database = FirebaseDatabase.getInstance().getReference("user")
            mRegisterPresenter.stateRegister(user)

            when (state) {
                "Success Register" -> {
                    //Create user in database
                    database.child(username).setValue(user).addOnSuccessListener {
                        Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
                    }
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                else -> {
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun state(message: String) {
        state = message
    }
}