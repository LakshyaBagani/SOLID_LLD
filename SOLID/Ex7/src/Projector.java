public class Projector implements Powerable, InputConnectable {
    private boolean on;

    public void powerOn() { on = true; }
    public void powerOff() { on = false; System.out.println("Projector OFF"); }
    public void connectInput(String port) { if (on) System.out.println("Projector ON (" + port + ")"); }
}
