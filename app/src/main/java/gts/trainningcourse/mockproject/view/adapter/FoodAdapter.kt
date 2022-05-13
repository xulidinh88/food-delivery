package gts.trainningcourse.mockproject.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mockproject.R
import gts.trainningcourse.mockproject.model.food.Food

class FoodAdapter(private val listFood:ArrayList<Food>, private val mIOnFoodItemClick: IOnFoodItemClick) : RecyclerView.Adapter<FoodAdapter.MyFoodViewHolder>() {

    inner class MyFoodViewHolder(view:View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val foodTitle:TextView = view.findViewById(R.id.foodTitle)
        val foodImage:ImageView = view.findViewById(R.id.foodImageView)
        val foodFee:TextView = view.findViewById(R.id.tvFee)
        val foodType:TextView = view.findViewById(R.id.tvType)
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                mIOnFoodItemClick.onFoodClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_food,parent, false)
        return MyFoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyFoodViewHolder, position: Int) {
        val foodCurrent = listFood[position]
        holder.foodTitle.text = foodCurrent.title
        holder.foodImage.setImageResource(foodCurrent.pic)
        holder.foodFee.text = foodCurrent.fee
        holder.foodType.text = foodCurrent.category
    }

    override fun getItemCount(): Int {
        return listFood.size
    }
}