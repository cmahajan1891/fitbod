package com.example.fitbod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

// import com.jjoe64.graphview.GraphView
// import com.jjoe64.graphview.GraphViewSeries

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var exerciseName: TextView
    private lateinit var exerciseWeight: TextView
    private lateinit var listDivider: View
    // private lateinit var lineGraphView: GraphView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupGraph(view)
    }

    private fun setupGraph(view: View) {
        // lineGraphView.setTitle("My Graph View");

        // on below line we are setting
        // text color to our graph view.
        // lineGraphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        // lineGraphView.setTitleTextSize(18);

        // on below line we are adding
        // data series to our graph view.
//        lineGraphView.addSeries(GraphViewSeries(arrayOf(
//            GraphView.GraphViewData(0.0, 2.0),
//            GraphView.GraphViewData(0.0, 1.0)
//        )))
    }

    private fun setupViews(view: View) {
        exerciseName = view.findViewById(R.id.exercise_name)
        exerciseWeight = view.findViewById(R.id.exercise_weight)
        listDivider = view.findViewById(R.id.folder_section_divider)
        // lineGraphView = view.findViewById(R.id.graph_view)
        listDivider.visibility = View.GONE
        exerciseName.text = args.exerciseName
        exerciseWeight.text = args.maxWeight
    }
}
