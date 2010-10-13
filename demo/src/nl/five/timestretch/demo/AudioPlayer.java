package nl.five.timestretch.demo;

import java.io.InputStream;

public interface AudioPlayer {
	void loadFile(InputStream fileStream);
	void loadFile(float[] samples);
	void play();
	void stop();
	void setStretchFactor(double stretchFactor);
	void destroy();
}
