package com.musica;

import java.awt.HeadlessException;

import javax.swing.JFileChooser;

import com.musica.browser.BrowserDetails;
import com.musica.ui.GUI;

public class Musica {
	static BrowserDetails browser;
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		browser = gui.getBrowserDetails();
		downloadDetails();
		gui.setVisible(true);
	}

	private static void downloadDetails() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose Download Directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				browser.setDownloadDirectory(chooser.getSelectedFile().getPath());
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
