package gts.trainningcourse.mockproject.view.fragment.profile

/**
 * @author: Nhóm 2
 * To Do: Show profile, having button to show settingFragment, editProfileFragment
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.mockproject.R
import com.example.mockproject.R.layout
import com.google.firebase.database.*
import gts.trainningcourse.mockproject.model.user.User
import gts.trainningcourse.mockproject.utils.Contacts
import gts.trainningcourse.mockproject.view.fragment.dialog.MyDialogFragment

private const val TITLE_SUPPORT = "Support"
private const val MSG_SUPPORT = "Please send inquiries to gmail team2android@gmail.com\n Thanks!!!"


class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var btnEditProfile: Button
    private lateinit var ivSetting: ImageView
    private lateinit var ivSupport: ImageView
    private lateinit var ivCard: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvGmail: TextView
    private lateinit var tv_tell: TextView
    private lateinit var database: DatabaseReference
    private lateinit var getUser:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnEditProfile = view.findViewById(R.id.btn_edit_profile)
        ivSetting = view.findViewById(R.id.iv_setting)
        ivSupport = view.findViewById(R.id.iv_support)
        ivCard = view.findViewById(R.id.iv_card)
        tvName = view.findViewById(R.id.tvName)
        tvGmail = view.findViewById(R.id.tvGmail)
        tv_tell = view.findViewById(R.id.tv_tell)
        database = FirebaseDatabase.getInstance().getReference("user")
        getDataProfile()
        btnEditProfile.setOnClickListener(this)
        ivSetting.setOnClickListener(this)
        ivSupport.setOnClickListener(this)
        ivCard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_profile -> handleClickEditProfile()
            R.id.iv_setting -> handleClickSetting()
            R.id.iv_support -> handleClickSupport()
            R.id.iv_card -> handleClickCard()
        }
    }

    private fun handleClickCard() {
        val supportFragmentManager: FragmentManager? = activity?.supportFragmentManager
        val dialogFragment = MyDialogFragment().newInstance(Contacts.TITLE_DEF, Contacts.MSG_DEF)
        supportFragmentManager?.let { dialogFragment.show(it, Contacts.DIALOG_FRAGMENT_TAG) }
    }

    private fun handleClickSupport() {
        val supportFragmentManager: FragmentManager? = activity?.supportFragmentManager
        val dialogFragment = MyDialogFragment().newInstance(TITLE_SUPPORT, MSG_SUPPORT)
        supportFragmentManager?.let { dialogFragment.show(it, Contacts.DIALOG_FRAGMENT_TAG) }
    }

    private fun handleClickSetting() {
        findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
    }

    private fun handleClickEditProfile() {
        findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
    }

    /*Get data from firebase*/
    private fun getDataProfile() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val resultData = arrayListOf<User>()
                if(snapshot.exists()) {
                    val user = snapshot.value.toString()
                    val bigArray = user.split("={","},","}}").toTypedArray()
                    for (i in bigArray.indices) {
                        if(i % 2 != 0) {
                            val one = bigArray[i].split(",","=").toTypedArray()
                            resultData.add(User(one[13], one[3], one[9], one[5]))
                            if(one[7] == "true") {
                                tvName.text = one[13]
                                tvGmail.text = one[9]
                                tv_tell.text = one[5]
                            }
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}