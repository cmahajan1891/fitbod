package com.example.fitbod.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitbod.R
import com.example.fitbod.activity.MainActivity
import com.example.fitbod.adapters.ExerciseAdapter
import com.example.fitbod.data.model.ExerciseModel
import com.example.fitbod.di.components.DaggerFragmentComponent
import com.example.fitbod.di.modules.FragmentModule
import javax.inject.Inject

class Exercises : Fragment() {

    @Inject
    lateinit var exerciseAdapter: ExerciseAdapter

    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var spinner: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        injectDependencies()
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupObservers()
    }

    private fun setupViews(view: View) {
        spinner = view.findViewById(R.id.spinner)
        exerciseRecyclerView = view.findViewById(R.id.exercises_list)
        exerciseRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = exerciseAdapter
        }
    }

    private fun injectDependencies() {
        DaggerFragmentComponent.builder()
            .fragmentModule(
                FragmentModule(this, this)
            ).build()
            .inject(this)
    }

    private fun setupObservers() {
        (requireActivity() as? MainActivity)?.viewModel?.exerciseMap?.observe(requireActivity(), {
            spinner.visibility = View.GONE
            exerciseAdapter.appendData(getListOfExercises(it))
        })
    }

    private fun getListOfExercises(groupedExercises: Map<String, List<ExerciseModel>>): List<ExerciseModel> {
        return groupedExercises.mapNotNull {
            it.value.maxByOrNull { entry -> entry.oneRepMax }
        }
    }

}
