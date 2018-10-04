The `gc4s` module
===========

This module contains the general-purpose collection of GUI Components for (Java) Swing. Javadoc documentation is available [here](http://sing-group.org/gc4s/javadoc). This [gallery](GALLERY.md) shows screenshots of different elements of this module.

Using this module
-----------------------
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
			<artifactId>gc4s</artifactId>
			<version>1.3.0</version>
		</dependency>
	</dependencies>
```

Examples of GUI components
-------------------------------------

The following image shows `CloseableJTabbedPane`, an extension of `JTabbedPane` that adds a close button to tabs.

![CloseableJTabbedPane](screenshots/CloseableJTabbedPane.gif)

The following image shows `JFileChooserPanel`, a component with a button to select a file (using a `JFileChooser`) and a text field that show the selected file. You have full control of how the underlying `JFileChooser` since you have can set file filters or choose the dialog mode.

![JFileChooserPanel](screenshots/JFileChooserPanel.png)

These are just two examples of GC4S components. To see more examples, please, see the [gallery](GALLERY.md) section. Also, the `gc4s-demo` module provides examples of usage of them.