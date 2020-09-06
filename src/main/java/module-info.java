module com.genSci.tools.FreqGraph {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires org.jfree.fxgraphics2d;
	requires java.desktop;
	requires commons.lang3;

    opens com.genSci.tools.FreqGraph to javafx.fxml;
    exports com.genSci.tools.FreqGraph;
}