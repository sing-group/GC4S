/*
 * #%L
 * GC4S genome browser
 * %%
 * Copyright (C) 2014 - 2018 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato, 
 * 			Florentino Fdez-Riverola, Rosalía Laza-Fidalgo, Reyes Pavón-Rial
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
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