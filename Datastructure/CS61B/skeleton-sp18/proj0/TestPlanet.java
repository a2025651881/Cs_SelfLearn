/** Using for test **/
public class TestPlanet {
    public static void main(String[] arg) {
        Planet Samh, Aegir, Rocinante;
        Samh = new Planet(1, 0, 0, 0, 10, "star,gif");
        Aegir = new Planet(3, 3, 0, 0, 5, "sun.gif");
        Rocinante = new Planet(5, -3, 0, 0, 50, "venus.gif");
        Planet[] planets = new Planet[] { Samh, Aegir, Rocinante };
        System.out.println(Samh.calcNetForceExertedByX(planets));
        System.out.println(Samh.calcNetForceExertedByY(planets));
    }
}
