package com.example.testkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testkotlin.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    companion object {
        const val ButtonState = "ButtonState" // const key to save/read value from bundle
    }

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState?.getString(ButtonState) == "Visible") {
            binding.buttonCheck.visibility = View.VISIBLE
        } else {
            binding.buttonCheck.visibility = View.INVISIBLE
        }
        binding.locenInput.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = "Niepoprawna liczba ocen"
                val text2 = "Nie może być puste"
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
                    binding.imieInput.error = "Nie może być puste"
                    Toast.makeText(context, "Nie może być puste", Toast.LENGTH_SHORT).show()
                }
                showButton()
            }
        }
        binding.nazwiskoInput.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.nazwiskoInput.text.isEmpty()) {
                    binding.nazwiskoInput.error = "Nie może być puste"
                    Toast.makeText(context, "Nie może być puste", Toast.LENGTH_SHORT).show()
                }
                showButton()
            }
        }
        binding.buttonCheck.setOnClickListener {
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
        } else
            "Error"
    }

    private fun validateSurname(): String? {
        return if (binding.nazwiskoInput.text.isNotEmpty()) {
            null
        } else
            "Error"
    }

    private fun showButton() {
        if ((validateGrades() == null) and (validateName() == null) and (validateSurname() == null)) {
            binding.buttonCheck.visibility = View.VISIBLE
        } else {
            binding.buttonCheck.visibility = View.INVISIBLE
        }
    }
}