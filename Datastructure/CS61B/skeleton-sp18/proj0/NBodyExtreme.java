public class NBodyExtreme extends NBody {
    public static void main(String[] arg) {
        StdDraw.enableDoubleBuffering();
        double T = Double.parseDouble(arg[0]);
        double dt = Double.parseDouble(arg[1]);
        String filename = arg[2];
        double Radius = readRadius(filename);

        double currentT = 0;
        while (currentT < T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            for (int j = 0; j != planets.length; j += 1) {
                xForces[j] = planets[j].calcNetForceExertedByX(planets);
                yForces[j] = planets[j].calcNetForceExertedByY(planets);
                planets[j].update(dt, xForces[j], yForces[j]);
            }
            StdDraw.setScale(-Radius, Radius);
            StdDraw.picture(0, 0, backgroundImg, Radius * 2, Radius * 2);

            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            currentT += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", Radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
