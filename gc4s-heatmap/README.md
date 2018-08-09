The `gc4s-heatmap` module
=================

This module provides the `JHeatMap` component to display data matrices as heat maps. By using this module, it is also possible to use the main `gc4s` components. This component also provides the `JHeatMap` component, a more advanced panel what wraps the `JHeatMap` to provide additional functionalities (i.e. color configuration, data manipulation, etc.).

The `gc4s-heatmap-demo` provides different examples of usage of these components with sample data. Javadoc documentation is available [here](http://sing-group.org/gc4s/javadoc/).

![JHeatMap](screenshots/JHeatMap.png)

Using this module
----------------------
Add the following repository and dependency declarations to your `pom.xml`:
```xml
	<repositories>
		<repository>
			<id>sing-repository</id>
			<name>SING repository</name>
			<url>http://maven.sing-group.org/repository/maven/</url>
		</repository>
	</repositories>
	
	<dependencies>
	    	<dependency>
			<groupId>org.sing_group</groupId>
			<artifactId>gc4s-heatmap</artifactId>
			<version>1.2.1</version>
		</dependency>
	</dependencies>
```

A simple example
----------------------
The following code (available at `org.sing_group.gc4s.visualization.heatmap.demo.MinimalJHeatMapDemo` of the demo module) shows the minimum code required to create a heatmap.
```java
import static java.lang.Double.NaN;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import org.sing_group.gc4s.visualization.heatmap.JHeatMap;

public class MinimalJHeatMapDemo {
	public static void main(String[] args) {
		double[][] data = {
			{1, 2, 3, 4, 5},
			{6, 7, 8, 9, NaN},
			{10, 11, 12, 13, 14},
		};
		String[] rowNames = { "R1", "R2", "R3" };
		String[] columnNames = { "C1", "C2", "C3", "C4", "C5" };
		
		JHeatMap heatmap = new JHeatMap(data, rowNames, columnNames);
		
		showComponent(heatmap, "JHeatMap demo");
	}
}
```