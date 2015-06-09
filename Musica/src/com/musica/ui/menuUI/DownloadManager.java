package com.musica.ui.menuUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class DownloadManager extends JFrame {

	private JProgressBar pb;

	public DownloadManager() {
		super("Download Manager");
		setSize(447, 477);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
	}

	public void newDownload(String title) {
		pb = new JProgressBar();
		pb.setPreferredSize(new Dimension(400, 25));
		pb.setStringPainted(true);
		pb.setString(title);
		add(pb);
	}
	
	public JProgressBar getProgressBar() {
		return pb;
	}
}
