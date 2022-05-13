package gts.trainningcourse.mockproject.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mockproject.R
import gts.trainningcourse.mockproject.model.food.Food

class ResultAdapter(private val listResult : ArrayList<Food>) : RecyclerView.Adapter <ResultAdapter.MyResultViewHolder>() {
    inner class MyResultViewHolder(view :View) : RecyclerView.ViewHolder(view) {
        val resultImage:ImageView = view.findViewById(R.id.resultImage)
        val resultTitle:TextView = view.findViewById(R.id.resultTitle)
        val resultFee:TextView = view.findViewById(R.id.resultFee)
        var resultCount:TextView = view.findViewById(R.id.resultCount)
        val btnSubInCart:Button = view.findViewById(R.id.btnSubInCart)
        val btnAddInCart:Button = view.findViewById(R.id.btnAddInCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_result,parent, false)
        return MyResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyResultViewHolder, position: Int) {
        val resultCurrent = listResult[position]
        holder.resultImage.setImageResource(resultCurrent.pic)
        holder.resultTitle.text = resultCurrent.title
        holder.resultFee.text = resultCurrent.fee
        holder.resultCount.text = "1"
    }

    override fun getItemCount(): Int {
        return listResult.size
    }
}