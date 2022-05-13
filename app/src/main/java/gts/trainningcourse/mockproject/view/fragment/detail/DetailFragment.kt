package gts.trainningcourse.mockproject.view.fragment.detail

/**
 * @author: Nhóm 2
 * To Do: Show detail of Food,
 * receive data from homeFragment and categoryFragment,
 * set data Firebase
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mockproject.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import gts.trainningcourse.mockproject.model.food.Food

class DetailFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var getData:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnDetailToCartFragment: ImageButton = view.findViewById(R.id.btnDetailToHomeFragment)
        val btnDetailToProfileFragment: ImageButton = view.findViewById(R.id.btnDetailToProfileFragment)
        val tvDetailFoodType:TextView = view.findViewById(R.id.tvDetailFoodType)
        val detailBanner:ImageView = view.findViewById(R.id.detailBanner)
        val tvDetailFoodTitle:TextView = view.findViewById(R.id.tvDetailFoodTitle)
        val tvDetailFoodDescription:TextView = view.findViewById(R.id.tvDetailFoodDescription)
        val tvDetailFoodFee:TextView = view.findViewById(R.id.tvDetailFoodFee)
        val btnAddFoodToCart:Button = view.findViewById(R.id.btnAddFoodToCart)

        /*receive data by using bundle*/
        val bundle = arguments
        val detailItem: Food = bundle?.getSerializable("ItemFood") as Food
        tvDetailFoodType.text = detailItem.category
        detailBanner.setImageResource(detailItem.pic)
        tvDetailFoodTitle.text = detailItem.title
        tvDetailFoodDescription.text = detailItem.decription
        tvDetailFoodFee.text = detailItem.fee

        /*Send data to Firebase*/
        database = FirebaseDatabase.getInstance().getReference("food")
        btnAddFoodToCart.setOnClickListener {
            database.child(tvDetailFoodTitle.text.toString()).setValue(detailItem).addOnSuccessListener {
                Toast.makeText(context, "Add $detailItem", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Fail Add Item", Toast.LENGTH_SHORT).show()
            }
//            val resultBundle = Bundle()
//            resultBundle.putSerializable("result", detailItem)
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }

        btnDetailToCartFragment.setOnClickListener{
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }
        btnDetailToProfileFragment.setOnClickListener{
            findNavController().navigate(R.id.action_detailFragment_to_profileFragment)
        }
    }

}