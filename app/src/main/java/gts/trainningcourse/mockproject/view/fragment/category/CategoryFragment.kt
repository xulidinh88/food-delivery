package gts.trainningcourse.mockproject.view.fragment.category

/**
 * @author: Nhóm 2
 * To Do: Show type of Food by Category
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mockproject.R
import gts.trainningcourse.mockproject.model.food.Food
import gts.trainningcourse.mockproject.view.adapter.IOnFoodItemClick
import gts.trainningcourse.mockproject.view.adapter.SecondCategoryAdapter

class CategoryFragment : Fragment(), IOnFoodItemClick {

    private lateinit var foodData:ArrayList<Food>
    private lateinit var secondCategoryRecyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCategoryToProfileFragment:ImageButton = view.findViewById(R.id.btnCategoryToProfileFragment)
        val btnCategoryToHomeFragment:ImageButton = view.findViewById(R.id.btnCategoryToHomeFragment)

        /*Get Data From Home Fragment by using Bundle*/
        val bundle = arguments
        foodData = bundle?.getSerializable("category") as ArrayList<Food>

        /*Set Up RecyclerView*/
        secondCategoryRecyclerView = view.findViewById(R.id.secondCategoryRecyclerView)
        secondCategoryRecyclerView.layoutManager = LinearLayoutManager(context)
        secondCategoryRecyclerView.adapter = SecondCategoryAdapter(foodData, this)

        /*Event to other fragment*/
        btnCategoryToProfileFragment.setOnClickListener{
            findNavController().navigate(R.id.action_categoryFragment_to_profileFragment)
        }
        btnCategoryToHomeFragment.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_homeFragment)
        }
    }

    /*Send Position Item Click to Detail Fragment*/
    override fun onFoodClick(position: Int) {
        val bundle = Bundle()
        //bundle.putString("text", "text")
        bundle.putSerializable("ItemFood", foodData[position])
        findNavController().navigate(R.id.action_categoryFragment_to_detailFragment, bundle)
    }
}