GC4S [![Build Status](https://travis-ci.org/sing-group/GC4S.svg?branch=master)](https://travis-ci.org/hlfernandez/GC4S) [![license](https://img.shields.io/badge/LICENSE-LGPLv3-blue.svg)]()
========================

GC4S is an open-source library that provides a bioinformatics-oriented collection of GUI Components for (Java) Swing.

Projects using GC4S
--------------------------
- [S2P](http://sing-group.org/s2p/): an open-source application for processing of 2D-gel and MALDI-based mass spectrometry protein data.
- [DEWE](http://sing-group.org/dewe/): an open-source application for executing differential expression analysis in RNA-Seq data.
- [SEDA](http://sing-group.org/seda/): an open-source application for processing FASTA files containing DNA and protein sequences.

GC4S modules
-------------------

This repository contains the following `GC4S` modules:
- `gc4s`: the main module containing the components library.
- `gc4s-genomebrowser`: a module that depends on the `gc4s` module to provide an interactive genome browser.
- `gc4s-heatmap`: a module that depends on the `gc4s` module to provide an interactive heat map visualization component.
- `gc4s-jsparklines-factory`: a module that eases the creation of [JSparklines](https://github.com/compomics/jsparklines) renderers.
- `gc4s-multiple-sequence-alignment-viewer`: a module that depends on the `gc4s` module to provide a multiple sequence alignments viewer.
- `gc4s-statistics-tests-table`: a module that depends on the `gc4s` module to provide a statistical tests table.

Additionally, there is a `*-demo` module for each one of them containing examples of the usage of the components included in them.

Using GC4S
---------------
Add the following repository and dependency declarations to your `pom.xml` in order to use the `gc4s` module:
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
			<version>1.2.1</version>
		</dependency>
	</dependencies>
```

Examples of GUI Components
--------------------------------------

The following image shows the `JHeatMap` and `JHeatMapPanel` components. The `JHeatMapPanel` component uses a `JHeatMap` to display a heatmap from a given double matrix along with controls to allow zooming, changing gradient's colors and exporting the heatmap as image, among other functions.

![JHeatMap](gc4s/screenshots/JHeatMap.png)

The following image shows `JFileChooserPanel`, a component with a button to select a file (using a `JFileChooser`) and a text field that show the selected file. You have full control of how the underlying `JFileChooser` since you have can set file filters or choose the dialog mode.

![JFileChooserPanel](gc4s/screenshots/JFileChooserPanel.png)

These are just two examples of GC4S components. To see more, please, see the [gallery](gc4s/GALLERY.md) section.
