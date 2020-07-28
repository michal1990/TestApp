package pl.mradtke.testapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.mradtke.testapp.databinding.FragmentListUserBinding
import pl.mradtke.testapp.list.UserListAdapter
import pl.mradtke.testapp.viewmodel.UserListFragmentViewModel

/**
 * @author Michał Radtke
 * @version 28.07.2020
 */
class UserListFragment : Fragment() {

    private companion object {
        val TAG = UserListFragment::class.java.simpleName
    }

    private val fragmentViewModel: UserListFragmentViewModel by viewModel()
    private lateinit var binding: FragmentListUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentListUserBinding>(inflater, R.layout.fragment_list_user, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            viewModel = fragmentViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userAdapter = UserListAdapter().also {
            binding.adapter = it
            it.setOnItemClickListener {
                Log.d(TAG, "Item clicked!")
            }
        }

        fragmentViewModel.userList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Fetched new items: ${it.size}")
            userAdapter.submitList(it)
        })

        fragmentViewModel.requestError.observe(viewLifecycleOwner, Observer {
            Log.e(TAG, it)
            Snackbar.make(view, "Something went wrong during data fetching...", Snackbar.LENGTH_LONG).show()
        })
    }
}
