package gts.trainningcourse.mockproject.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mockproject.R
import gts.trainningcourse.mockproject.model.food.Food

class SecondCategoryAdapter(private val list:List<Food>, private val mIOnFoodItemClick: IOnFoodItemClick):
    RecyclerView.Adapter<SecondCategoryAdapter.MySecondCategoryViewHolder>() {

    inner class MySecondCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val categoryResultTitle: TextView = view.findViewById(R.id.categoryResultTitle)
        val categoryResultImage: ImageView = view.findViewById(R.id.categoryResultImage)
        val categoryResultFee: TextView = view.findViewById(R.id.categoryResultFee)
        val categoryResultDescription:TextView = view.findViewById(R.id.categoryResultDescription)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySecondCategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_second_category, parent, false)
        return MySecondCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MySecondCategoryViewHolder, position: Int) {
        val foodCurrent = list[position]
        holder.categoryResultTitle.text = foodCurrent.title
        holder.categoryResultImage.setImageResource(foodCurrent.pic)
        holder.categoryResultFee.text = foodCurrent.fee
        holder.categoryResultDescription.text = foodCurrent.decription
    }

    override fun getItemCount(): Int {
        return list.size
    }
}