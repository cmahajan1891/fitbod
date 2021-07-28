package com.example.fitbod.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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

    private var minX: Double = 0.0
    private var minY: Double = 0.0
    private var maxX: Double = 0.0
    private var maxY: Double = 0.0

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
        val yAxisTitleSuffix = graphView.resources.getString(R.string.exercise_list_item_lbs_text)

        viewModel?.let {
            viewModel.updateActionBarTitle(args.exerciseName)
            viewModel.exerciseMap.observe(
                requireActivity(),
                { exerciseMap ->
                    val lineGraphSeries = getDataPoints(exerciseMap)
                    graphView.removeAllSeries()
                    graphView.addSeries(lineGraphSeries)

                    graphView.viewport.apply {
                        isScrollable = true
                        isScalable = true
                        isXAxisBoundsManual = true
                        isYAxisBoundsManual = true
                        setMinX(minX)
                        setMaxX(maxX)
                        setMinY(minY)
                        setMaxY(maxY)
                    }

                    graphView.onDataChanged(true, true)
                    graphView.gridLabelRenderer.apply {
                        numHorizontalLabels = 5
                        numVerticalLabels = 5
                        labelsSpace = 5
                        setHumanRounding(false)
                        labelFormatter = object : DefaultLabelFormatter() {
                            override fun formatLabel(value: Double, isValueX: Boolean): String {
                                return if (isValueX) {
                                    DATE_FORMATTER_RENDER.format(Date(value.toLong()))
                                } else {
                                    return value.toString().plus(" $yAxisTitleSuffix ")
                                }
                            }
                        }
                    }
                })
        }
    }

    private fun getDataPoints(exerciseMap: Map<String, List<ExerciseModel>>): LineGraphSeries<DataPoint> {
        val exercises = exerciseMap.getValue(args.exerciseName)

        val sortedE = exercises.sortedWith { a, b ->
            when {
                a.date > b.date -> 1
                a.date < b.date -> -1
                else -> b.oneRepMax.compareTo(a.oneRepMax)
            }
        }.distinctBy { it.date }

        sortedE.minByOrNull { it.date }?.date?.time?.toDouble()?.let { minX = it }
        sortedE.minByOrNull { it.oneRepMax }?.oneRepMax?.toDouble()?.let { minY = it }
        sortedE.maxByOrNull { it.date }?.date?.time?.toDouble()?.let { maxX = it }
        sortedE.maxByOrNull { it.oneRepMax }?.oneRepMax?.toDouble()?.let { maxY = it }

        return LineGraphSeries(sortedE.map {
            Log.d("DetailFragment", "DATE - ${it.date} oneRepMax - ${it.oneRepMax}")
            DataPoint(it.date, it.oneRepMax.toDouble())
        }.toTypedArray()).apply<LineGraphSeries<DataPoint>> {
            isDrawDataPoints = true
            isDrawAsPath = true
        }
    }

    private fun setupViews(view: View) {
        exerciseName = view.findViewById(R.id.exercise_name)
        exerciseWeight = view.findViewById(R.id.exercise_one_rep_max)
        listDivider = view.findViewById(R.id.folder_section_divider)
        graphView = view.findViewById(R.id.graph_view)
        listDivider.visibility = View.GONE
        exerciseName.text = args.exerciseName
        exerciseWeight.text = args.oneRepMax
    }
}
