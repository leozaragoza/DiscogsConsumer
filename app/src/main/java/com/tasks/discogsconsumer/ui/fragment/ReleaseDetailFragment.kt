package com.tasks.discogsconsumer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tasks.discogsconsumer.application.DiscogsConsumerApplication
import com.tasks.discogsconsumer.databinding.FragmentReleaseDetailBinding
import com.tasks.discogsconsumer.di.factory.ViewModelFactory
import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.common.Constants.Companion.RELEASE_ID_BUNDLE_KEY
import com.tasks.discogsconsumer.ui.common.BaseFragment
import com.tasks.discogsconsumer.ui.viewmodel.ReleaseDetailViewModel
import javax.inject.Inject

class ReleaseDetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var releaseDetailViewModel: ReleaseDetailViewModel

    private lateinit var binding: FragmentReleaseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DiscogsConsumerApplication.discogsConsumerComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReleaseDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        releaseDetailViewModel = ViewModelProvider(this, viewModelFactory)[ReleaseDetailViewModel::class.java]
        getReleaseData()
    }

    private fun getReleaseData() {
        arguments?.getInt(RELEASE_ID_BUNDLE_KEY)?.let {
            releaseDetailViewModel.getRelease(it).observe(viewLifecycleOwner, { release ->
                release?.let { populateReleaseData(release) }
            })
        }
    }

    private fun populateReleaseData(release: Release) {
        with(binding) {
            status.text = release.status
            type.text = release.type
            title.text = release.title
            year.text = release.year.toString()
            artist.text = release.artist
            label.text = release.label
            format.text = release.format
        }
    }
}