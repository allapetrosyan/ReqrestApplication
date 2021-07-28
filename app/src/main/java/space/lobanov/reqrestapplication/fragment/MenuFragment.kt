package space.lobanov.reqrestapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import space.lobanov.reqrestapplication.R


class MenuFragment : Fragment() {
    private var menuClickListener: MenuFragmentClickListener? = null
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnRegister = view.findViewById<Button>(R.id.registerBtn)
        btnRegister.setOnClickListener {
            menuClickListener?.onRegClick()
        }

        val btnLogin = view.findViewById<Button>(R.id.loginBtn)
        btnLogin.setOnClickListener {
            menuClickListener?.onLoginClick()
        }
    }

    fun setMenuFragmentsClickListener(menuFragmentClickListener: MenuFragmentClickListener) {
        this.menuClickListener = menuFragmentClickListener
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}