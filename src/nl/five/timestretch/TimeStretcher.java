package nl.five.timestretch;


public class TimeStretcher {

	private final float[] sourceBuffer;
	private long now = 0L;
	private float stretchFactor = 1.0F;
	private Grain currentGrain = null;
	private Grain fadingGrain = null;
	private final Granulator granulator;

	public TimeStretcher(float[] buffer, float sampleRate, float grainSizeSecs) {
		this.sourceBuffer = buffer;
		this.granulator = new Granulator(buffer, sampleRate, grainSizeSecs);
	}

	public void process(float[] signal) {
		if (currentGrain == null) {
			currentGrain = createGrain();
		}
		for(int i = 0; i < signal.length; i++) {
			if (currentGrain.isFading(now)) {
				fadingGrain = currentGrain;
				currentGrain = createGrain();
			}
			signal[i] = currentGrain.getSample(now);
			if (fadingGrain != null) {
				signal[i] += fadingGrain.getSample(now);
				if (!fadingGrain.hasMoreSamples(now)) {
					fadingGrain = null;
				}
			}
			now ++;
		}
	}

	/**
	 * Creates a grain that starts fading in right now.
	 * @return
	 */
	private Grain createGrain() {
		int startIndex = Math.round(now / stretchFactor) % sourceBuffer.length;
		return granulator.createGrain(startIndex, now);
	}

	public void setStretchFactor(float stretchFactor) {
		this.stretchFactor = stretchFactor;
	}

}
