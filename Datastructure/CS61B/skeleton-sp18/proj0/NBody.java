import java.io.File;
import java.io.IOException;

public class NBody {
    public static String backgroundImg = "./images/starfield.jpg";
    public static double min = 1e15;

    public static double readRadius(String x) {
        In in = new In(x);
        int firstItemInFile = in.readInt();
        double secondItemInFile = in.readDouble();
        return secondItemInFile;
    }

    public static Planet[] readPlanets(String x) {
        In in = new In(x);
        int firstItemInFile = in.readInt();
        double secondItemInFile = in.readDouble();
        Planet[] planets = new Planet[firstItemInFile];
        for (int i = 0; i != firstItemInFile; i += 1) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);
        }
        return planets;
    }

    public static double[] getPlanetsRadius(Planet[] planets) {
        double[] planetsRadius = new double[planets.length];
        int n = 0;
        for (Planet i : planets) {
            File f1 = new File("./images/" + i.imgFileName);
            FastImageInfo imageInfo;
            try {
                imageInfo = new FastImageInfo(f1);
                planetsRadius[n] = imageInfo.getWidth() / 2;
            } catch (IOException e) {
                e.printStackTrace();
            }
            n += 1;
        }
        return planetsRadius;
    }

    public static void elasticCollision(Planet[] planets, double Radius) {
        int i = 0, j = 0;
        double[] planetsRadius = getPlanetsRadius(planets);
        for (double temp : planetsRadius) {
            System.out.println(temp);
        }
        while (i != planets.length) {
            j = i;
            while (j != planets.length) {
                if (!planets[i].equals(planets[j])) {
                    if (planets[i].calcDistance(
                            planets[j]) < (planetsRadius[i] * Radius / 256 + planetsRadius[j] * Radius / 256)) {
                        System.out.println(planets[i].calcDistance(planets[j]));
                        System.out.println(planetsRadius[i] * Radius / 256 + planetsRadius[j] * Radius / 256);
                        planets[i].xxVel = (planets[i].mass - planets[j].mass) / (planets[i].mass +
                                planets[j].mass) * planets[i].xxVel;
                        planets[i].yyVel = (planets[i].mass - planets[j].mass) / (planets[i].mass +
                                planets[j].mass) * planets[i].yyVel;
                        planets[j].xxVel = 2 * planets[i].mass / (planets[i].mass + planets[j].mass)
                                * planets[j].xxVel;
                        planets[j].yyVel = 2 * planets[i].mass / (planets[i].mass + planets[j].mass)
                                * planets[j].yyVel;
                    }
                    if (planets[i].calcDistance(planets[j]) < min) {
                        min = planets[i].calcDistance(planets[j]);
                    }
                }
                j += 1;
            }
            if (planets[i].xxPos < -Radius || planets[i].xxPos > Radius) {
                planets[i].xxVel = -planets[i].xxVel;
            } else if (planets[i].yyPos < -Radius || planets[i].yyPos > Radius) {
                planets[i].yyVel = -planets[i].yyVel;
            }
            i += 1;
        }
    }

    public static void main(String[] arg) {
        StdDraw.enableDoubleBuffering();
        double T = Double.parseDouble(arg[0]);
        double dt = Double.parseDouble(arg[1]);
        String filename = arg[2];
        Planet[] planets = readPlanets(filename);
        double Radius = readRadius(filename);

        double currentT = 0;
        while (currentT < T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            elasticCollision(planets, Radius);
            for (int j = 0; j != planets.length; j += 1) {
                xForces[j] = planets[j].calcNetForceExertedByX(planets);
                yForces[j] = planets[j].calcNetForceExertedByY(planets);
                planets[j].update(dt, xForces[j], yForces[j], planets);
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
        System.out.println(min);
    }

}