package gts.trainningcourse.mockproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mockproject.R
import gts.trainningcourse.mockproject.view.permission.NetworkConnection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBarWithNavController(findNavController(R.id.fragment))
        handleNetWork()
    }

    private fun handleNetWork() {
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if(isConnected) {
                Toast.makeText(this, "Internet Connected!!!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Internet Disconnected!!!", Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}