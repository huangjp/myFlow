package entity.vo.database;

public class Select<T> {
	
	private selector selector; 
	private String sortFiled;
	private int order;//0-降；1-升
	public selector getSelector() {
		return selector;
	}
	public void setSelector(selector selector) {
		this.selector = selector;
	}
	public String getSortFiled() {
		return sortFiled;
	}
	public void setSortFiled(String sortFiled) {
		this.sortFiled = sortFiled;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}

class selector {
	private String field;
	private Object value;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
