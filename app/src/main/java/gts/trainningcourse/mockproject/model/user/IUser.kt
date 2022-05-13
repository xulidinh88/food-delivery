package gts.trainningcourse.mockproject.model.user

interface IUser {
    val username:String
    val password:String
    val email:String
    val phone:String
    val isLogin:Boolean
    val isValidLogin:Boolean
    val isValidRegister: Boolean
}