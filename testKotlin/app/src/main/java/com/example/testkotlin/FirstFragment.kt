package com.example.testkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testkotlin.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    companion object {
        const val ButtonState = "ButtonState"
    }

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = context?.getString(R.string.errorGradeNotInAScope)
        val text2 = context?.getString(R.string.errorMissingText)

        if (savedInstanceState?.getString(ButtonState) == "Visible") {
            binding.buttonCheck.visibility = View.VISIBLE
        } else {
            binding.buttonCheck.visibility = View.INVISIBLE
        }

        binding.locenInput.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.locenInput.text.isNotEmpty()) {
                    val grades = binding.locenInput.text.toString().toInt()
                    if (!((grades <= 15) and (grades >= 5))) {
                        binding.locenInput.error = text
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    binding.locenInput.error = text2
                    Toast.makeText(context, text2, Toast.LENGTH_SHORT).show()
                }
                showButton()
            }
        }
        binding.imieInput.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.imieInput.text.isEmpty()) {
                    binding.imieInput.error = text2
                    Toast.makeText(context, text2, Toast.LENGTH_SHORT).show()
                }
                showButton()
            }
        }
        binding.nazwiskoInput.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.nazwiskoInput.text.isEmpty()) {
                    binding.nazwiskoInput.error = text2
                    Toast.makeText(context, text2, Toast.LENGTH_SHORT).show()
                }
                showButton()
            }
        }
        binding.buttonCheck.setOnClickListener {
            val bundle = bundleOf(
                "liczba_ocen" to binding.locenInput.text.toString(),
                "imie" to binding.imieInput.text.toString(),
                "nazwisko" to binding.nazwiskoInput.text.toString()
            )
            val fragment = SecondFragment()
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        if (this.arguments?.getString("srednia_ocen") != null) {
            binding.sredniaOcen.text =
                context?.getString(R.string.your_srednia_label) + " " + String.format(
                    "%.2f", this.arguments?.getString("srednia_ocen").toString().toDouble()
                )
            binding.sredniaOcen.visibility = View.VISIBLE
            binding.imieInput.text = this.arguments?.getString("imie").toString().toEditable()
            binding.nazwiskoInput.text =
                this.arguments?.getString("nazwisko").toString().toEditable()
            binding.locenInput.text =
                this.arguments?.getString("liczba_ocen").toString().toEditable()
            binding.buttonCheck.visibility = View.VISIBLE
            if (this.arguments?.getString("srednia_ocen").toString().toDouble() > 3.0) {
                binding.buttonCheck.text = context?.getString(R.string.congratulations)
                buttonExit("Gratulacje!")
            } else {
                binding.buttonCheck.text = context?.getString(R.string.unfortunate)
                buttonExit("Warunek!")
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) { // Here You have to save count value
        super.onSaveInstanceState(outState)
        if (binding.buttonCheck.visibility == View.VISIBLE) {
            outState.putString(ButtonState, "Visible")
        } else {
            outState.putString(ButtonState, "Invisible")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateGrades(): String? {
        return if (binding.locenInput.text.isNotEmpty()) {
            val grades = binding.locenInput.text.toString().toInt()
            if ((grades <= 15) and (grades >= 5)) {
                null
            } else {
                "Error"
            }
        } else {
            "Error"
        }
    }

    private fun validateName(): String? {
        return if (binding.imieInput.text.isNotEmpty()) {
            null
        } else "Error"
    }

    private fun validateSurname(): String? {
        return if (binding.nazwiskoInput.text.isNotEmpty()) {
            null
        } else "Error"
    }

    private fun showButton() {
        if ((validateGrades() == null) and (validateName() == null) and (validateSurname() == null)) {
            binding.buttonCheck.visibility = View.VISIBLE
        } else {
            binding.buttonCheck.visibility = View.INVISIBLE
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun buttonExit(exitText: String) {
        binding.buttonCheck.setOnClickListener {
            Toast.makeText(context, exitText, Toast.LENGTH_SHORT).show()
            activity?.finishAffinity()
        }
    }
}