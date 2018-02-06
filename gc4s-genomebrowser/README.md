The `gc4s-genomebrowser` module
===============================

This module provides the `GenomeBrowser` component to display an interactive genome browser that supports different track files (bed, bam, vcf, gff and pileup). By using this module, it is also possible to use the main `gc4s` components.

The `gc4s-genomebrowser-demo` provides an example of usage of this component with sample data. Javadoc documentation is available [here](http://sing-group.org/gc4s/javadoc/genomebrowser).

![GenomeBrowser](screenshots/GenomeBrowser.png)

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
			<artifactId>gc4s-genomebrowser</artifactId>
			<version>1.0.0</version>
		</dependency>
	</dependencies>
```