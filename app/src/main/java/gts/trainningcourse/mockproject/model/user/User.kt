package gts.trainningcourse.mockproject.model.user

import android.text.TextUtils
import java.io.Serializable

class User(override val username: String,
           override val password: String,
           override val email: String,
           override val phone: String,
) : IUser, Serializable {

    override val isLogin: Boolean
        get() = false

    override val isValidLogin: Boolean
        get() = !TextUtils.isEmpty(username) && password.length >= 6
    override val isValidRegister: Boolean
        get() = !TextUtils.isEmpty(username) && password.length >= 6
                && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone)
}
