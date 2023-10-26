public class PlanetExtreme extends Planet {

    public PlanetExtreme(double xP, double yP, double xV,
            double yV, double m, String img) {
        super(xP, yP, xV, yV, m, img);
    }

    public PlanetExtreme(Planet p) {
        super(p);
    }

    public void elasticCollision(Planet[] planets) {
        int i = 0, j = 0;
        while (i != planets.length) {
            j = 0;
            while (j != planets.length) {
                if (planets[i].equals(planets[j])) {
                    if (planets[i].calcDistance(planets[j]) < 10) {
                        planets[i].xxVel = (planets[i].mass - planets[j].mass) / (planets[i].mass + planets[j].mass)
                                * planets[i].xxVel;
                        planets[i].yyVel = (planets[i].mass - planets[j].mass) / (planets[i].mass + planets[j].mass)
                                * planets[i].yyVel;
                        planets[j].xxVel = 2 * planets[i].mass / (planets[i].mass + planets[j].mass)
                                * planets[j].xxVel;
                        planets[j].yyVel = 2 * planets[i].mass / (planets[i].mass + planets[j].mass)
                                * planets[j].yyVel;
                    }
                }
                j += 1;
            }
            i += 1;
        }
    }
}
