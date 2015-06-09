package com.musica.download;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musica.browser.BrowserDetails;
import com.musica.download.json.JSON;

public class DownloadMP3 {

	private BrowserDetails browser;
	private String downloadURL;
	private String title;
	private boolean isDownloadActive = false;

	public void setBrowser(BrowserDetails browser) {
		this.browser = browser;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public String getDownloadURL() {
		return downloadURL;
	}
	
	public void setDownloadActive(boolean isDownloadActive) {
		this.isDownloadActive = isDownloadActive;
	}
	public boolean getDownloadActive() {
		return isDownloadActive;
	}
	

	public void dowloadMusic(String URL) {
		while(isDownloadActive) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		isDownloadActive = true;
		
		String songInfo = null;
		try {
			songInfo = Jsoup.connect(URL).timeout(0).ignoreContentType(true).execute()
					.body();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "AYYYY");
			e1.printStackTrace();
		}
		JSON json = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.readValue(songInfo, JSON.class);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "JSON Page Not Found! Please Try Again! ");
			isDownloadActive = false;
			e.printStackTrace();
			
		}
		if (json.getTitle() == "") {
			setTitle("NO TITLE FOUND " + URL);
		} else {
			setTitle(json.getTitle());
		}
		setDownloadURL(json.getLink());

		saveFile(downloadURL);

	}

	private void saveFile(String downloadURL) {
		browser.loadURL(downloadURL);
	}
}
