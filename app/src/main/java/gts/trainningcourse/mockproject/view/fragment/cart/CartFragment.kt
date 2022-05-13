package gts.trainningcourse.mockproject.view.fragment.cart

/**
 * @author: Nhóm 2
 * */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mockproject.R
import gts.trainningcourse.mockproject.model.food.Food
import gts.trainningcourse.mockproject.view.adapter.ResultAdapter

/**
 * @author: Nhóm 2
 * To Do: Show your cart and calculate your pay
 */
class CartFragment : Fragment() {
    private var foodData:ArrayList<Food> = arrayListOf()
    private lateinit var resultRecyclerView : RecyclerView
    private var total:Int = 0
    private lateinit var tvTotal:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCartToHomeFragment:ImageButton = view.findViewById(R.id.btnCartToHomeFragment)
        val btnCartToProfileFragment:ImageButton = view.findViewById(R.id.btnCartToProfileFragment)

        tvTotal = view.findViewById(R.id.total)

        /*Get Bundle from detail fragment*/
        val resultBundle = arguments
        foodData = resultBundle?.getSerializable("result") as ArrayList<Food>

        /*Set up recyclerView*/
        resultRecyclerView = view.findViewById(R.id.resultRecyclerView)
        resultRecyclerView.layoutManager = LinearLayoutManager(context)
        resultRecyclerView.adapter = ResultAdapter(foodData)

        /*Calculate your pay*/
        for(value in foodData) {
            total += value.fee.toInt()
        }
        tvTotal.text = total.toString()

        /*Event to other fragment*/
        btnCartToHomeFragment.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_homeFragment)
        }
        btnCartToProfileFragment.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_profileFragment)
        }
    }
}