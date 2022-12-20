public class Planet{
    private static final double G = 6.67e-11;
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
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
	mass =  p.mass;
	imgFileName = p.imgFileName;
    }
    
    public double calcDistance(Planet p) {
	return Math.sqrt((this.xxPos - p.xxPos) * (this.xxPos - p.xxPos) + (this.yyPos - p.yyPos) * (this.yyPos - p.yyPos));
    }

    public double calcForceExertedBy(Planet p) {
       	double distance = calcDistance(p);
	return this.mass * p.mass * G / (distance * distance);
    }

    public double calcForceExertedByX(Planet p) {
	double F = calcForceExertedBy(p);
	double distance = calcDistance(p);
	return F * (p.xxPos - this.xxPos) / distance;
    }

    public double calcForceExertedByY(Planet p) {
	double F = calcForceExertedBy(p);
	double distance = calcDistance(p);
	return F * (p.yyPos - this.yyPos) / distance;
    }

    public double calcNetForceExertedByX(Planet[] ps) {
        double sum = 0;
	for (Planet p : ps) {
	    if (!this.equals(p)) {
		sum += calcForceExertedByX(p);
	    }
	 }
	return sum;
    }

    public double calcNetForceExertedByY(Planet[] ps) {
        double sum = 0;
	for (Planet p : ps) {
	    if (!this.equals(p)) {
	        sum += calcForceExertedByY(p);
	    }
	}
	return sum;
    }

    public void update(double dT, double fx, double fy) {
	double ax = fx / this.mass;
	double ay = fy / this.mass;
	this.xxVel += dT * ax;
	this.yyVel += dT * ay;
	this.xxPos += dT * this.xxVel;
	this.yyPos += dT * this.yyVel;
    }

    public void draw() {
	StdDraw.picture(this.xxPos, this.yyPos, this.imgFileName);
    }

}













