package gts.trainningcourse.mockproject.presenter.registerpresenter

import gts.trainningcourse.mockproject.model.user.User

interface IRegisterPresenter {
    fun stateRegister(user : User)
}