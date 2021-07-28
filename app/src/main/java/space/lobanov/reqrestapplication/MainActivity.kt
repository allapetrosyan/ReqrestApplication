package space.lobanov.reqrestapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.lobanov.reqrestapplication.adapter.MyRecyclerViewAdapter
import space.lobanov.reqrestapplication.fragment.*
import space.lobanov.reqrestapplication.login.LoginClickListener
import space.lobanov.reqrestapplication.login.LoginPageFragment
import space.lobanov.reqrestapplication.model.Images
import space.lobanov.reqrestapplication.preferences.IPreferenceHelper
import space.lobanov.reqrestapplication.preferences.PreferenceManager
import space.lobanov.reqrestapplication.register.RegisterClickListener
import space.lobanov.reqrestapplication.register.RegisterPageFragment
import space.lobanov.reqrestapplication.viewmodel.MyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var recView: RecyclerView

    private val viewModel: MyViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }
        val userToken = preferenceHelper.getUserToken()
        if (userToken.isNotBlank()) {
            showRecView()
            getRecViewData()
        } else {
            hideRecView()
            initMenuFragment()
        }
    }

    private fun getRecViewData() {
        viewModel.getMutableLiveData()
        viewModel.img.observe(this, Observer {
            if (it.isNotEmpty() && it != null) {
                initRecyclerView(it)
            }
        })
    }

    private fun hideRecView() {
        recView.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
    }

    private fun showRecView() {
        recView.visibility = View.VISIBLE
        fragmentContainer.visibility = View.GONE
    }

    private fun initMenuFragment() {
        val menuFragment = MenuFragment.newInstance("param1", "param2")
        menuFragment.setMenuFragmentsClickListener(
            object : MenuFragmentClickListener {
                override fun onLoginClick() {
                    initLoginFragment()
                }

                override fun onRegClick() {
                    //  Replace by Reg fragment
                    initRegFragment()
                    Log.d("dwd", "Reg clicked")

                }


            })
        supportFragmentManager.beginTransaction().replace(fragmentContainer.id, menuFragment)
            .commit()
    }

    private fun initLoginFragment() {
        val loginFragment = LoginPageFragment.newInstance()
        loginFragment.setLoginListener(
            object : LoginClickListener {
                override fun onLoginSuccess() {
                    getRecViewData()
                    showRecView()
                }
            })
        supportFragmentManager.beginTransaction().replace(fragmentContainer.id, loginFragment)
            .commit()
    }

    private fun initRegFragment() {
        val register = RegisterPageFragment.newInstance()
        register.setRegListener(
            object : RegisterClickListener {
                override fun onRegistrationSuccess() {
                    getRecViewData()
                    showRecView()
                }
            })
        supportFragmentManager.beginTransaction().replace(fragmentContainer.id, register).commit()
    }


    private fun initViews() {
        fragmentContainer = findViewById(R.id.fragment_container)
        recView = findViewById(R.id.recViewId)
    }

    private fun initRecyclerView(list: List<Images>) {
        findViewById<RecyclerView>(R.id.recViewId).apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = MyRecyclerViewAdapter(list)
        }
    }

}

