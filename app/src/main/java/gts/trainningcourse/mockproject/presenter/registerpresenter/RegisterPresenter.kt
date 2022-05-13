package gts.trainningcourse.mockproject.presenter.registerpresenter

import gts.trainningcourse.mockproject.model.user.User
import gts.trainningcourse.mockproject.view.fragment.register.IRegisterView

class RegisterPresenter(private val mIRegisterView: IRegisterView): IRegisterPresenter {
    override fun stateRegister(user: User) {
        val isValid = user.isValidRegister
        if(isValid) {
            mIRegisterView.state("Success Register")
        } else {
            mIRegisterView.state("Fail Register")
        }
    }
}