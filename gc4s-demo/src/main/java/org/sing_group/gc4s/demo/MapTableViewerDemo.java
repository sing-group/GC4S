/*
 * #%L
 * GC4S components demo
 * %%
 * Copyright (C) 2014 - 2020 Hugo López-Fernández, Daniel Glez-Peña, Miguel Reboiro-Jato,
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
package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.visualization.VisualizationUtils.createPanelAndCenterComponent;
import static org.sing_group.gc4s.visualization.VisualizationUtils.showComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import javax.swing.JScrollPane;

import org.sing_group.gc4s.visualization.table.MapTableViewer;

/**
 * An example showing the use of {@link MapTableViewer}.
 * 
 * @author hlfernandez
 *
 */
public class MapTableViewerDemo {

  public static void main(String[] args) {
    showComponent(
      createPanelAndCenterComponent(new JScrollPane(new MapTableViewer<>(getMap()))),
      "MapTableViewer demo"
    );
  }

  private static Map<String, String> getMap() {
    Map<String, String> map = new HashMap<>();
    IntStream.rangeClosed(65, 90).forEach(n -> map.put(String.valueOf(n), String.valueOf((char) n)));

    return map;
  }
}
