package org.sing_group.gc4s.genomebrowser;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * A {@code FileFilter} implementation to obtain the supported files.
 *  
 * @author hlfernandez
 *
 */
public class GPFilter extends FileFilter {
	
    public boolean accept(File f) {
        return f.isDirectory() 
        ||f.getName().toLowerCase().endsWith(".bed")
        || f.getName().toLowerCase().endsWith(".bed.bgz")
        
        || f.getName().toLowerCase().endsWith(".gff")
        || f.getName().toLowerCase().endsWith(".gff.bgz")
        
        || f.getName().toLowerCase().endsWith(".pileup")
        || f.getName().toLowerCase().endsWith(".pileup.bgz")
        
        || f.getName().toLowerCase().endsWith(".vcf")
        || f.getName().toLowerCase().endsWith(".vcf.bgz")
        
        || f.getName().toLowerCase().endsWith(".bam")
        
        || f.getName().toLowerCase().endsWith(".gpfile")
        || f.getName().toLowerCase().endsWith(".gpfile.bgz");
    }
    
	public String getDescription() {
		return "GP files";
	}
}