package com.royalit.mfd.views.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.royalit.mfd.adapters.ServiceHomeAdapter
import com.royalit.mfd.databinding.FragmentDashboardBinding
import com.royalit.mfd.views.home.AppViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var serviceHomeAdapter: ServiceHomeAdapter
    private val sharedViewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serviceHomeAdapter=ServiceHomeAdapter()
        binding.recyclerviewCategories.layoutManager=GridLayoutManager(requireActivity().applicationContext,2)
        binding.recyclerviewCategories.adapter=serviceHomeAdapter
        sharedViewModel.categories.observe(requireActivity(), Observer {
            Log.d("Categories List","Categories List ${it.size}" )

            serviceHomeAdapter.setData(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}