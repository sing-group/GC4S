The `gc4s-genomebrowser` module
=====================

This module provides the `GenomeBrowser` component to display an interactive genome browser that supports different track files (bed, bam, vcf, gff and pileup). By using this module, it is also possible to use the main `gc4s` components.

The `gc4s-genomebrowser-demo` provides an example of usage of this component with sample data. Javadoc documentation is available [here](http://sing-group.org/gc4s/javadoc/genomebrowser).

![GenomeBrowser](screenshots/GenomeBrowser.png)

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
			<artifactId>gc4s-genomebrowser</artifactId>
			<version>1.2.0</version>
		</dependency>
	</dependencies>
```

A simple example
-----------------------
The following code shows the minimum code required to create a GenomeBrowser with two tracks. For a working example, look at the `GenomeBrowserDemo` class of the package `org.sing_group.gc4s.genomebrowser.demo` from the demo module.

```java
// Definition of the genome and genome index files.
File genomeFile = new File("/data/hg18.fa");
File genomeIndexFile = new File("/data/hg18.fa.fai");

// Construction of the genome index required by the GenomeBrowser
// component to work. 
GenomeIndexBuilder.buildGenome(genomeFile, genomeIndexFile, null);
GenomeIndex genomeIndex = new PileLineGenomeIndex(genomeIndexFile);

// Instantiation of the GenomeBrowser, which only requires the genome
// index file.
GenomeBrowser genomeBrowser = new GenomeBrowser(genomeIndex);

// Establishment of the sequence and positions to be shown. They
// can also be established manually by the user trough the GUI
// component.
genomeBrowser.setSequence("22");
genomeBrowser.seekPositions(14880570, 18748443);

// Addition of file tracks to the GenomeBrowser programmatically.
genomeBrowser.addTrack(new File("/data/track.bed"));
genomeBrowser.addTrack(new File("/data/track.bam"));
```