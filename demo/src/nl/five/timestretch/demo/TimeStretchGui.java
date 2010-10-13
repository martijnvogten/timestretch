package nl.five.timestretch.demo;

import java.awt.Button;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TimeStretchGui extends JPanel {

	private final AudioPlayer player;
	private final JSlider tempoSlider;

	public TimeStretchGui(final AudioPlayer player) {
		this.player = player;
		setLayout(null);
		setSize(200, 340);
//		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		Button playButton = new Button("Play");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play();
			}
		});
		
		Button stopButton = new Button("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		

//		Button openFileButton = new Button("Open...");
//		openFileButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				showFileDialog();
//			}
//		});

		// Create the label.
		JLabel sliderLabel = new JLabel("Tempo", JLabel.CENTER);
		sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create the slider.
		tempoSlider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
		tempoSlider.setMajorTickSpacing(10);
		tempoSlider.setMinorTickSpacing(1);
		tempoSlider.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		tempoSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateStretchFactor(1.0F - (tempoSlider.getValue() - 50) / 100.0);
			}
		});

		// Put everything together.
//		add(openFileButton);
//		openFileButton.setBounds(0, 0, 200, 40);
		add(sliderLabel);
		sliderLabel.setBounds(0, 40, 200, 20);
		add(tempoSlider);
		tempoSlider.setBounds(0, 60, 200, 200);
		add(playButton);
		playButton.setBounds(0, 260, 200, 40);
		add(stopButton);
		stopButton.setBounds(0, 300, 200, 40);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	private void updateStretchFactor(double value) {
		player.setStretchFactor(value);
	}

	@SuppressWarnings("unused")
	private void showFileDialog() {
		Frame frame = (Frame) TimeStretchGui.this.getTopLevelAncestor();
		FileDialog dialog = new FileDialog(frame, "Select audio file", FileDialog.LOAD);
		dialog.setVisible(true);
		if (dialog.getFile() != null) {
			try {
				player.loadFile(new FileInputStream(new File(dialog.getDirectory(), dialog.getFile())));
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private void play() {
		player.play();
	}
	
	private void stop() {
		player.stop();
	}
}
