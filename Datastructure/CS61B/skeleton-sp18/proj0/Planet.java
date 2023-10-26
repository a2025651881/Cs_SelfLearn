public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    double mass;
    String imgFileName;
    static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV,
            double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet x) {
        double Distance;
        double Distance_x = x.xxPos - this.xxPos;
        double Distance_y = x.yyPos - this.yyPos;
        Distance = Math.sqrt(Math.pow(Distance_x, 2) + Math.pow(Distance_y, 2));
        return Distance;
    }

    public double calcForceExertedBy(Planet x) {
        double Force;
        double Distance = this.calcDistance(x);
        Force = this.mass * x.mass * G / Math.pow(Distance, 2);
        return Force;
    }

    public double calcForceExertedByX(Planet x) {
        double ForceX;
        ForceX = this.calcForceExertedBy(x) * (x.xxPos - this.xxPos) / this.calcDistance(x);
        return ForceX;
    }

    public double calcForceExertedByY(Planet x) {
        double ForceY;
        ForceY = this.calcForceExertedBy(x) * (x.yyPos - this.yyPos) / this.calcDistance(x);
        return ForceY;
    }

    public double calcNetForceExertedByX(Planet[] x) {
        int count = x.length;
        double NetForceByX = 0;
        while (count != 0) {
            if (!this.equals(x[count - 1])) {
                NetForceByX += this.calcForceExertedByX(x[count - 1]);
            }
            count -= 1;
        }
        return NetForceByX;
    }

    public double calcNetForceExertedByY(Planet[] x) {
        int count = x.length;
        double NetForceByY = 0;
        while (count != 0) {
            if (!this.equals(x[count - 1])) {
                NetForceByY += this.calcForceExertedByY(x[count - 1]);
            }
            count -= 1;
        }
        return NetForceByY;
    }

    public void update(double dt, double fX, double fY, Planet[] planets) {
        double aX, aY;
        aX = fX / this.mass;
        aY = fY / this.mass;

        this.xxVel += dt * aX;
        this.yyVel += dt * aY;

        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "./images/" + this.imgFileName);
    }

}