package gts.trainningcourse.mockproject.model.food

import java.io.Serializable

class Food(
    override val title: String,
    override val pic: Int,
    override val decription: String,
    override val fee: String,
    override val category: String,
) : IFood, Serializable

