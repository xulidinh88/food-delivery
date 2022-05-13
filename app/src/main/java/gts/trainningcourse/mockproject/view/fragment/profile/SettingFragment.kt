package gts.trainningcourse.mockproject.view.fragment.profile

/**
 * @author: Nhóm 2
 * To Do: Setting using Preference Fragment,
 * darkmode,
 * require permission,
 * logout,
 * delete account
 */
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.mockproject.R
import com.google.firebase.database.*
import gts.trainningcourse.mockproject.utils.Contacts
import gts.trainningcourse.mockproject.view.fragment.dialog.MyDialogFragment

private const val TITLE_ABOUT = "About"
private const val MSG_ABOUT = "Team 2 - HN21_CPL_Android_02"
private const val FINE_LOCATION = 101

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener,
    Preference.OnPreferenceClickListener {

    private lateinit var database:DatabaseReference
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        loadSavedData(sharedPreferences)
        database = FirebaseDatabase.getInstance().getReference("user")
        PreferenceManager.getDefaultSharedPreferences(context)
            .registerOnSharedPreferenceChangeListener(this)

    }

    private fun loadSavedData(sharedPreferences: SharedPreferences?) {
        // switch dark mode preference
        val switchDarkMode = sharedPreferences?.getBoolean(Contacts.KEY_DARK_MODE, false)
        val preferenceDarkMode: Preference? = findPreference(Contacts.KEY_DARK_MODE)
        preferenceDarkMode?.setDefaultValue(switchDarkMode)

        if (switchDarkMode == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        //  location preference
        val preferenceLocation: Preference? = findPreference(Contacts.KEY_LOCATION)
        preferenceLocation?.onPreferenceClickListener = this

        // log out preference
        val preferenceLogOut: Preference? = findPreference(Contacts.KEY_LOGOUT)
        preferenceLogOut?.onPreferenceClickListener = this

        //about preference
        val preferenceAbout: Preference? = findPreference(Contacts.KEY_ABOUT)
        preferenceAbout?.onPreferenceClickListener = this

        // delete account preference
        val preferenceDeleteAccount: Preference? = findPreference(Contacts.KEY_DELETE_ACCOUNT)
        preferenceDeleteAccount?.onPreferenceClickListener = this
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            Contacts.KEY_DARK_MODE -> sharedPreferences?.getBoolean(key,false)?.let { handleDarkMode(it) }
        }
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference?.key) {
            Contacts.KEY_LOGOUT -> handleLogOut()
            Contacts.KEY_ABOUT -> handleAbout()
            Contacts.KEY_DELETE_ACCOUNT -> handleDeleteAccount()
            Contacts.KEY_LOCATION -> handleLocation()
        }
        return true
    }

    private fun handleLogOut() {
        logoutProfile()
        findNavController().navigate(R.id.action_settingFragment_to_loginFragment)
    }

    private fun handleDeleteAccount() {
        //handle delete account here
        deleteDataProfile()
        findNavController().navigate(R.id.action_settingFragment_to_loginFragment)
    }

    private fun handleAbout() {
        val supportFragmentManager: FragmentManager? = activity?.supportFragmentManager
        val dialogFragment = MyDialogFragment().newInstance(TITLE_ABOUT, MSG_ABOUT)
        supportFragmentManager?.let { dialogFragment.show(it, Contacts.DIALOG_FRAGMENT_TAG) }
    }

    private fun handleLocation() {
        checkForPermissionLocation(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun handleDarkMode(boolean: Boolean) {
        if (boolean) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun checkForPermissionLocation(permission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(requireContext().applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(requireContext().applicationContext, "Location permission granted", Toast.LENGTH_SHORT).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission)
                else -> ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), FINE_LOCATION)
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck() {
            if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext().applicationContext, "Location permission refused", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext().applicationContext, "Location permission granted", Toast.LENGTH_SHORT).show()
            }
        }
        innerCheck()
    }

    private fun showDialog(permission: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setMessage("Permission to access your location is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { _, _ ->
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission),
                    FINE_LOCATION
                )
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)

    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)

    }

    private fun deleteDataProfile() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val user = snapshot.value.toString()
                    val bigArray = user.split("={","},","}}").toTypedArray()
                    for (i in bigArray.indices) {
                        if(i % 2 != 0) {
                            val one = bigArray[i].split(",","=").toTypedArray()
                            if(one[7] == "true") {
                                database.child(one[13]).removeValue()
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
    private fun logoutProfile() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val user = snapshot.value.toString()
                    val bigArray = user.split("={","},","}}").toTypedArray()
                    for (i in bigArray.indices) {
                        if(i % 2 != 0) {
                            val one = bigArray[i].split(",","=").toTypedArray()
                            if(one[7] == "true") {
                                database.child(one[13]).child("login").setValue(false)
                            }
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}