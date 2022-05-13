package gts.trainningcourse.mockproject.view.fragment.home
/**
 * @author: Nhóm 2
 * To Do: Home Screen, Load data, set event click item, get data Firebase
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
import com.google.firebase.database.*
import gts.trainningcourse.mockproject.model.category.Category
import gts.trainningcourse.mockproject.model.food.Food
import gts.trainningcourse.mockproject.utils.Contacts
import gts.trainningcourse.mockproject.view.adapter.CategoryAdapter
import gts.trainningcourse.mockproject.view.adapter.FoodAdapter
import gts.trainningcourse.mockproject.view.adapter.IOnCategoryItemClick
import gts.trainningcourse.mockproject.view.adapter.IOnFoodItemClick


class HomeFragment : Fragment(), IOnFoodItemClick, IOnCategoryItemClick {

    private lateinit var categoryData:ArrayList<Category>
    private lateinit var foodData:ArrayList<Food>
    private lateinit var categoryRecyclerView : RecyclerView
    private lateinit var popularFoodRecyclerView : RecyclerView
    private lateinit var databaseFood: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnHomeToCartFragment: ImageButton = view.findViewById(R.id.btnHomeToCartFragment)
        val btnHomeToProfileFragment: ImageButton = view.findViewById(R.id.btnHomeToProfileFragment)
        importCategoryData()
        importFoodData()

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.adapter = CategoryAdapter(categoryData, this)

        popularFoodRecyclerView = view.findViewById(R.id.popularFoodRecyclerView)
        popularFoodRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        popularFoodRecyclerView.adapter = FoodAdapter(foodData, this)

        databaseFood = FirebaseDatabase.getInstance().getReference("food")
        btnHomeToCartFragment.setOnClickListener{
            handleClickCartButton()
        }

        btnHomeToProfileFragment.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    /*Send data to firebase when click cart button*/
    private fun handleClickCartButton()  {
        databaseFood.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val resultData = arrayListOf<Food>()
                if(snapshot.exists()) {
                    /*Get json, convert to String*/
                    val user = snapshot.value.toString()
                    /*Get each user*/
                    val bigArray = user.split("={","},","}}").toTypedArray()
                    for (i in bigArray.indices) {
                        /*set one User, add to resultList*/
                        if(i % 2 != 0) {
                            val one = bigArray[i].split(",","=").toTypedArray()
                            resultData.add(Food(one[9],one[5].toInt(), one[1], one[3], one[7]))
                        }
                    }
                    /*Send resultList to cartFragment*/
                    val resultBundle = Bundle()
                    resultBundle.putSerializable("result", resultData)
                    //Toast.makeText(context, "Send Data", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_homeFragment_to_cartFragment, resultBundle)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
       // findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
    }

    /*Load data*/
    private fun importCategoryData() {
        categoryData = arrayListOf()
        categoryData.add(Category(R.drawable.cat_pizza, Contacts.CATEGORY_PIZZA))
        categoryData.add(Category(R.drawable.cat_drink, Contacts.CATEGORY_DRINK))
        categoryData.add(Category(R.drawable.cat_noodle, Contacts.CATEGORY_NOODLE))
        categoryData.add(Category(R.drawable.cat_rice, Contacts.CATEGORY_RICE))
        categoryData.add(Category(R.drawable.cat_chicken, Contacts.CATEGORY_CHICKEN))
        categoryData.add(Category(R.drawable.cat_friedpotato, Contacts.CATEGORY_FRIEDPOTATO))
    }
    private fun importFoodData() {
        foodData = arrayListOf()
        foodData.add(Food(Contacts.TITLE_COMNHALAM, R.drawable.cat_rice, Contacts.NOTE_COMNHALAM, "50000","Rice"))
        foodData.add(Food(Contacts.TITLE_COCACOLA, R.drawable.cat_drink, Contacts.NOTE_COCACOLA, "10000","Drink"))
        foodData.add(Food(Contacts.TITLE_MIY, R.drawable.cat_noodle, Contacts.NOTE_MIY, "50000","Noodle"))
        foodData.add(Food(Contacts.TITLE_PIZZA1, R.drawable.cat_pizza, Contacts.NOTE_PIZZA, "150000","Pizza"))
        foodData.add(Food(Contacts.TITLE_FRIEDPOTATO1, R.drawable.cat_friedpotato, Contacts.NOTE_FRIEDPOTATO, "50000","Fried Potato"))
        foodData.add(Food(Contacts.TITLE_COMRANG1, R.drawable.cat_rice, Contacts.NOTE_COMRANG, "50000", "Rice"))
        foodData.add(Food(Contacts.TITLE_FRIEDPOTATO2, R.drawable.cat_friedpotato, Contacts.NOTE_FRIEDPOTATO, "50000","Fried Potato"))
        foodData.add(Food(Contacts.TITLE_COMRANG2, R.drawable.cat_rice, Contacts.NOTE_COMNHALAM, "50000","Rice"))
        foodData.add(Food(Contacts.TITLE_PEPSI, R.drawable.cat_drink, Contacts.NOTE_COCACOLA, "50000","Drink"))
        foodData.add(Food(Contacts.TITLE_MIXAO, R.drawable.cat_noodle, Contacts.NOTE_PHOXAO, "50000","Noodle"))
        foodData.add(Food(Contacts.TITLE_PIZZA2, R.drawable.cat_pizza, Contacts.NOTE_PIZZA, "80000","Pizza"))
        foodData.add(Food(Contacts.TITLE_NUOCKHOANG, R.drawable.cat_drink, Contacts.NOTE_NUOCKHOANG, "100000","Drink"))
        foodData.add(Food(Contacts.TITLE_MITRON, R.drawable.cat_noodle, Contacts.NOTE_MITRON, "50000","Noodle"))
        foodData.add(Food(Contacts.TITLE_FRIEDPOTATO1, R.drawable.cat_friedpotato, Contacts.NOTE_FRIEDPOTATO, "50000","Fried Potato"))
        foodData.add(Food(Contacts.TITLE_CHICKEN, R.drawable.cat_chicken, Contacts.NOTE_CHICKEN, "150000","Chicken"))
    }

    /*Event click item using interface*/
    override fun onFoodClick(position: Int) {
        val bundle = Bundle()
        //bundle.putString("text", "text")
        bundle.putSerializable("ItemFood", foodData[position])
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }
    override fun onCategoryClick(position: Int) {
        val tempArray:ArrayList<Food> = arrayListOf()
        for(value in foodData) {
            if(value.category == categoryData[position].title) {
                tempArray.add(value)
            }
        }
        val bundle = Bundle()
        //bundle.putString("text", "text")
        bundle.putSerializable("category", tempArray)
        findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
    }
}

//                    for (i in snapshot.children) {
//                        val user = i.value.toString()
//                        val temp = user.split(", ","=").toTypedArray()
//                        resultData.add(Food(temp[9],temp[5].toInt(), temp[1], temp[3], temp[7]))
//                    }
