package gts.trainningcourse.mockproject.view.fragment.profile

/**
 * @author: Nhóm 2
 * To Do: Update Profile
 */
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

class EditProfileFragment : Fragment() {
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editName:EditText = view.findViewById(R.id.edit_name)
        val editGmail:EditText = view.findViewById(R.id.edit_gmail)
        val editTell:EditText = view.findViewById(R.id.edit_tell)
        val editPassword:EditText = view.findViewById(R.id.edit_password)
        val btnSaveEdit: Button = view.findViewById(R.id.btn_save_edit)

        /*Update data in firebase*/
        btnSaveEdit.setOnClickListener {
            val username = editName.text.toString()
            val password = editPassword.text.toString()
            val email = editGmail.text.toString()
            val phone = editTell.text.toString()
            val user = User(username, password, email, phone)
            database = FirebaseDatabase.getInstance().getReference("user")
            database.child(username).setValue(user).addOnSuccessListener {
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
            }
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }

    }
}