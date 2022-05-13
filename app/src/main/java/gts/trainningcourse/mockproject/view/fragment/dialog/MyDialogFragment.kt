package gts.trainningcourse.mockproject.view.fragment.dialog
/**
 * @author: Nhóm 2
 */
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.example.mockproject.R

class MyDialogFragment :DialogFragment() {

    private lateinit var btnClose: Button
    private lateinit var textTitle: TextView
    private lateinit var textMsg: TextView

    private var title: String?= null
    private var messenger: String?= null

    fun newInstance(title: String, msg: String) =
        MyDialogFragment().apply {
            arguments = Bundle().apply {
                putString("title", title)
                putString("messenger", msg)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textTitle = view.findViewById(R.id.title)
        textMsg = view.findViewById(R.id.text)
        btnClose = view.findViewById(R.id.btnClose)

        arguments?.let {
            title = it.getString("title")!!
            messenger = it.getString("messenger")!!
        }

        textTitle.text = title
        textMsg.text = messenger

        btnClose.setOnClickListener {
            dismiss()
        }
    }
}