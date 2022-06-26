package com.tasks.discogsconsumer.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.tasks.discogsconsumer.R
import com.tasks.discogsconsumer.application.DiscogsConsumerApplication
import com.tasks.discogsconsumer.databinding.FragmentReleasesListBinding
import com.tasks.discogsconsumer.di.factory.ViewModelFactory
import com.tasks.discogsconsumer.ui.extensions.show
import com.tasks.discogsconsumer.common.Constants.Companion.RELEASE_ID_BUNDLE_KEY
import com.tasks.discogsconsumer.network.isNetworkConnected
import com.tasks.discogsconsumer.ui.adapter.ReleaseAdapter
import com.tasks.discogsconsumer.ui.common.BaseFragment
import com.tasks.discogsconsumer.ui.viewmodel.ArtistReleasesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReleasesListFragment  : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var artistReleasesViewModel: ArtistReleasesViewModel

    lateinit var binding: FragmentReleasesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DiscogsConsumerApplication.discogsConsumerComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReleasesListBinding.inflate(inflater)
        return binding.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artistReleasesViewModel = ViewModelProvider(this, viewModelFactory)[ArtistReleasesViewModel::class.java]
        setupReleasesList()
    }

    @ExperimentalPagingApi
    private fun setupReleasesList() {
        val pagingAdapter = ReleaseAdapter(ReleaseAdapter.ReleaseComparator) {
            navigateToReleaseDetail(it)
        }

        with(binding.releasesRv) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pagingAdapter
        }

        lifecycleScope.launch {
            artistReleasesViewModel.pagedFlow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.show(loadStates.refresh is LoadState.Loading)
                //TODO: handle errors and retry
                //retry.isVisible = loadState.refresh !is LoadState.Loading
                //errorMsg.isVisible = loadState.refresh is LoadState.Error
            }
        }

        binding.swipeRefreshLy.apply {
            //TODO: implement swipe to refresh
            setOnRefreshListener {
                //artistReleasesViewModel.cleanReleases()
                isRefreshing = false
            }
        }
    }

    private fun navigateToReleaseDetail(id: Int) {
        //TODO: use safe args, avoided to use it now because of bug with the library current version
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // code for portrait mode
            findNavController().navigate(R.id.action_releasesListFragment_to_releaseDetailFragment, Bundle().apply {
                putInt(RELEASE_ID_BUNDLE_KEY, id)
            })
        } else {
            // code for landscape mode
            childFragmentManager.commit {
                val arguments = Bundle()
                arguments.putInt(RELEASE_ID_BUNDLE_KEY, id)
                val ft = childFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.detail_container, ReleaseDetailFragment::class.java, arguments)
                if (binding.slidingPaneLayout?.isOpen == true) {
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                }
                ft.commit()
                binding.slidingPaneLayout?.open()
            }
            binding.slidingPaneLayout?.open()
        }
    }

    override fun onResume() {
        super.onResume()
        checkNetworkConnection()
    }

    private fun checkNetworkConnection() {
        if (!isNetworkConnected(requireActivity())) {
            Toast.makeText(requireActivity(), R.string.internet_not_connected, Toast.LENGTH_SHORT).show()
        }
    }
}