package org.sing_group.gc4s.demo;

import static org.sing_group.gc4s.demo.DemoUtils.createPanelAndCenterComponent;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;
import static java.util.Arrays.asList;

import java.util.List;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;
import org.sing_group.gc4s.visualization.table.BeanTableModel;

/**
 * An example showing the use of {@link BeanTableModel}.
 * 
 * @author hlfernandez
 *
 */
public class BeanTableModelDemo {

	public static class Bean {

		private String name;
		private int value;

		public Bean(String name, int value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}
	
	public static void main(String[] args) {
		List<Bean> beans = asList(new Bean("Row 1", 1), new Bean("Row 2", 2));
		JXTable table = new JXTable(new BeanTableModel<Bean>(beans));
		showComponent(createPanelAndCenterComponent(new JScrollPane(table)));
	}
}
