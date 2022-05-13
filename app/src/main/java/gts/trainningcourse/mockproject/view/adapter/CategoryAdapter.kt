package gts.trainningcourse.mockproject.view.adapter

/**
 * @author: Nhóm 2
 * */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mockproject.R
import gts.trainningcourse.mockproject.model.category.Category

class CategoryAdapter(private val categoryList:List<Category>, private val mIOnCategoryClick : IOnCategoryItemClick):
    RecyclerView.Adapter<CategoryAdapter.MyCategoryViewHolder>() {

    inner class MyCategoryViewHolder(view :View): RecyclerView.ViewHolder(view), View.OnClickListener  {
        val categoryImage:ImageView = view.findViewById(R.id.imageOneCategory)
        val categoryName:TextView = view.findViewById(R.id.tvOneCategory)

        /* Constructor Click Item */
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                mIOnCategoryClick.onCategoryClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category,parent, false)
        return MyCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCategoryViewHolder, position: Int) {
        val categoryCurrent = categoryList[position]
        holder.categoryName.text = categoryCurrent.title
        holder.categoryImage.setImageResource(categoryCurrent.pic)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}