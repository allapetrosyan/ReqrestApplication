package space.lobanov.reqrestapplication.register

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_login_page.*
import kotlinx.android.synthetic.main.row_layout.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.lobanov.reqrestapplication.R
import space.lobanov.reqrestapplication.login.LoginClickListener
import space.lobanov.reqrestapplication.login.LoginPageFragment
import space.lobanov.reqrestapplication.preferences.IPreferenceHelper
import space.lobanov.reqrestapplication.preferences.PreferenceManager
import space.lobanov.reqrestapplication.viewmodel.MyViewModel


class RegisterPageFragment : Fragment() {

    lateinit var enterRegMail: EditText
    lateinit var enterRegPass: EditText
    val tokenResponse = MutableLiveData<String>()
    private var registerClickListener: RegisterClickListener? = null
    private val rViewModel: RegViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_page, container, false)

    }

    fun setRegListener(registerClickListener: RegisterClickListener) {
        this.registerClickListener = registerClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    fun initView(view: View) {
        enterRegMail = view.findViewById(R.id.enterRegMail)
        enterRegPass = view.findViewById(R.id.enterRegPass)
        val regBtn = view.findViewById<Button>(R.id.registrationBtn)
        regBtn.setOnClickListener {
            Log.d("dwd", "Reg ok")
            signUp()
        }

    }

    companion object {
        fun newInstance() =
            RegisterPageFragment()
    }


    private fun signUp() {
        val emailString = enterRegMail.text.toString()
        val passString = enterRegPass.text.toString()

        rViewModel.regIn(email = emailString, pass = passString)

        rViewModel.handleEmailEmptyTextError.observe(viewLifecycleOwner, Observer {
            emailText.error = it
            emailText.requestFocus()
        })
        rViewModel.handlePassEmptyTextError.observe(viewLifecycleOwner, Observer {
            passwordEditText.error = it
            passwordEditText.requestFocus()
        })

        rViewModel.tokenResponse.observe(viewLifecycleOwner, Observer {
            if (it.isNotBlank()) {
                val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
                preferenceHelper.setUserToken(it)
                registerClickListener?.onRegistrationSuccess()
            }
        })
    }

}

