package gts.trainningcourse.mockproject.view.fragment.profile

/**
 * Call back setting fragment
 */
interface ISettingFragment {
    /**
     * @param isDarkMode is true when system turn on dark mode.
     */
    fun sendDataDarkMode(isDarkMode: Boolean)
}
