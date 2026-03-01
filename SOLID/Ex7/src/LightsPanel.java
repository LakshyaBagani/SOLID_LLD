public class LightsPanel implements Powerable, BrightnessControllable {
    public void powerOn() { }
    public void powerOff() { System.out.println("Lights OFF"); }
    public void setBrightness(int pct) { System.out.println("Lights set to " + pct + "%"); }
}
