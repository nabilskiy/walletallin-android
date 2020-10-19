package com.example.coinsliberty.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.coinsliberty.dialogs.AcceptDialog
import com.example.coinsliberty.utils.extensions.bindDataTo

abstract class BaseKotlinFragment : Fragment() {

    abstract val layoutRes: Int

    abstract val viewModel: BaseViewModel

    abstract val navigator: BaseNavigator

    var navController: NavController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDataTo(viewModel.onStartProgress) { }
        bindDataTo(viewModel.onEndProgress) { }
        onReceiveParams(arguments)
    }

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    protected open fun onReceiveParams(arguments: Bundle?) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigator.attach(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.release()
    }
}