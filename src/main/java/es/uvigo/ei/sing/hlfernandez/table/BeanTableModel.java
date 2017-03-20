package es.uvigo.ei.sing.hlfernandez.table;

import static java.beans.Introspector.getBeanInfo;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

/**
 * A table model for easily displaying a list of Java beans in a table, using
 * one row for each bean and one column for each bean's property.
 * 
 * @author hlfernandez
 *
 * @param <T> the type of elements (beans) in this model
 */
public class BeanTableModel<T> extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private List<T> beans;
	private Map<T, Map<String, Object>> propertiesMap;

	private LinkedList<String> columns;

	/**
	 * Creates a new {@code BeanTableModel} instance using the list of beans.
	 * 
	 * @param beans the list of beans.
	 */
	public BeanTableModel(List<T> beans) {
		this.beans = beans;
		
		this.initData();
	}

	protected void initData() {
		propertiesMap = beans.stream()
			.collect(toMap(o -> o, o -> beanProperties(o)));
		columns = new LinkedList<String>(
			propertiesMap.get(beans.get(0)).keySet()
		);
	}

	@Override
	public int getRowCount() {
		return beans.size();
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns.get(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return propertiesMap.get(beans.get(rowIndex)).get(columns.get(columnIndex));
	}

	protected static Map<String, Object> beanProperties(Object bean) {
		try {
			return 	getPropertyDescriptors(bean).stream()
					.filter(pd -> nonNull(pd.getReadMethod()))
					.collect(
						toMap(
							PropertyDescriptor::getName, 
							pd -> {
								try {
										return pd.getReadMethod().invoke(bean);
								} catch (Exception e) {
									return null;
								}
							}
						)
					);
		} catch (IntrospectionException e) {
			return emptyMap();
		}
	}

	protected static Collection<PropertyDescriptor> getPropertyDescriptors(
		Object bean) throws IntrospectionException 
	{
		return asList(
			getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors()
		);
	}
}
