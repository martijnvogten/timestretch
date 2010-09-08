package nl.five.timestretch.demo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public class TimeStretchDemo {

	public static void main(String[] args) throws IOException {
		
		final AudioPlayer player = new JavaSoundAudioPlayer();
		
		File f = new File("demo/Beats example - Amen loop.wav");
		player.loadFile(f.getCanonicalPath());
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Basic Timestretch Demo");
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						player.destroy();
						System.exit(0);
					}
				});
				TimeStretchGui gui = new TimeStretchGui(player);
				frame.setResizable(false);
				frame.setLayout(null);
				frame.add(gui);
				frame.setSize(200, 360);
				frame.setVisible(true);
			}
		});
	}

}
