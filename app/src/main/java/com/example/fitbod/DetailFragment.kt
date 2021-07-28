package com.example.fitbod

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.fitbod.activity.MainActivity
import com.example.fitbod.data.model.ExerciseModel
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT_RENDER = "MMM dd"

class DetailFragment : Fragment() {

    companion object {
        @SuppressLint("ConstantLocale")
        private val DATE_FORMATTER_RENDER =
            SimpleDateFormat(DATE_FORMAT_RENDER, Locale.getDefault())
    }

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var exerciseName: TextView
    private lateinit var exerciseWeight: TextView
    private lateinit var listDivider: View
    private lateinit var graphView: GraphView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupObservers()
    }

    private fun setupObservers() {
        val viewModel = (requireActivity() as? MainActivity)?.viewModel
        viewModel?.exerciseMap?.observe(
            requireActivity(),
            { exercises ->
                val lineGraphSeries = getDataPoints(exercises)
                graphView.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
                    override fun formatLabel(value: Double, isValueX: Boolean): String {
                        return if (isValueX) {
                            DATE_FORMATTER_RENDER.format(Date(value.toLong()))
                        } else {
                            super.formatLabel(value, isValueX)
                        }
                    }
                }
                graphView.addSeries(lineGraphSeries)
            })
    }

    private fun getDataPoints(exerciseMap: Map<String, List<ExerciseModel>>): LineGraphSeries<DataPoint> {
        val lineGraphSeries = LineGraphSeries<DataPoint>()
        val exercises = exerciseMap.getValue(args.exerciseName)
        exercises.sortedBy { it.date }.forEach {
            lineGraphSeries.appendData(DataPoint(it.date, it.oneRepMax.toDouble()), true, 50)
        }
        lineGraphSeries.isDrawDataPoints = true
        lineGraphSeries.isDrawAsPath = true
        return lineGraphSeries
    }

    private fun setupViews(view: View) {
        exerciseName = view.findViewById(R.id.exercise_name)
        exerciseWeight = view.findViewById(R.id.exercise_weight)
        listDivider = view.findViewById(R.id.folder_section_divider)
        graphView = view.findViewById(R.id.graph_view)
        listDivider.visibility = View.GONE
        exerciseName.text = args.exerciseName
        exerciseWeight.text = args.maxWeight
    }
}
