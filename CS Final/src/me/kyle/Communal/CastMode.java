package me.kyle.Communal;


public class CastMode {
	
	public static TransferMode clientToTransfer(ClientMode mode){
		if(mode.equals(ClientMode.GenerateNumbers)){
			return TransferMode.GenerateNumbers;
		} else if(mode.equals(ClientMode.ReturnData)){
			return TransferMode.ReturnData;
		} else if(mode.equals(ClientMode.Sleep)){
			return TransferMode.Sleep;
		} else {
			return null;
		}
	}
	
	public static ClientMode transferToClient(TransferMode mode){
		if(mode.equals(TransferMode.GenerateNumbers)){
			return ClientMode.GenerateNumbers;
		} else if(mode.equals(TransferMode.ReturnData)){
			return ClientMode.ReturnData;
		} else if(mode.equals(TransferMode.Sleep)){
			return ClientMode.Sleep;
		} else {
			return null;
		}
	}
}
