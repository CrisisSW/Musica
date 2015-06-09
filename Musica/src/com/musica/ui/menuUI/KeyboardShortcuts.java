package com.musica.ui.menuUI;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class KeyboardShortcuts extends JFrame {
	
	
	public KeyboardShortcuts() {
		super("Keyboard Shortcuts");
		setLayout(new GridLayout(6, 1));
		setSize(722, 274);
		setResizable(false);
		
		addShortcuts("B - Navigate Backwards in Browser" );
		addShortcuts("F - Navigate Forward in Browser" );
		addShortcuts("M - Download MP3 of Current Video Being Viewed" );
		addShortcuts("P - Download Playlist of Current Playlist Being Viewed" );
		addShortcuts("D - Display Download Manager" );
		addShortcuts("S - Show Keyboard Shortcuts" );





	}

	private void addShortcuts(String shortcut) {
		JLabel shortcutLabel = new JLabel("Shift + " + shortcut);
		shortcutLabel.setFont(new Font("Garamond", 10, 23));
		add(shortcutLabel);
		
	}
	
}
