public class NBody {
    public static String imgBackground = "images/starfield.jpg";

    public static double readRadius(String FileName) {
        In in = new In(FileName);
        int number = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Body[] readBodies(String FileName) {
        In in = new In(FileName);
        int number = in.readInt();
        double radius = in.readDouble();
        Body[] bodyArray = new Body[number];
        for(int i = 0; i < number; i += 1) {
            bodyArray[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return bodyArray;
    }   

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dT = Double.parseDouble(args[1]);
        String FileName = args[2];
        double radius = readRadius(FileName);
        Body[] bodies = readBodies(FileName);
        int size = bodies.length; 

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius,radius);
        StdDraw.clear();
        

        for (double presentTime = 0; presentTime < T; presentTime += dT) {
            Double[] xForces = new Double[size];
            Double[] yForces = new Double[size];

            for (int i = 0; i < size; i += 1) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }

            for (int i = 0; i < size; i += 1) {
                bodies[i].update(dT, xForces[i], yForces[i]);
            }
            StdDraw.picture(0, 0, imgBackground);
            for (Body body:bodies) {
                body.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
            bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
        }
    }
}