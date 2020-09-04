package com.genSci.tools.FreqGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import org.jfree.fx.FXGraphics2D;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    TextArea log; //bottom の Log 
    @FXML
    TextArea rihtLog; //right の Log.統計値を出力
    @FXML
    TextArea dataArea; //left のデータエリア
    @FXML
    Canvas canvas; //center の度数分布エリア
    @FXML
    TextField classWidth; //階級幅
    @FXML
    private void quitAction() {
    	System.exit(0);
    }
    @FXML
    private void execAction() {
    	 GraphicsContext ctx = canvas.getGraphicsContext2D();
         Graphics2D g = new FXGraphics2D(ctx);
         //
         //testDrawEllipse2D(g);
         Line2D line2d = new Line2D.Double(0, 0, 100, 100);
         g.setStroke(new BasicStroke(1f));
         g.draw(line2d);
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
