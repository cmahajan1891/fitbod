package com.example.fitbod.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.fitbod.DetailFragmentArgs
import com.example.fitbod.R
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

    private var minX: Double? = 0.0
    private var minY: Double? = 0.0
    private var maxX: Double? = 0.0
    private var maxY: Double? = 0.0

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
            { exerciseMap ->
                val lineGraphSeries = getDataPoints(exerciseMap)

                graphView.addSeries(lineGraphSeries)
                graphView.viewport.isXAxisBoundsManual = true
                graphView.viewport.setMinX(minX ?: 0.0)
                graphView.viewport.setMaxX(maxX ?: 0.0 + 20)
                graphView.gridLabelRenderer.numHorizontalLabels = 5
                graphView.gridLabelRenderer.numVerticalLabels = 5
                graphView.viewport.isYAxisBoundsManual = true
                graphView.viewport.setMinY(minY ?: 0.0)
                graphView.viewport.setMaxY(maxY ?: 0.0)
                graphView.gridLabelRenderer.labelsSpace = 7
                graphView.onDataChanged(true, true)
                graphView.viewport.isScrollable = true
                graphView.viewport.isScalable = true
                graphView.gridLabelRenderer.isHumanRounding = false

                graphView.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
                    override fun formatLabel(value: Double, isValueX: Boolean): String {
                        return if (isValueX) {
                            DATE_FORMATTER_RENDER.format(Date(value.toLong()))
                        } else {
                            super.formatLabel(value, isValueX)
                        }
                    }
                }
            })
    }

    private fun getDataPoints(exerciseMap: Map<String, List<ExerciseModel>>): LineGraphSeries<DataPoint> {
        val exercises = exerciseMap.getValue(args.exerciseName)
        minX = exercises.minByOrNull { it.date }?.date?.time?.toDouble()
        minY = exercises.minByOrNull { it.oneRepMax }?.oneRepMax?.toDouble()
        maxX = exercises.maxByOrNull { it.date }?.date?.time?.toDouble()
        maxY = exercises.maxByOrNull { it.oneRepMax }?.oneRepMax?.toDouble()
        val lineGraphSeries = LineGraphSeries<DataPoint>()
        exercises.sortedBy { it.date }.forEach {
            lineGraphSeries.appendData(DataPoint(it.date, it.oneRepMax.toDouble()), true, 100)
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
