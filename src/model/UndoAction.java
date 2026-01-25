package model;

public class UndoAction {
    public enum Type {
        ADD_ORDER, ADD_LOCATION, ASSIGN_ORDER
    }

    private Type type;
    private Object data;

    public UndoAction(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
