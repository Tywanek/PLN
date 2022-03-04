package com.radlab.pln

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.radlab.pln.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EuroFragment : Fragment() {
    private val viewModel: EuroFragmentViewModel by viewModels()
    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.response.observe(viewLifecycleOwner) {
            binding.textviewFirst.text = it
        }

        viewModel.responseEUR.observe(viewLifecycleOwner) {
            binding.textviewEur.text = it
        }

        viewModel.responsePLN.observe(viewLifecycleOwner) {
            binding.textviewPln.text = it
        }

        binding.editTextFirst.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.calculate(binding.editTextFirst.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}