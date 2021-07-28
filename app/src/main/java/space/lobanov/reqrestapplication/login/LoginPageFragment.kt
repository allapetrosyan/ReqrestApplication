package space.lobanov.reqrestapplication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_login_page.*
import kotlinx.android.synthetic.main.row_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.lobanov.reqrestapplication.R
import space.lobanov.reqrestapplication.preferences.IPreferenceHelper
import space.lobanov.reqrestapplication.preferences.PreferenceManager


class LoginPageFragment : Fragment() {
    private val lViewModel: LoginViewModel by viewModel()

    private lateinit var enterMail: EditText
    private lateinit var enterPass: EditText
    private var loginClickListener: LoginClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        enterMail = view.findViewById(R.id.loginEditText)
        enterPass = view.findViewById(R.id.passwordEditText)
        val loginBtn = view.findViewById<Button>(R.id.logBtn)
        loginBtn?.setOnClickListener {

            login()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_page, container, false)
    }

    fun setLoginListener(loginClickListener: LoginClickListener) {
        this.loginClickListener = loginClickListener
    }

    companion object {
        fun newInstance() =
            LoginPageFragment()
    }

    private fun login() {
        val emailString = enterMail.text.toString()
        val passString = enterPass.text.toString()

        lViewModel.logIn(email = emailString, pass = passString)

        lViewModel.handleEmailEmptyTextError.observe(viewLifecycleOwner, Observer {
            emailText.error = it
            emailText.requestFocus()
        })
        lViewModel.handlePassEmptyTextError.observe(viewLifecycleOwner, Observer {
            passwordEditText.error = it
            passwordEditText.requestFocus()
        })

        lViewModel.replaceResponse.observe(viewLifecycleOwner, Observer {
            if (it.isNotBlank()) {
                val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
                preferenceHelper.setUserToken(it)
                loginClickListener?.onLoginSuccess()
            }
        })
    }

}





