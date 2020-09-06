package com.genSci.tools.FreqGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jfree.fx.FXGraphics2D;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class PrimaryController {
	@FXML
	TextArea log; // bottom の Log
	@FXML
	TextArea rightLog; // right の Log.統計値を出力
	@FXML
	TextArea dataArea; // left のデータエリア
//	@FXML
//	Canvas canvas; // center の度数分布エリア
	@FXML
	private TextField classWidth; // 階級幅
	@FXML
	private TextField classMin;
	@FXML
	private TextField classMax;
//	@FXML
//	// Create a BarChart
//	private BarChart<String, Number> barChart;
//	@FXML
//	private CategoryAxis xAxis;
//	@FXML
//	private NumberAxis yAxis;
	@FXML
	private Pane viewPane;

	// クラス変数として軸とチャートを作る
	private CategoryAxis xAxis = new CategoryAxis();
	private NumberAxis yAxis = new NumberAxis();
	private BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
	private XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
	private int[] freqCount;
	// 横軸の階級値
	private List<Double> classArray = new ArrayList<Double>();
	//グラフタイトルに付ける系列名
	private String fieldLabel;
	@FXML
	private void quitAction() {
		System.exit(0);
	}

	@FXML
	private void initialize() {
		barChart.prefWidthProperty().bind(viewPane.prefWidthProperty());
		barChart.prefHeightProperty().bind(viewPane.prefHeightProperty());
		barChart.setTitle("ヒストグラム");
		xAxis.setLabel("階級");
		yAxis.setLabel("人数");
		// viewPane.getChildren().add(barChart);
	}

	@FXML
	private void clearData() {
		dataArea.clear();
	}
	@FXML
	private void clearLog() {
		rightLog.clear();
	}
	@FXML
	private void execAction() {
		// rightLog をクリアする
		rightLog.clear();
		viewPane.getChildren().clear();
		dataSeries1.getData().clear();
		barChart.getData().clear();
		classArray.clear();
		
		makeLabel();
		makeData();
		barChart.setTitle("ヒストグラム："+fieldLabel);
		for(int i=0;i<freqCount.length;i++) {
			double d = classArray.get(i);
			dataSeries1.getData().add(new XYChart.Data<String,Number>(d+"-",freqCount[i]));
		}
		barChart.getData().add(dataSeries1);
		viewPane.getChildren().add(barChart);
	}

	private void makeData() {
		String dataStr = dataArea.getText();
		List<Double> dataList = new ArrayList<Double>();
		String[] dataStrRecord;
		if (dataStr != null) {
			dataStrRecord = dataStr.split("\n");
		} else {
			rightLog.appendText("no data set in dataArea\n");
			return;
		}
		//1行目をタイトルにいれるため。
		fieldLabel = dataStrRecord[0];
		//
		for (String s : dataStrRecord) {
			if (NumberUtils.isNumber(s)) {
				double d = Double.parseDouble(s);
				dataList.add(d);
			}
		}
		// 統計値
		rightLog.appendText("人数：" + dataList.size() + "\n");
		double average = ave(dataList);
		rightLog.appendText("平均：" + String.format("%.2f", average) + "\n");
		double stddev = stdDev(dataList);
		rightLog.appendText("標準偏差：" + String.format("%.2f", stddev) + "\n");
		// 階級別にカウント
		freqCount = new int[classArray.size()];
		for (int i = 0; i < freqCount.length; i++) {
			freqCount[i] = 0;
		}
		// 受験者一人一人の得点
		for (double d : dataList) {
			for (int i = 1; i < freqCount.length; i++) {
				double lower = classArray.get(i - 1);
				double upper = classArray.get(i);
				if (d >= lower && d < upper) {
					freqCount[i - 1]++;
				}
			}
			// classArray の最大値
			double upper = classArray.get(classArray.size() - 1);
			if (d >= upper) {
				freqCount[freqCount.length - 1]++;
			}
		}
		// 度数を表示してみる
		for (int i = 0; i < classArray.size(); i++) {
			rightLog.appendText(classArray.get(i) + "\t" + freqCount[i] + "\n");
		}
	}

	private double stdDev(List<Double> dataList) {
		double ave = ave(dataList);
		double sum = 0.0;
		for (double d : dataList) {
			sum += (d - ave) * (d - ave);
		}
		double var = sum / (double) dataList.size();
		double r = Math.sqrt(var);
		return r;
	}

	//
	private void makeLabel() {
		// 最小値・最大値・階級幅を設定
		double min = NumberUtils.toDouble(classMin.getText());
		double max = NumberUtils.toDouble(classMax.getText());
		double width = NumberUtils.toDouble(classWidth.getText());
		double limit = min;
		while (limit < max) {
			classArray.add(limit);
			limit += width;
		}
		// check
//		for(double d: classArray) {
//			rightLog.appendText(d+"\n");
//		}

	}

	private double ave(List<Double> dataList) {
		int size = dataList.size();
		double sum = 0.0;
		for (double d : dataList) {
			sum += d;
		}
		double a = sum / (double) size;
		return a;
	}

	//
	private void testDrawEllipse2D(Graphics2D g) {
		// 楕円を表現するEllipse2Dオブジェクトを生成する
		// 基本図形は他にLine2D(線分)，Rectangle2D(長方形)，Arc2D(弧)などもある
		Ellipse2D ellipse = new Ellipse2D.Double(50.0, 25.0, 50.0, 50.0);
		// Ellipseオブジェクトの内部の塗りつぶし色を設定する
		g.setColor(new Color(255, 75, 0));
		// Ellipseオブジェクトの内部を塗りつぶす
		g.fill(ellipse);
		// Ellipseオブジェクトの輪郭線の色を黒に設定する
		g.setColor(Color.BLACK);
		// Ellipseオブジェクトの輪郭線を太さ1の線に設定する
		g.setStroke(new BasicStroke(1f));
		g.draw(ellipse);
	}

}
