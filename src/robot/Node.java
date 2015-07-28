package robot;

public class Node {

	private Node parent;
	private Node[] children;
	private Component component;

	public Node(Node[] children, Component component) {
		if (children == null) {
			throw new RuntimeException("");
		}
		this.component = component;
		this.children = children;
		for (Node child : children) {
			child.setParent(this);
		}
	}

	public Node[] getChildren() {
		return children;
	}

	public void setChildren(Node[] children) {
		this.children = children;
	}
	
	public void replaceChild(Node old, Node newChild) {
		for (int i = 0; i < children.length; i++) {
			if (children[i].equals(old)) {
				children[i] = newChild;
			}
		}
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public String composite() {
		String representation = component.toString();
		if (!isLeaf()) {
			representation += "(";
			for (int i = 0; i < children.length; i++) {
				representation += children[i].composite();
				if (i != children.length - 1) {
					representation += ", ";
				}
			}
			representation += ")";
		}
		return representation;
	}

	public boolean isLeaf() {
		return children.length == 0;
	}

	@Override
	public String toString() {
		return composite();
	}
}
