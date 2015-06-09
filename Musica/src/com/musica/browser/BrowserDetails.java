package com.musica.browser;

import java.io.File;

import com.musica.download.DownloadMP3;
import com.musica.ui.menuUI.DownloadManager;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.DownloadHandler;
import com.teamdev.jxbrowser.chromium.DownloadItem;
import com.teamdev.jxbrowser.chromium.events.DownloadEvent;
import com.teamdev.jxbrowser.chromium.events.DownloadListener;

public class BrowserDetails extends Browser {

	private DownloadMP3 downloadMP3;
	
	private String downloadDirectory;
	
	private DownloadManager dm;

	public BrowserDetails() {
		super.setDownloadHandler(new DownloadHandler() {
			@Override
			public boolean allowDownload(DownloadItem download) {
				download.setDestinationFile(new File(downloadDirectory + "\\" + downloadMP3.getTitle() + ".mp3"));
				dm.newDownload(downloadMP3.getTitle());
				dm.revalidate();
				download.addDownloadListener(new DownloadListener() {
					@Override
					public void onDownloadUpdated(DownloadEvent event) {
						if(event.getDownloadItem().isCompleted()) {
							downloadMP3.setDownloadActive(false);
						}
						dm.getProgressBar().setValue(event.getDownloadItem().getPercentComplete());
					}
					
				});
				return true;
			}
		});
	}

	public void setDownloadMP3(DownloadMP3 downloadMP3) {
		this.downloadMP3 = downloadMP3;
	}

	public DownloadMP3 getDownloadMP3() {
		return downloadMP3;
	}
	
	public void setDownloadDirectory(String downloadDirectory) {
		this.downloadDirectory = downloadDirectory;	
	}

	public void setDownloadManager(DownloadManager dm) {
		this.dm = dm;
		
	}
}
