package com.example.testkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testkotlin.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var listaOcen: RecyclerView
    private var mDane: ArrayList<ModelOceny> = arrayListOf()
    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listaOcen = requireView().findViewById(R.id.lista_ocen_rv)
        listaOcen.layoutManager = LinearLayoutManager(context)
        val classes = resources.getStringArray(R.array.classes)
        for (i in 0 until this.arguments?.getString("liczba_ocen").toString().toInt()) {
            mDane.add(ModelOceny(classes[i], 5))
        }
        listaOcen.adapter = AdapterTablicy(mDane)
        binding.obliczSrednia.setOnClickListener {
            val bundle2 = bundleOf(
                "srednia_ocen" to obliczSrednia(mDane).toString(),
                "liczba_ocen" to this.arguments?.getString("liczba_ocen").toString(),
                "imie" to this.arguments?.getString("imie"),
                "nazwisko" to this.arguments?.getString("nazwisko").toString()
            )
            val fragment = FirstFragment()
            fragment.arguments = bundle2
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment, bundle2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obliczSrednia(oceny: List<ModelOceny>): Double {
        return (oceny.sumOf { x -> x.ocena }.toDouble() / oceny.size.toDouble())
    }
}