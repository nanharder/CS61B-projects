public class Body {
    public static double G = 6.67e-11;
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Body(double xP,double yP,double xV,double yV,double m,String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double dxPos = xxPos - b.xxPos;
        double dyPos = yyPos - b.yyPos;
        double distance = Math.sqrt(dxPos * dxPos + dyPos * dyPos);
        return distance;
    }

    public double calcForceExertedBy(Body b) {
        double distance = calcDistance(b);
        double force;
        force = G * mass * b.mass / (distance * distance);
        return force;
    }

    public double calcForceExertedByX(Body b) {
        double force = calcForceExertedBy(b);
        double distance = calcDistance(b);
        double dxPos = b.xxPos - xxPos;
        double xForce = force * dxPos / distance;
        return xForce;
    }

    public double calcForceExertedByY(Body b) {
        double force = calcForceExertedBy(b);
        double distance = calcDistance(b);
        double dyPos = b.yyPos - yyPos;
        double yForce = force * dyPos / distance;
        return yForce;
    }

    public double calcNetForceExertedByX(Body[] bLst) {
        double netForceX = 0;
        for (Body b : bLst) {
            if (this.equals(b)) {
                continue;
            }
            netForceX += calcForceExertedByX(b);
        }
        return netForceX;

    }

    public double calcNetForceExertedByY(Body[] bLst) {
        double netForceY = 0;
        for (Body b : bLst) {
            if (this.equals(b)) {
                continue;
            }
            netForceY += calcForceExertedByY(b);
        }
        return netForceY;

    }

    public void update(double unitTime,double xForce,double yForce) {
        double xAcceleration = xForce / mass;
        double yAcceleration = yForce / mass;
        xxVel += unitTime * xAcceleration;
        yyVel += unitTime * yAcceleration;
        xxPos += unitTime * xxVel;
        yyPos += unitTime * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}