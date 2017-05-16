GC4S [![Build Status](https://travis-ci.org/hlfernandez/GC4S.svg?branch=master)](https://travis-ci.org/hlfernandez/GC4S) [![license](https://img.shields.io/badge/LICENSE-GPLv3-blue.svg)]()
========================

A collection of GUI Components for (Java) Swing.

Projects using GC4S
-------------------
- [S2P](http://sing-group.org/s2p/): an open-source application for processing of 2D-gel and MALDI-based mass spectrometry protein data. a. The graphical user interface has been created using GC4S.

Using GC4S
----------
Clone this project and install it using `mvn install` or add the following repository and dependency declarations to your `pom.xml`:
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
			<artifactId>gc4s</artifactId>
			<version>0.7.0</version>
		</dependency>
	</dependencies>
```

Examples
--------

The following image shows `JHeatMap`, a component that shows a heatmap from a given double matrix. It allows zooming, changing gradient's colors and exporting the heatmap as image.

![JHeatMap](screenshots/JHeatMap.gif)

The following image shows `JFileChooserPanel`, a component with a button to select a file (using a `JFileChooser`) and a text field that show the selected file. You have full control of how the underlying `JFileChooser` since you have can set file filters or choose the dialog mode.

![JFileChooserPanel](screenshots/JFileChooserPanel.png)

These are just two examples of GC4S components. To see more, please, see the [examples](EXAMPLES.md) section.
