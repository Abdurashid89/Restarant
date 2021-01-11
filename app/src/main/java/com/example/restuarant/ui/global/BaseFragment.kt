package com.example.restuarant.ui.global

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.restuarant.di.DI
import com.example.restuarant.extentions.objectScopeName
import moxy.MvpAppCompatFragment
import toothpick.Scope
import toothpick.Toothpick


private const val PROGRESS_TAG = "bf_progress"
private const val STATE_SCOPE_NAME = "state_scope_name"

abstract class BaseFragment : MvpAppCompatFragment() {
    abstract val layoutRes: Int

    private var instanceStateSaved: Boolean = false

    private val viewHandler = Handler()

    protected open val parentScopeName: String by lazy {
        (parentFragment as? BaseFragment)?.fragmentScopeName
            ?: DI.SERVER_SCOPE
    }

    lateinit var fragmentScopeName: String
    protected lateinit var scope: Scope
        private set

    protected open fun installModules(scope: Scope) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentScopeName = savedInstanceState?.getString(STATE_SCOPE_NAME) ?: objectScopeName()

        if (Toothpick.isScopeOpen(fragmentScopeName)) {
            scope = Toothpick.openScope(fragmentScopeName)
        } else {
            scope = Toothpick.openScopes(parentScopeName, fragmentScopeName)
            installModules(scope)
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(layoutRes, container, false)

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    // Fix for async views (like swipeToRefresh and RecyclerView)
    // If synchronously call actions on swipeToRefresh in sequence show and hide then swipeToRefresh will not hidden
    protected fun postViewAction(action: () -> Unit) {
        viewHandler.post(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewHandler.removeCallbacksAndMessages(null)
    }

    fun getContextBasic() = context

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
        outState.putString(STATE_SCOPE_NAME, fragmentScopeName)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needCloseScope()) {
            // Destroy this fragment with scope

            Toothpick.closeScope(scope.name)
        }
    }

    // This is android, baby!
    private fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) || // Because isRemoving == true for fragment in backstack on screen rotation
                ((parentFragment as? BaseFragment)?.isRealRemoving() ?: false)

    // It will be valid only for 'onDestroy()' method
    private fun needCloseScope(): Boolean =
        when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> isRealRemoving()
        }

    protected fun showProgressDialog() {
        if (!isAdded || instanceStateSaved) return
    }

    open fun onBackPressed() {
        requireActivity().finish()
    }
}
