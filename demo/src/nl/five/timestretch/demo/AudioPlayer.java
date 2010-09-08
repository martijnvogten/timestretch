package nl.five.timestretch.demo;

public interface AudioPlayer {
	void loadFile(String fileName);
	void play();
	void stop();
	void setStretchFactor(double stretchFactor);
	void destroy();
}
