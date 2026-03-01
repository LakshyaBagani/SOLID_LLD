// Smell: original fat interface.
public interface SmartClassroomDevice {
    void powerOn();
    void powerOff();
    void setBrightness(int pct);
    void setTemperatureC(int c);
    int scanAttendance();
    void connectInput(String port);
}
