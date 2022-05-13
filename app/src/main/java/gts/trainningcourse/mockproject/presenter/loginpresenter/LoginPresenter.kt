package gts.trainningcourse.mockproject.presenter.loginpresenter

import gts.trainningcourse.mockproject.model.user.User
import gts.trainningcourse.mockproject.view.fragment.login.ILoginView

class LoginPresenter(private val mILoginView: ILoginView) : ILoginPresenter {
    override fun stateLogin(username: String, password: String, check : Boolean) {

        val user = User(username, password,"","")
        val isValidLogin = user.isValidLogin

        if(isValidLogin && check) {
            mILoginView.state("Success Login")
        } else {
            mILoginView.state("Fail Login")
        }
    }
}