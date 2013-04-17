package me.kyle.Communal;

public enum STCTransferMode {
	Init,
	ModeChange(ClientMode.class),
	SendMoreData,
	Exit,
	;
	
	Class<?> type;
	boolean classrequired;

	private STCTransferMode() {
		this.type = null;
		classrequired = false;
	}
	
	private STCTransferMode(Class<?> type) {
		this.type = type;
		classrequired = true;
	}
	
	public Class<?> getType(){
		return type;
	}
	
	public boolean isClassrequired() {
		return classrequired;
	}

}
