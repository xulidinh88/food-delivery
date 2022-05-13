package gts.trainningcourse.mockproject.model.category

import java.io.Serializable

class Category(override val pic: Int,
               override val title: String)
    : ICategory, Serializable {
}