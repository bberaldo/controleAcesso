package APS;

import java.util.TimerTask;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class TimerScheduleHandler extends TimerTask implements SerialPortDataListener {

	private final long timeStart;
	
	//contructor
	public TimerScheduleHandler(long timeStart) {
		this.timeStart = timeStart;
	}
	
	//Override run method in TimerTask
	@Override
	public void run() {
		System.out.println("Time elapsed: "  + (System.currentTimeMillis() - this.timeStart) + "milliseconds");
	}
	
	@Override
	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
	}
	
	@Override
	public void serialEvent(SerialPortEvent serialPortEvent) {
		System.out.println("Entrou aqui");
		if(serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
			System.out.println("Yes! The arduino is alive!");
		}
	}
}
