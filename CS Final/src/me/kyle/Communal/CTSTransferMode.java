package me.kyle.Communal;

/**
 * The client to server mode
 */
public enum CTSTransferMode {
	Init(Integer.class),
	DataSet(int[].class),
	ModeUpdate(ClientMode.class),
	;
	
	Class<?> type;
	
	private CTSTransferMode(Class<?> type) {
		this.type = type;
	}
	
	/**
	 * @return The datatype bound to the mode
	 */
	public Class<?> GetType(){
		return type;
	}
}
