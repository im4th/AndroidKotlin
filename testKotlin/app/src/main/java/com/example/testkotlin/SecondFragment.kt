package com.example.testkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testkotlin.databinding.FragmentSecondBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var listaOcen: RecyclerView
    private var mDane: ArrayList<ModelOceny> = arrayListOf()
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}