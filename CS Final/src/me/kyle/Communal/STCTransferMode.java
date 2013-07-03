package me.kyle.Communal;

/**
 * The server to client mode
 */
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
	
	/**
	 * @return The datatype bound to the mode
	 */
	public Class<?> getType(){
		return type;
	}
	
	/**
	 * @return True if there is a datatype attached to the mode
	 */
	public boolean isClassrequired() {
		return classrequired;
	}

}
