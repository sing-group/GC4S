The `gc4s-multiple-sequence-alignment-viewer` module
=================================

This module provides the `MultipleSequenceAlignmentViewerPanel` component to display multiple sequence alignments. By using this module, it is also possible to use the main `gc4s` components. This module also provides the `MultipleSequenceAlignmentViewerControl` that adds some functionalities to the panel.

The `gc4s-multiple-sequence-alignment-viewer-demo` provides different examples of usage of these components with sample data. Javadoc documentation is available [here](http://sing-group.org/gc4s/javadoc/).

![MultipleSequenceAlignmentViewerPanel](screenshots/MultipleSequenceAlignmentViewerPanel.png)

Using this module
-----------------
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
			<artifactId>gc4s-multiple-sequence-alignment-viewer</artifactId>
			<version>1.0.0</version>
		</dependency>
	</dependencies>
```