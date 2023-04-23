package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.List;

public class GraphActivity extends AppCompatActivity {

    CheckBox checkQuantity, checkPrice, checkWaste;
    Button btn_plot;
    GraphView graphView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        checkQuantity = findViewById(R.id.checkQuantity);
        checkPrice = findViewById(R.id.checkPrice);
        checkWaste = findViewById(R.id.checkWaste);
        btn_plot = findViewById(R.id.btn_plot);

        graphView = findViewById(R.id.idGraphView);

        databaseHelper = new DatabaseHelper(GraphActivity.this);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>();

        List<Pair<Integer, Integer>> listQuantity = databaseHelper.plotQuantity();
        List<Pair<Integer, Integer>> listPrice = databaseHelper.plotPrice();
        List<Pair<Integer, Integer>> listWastage = databaseHelper.plotWastage();

        int id=0, quantity=0, maxid=0;
        for(int i=0;i<listQuantity.size();i++) {
            id = listQuantity.get(i).first;
            quantity = listQuantity.get(i).second;
            maxid = Math.max(maxid, id);

            series.appendData(new DataPoint(id,quantity), true, listQuantity.size());
        }

        int price=0;
        for(int i=0;i<listPrice.size();i++) {
            id = listPrice.get(i).first;
            price = listPrice.get(i).second;

            series2.appendData(new DataPoint(id,price), true, listPrice.size());
        }

        int waste=0;
        for(int i=0;i<listWastage.size();i++) {
            id = listWastage.get(i).first;
            waste = listWastage.get(i).second;

            series3.appendData(new DataPoint(id,waste), true, listWastage.size());
        }


        series.setTitle("Quantity");
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        series2.setTitle("Price");
        series2.setColor(Color.BLUE);
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);
        series2.setThickness(8);

        series3.setTitle("Waste");
        series3.setColor(Color.RED);
        series3.setDrawDataPoints(true);
        series3.setDataPointsRadius(10);
        series3.setThickness(8);


        graphView.setTitle("Graph View");
        graphView.setTitleColor(R.color.purple_200);
        graphView.setTitleTextSize(100);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(maxid+1);


        graphView.getViewport().setScrollable(true); // enables horizontal scrolling
        graphView.getViewport().setScrollableY(true); // enables vertical scrolling
        graphView.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graphView.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        //adding series to graph
        btn_plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                graphView.removeAllSeries();

                if(checkQuantity.isChecked()) {
                    graphView.addSeries(series);
                }
                if(checkPrice.isChecked()) {
                    graphView.addSeries(series2);
                }
                if(checkWaste.isChecked()) {
                    graphView.addSeries(series3);
                }

                // legend
                graphView.getLegendRenderer().setVisible(true);
                graphView.getLegendRenderer().setBackgroundColor(Color.LTGRAY);
                graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            }
        });

//        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
//            @Override
//            public String formatLabel(double value, boolean isValueX) {
//                if (isValueX) {
//                    // show normal x values
//                    return super.formatLabel(value, isValueX);
//                } else {
//                    // show currency for y values
//                    return super.formatLabel(value, isValueX) + " €";
//                }
//            }
//        });


        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(GraphActivity.this, "Id/Quantity= " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(GraphActivity.this, "Id/Price= " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        series3.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(GraphActivity.this, "Id/Waste= " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

    }
}