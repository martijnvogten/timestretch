
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.sound.sampled.UnsupportedAudioFileException;

import nl.five.timestretch.compact.TimeStretcherCompact;

import com.cycling74.msp.AudioFileBuffer;
import com.cycling74.msp.MSPObject;
import com.cycling74.msp.MSPSignal;

public class MaxPlayer extends MSPObject {
	
	private TimeStretcherCompact timeStretcher;

	public MaxPlayer() {
		try {
			AudioFileBuffer fileBuffer = new AudioFileBuffer("sample.wav");
			float[] sourceBuffer = fileBuffer.buf[0];
			timeStretcher = new TimeStretcherCompact(sourceBuffer, fileBuffer.getSampleRate(), 0.08F);
			timeStretcher.play();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		declareInlets(new int[] {});
		declareOutlets(new int[] {MSPObject.SIGNAL});
	}
	
	public void bang() {
		outlet(0, "Congratulations!");
	}

	@Override
	public Method dsp(MSPSignal[] arg0, MSPSignal[] arg1) {
		return getPerformMethod("doit");
	}
	
	public void doit(MSPSignal[] ins, MSPSignal[] outs) {
		timeStretcher.process(outs[0].vec);
	}
	

}
