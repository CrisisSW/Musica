package com.musica.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.musica.browser.BrowserDetails;
import com.musica.download.DownloadMP3;
import com.musica.ui.menuUI.DownloadManager;
import com.musica.ui.menuUI.KeyboardShortcuts;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, KeyListener {

	// JComponents
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu downloadMenu = new JMenu("Download");
	private JMenuItem downloadMP3Item = new JMenuItem("Download MP3",new ImageIcon(getClass().getResource("Musical Note.png")));
	private JMenuItem downloadPlaylistItem = new JMenuItem("Download Playlist",new ImageIcon(getClass().getResource("Playlist.png")));
	
	
	private JMenu viewMenu = new JMenu("View");
	private JMenuItem downloadManagerItem = new JMenuItem("Download Manager",new ImageIcon(getClass().getResource("down_arrow.png")));
	private JMenuItem shortcutItem = new JMenuItem("Keyboard Shortcuts", new ImageIcon(getClass().getResource("keyboard.png")));



	// ArrayList
	private Set<String> playlistLinks = new LinkedHashSet<String>();

	// jxbrowser
	private final BrowserDetails browser = new BrowserDetails();
	private final BrowserView browserView = new BrowserView(browser);

	// DownloadMP3
	private final DownloadMP3 downloadMP3 = new DownloadMP3();
	
	//DownloadManager
	private DownloadManager dm = new DownloadManager();

	public GUI() {
		super("Musica - YouTube to MP3 Dowload");
		frameDetails();
		componentDetails();
		addComponents();
	}

	private void componentDetails() {
		browserView.addKeyListener(this);
		downloadMP3.setBrowser(browser);
		browser.setDownloadMP3(downloadMP3);
		browser.loadURL("https://www.youtube.com");
		browser.setDownloadManager(dm);
	
		downloadMP3Item.addActionListener(this);
		downloadPlaylistItem.addActionListener(this);
	

		// JMenu
		menuBar.add(downloadMenu);
		downloadMenu.add(downloadMP3Item);
		downloadMenu.add(downloadPlaylistItem);
		
		menuBar.add(viewMenu);
		viewMenu.add(downloadManagerItem);
		viewMenu.add(shortcutItem);
		
		shortcutItem.addActionListener(this);
		downloadManagerItem.addActionListener(this);


		
	}

	private void addComponents() {
		add(menuBar, BorderLayout.NORTH);
		add(browserView, BorderLayout.CENTER);
	}

	private void frameDetails() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(919, 634);
		setLayout(new BorderLayout());
		setResizable(false);
		setLocationRelativeTo(null);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(downloadMP3Item)
				&& browser.getURL().contains("/watch?")) {
			downloadSong();
		}

		if (event.getSource().equals(downloadPlaylistItem)
				&& browser.getURL().contains("/playlist?")) {
			downloadPlaylist();
		}
		
		if(event.getSource().equals(shortcutItem)) {
			showKeyboardShortcuts();
		}
		
		if(event.getSource().equals(downloadManagerItem)) {
			showDownloadManager();
		}
	}

	private void downloadPlaylist() {
		Document doc = null;
		try {
			doc = Jsoup.connect(browser.getURL()).get();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Elements links = doc.select("a[href]");

		for (Element e : links) {
			playlistLinks.add(e.attr("abs:href"));

		}

		for (String url : playlistLinks) {
			if (url.contains("index=")) {
				downloadMP3
						.dowloadMusic("http://youtubeinmp3.com/fetch/?api=advanced&format=JSON&video="
								+ url);
			}
		}
	}

	private void downloadSong() {
		downloadMP3
				.dowloadMusic("http://youtubeinmp3.com/fetch/?api=advanced&format=JSON&video="
						+ browser.getURL());

	}

	public BrowserDetails getBrowserDetails() {
		return browser;
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent.isShiftDown()) {
			switch (keyEvent.getKeyChar()) {
			case 'B':
				if (browser.canGoBack()) {browser.goBack();}
				break;

			case 'F':
				if (browser.canGoForward()) {browser.goForward();}
				break;

			case 'M':
				downloadSong();
				break;
				
			case 'P':
				downloadPlaylist();
				break;
				
			case 'D':
				showDownloadManager();
				break;
				
			case 'S':
				showKeyboardShortcuts();
				break;
			}
		}
	}

	private void showKeyboardShortcuts() {
		KeyboardShortcuts shortcuts = new KeyboardShortcuts();
		shortcuts.setVisible(true);
		
	}

	private void showDownloadManager() {
		dm.setVisible(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
