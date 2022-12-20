public class NBody{
    public static double readRadius(String img) {
	In cin = new In(img);
	cin.readInt();
	return cin.readDouble();
    }

    public static Planet[] readPlanets(String img) {
	In cin = new In(img);
	int n = cin.readInt();
	cin.readDouble();
	Planet[] ps = new Planet[n];
	for (int i = 0; i < n; i++) {
	    ps[i] = new Planet(cin.readDouble(), cin.readDouble(), cin.readDouble(), cin.readDouble(), cin.readDouble(), cin.readString());
	}
	return ps;
    }

    public static void main(String[] args) {
	double T = Double.parseDouble(args[0]);
	double dt = Double.parseDouble(args[1]);
	String filename = args[2];
	double radius = readRadius(filename);	
	Planet[] planets = readPlanets(filename);
	StdDraw.setScale(-radius,radius);
	    StdDraw.enableDoubleBuffering();
	for (double time = 0; time <= T; time += dt) {
	    int size = planets.length;
	    double[] xForces = new double[size];
	    double[] yForces = new double[size];
	    for (int i = 0; i < size; i++) {
		xForces[i] = planets[i].calcNetForceExertedByX(planets);
		yForces[i] = planets[i].calcNetForceExertedByY(planets);
	    }
	    for (int i = 0; i < size; i++) {
		planets[i].update(dt, xForces[i], yForces[i]);
	    }
	    StdDraw.picture(0, 0, "C:\\Rjdata\\selfStudy\\CS61B-2018Spring\\proj0\\images\\starfield.jpg");
	    for (int i = 0; i < size; i++) {
		planets[i].draw();
	    }
	    
	    StdDraw.show();

	    StdDraw.pause(5);
	}
	StdOut.printf("%d\n", planets.length);
	StdOut.printf("%.2e\n", radius);
	for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
	}
    }
}
