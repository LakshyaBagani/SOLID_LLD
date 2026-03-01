public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

    public void startClass() {
        Powerable pj = reg.getFirst(Powerable.class);
        pj.powerOn();
        reg.getFirst(InputConnectable.class).connectInput("HDMI-1");

        reg.getFirst(BrightnessControllable.class).setBrightness(60);
        reg.getFirst(TemperatureControllable.class).setTemperatureC(24);
        System.out.println("Attendance scanned: present=" + reg.getFirst(Scannable.class).scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        for (Powerable p : reg.getAll(Powerable.class)) {
            p.powerOff();
        }
    }
}
